package io.metaloom.maven.snippet;

import org.apache.maven.shared.filtering.MavenFilteringException;
import org.codehaus.plexus.PlexusTestCase;
import org.codehaus.plexus.component.repository.exception.ComponentLookupException;
import org.junit.jupiter.api.Test;

public class SnippetFilterTest extends PlexusTestCase {

	@Test
	public void testBasics() throws MavenFilteringException, ComponentLookupException {
		SnippetFilter filter = lookup(SnippetFilter.class, "snippetFilter");
		assertNotNull(filter);
	}
}
