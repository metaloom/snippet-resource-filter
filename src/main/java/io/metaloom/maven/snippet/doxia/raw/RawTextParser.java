package io.metaloom.maven.snippet.doxia.raw;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.maven.doxia.macro.MacroExecutionException;
import org.apache.maven.doxia.macro.MacroRequest;
import org.apache.maven.doxia.macro.manager.MacroNotFoundException;
import org.apache.maven.doxia.markup.TextMarkup;
import org.apache.maven.doxia.parser.AbstractTextParser;
import org.apache.maven.doxia.parser.ParseException;
import org.apache.maven.doxia.parser.Parser;
import org.apache.maven.doxia.sink.Sink;
import org.codehaus.plexus.component.annotations.Component;
import org.codehaus.plexus.util.IOUtil;
import org.codehaus.plexus.util.StringUtils;

@Component(role = Parser.class, hint = "raw")
public class RawTextParser extends AbstractTextParser implements TextMarkup {

	// ----------------------------------------------------------------------
	// Markup separators
	// ----------------------------------------------------------------------

	/** RAW percent markup char: '%' */
	private static final char PERCENT = '%';

	/** RAW tab markup char: '\t' */
	private static final char TAB = '\t';

	/** An array of 85 spaces. */
	protected static final char[] SPACES;

	/** Default tab width. */
	public static final int TAB_WIDTH = 8;

	// ----------------------------------------------------------------------
	// Instance fields
	// ----------------------------------------------------------------------

	/** blockFileName. */
	private String blockFileName;

	/** blockLineNumber. */
	private int blockLineNumber;

	/** sourceContent. */
	protected String sourceContent;

	/** the sink to receive the events. */
	protected Sink sink;

	/**
	 * Map of warn messages with a String as key to describe the error type and a Set as value. Using to reduce warn messages.
	 */
	protected Map<String, Set<String>> warnMessages;

	private static final int NUMBER_OF_SPACES = 85;

	static {
		SPACES = new char[NUMBER_OF_SPACES];

		for (int i = 0; i < NUMBER_OF_SPACES; i++) {
			SPACES[i] = ' ';
		}
	}

	// ----------------------------------------------------------------------
	// Public methods
	// ----------------------------------------------------------------------

	/** {@inheritDoc} */
	@Override
	public void parse(Reader source, Sink sink)
		throws ParseException {
		parse(source, sink, "");
	}

	/** {@inheritDoc} */
	@Override
	public void parse(Reader source, Sink sink, String reference)
		throws ParseException {
		init();

		try {
			StringWriter contentWriter = new StringWriter();
			IOUtil.copy(source, contentWriter);
			sourceContent = contentWriter.toString();
		} catch (IOException e) {
			throw new RawParseException("IOException: " + e.getMessage(), e);
		}

		try {
			this.sink = sink;
			this.sink.enableLogging(getLog());

			StringReader reader = new StringReader(sourceContent);

			LineNumberReader lineReader = new LineNumberReader(reader);

			String line;
			while ((line = lineReader.readLine()) != null) {

				if (line != null && line.length() >= 1 && line.charAt(0) == PERCENT) {
					new MacroBlock(line).traverse();
				} else {
					sink.rawText(line);
				}
			}

		} catch (RawParseException | IOException ape) {
			// TODO handle column number
			throw new RawParseException(ape.getMessage(), ape, getSourceName(), getSourceLineNumber(), -1);
		} finally {
			logWarnings();

			setSecondParsing(false);
			init();
		}
	}

	/**
	 * Returns the name of the Raw source document.
	 *
	 * @return the source name.
	 */
	public String getSourceName() {
		// Use this rather than source.getName() to report errors.
		return blockFileName;
	}

	/**
	 * Returns the current line number of the Raw source document.
	 *
	 * @return the line number.
	 */
	public int getSourceLineNumber() {
		// Use this rather than source.getLineNumber() to report errors.
		return blockLineNumber;
	}

