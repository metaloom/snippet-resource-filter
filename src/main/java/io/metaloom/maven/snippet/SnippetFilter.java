package io.metaloom.maven.snippet;

import java.io.File;
import java.nio.file.Files;

import javax.inject.Inject;

import org.apache.maven.shared.filtering.MavenFilteringException;
import org.apache.maven.shared.filtering.MavenResourcesExecution;
import org.apache.maven.shared.filtering.MavenResourcesFiltering;
import org.codehaus.plexus.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.plexus.build.incremental.BuildContext;

@Component(role = MavenResourcesFiltering.class, hint = "snippetFilter")
public class SnippetFilter extends AbstractResourceFilter {

	/** RAW percent markup char: '%' */
	private static final char PERCENT = '%';

	/** Default tab width. */
	public static final int TAB_WIDTH = 8;

	private static final Logger log = LoggerFactory.getLogger(MavenResourcesFiltering.class);

	@Inject
	public SnippetFilter(BuildContext buildContext) {
		super(buildContext);
	}

	@Override
	protected void filterFile(MavenResourcesExecution mavenResourcesExecution, File source, File destinationFile) throws MavenFilteringException {
		try {
			String input;
			if (destinationFile.exists()) {
				log.debug("Applying snippet filter to {}", destinationFile);
				input = Files.readString(destinationFile.toPath());
			} else {
				log.debug("Applying snippet filter to {}", source);
				input = Files.readString(source.toPath());
			}

			String output = transform(input, mavenResourcesExecution.getMavenProject().getBasedir());
			Files.writeString(destinationFile.toPath(), output);

		} catch (Exception e) {
			throw new MavenFilteringException("Error while parsing content of" + source.getAbsolutePath(), e);
		}
	}

	private String transform(String input, File baseDir) {
		StringBuffer buffer = new StringBuffer();
		input.lines().forEach(line -> {
			if (line.length() >= 1 && line.charAt(0) == PERCENT) {
				try {
					buffer.append(new SnippetLine(line, baseDir).parse());
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				buffer.append(line + "\n");
			}

		});
		return buffer.toString();
	}

}
