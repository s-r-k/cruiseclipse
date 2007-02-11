package org.cruiseclipse.plugin;

import java.util.List;

import junit.framework.TestCase;

public class CruiseFeedReaderTest extends TestCase {
	
	private static final String TEST_RSS = "file:///home/srk/dev/cruiseclipse/test/rss";

	public void testFetchRSSFeed() throws Exception {
		CruiseControlDataSource reader = new CruiseFeedReader(TEST_RSS);
		List names = reader.getProjectNames();
		assertEquals(7, names.size());
	}

}