	/**
	 * Returns the character at position i of the given string.
	 *
	 * @param string
	 *            the string.
	 * @param length
	 *            length.
	 * @param i
	 *            offset.
	 * @return the character, or '\0' if i &gt; length.
	 */
	protected static char charAt(String string, int length, int i) {
		return (i < length) ? string.charAt(i) : '\0';
	}

	/**
	 * Skip spaces.
	 *
	 * @param string
	 *            string.
	 * @param length
	 *            length.
	 * @param i
	 *            offset.
	 * @return int.
	 */
	protected static int skipSpace(String string, int length, int i) {
		loop: for (; i < length; ++i) {
			switch (string.charAt(i)) {
			case SPACE:
			case TAB:
				break;
			default:
				break loop;
			}
		}
		return i;
	}

	/**
	 * Replace part of a string.
	 *
	 * @param string
	 *            the string
	 * @param oldSub
	 *            the substring to replace
	 * @param newSub
	 *            the replacement string
	 * @return String
	 */
	protected static String replaceAll(String string, String oldSub, String newSub) {
		StringBuilder replaced = new StringBuilder();
		int oldSubLength = oldSub.length();
		int begin, end;

		begin = 0;
		while ((end = string.indexOf(oldSub, begin)) >= 0) {
			if (end > begin) {
				replaced.append(string, begin, end);
			}
			replaced.append(newSub);
			begin = end + oldSubLength;
		}
		if (begin < string.length()) {
			replaced.append(string.substring(begin));
		}

		return replaced.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	protected void init() {
		super.init();

		this.sourceContent = null;
		this.sink = null;
		this.blockFileName = null;
		this.blockLineNumber = 0;
		this.warnMessages = null;
	}

	// ----------------------------------------------------------------------
	// Private methods
	// ----------------------------------------------------------------------

	// -----------------------------------------------------------------------

	/**
	 * @since 1.1.2
	 */
	private void logWarnings() {
		if (getLog().isWarnEnabled() && this.warnMessages != null && !isSecondParsing()) {
			for (Map.Entry<String, Set<String>> entry : this.warnMessages.entrySet()) {
				for (String msg : entry.getValue()) {
					getLog().warn(msg);
				}
			}

			this.warnMessages = null;
		}
	}

	// -----------------------------------------------------------------------

	/** A block of an apt source document. */
	private abstract class Block {

		/** text. */
		protected String text;

		/**
		 * Parse the block.
		 *
		 * @throws RawParseException
		 *             if something goes wrong.
		 */
		public abstract void traverse()
			throws RawParseException;

	}

	/** A MacroBlock Block. */
	private class MacroBlock
		extends Block {
		/**
		 * Constructor.
		 *
		 * @param firstLine
		 *            the first line.
		 * @throws RawParseException
		 *             RawParseException
		 */
		MacroBlock(String firstLine)
			throws RawParseException {
			text = firstLine;
		}

		/** {@inheritDoc} */
		public void traverse()
			throws RawParseException {
			if (isSecondParsing()) {
				return;
			}

			final int start = text.indexOf('{');
			final int end = text.indexOf('}');

			String s = text.substring(start + 1, end);

			s = escapeForMacro(s);

			String[] params = StringUtils.split(s, "|");

			String macroId = params[0];

			Map<String, Object> parameters = new HashMap<>();

			for (int i = 1; i < params.length; i++) {
				String[] param = StringUtils.split(params[i], "=");

				if (param.length == 1) {
					throw new RawParseException("Missing 'key=value' pair for macro parameter: " + params[i]);
				}

				String key = unescapeForMacro(param[0]);
				String value = unescapeForMacro(param[1]);

				parameters.put(key, value);
			}
			parameters.put("verbatim", "false");

			// getBasedir() does not work in multi-module builds, see DOXIA-373
			// the basedir should be injected from here, see DOXIA-224
			MacroRequest request = new MacroRequest(sourceContent, new RawTextParser(), parameters, getBasedir());
			try {
				RawTextParser.this.executeMacro(macroId, request, sink);
			} catch (MacroExecutionException e) {
				throw new RawParseException("Unable to execute macro in the RAW document", e);
			} catch (MacroNotFoundException e) {
				throw new RawParseException("Unable to find macro used in the RAW document", e);
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
}