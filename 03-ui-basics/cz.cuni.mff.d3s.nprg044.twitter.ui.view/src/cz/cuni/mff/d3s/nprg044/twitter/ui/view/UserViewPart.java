/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.view;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

/**
 * @author michal
 *
 */
public class UserViewPart extends ViewPart {
	
	public static final String ID = "cz.cuni.mff.d3s.nprg044.twitter.ui.view.UserInfoView";

	private Text searchBox;
	private TreeViewer viewer;

	/**
	 * 
	 */
	public UserViewPart() {		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(1, true);
		parent.setLayout(layout);		
		searchBox = new Text(parent, SWT.SINGLE | SWT.SEARCH | SWT.ICON_SEARCH);
		searchBox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchBox.setText("vtipy");		
				
		viewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.VIRTUAL);		
		viewer.setContentProvider(new UserInfoContentProvider());
		// the input for the content provider
		viewer.setInput(searchBox);		
		viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

		// make selection in the viewer available to others
		getSite().setSelectionProvider(viewer);		
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		this.viewer.getControl().setFocus();
	}

}
