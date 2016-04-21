package cz.cuni.mff.d3s.nprg044.twitter.ui.view;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

import cz.cuni.mff.d3s.nprg044.twitter.ui.view.providers.UserInfoContentProvider;
import cz.cuni.mff.d3s.nprg044.twitter.ui.view.providers.UserInfoLabelProvider;

public class UserViewPart extends ViewPart {

	public static final String ID = "cz.cuni.mff.d3s.nprg044.twitter.ui.view.UserInfoView";

	private Text searchBox;
	private TreeViewer viewer;
	private ProgressBar progressBar;

	public UserViewPart() {
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayout layout = new GridLayout(1, true);
		parent.setLayout(layout);
		searchBox = new Text(parent, SWT.SINGLE | SWT.SEARCH | SWT.ICON_SEARCH);
		searchBox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchBox.setText("vtipy");

		progressBar = new ProgressBar(parent, SWT.HORIZONTAL);
		progressBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// viewer contains "virtual" data -> its content provider returns the
		// actual data in a lazy manner
		viewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.VIRTUAL);
		viewer.setContentProvider(new UserInfoContentProvider());
		viewer.setLabelProvider(new UserInfoLabelProvider());

		// the input for the content provider
		viewer.setInput(searchBox);
		viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

		// make selection in the viewer available to others
		getSite().setSelectionProvider(viewer);
	}

	@Override
	public void setFocus() {
		this.viewer.getControl().setFocus();
	}

}
