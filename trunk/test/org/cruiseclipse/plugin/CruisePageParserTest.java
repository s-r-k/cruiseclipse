package org.cruiseclipse.plugin;

import java.util.List;

import cruiseclipse.preferences.PreferenceInitializer;

import junit.framework.TestCase;

public class CruisePageParserTest extends TestCase {

	private CruiseControlDataSource parser;

	@Override
	protected void setUp() throws Exception {
		parser = new CruisePageParser(PreferenceInitializer.DEFAULT_URL);
	}

	public void testCruisePageLoadsUp() {
		try {
			parser.getCurrentStatus();
		} catch (Exception e) {
			fail("Should not throw excaption");
		}
	}

	public void testGetProjectNames() {
		List<String> names = parser.getProjectNames();
		assertEquals(23, names.size());
		assertEquals("build-tools-build-components",names.get(0));
	}

	public void testGetProjectStatus() {
		assertEquals("passed", parser.getCurrentStatus("build-tools-build-components"));
	}
	
	public void testGetsLastUpdatedTime() {
		assertEquals("listing generated at 12:53", parser.getLastUpdatedTime());
	}

}
