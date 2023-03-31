package io.metaloom.maven.snippet;

import java.io.File;

import org.junit.Test;

public class SnippetLineTest {

	public static final String SNIPPET = "%{snippet|id=firstId|file=src/test/resources/base/snippet/testSnippet.txt}";

	@Test
	public void testBasics() throws Exception {
		String out = new SnippetLine(SNIPPET, new File(".")).parse();
		System.out.println(out);
	}
}
