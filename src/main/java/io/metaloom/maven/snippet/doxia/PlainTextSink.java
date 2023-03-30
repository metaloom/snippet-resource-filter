package io.metaloom.maven.snippet.doxia;

import java.io.Writer;

import org.apache.maven.doxia.sink.SinkEventAttributes;
import org.apache.maven.doxia.sink.impl.SinkUtils;

public class PlainTextSink extends AbstractNoopSink {

	/**
	 * Constructor.
	 * 
	 * @param writer
	 *            The writer for writing the result.
	 */
	public PlainTextSink(Writer writer) {
		super(writer);
	}

	@Override
	public void data(String value) {
		write(value);
	}

	@Override
	public void tableCell(String width) {
		write(width);
	}

	@Override
	public void anchor(String name) {
		write(name);
	}

	@Override
	public void link(String name) {
		write(name);
	}

	@Override
	public void text(String text) {
		write(text);
	}

	@Override
	public void rawText(String text) {
		write(text);
	}

	@Override
	public void comment(String comment) {
		write(comment);
	}

	@Override
	public void figure(SinkEventAttributes attributes) {
		write(SinkUtils.getAttributeString(attributes));
	}

	@Override
	public void data(String value, SinkEventAttributes attributes) {
		write(value);
	}

	@Override
	public void time(String datetime, SinkEventAttributes attributes) {
		write(datetime);
	}

	@Override
	public void verbatim(SinkEventAttributes attributes) {
		boolean boxed = false;

		if (attributes != null && attributes.isDefined(SinkEventAttributes.DECORATION)) {
			boxed = "boxed".equals(
				attributes.getAttribute(SinkEventAttributes.DECORATION).toString());
		}

		verbatim(boxed);
	}

	@Override
	public void anchor(String name, SinkEventAttributes attributes) {
		write(name);
	}

	@Override
	public void link(String name, SinkEventAttributes attributes) {
		write(name);
	}

	@Override
	public void text(String text, SinkEventAttributes attributes) {
		write(text);
	}

	@Override
	public void unknown(String name, Object[] requiredParams, SinkEventAttributes attributes) {
		write(name);
	}

	@Override
	public void lineBreak() {
		write("\n");
	}

	@Override
	public void lineBreak(SinkEventAttributes attributes) {
		write("\n");
	}

}
