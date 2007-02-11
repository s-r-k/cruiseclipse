package org.cruiseclipse.plugin;

import com.sun.syndication.feed.synd.SyndEntryImpl;

import junit.framework.TestCase;

public class RSSProjectTest extends TestCase {

	public void testName() {
		SyndEntryImpl syndEntryImpl = new SyndEntryImpl();
		syndEntryImpl.setTitle("project-name FAILED 02/05/2007 05:25:58");
		RSSProject project = new RSSProject(syndEntryImpl);
		assertEquals("project-name", project.name());
	}

	public void testCurrentStatus() {
		fail("Not yet implemented");
	}

	public void testCompareTo() {
		fail("Not yet implemented");
	}

}
