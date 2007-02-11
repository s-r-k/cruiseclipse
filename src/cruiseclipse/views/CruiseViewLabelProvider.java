package cruiseclipse.views;

import org.cruiseclipse.plugin.HTMLProject;
import org.cruiseclipse.plugin.Project;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

class CruiseViewLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider{
	private final Image eclipseIcon = CruiseView.createImage("icons/sample.gif");
	private final Image buildPassed = CruiseView.createImage("icons/pass.jpg");
	private final Image buildFailed = CruiseView.createImage("icons/fail.jpg");

	public String getColumnText(Object obj, int index) {
		Project project = (Project) obj;
		switch (index) {
		case 0:
			return project.name();
		case 1:
			return project.currentStatus();
		case 2:
			return project.currentBuildLabel();
		case 3:
			return project.timeSinceLastBuild();
		}
		return getText(obj);
	}

	public Image getColumnImage(Object obj, int index) {
		Project project = (Project) obj;
		switch (index) {
		case 0: return eclipseIcon;
		case 1: return project.isGreen()? buildPassed: buildFailed;
		}
		return getImage(obj);
	}

	public Image getImage(Object obj) {
		return PlatformUI.getWorkbench().getSharedImages().getImage(
				ISharedImages.IMG_OBJ_ELEMENT);
	}

	public Color getForeground(Object obj, int columnIndex) {
		Project project = (Project) obj;
		if (project.hasStatusChanged()) {
			System.out.println("Status of project:" + project.name() + "has changed.");
			if (project.isGreen())
				return new Color(null, 0, 255, 0);
			return new Color(null, 255, 0, 0);
		}
		return new Color(null, 0, 0, 0);
	}

	public Color getBackground(Object element, int columnIndex) {
		return null;
	}
}