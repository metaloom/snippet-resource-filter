package io.metaloom.maven.snippet;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SnippetMacroTest {

	@Test
	public void testBasics() {
		SnippetMacro macro = new SnippetMacro();
		Map<String, String> params = new HashMap<>();
		params.put("file", "src/test/resources/base/snippet/testSnippet.txt");
		params.put("id", "firstId");
		String out = macro.execute(params, new File("."));
		assertNotNull(out);
		System.out.println(out);
	}
}
