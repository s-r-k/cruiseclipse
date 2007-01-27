/**
 * 
 */
package cruiseclipse.views;

import org.cruiseclipse.plugin.CruiseProject;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

final class CruiseViewerSorter extends ViewerSorter {
	@Override
	public boolean isSorterProperty(Object element, String property) {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compare(Viewer testViewer, Object e1, Object e2) {
	    return ((Comparable) e1).compareTo((CruiseProject) e2);
	}
}