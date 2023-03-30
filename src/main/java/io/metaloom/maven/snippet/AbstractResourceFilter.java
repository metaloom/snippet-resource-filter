package io.metaloom.maven.snippet;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.maven.model.Resource;
import org.apache.maven.shared.filtering.FilterWrapper;
import org.apache.maven.shared.filtering.FilteringUtils;
import org.apache.maven.shared.filtering.MavenFileFilter;
import org.apache.maven.shared.filtering.MavenFilteringException;
import org.apache.maven.shared.filtering.MavenResourcesExecution;
import org.apache.maven.shared.filtering.MavenResourcesFiltering;
import org.codehaus.plexus.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.plexus.build.incremental.BuildContext;

public abstract class AbstractResourceFilter implements MavenResourcesFiltering {

	private static final Logger log = LoggerFactory.getLogger(AbstractResourceFilter.class);

	private static final String[] EMPTY_STRING_ARRAY = {};

	private static final String[] DEFAULT_INCLUDES = { "**/**" };

	private final MavenFileFilter mavenFileFilter;

	private final BuildContext buildContext;

	public AbstractResourceFilter(MavenFileFilter mavenFileFilter, BuildContext buildContext) {
		this.mavenFileFilter = requireNonNull(mavenFileFilter);
		this.buildContext = requireNonNull(buildContext);
		System.out.println("SNIPPET");
	}

	@Override
	public void filterResources(MavenResourcesExecution mavenResourcesExecution) throws MavenFilteringException {
		for (Resource resource : mavenResourcesExecution.getResources()) {
			String targetPath = resource.getTargetPath();
			File outputDirectory = mavenResourcesExecution.getOutputDirectory();
			boolean outputExists = outputDirectory.exists();
			if (!outputExists && !outputDirectory.mkdirs()) {
				throw new MavenFilteringException("Cannot create resource output directory: " + outputDirectory);
			}

			boolean ignoreDelta = !outputExists
				|| buildContext.hasDelta(mavenResourcesExecution.getFileFilters())
				|| buildContext.hasDelta(getRelativeOutputDirectory(mavenResourcesExecution));
			log.debug("ignoreDelta " + ignoreDelta);

			System.out.println("TESTING " + targetPath + " to " + outputDirectory.getAbsolutePath());
			File resourceDirectory = (resource.getDirectory() == null) ? null : new File(resource.getDirectory());

			Scanner scanner = buildContext.newScanner(resourceDirectory, ignoreDelta);

			setupScanner(resource, scanner, mavenResourcesExecution.isAddDefaultExcludes());

			scanner.scan();

			List<String> includedFiles = Arrays.asList(scanner.getIncludedFiles());

			for (String name : includedFiles) {
				System.out.println();
				File source = new File(resourceDirectory, name);
				File destinationFile = getDestinationFile(outputDirectory, targetPath, name, mavenResourcesExecution);

				filterFile(mavenResourcesExecution, source, destinationFile);
			}
		}
	}

	/**
	 * Process the matching resource.
	 * 
	 * @param mavenResourcesExecution
	 * @param source
	 * @param destinationFile
	 * @throws MavenFilteringException 
	 */
	protected abstract void filterFile(MavenResourcesExecution mavenResourcesExecution, File source, File destinationFile) throws MavenFilteringException;

	private String[] setupScanner(Resource resource, Scanner scanner, boolean addDefaultExcludes) {
		String[] includes;
		if (resource.getIncludes() != null && !resource.getIncludes().isEmpty()) {
			includes = resource.getIncludes().toArray(EMPTY_STRING_ARRAY);
		} else {
			includes = DEFAULT_INCLUDES;
		}
		scanner.setIncludes(includes);

		String[] excludes = null;
		if (resource.getExcludes() != null && !resource.getExcludes().isEmpty()) {
			excludes = resource.getExcludes().toArray(EMPTY_STRING_ARRAY);
			scanner.setExcludes(excludes);
		}

		if (addDefaultExcludes) {
			scanner.addDefaultExcludes();
		}
		return includes;
	}

	@Override
	public List<String> getDefaultNonFilteredFileExtensions() {
		return Collections.emptyList();
	}

	@Override
	public boolean filteredFileExtension(String fileName, List<String> userNonFilteredFileExtensions) {
		return false;
	}

	private File getDestinationFile(
		File outputDirectory, String targetPath, String name, MavenResourcesExecution mavenResourcesExecution)
		throws MavenFilteringException {
		String destination;
		if (!mavenResourcesExecution.isFlatten()) {
			destination = name;
		} else {
			Path path = Paths.get(name);
			Path filePath = path.getFileName();
			destination = filePath.toString();
		}

		if (mavenResourcesExecution.isFilterFilenames()
			&& mavenResourcesExecution.getFilterWrappers().size() > 0) {
			destination = filterFileName(destination, mavenResourcesExecution.getFilterWrappers());
		}

		if (targetPath != null) {
			destination = targetPath + "/" + destination;
		}

		File destinationFile = new File(destination);
		if (!destinationFile.isAbsolute()) {
			destinationFile = new File(outputDirectory, destination);
		}

		if (!destinationFile.getParentFile().exists()) {
			destinationFile.getParentFile().mkdirs();
		}
		return destinationFile;
	}

	private String getRelativeOutputDirectory(MavenResourcesExecution execution) {
		String relOutDir = execution.getOutputDirectory().getAbsolutePath();

		if (execution.getMavenProject() != null && execution.getMavenProject().getBasedir() != null) {
			String basedir = execution.getMavenProject().getBasedir().getAbsolutePath();
			relOutDir = FilteringUtils.getRelativeFilePath(basedir, relOutDir);
			if (relOutDir == null) {
				relOutDir = execution.getOutputDirectory().getPath();
			} else {
				relOutDir = relOutDir.replace('\\', '/');
			}
		}

		return relOutDir;
	}

	/*
	 * Filter the name of a file using the same mechanism for filtering the content of the file.
	 */
	private String filterFileName(String name, List<FilterWrapper> wrappers) throws MavenFilteringException {

		Reader reader = new StringReader(name);
		for (FilterWrapper wrapper : wrappers) {
			reader = wrapper.getReader(reader);
		}

		try (StringWriter writer = new StringWriter()) {
			IOUtils.copy(reader, writer);
			String filteredFilename = writer.toString();

			if (log.isDebugEnabled()) {
				log.debug("renaming filename " + name + " to " + filteredFilename);
			}
			return filteredFilename;
		} catch (IOException e) {
			throw new MavenFilteringException("Failed filtering filename" + name, e);
		}
	}

}
