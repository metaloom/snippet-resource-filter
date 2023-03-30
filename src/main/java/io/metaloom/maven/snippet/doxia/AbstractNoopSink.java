package io.metaloom.maven.snippet.doxia;

import java.io.IOException;
import java.io.Writer;

import org.apache.maven.doxia.sink.SinkEventAttributes;
import org.apache.maven.doxia.sink.impl.AbstractSink;

public abstract class AbstractNoopSink extends AbstractSink {

	private final Writer out;

	public AbstractNoopSink(Writer writer) {
		this.out = writer;
	}

	@Override
	public void head() {
	}

	@Override
	public void head(SinkEventAttributes attributes) {

	}

	@Override
	public void head_() {

	}

	@Override
	public void title() {

	}

	@Override
	public void title(SinkEventAttributes attributes) {

	}

	@Override
	public void title_() {

	}

	@Override
	public void author() {

	}

	@Override
	public void author(SinkEventAttributes attributes) {

	}

	@Override
	public void author_() {

	}

	@Override
	public void date() {

	}

	@Override
	public void date(SinkEventAttributes attributes) {

	}

	@Override
	public void date_() {

	}

	@Override
	public void body() {

	}

	@Override
	public void body(SinkEventAttributes attributes) {

	}

	@Override
	public void body_() {

	}

	@Override
	public void article() {

	}

	@Override
	public void article(SinkEventAttributes attributes) {

	}

	@Override
	public void article_() {

	}

	@Override
	public void navigation() {

	}

	@Override
	public void navigation(SinkEventAttributes attributes) {

	}

	@Override
	public void navigation_() {

	}

	@Override
	public void sidebar() {

	}

	@Override
	public void sidebar(SinkEventAttributes attributes) {

	}

	@Override
	public void sidebar_() {

	}

	@Override
	public void sectionTitle() {

	}

	@Override
	public void sectionTitle_() {

	}

	@Override
	public void section1() {

	}

	@Override
	public void section1_() {

	}

	@Override
	public void sectionTitle1() {

	}

	@Override
	public void sectionTitle1_() {

	}

	@Override
	public void section2() {

	}

	@Override
	public void section2_() {

	}

	@Override
	public void sectionTitle2() {

	}

	@Override
	public void sectionTitle2_() {

	}

	@Override
	public void section3() {

	}

	@Override
	public void section3_() {

	}

	@Override
	public void sectionTitle3() {

	}

	@Override
	public void sectionTitle3_() {

	}

	@Override
	public void section4() {

	}

	@Override
	public void section4_() {

	}

	@Override
	public void sectionTitle4() {

	}

	@Override
	public void sectionTitle4_() {

	}

	@Override
	public void section5() {

	}

	@Override
	public void section5_() {

	}

	@Override
	public void sectionTitle5() {

	}

	@Override
	public void sectionTitle5_() {

	}

	@Override
	public void section6() {

	}

	@Override
	public void section6_() {

	}

	@Override
	public void sectionTitle6() {

	}

	@Override
	public void sectionTitle6_() {

	}

	@Override
	public void section(int level, SinkEventAttributes attributes) {

	}

	@Override
	public void section_(int level) {

	}

	@Override
	public void sectionTitle(int level, SinkEventAttributes attributes) {

	}

	@Override
	public void sectionTitle_(int level) {

	}

	@Override
	public void header() {

	}

	@Override
	public void header(SinkEventAttributes attributes) {

	}

	@Override
	public void header_() {

	}

	@Override
	public void content() {

	}

	@Override
	public void content(SinkEventAttributes attributes) {

	}

	@Override
	public void content_() {

	}

	@Override
	public void footer() {

	}

	@Override
	public void footer(SinkEventAttributes attributes) {

	}

	@Override
	public void footer_() {

	}

	@Override
	public void list() {

	}

	@Override
	public void list(SinkEventAttributes attributes) {

	}

	@Override
	public void list_() {

	}

	@Override
	public void listItem() {

	}

	@Override
	public void listItem(SinkEventAttributes attributes) {

	}

	@Override
	public void listItem_() {

	}

	@Override
	public void numberedList(int numbering) {

	}

	@Override
	public void numberedList(int numbering, SinkEventAttributes attributes) {

	}

	@Override
	public void numberedList_() {

	}

	@Override
	public void numberedListItem() {

	}

	@Override
	public void numberedListItem(SinkEventAttributes attributes) {

	}

	@Override
	public void numberedListItem_() {

	}

	@Override
	public void definitionList() {

	}

	@Override
	public void definitionList(SinkEventAttributes attributes) {

	}

	@Override
	public void definitionList_() {

	}

	@Override
	public void definitionListItem() {

	}

	@Override
	public void definitionListItem(SinkEventAttributes attributes) {

	}

