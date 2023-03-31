package io.metaloom.maven.snippet;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.plexus.util.StringUtils;

public class SnippetLine {

	private String text;
	private File baseDir;

	public SnippetLine(String line, File baseDir) {
		this.text = line;
		this.baseDir = baseDir;
	}

	public String parse()
		throws Exception {

		final int start = text.indexOf('{');
		final int end = text.indexOf('}');

		String s = text.substring(start + 1, end);

		s = escapeForMacro(s);

		String[] params = StringUtils.split(s, "|");

		Map<String, String> parameters = new HashMap<>();

		for (int i = 1; i < params.length; i++) {
			String[] param = StringUtils.split(params[i], "=");

			if (param.length == 1) {
				throw new RuntimeException("Missing 'key=value' pair for macro parameter: " + params[i]);
			}

			String key = unescapeForMacro(param[0]);
			String value = unescapeForMacro(param[1]);

			parameters.put(key, value);
		}

		try {
			return new SnippetMacro().execute(parameters, baseDir);
		} catch (Exception e) {
			throw new RuntimeException("Unable to execute macro in the RAW document", e);
		}
	}

	/**
	 * escapeForMacro
	 *
	 * @param s
	 *            String
	 * @return String
	 */
	private String escapeForMacro(String s) {
		if (s == null || s.length() < 1) {
			return s;
		}

		String result = s;

		// use some outrageously out-of-place chars for text
		// (these are device control one/two in unicode)
		result = StringUtils.replace(result, "\\=", "\u0011");
		result = StringUtils.replace(result, "\\|", "\u0012");

		return result;
	}

	/**
	 * unescapeForMacro
	 *
	 * @param s
	 *            String
	 * @return String
	 */
	private String unescapeForMacro(String s) {
		if (s == null || s.length() < 1) {
			return s;
		}

		String result = s;

		result = StringUtils.replace(result, "\u0011", "=");
		result = StringUtils.replace(result, "\u0012", "|");

		return result;
	}

}
