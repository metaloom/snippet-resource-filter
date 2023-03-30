package io.metaloom.maven.snippet;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.parser.ParseException;
import org.apache.maven.doxia.parser.Parser;
import org.codehaus.plexus.PlexusTestCase;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.junit.Test;

import io.metaloom.maven.snippet.doxia.raw.PlainTextSink;

public class DoxiaSnippetTest extends PlexusTestCase {

	@Test
	public void testRawParser() throws MacroExecutionException, IOException, ParseException, ComponentLookupException {
		Parser parser = lookup(Parser.class, "raw");

		// SNIPPET START test_snippet
		String helloWorld = "helloWorld";
			//yes
				//works
			//well
		// SNIPPET END test_snippet

		// MarkdownHtmlParser mParser = new MarkdownHtmlParser();
		String input = """
# The Input doc
## L2
A [link](Bluar)
**Blub**
dsgs
%{snippet|id=firstId|file=src/test/resources/base/snippet/testSnippet.txt}
line
%{snippet|id=test_snippet|file=src/test/java/io/metaloom/maven/snippet/DoxiaSnippetTest.java}


The End
""";

		StringWriter writer = new StringWriter();
		PlainTextSink sink = new PlainTextSink(writer);
		try (Reader reader = new StringReader(input)) {
			parser.parse(reader, sink);
		}
		System.out.println("----------");
		System.out.println(writer.toString());
		System.out.println("----------");
	}

}