	@Override
	public void definitionListItem_() {

	}

	@Override
	public void definition() {

	}

	@Override
	public void definition(SinkEventAttributes attributes) {

	}

	@Override
	public void definition_() {

	}

	@Override
	public void definedTerm() {

	}

	@Override
	public void definedTerm(SinkEventAttributes attributes) {

	}

	@Override
	public void definedTerm_() {

	}

	@Override
	public void figure() {

	}

	@Override
	public void figure(SinkEventAttributes attributes) {

	}

	@Override
	public void figure_() {

	}

	@Override
	public void figureCaption() {

	}

	@Override
	public void figureCaption(SinkEventAttributes attributes) {

	}

	@Override
	public void figureCaption_() {

	}

	@Override
	public void figureGraphics(String name) {

	}

	@Override
	public void figureGraphics(String src, SinkEventAttributes attributes) {

	}

	@Override
	public void table() {

	}

	@Override
	public void table(SinkEventAttributes attributes) {

	}

	@Override
	public void table_() {

	}

	@Override
	public void tableRows(int[] justification, boolean grid) {

	}

	@Override
	public void tableRows_() {

	}

	@Override
	public void tableRow() {

	}

	@Override
	public void tableRow(SinkEventAttributes attributes) {

	}

	@Override
	public void tableRow_() {

	}

	@Override
	public void tableCell() {

	}

	@Override
	public void tableCell(String width) {

	}

	@Override
	public void tableCell(SinkEventAttributes attributes) {

	}

	@Override
	public void tableCell_() {

	}

	@Override
	public void tableHeaderCell() {

	}

	@Override
	public void tableHeaderCell(String width) {

	}

	@Override
	public void tableHeaderCell(SinkEventAttributes attributes) {

	}

	@Override
	public void tableHeaderCell_() {

	}

	@Override
	public void tableCaption() {

	}

	@Override
	public void tableCaption(SinkEventAttributes attributes) {

	}

	@Override
	public void tableCaption_() {

	}

	@Override
	public void paragraph() {

	}

	@Override
	public void paragraph(SinkEventAttributes attributes) {

	}

	@Override
	public void paragraph_() {

	}

	@Override
	public void data_() {

	}

	@Override
	public void time(String datetime) {

	}

	@Override
	public void time(String datetime, SinkEventAttributes attributes) {

	}

	@Override
	public void time_() {

	}

	@Override
	public void address() {

	}

	@Override
	public void address(SinkEventAttributes attributes) {

	}

	@Override
	public void address_() {

	}

	@Override
	public void blockquote() {

	}

	@Override
	public void blockquote(SinkEventAttributes attributes) {

	}

	@Override
	public void blockquote_() {

	}

	@Override
	public void division() {

	}

	@Override
	public void division(SinkEventAttributes attributes) {

	}

	@Override
	public void division_() {

	}

	@Override
	public void verbatim(boolean boxed) {

	}

	@Override
	public void verbatim(SinkEventAttributes attributes) {

	}

	@Override
	public void verbatim_() {

	}

	@Override
	public void horizontalRule() {

	}

	@Override
	public void horizontalRule(SinkEventAttributes attributes) {

	}

	@Override
	public void pageBreak() {

	}

	@Override
	public void anchor(String name) {

	}

	@Override
	public void anchor(String name, SinkEventAttributes attributes) {

	}

	@Override
	public void anchor_() {

	}

	@Override
	public void link(String name) {

	}

	@Override
	public void link(String name, SinkEventAttributes attributes) {

	}

	@Override
	public void link_() {

	}

	@Override
	public void inline() {

	}

	@Override
	public void inline(SinkEventAttributes attributes) {

	}

	@Override
	public void inline_() {

	}

	@Override
	public void italic() {

	}

	@Override
	public void italic_() {

	}

	@Override
	public void bold() {

	}

	@Override
	public void bold_() {

	}

	@Override
	public void monospaced() {

	}

	@Override
	public void monospaced_() {

	}

	@Override
	public void lineBreakOpportunity() {

	}

	@Override
	public void lineBreakOpportunity(SinkEventAttributes attributes) {

	}

	@Override
	public void nonBreakingSpace() {

	}

	@Override
	public void flush() {
		try {
			out.flush();
		} catch (IOException e) {
			getLog().warn("Could not flush sink: " + e.getMessage(), e);
		}
	}

	@Override
	public void close() {
		try {
			out.close();
		} catch (IOException e) {
			getLog().warn("Could not close sink: " + e.getMessage(), e);
		}
	}

	/**
	 * Writes the given string + EOL.
	 *
	 * @param text
	 *            The text to write.
	 */
	public void write(String text) {
		try {
			out.write(text + EOL);
		} catch (IOException e) {
			getLog().warn("Could not write to sink: " + e.getMessage(), e);
		}
	}

}
