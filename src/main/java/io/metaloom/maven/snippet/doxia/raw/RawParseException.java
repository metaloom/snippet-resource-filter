package io.metaloom.maven.snippet.doxia.raw;

import org.apache.maven.doxia.parser.ParseException;

public class RawParseException extends ParseException {

	private static final long serialVersionUID = 2143085093524049075L;

	/**
	 * Construct a new RawParseException with the specified detail message.
	 *
	 * @param message
	 *            The detailed message. This can later be retrieved by the <code>Throwable.getMessage()</code> method.
	 */
	public RawParseException(String message) {
		super(message);
	}

	/**
	 * Construct a new RawParseException with the specified detail message and cause.
	 *
	 * @param message
	 *            The detailed message. This can later be retrieved by the <code>Throwable.getMessage()</code> method.
	 * @param e
	 *            the cause. This can be retrieved later by the <code>Throwable.getCause()</code> method. (A null value is permitted, and indicates that the
	 *            cause is nonexistent or unknown.)
	 */
	public RawParseException(String message, Exception e) {
		super(message, e);
	}

	/**
	 * Construct a new RawParseException with the specified cause, detail message, filename, line number and column number.
	 *
	 * @param message
	 *            The detailed message. This can later be retrieved by the <code>Throwable.getMessage()</code> method.
	 * @param e
	 *            the cause. This can be retrieved later by the <code>Throwable.getCause()</code> method. (A null value is permitted, and indicates that the
	 *            cause is nonexistent or unknown.)
	 * @param name
	 *            Name of a file that couldn't be parsed. This can later be retrieved by the getFileName() method.
	 * @param line
	 *            The line number where the parsing failed. This can later be retrieved by the getLineNumber() method.
	 * @param column
	 *            The column number where the parsing failed. This can later be retrieved by the getColumnNumber() method.
	 */
	public RawParseException(String message, Exception e, String name, int line, int column) {
		super(e, message, name, line, column);
	}

	/**
	 * Construct a new RawParseException with the specified detail message and cause.
	 *
	 * @param message
	 *            The detailed message. This can later be retrieved by the <code>Throwable.getMessage()</code> method.
	 * @param e
	 *            the cause. This can be retrieved later by the <code>Throwable.getCause()</code> method. (A null value is permitted, and indicates that the
	 *            cause is nonexistent or unknown.)
	 * @param line
	 *            The line number where the parsing failed. This can later be retrieved by the getLineNumber() method.
	 * @param column
	 *            The column number where the parsing failed. This can later be retrieved by the getColumnNumber() method.
	 */
	public RawParseException(String message, Exception e, int line, int column) {
		super(message, e, line, column);
	}

}
