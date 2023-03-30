package io.metaloom.maven.snippet;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.maven.doxia.parser.Parser;
import org.apache.maven.shared.filtering.MavenFileFilter;
import org.apache.maven.shared.filtering.MavenFilteringException;
import org.apache.maven.shared.filtering.MavenResourcesExecution;
import org.apache.maven.shared.filtering.MavenResourcesFiltering;
import org.codehaus.plexus.component.annotations.Requirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.plexus.build.incremental.BuildContext;

import io.metaloom.maven.snippet.doxia.raw.PlainTextSink;

/**
 * @plexus.component role="org.apache.maven.shared.filtering.MavenResourcesFiltering" role-hint="snippetFilter"
 */
@Singleton
@Named("snippetFilter")
public class SnippetFilter extends AbstractResourceFilter {

	@Requirement
	private Parser parser;

	private static final Logger log = LoggerFactory.getLogger(MavenResourcesFiltering.class);

	@Inject
	public SnippetFilter(MavenFileFilter mavenFileFilter, BuildContext buildContext, @Named("raw") Parser parser) {
		super(mavenFileFilter, buildContext);
		this.parser = parser;
	}

	@Override
	protected void filterFile(MavenResourcesExecution mavenResourcesExecution, File source, File destinationFile) throws MavenFilteringException {
		if (parser != null) {
			System.out.println("YEAH");
		} else {
			System.out.println("Boohoo");
		}

		try {
			// MarkdownHtmlParser mParser = new MarkdownHtmlParser();
			String input = Files.readString(source.toPath());
			// String input = """
			// # The Input doc
			// ## L2
			// A [link](Bluar)
			// **Blub**
			//
			// %{snippet|id=firstId|file=src/test/resources/base/snippet/testSnippet.txt}
			//
			// The End
			//
			// """;

			StringWriter writer = new StringWriter();
			PlainTextSink sink = new PlainTextSink(writer);
			try (Reader reader = new StringReader(input)) {
				parser.parse(reader, sink);
			}
			System.out.println("----------");
			System.out.println(writer.toString());
			System.out.println("----------");

			log.info("Filtering " + source.getAbsolutePath());
			log.info("Filtering " + destinationFile.getAbsolutePath());
			File baseDir = mavenResourcesExecution.getMavenProject().getBasedir();
			log.info("BaseDir:" + baseDir.getAbsolutePath());
			log.info("PP " + mavenResourcesExecution.getOutputDirectory().getAbsolutePath());
		} catch (Exception e) {
			throw new MavenFilteringException("Error while parsing content of" + source.getAbsolutePath(), e);
		}
	}

}
