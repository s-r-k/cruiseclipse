package cruiseclipse.actions;

import org.cruiseclipse.plugin.CruiseMonitor;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.testfixture.TestProject;
import org.jmock.cglib.Mock;
import org.jmock.cglib.MockObjectTestCase;

public class CruiseActionTest extends MockObjectTestCase {
	
	public void xtestCruiseAction() throws Exception {
		TestProject project = new TestProject();
		IPackageFragment packageFragment = project.createPackage("pack1");
		@SuppressWarnings("unused") IType type = project.createType(packageFragment, "Filename.java", "public class Filename{}");
		CruiseAction action = new CruiseAction(new CruiseMonitor("file:///C:/DevTools/data/CruiseControl Status Page.htm", 10000));
		action.getCurrentBuildStatus();
	}
	
	public void testCruiseActionStatus() throws Exception {
		Mock monitorMock = new Mock(CruiseMonitor.class);
		monitorMock.expects(atLeastOnce()).method("currentStatus").will(onConsecutiveCalls(returnValue("Passed"), returnValue("Failed")));
		CruiseAction action = new CruiseAction((CruiseMonitor) monitorMock.proxy());
		System.out.println("action created");
		assertEquals("Passed", action.getCurrentBuildStatus());
		assertEquals("Failed", action.getCurrentBuildStatus());
	}
}
