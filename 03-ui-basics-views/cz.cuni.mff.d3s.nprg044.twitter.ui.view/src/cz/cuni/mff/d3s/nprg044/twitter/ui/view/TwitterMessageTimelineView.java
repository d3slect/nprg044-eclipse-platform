package cz.cuni.mff.d3s.nprg044.twitter.ui.view;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;

import cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model.UserNode;
import cz.cuni.mff.d3s.nprg044.twitter.ui.view.providers.MessageTimelineContentProvider;
import cz.cuni.mff.d3s.nprg044.twitter.ui.view.providers.MessageTimelineLabelProvider;

/**
 * A class implementing our view. The superclass ViewPart implements basic
 * infrastructure.
 */
public class TwitterMessageTimelineView {

	private static final String[] COLUMN_NAMES = { "#", "username", "message" };
	private static final int[] COLUMN_WIDTHS = { 70, 100, 150 };

	private static final String POPUP_MENU_ID = "cz.cuni.mff.d3s.nprg044.twitter.ui.view.popupmenu.tablemenu";
	
	private Text searchBox;
	private TableViewer viewer;
	private ProgressBar progressBar;

	/**
	 * This method creates a graphical representation of this view. Any widgets
	 * (SWT) can be used here.
	 */
	@PostConstruct
	public void createPartControl(Composite parent, ESelectionService selectionService, EMenuService menuService) {
		// grid with one column
		GridLayout layout = new GridLayout(1, true);
		parent.setLayout(layout);

		// create text control 'searchbox'
		// it allows single line and supports searching
		searchBox = new Text(parent, SWT.SINGLE | SWT.SEARCH | SWT.ICON_SEARCH);
		// modify layout: fill the available horizontal space
		searchBox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		searchBox.setText("vtipy");

		// listener notified when the search box gains focus
		searchBox.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (viewer.getInput() != searchBox) {
					// update input for the viewer
					viewer.setInput(searchBox);
				}
			}
		});

		// horizontal progress bar
		progressBar = new ProgressBar(parent, SWT.HORIZONTAL);
		progressBar.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// create a table viewer control (JFace)
		// it has a border and scroll bars
		viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		// add columns into the table
		createColumns(viewer);

		// set provider of data in the columns
		viewer.setContentProvider(new MessageTimelineContentProvider(progressBar));

		// set provider of the column labels
		viewer.setLabelProvider(new MessageTimelineLabelProvider());

		// set input of the content provider
		// viewer should generate the table based on the content of the search
		// box
		viewer.setInput(searchBox);

		// getControl() returns the underlying SWT widget
		// fill the available horizontal and vertical space
		viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

		// define style of the underlying table
		viewer.getTable().setLinesVisible(true);
		viewer.getTable().setHeaderVisible(true);

		// make selection in the table available to other controls
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection selection = (IStructuredSelection) event.getSelection();
				Object selectionForService = selection.size() == 1 ? selection.getFirstElement() : selection.toArray();
				// set the selection to the service
				// NOTE: calling Post selection to notify Properties view via adapter 
				selectionService.setPostSelection(selectionForService);
		    }
		});
		
		// we need to register the context menu first to allow contributions via
		// extension points
		menuService.registerContextMenu(viewer.getControl(), POPUP_MENU_ID);
	}

	private void createColumns(TableViewer tableViewer) {
		for (int i = 0; i < COLUMN_NAMES.length; i++) {
			TableViewerColumn tvColumn = new TableViewerColumn(tableViewer, SWT.NULL);
			TableColumn column = tvColumn.getColumn();
			column.setWidth(COLUMN_WIDTHS[i]);
			column.setText(COLUMN_NAMES[i]);

			// NOTE
			// it is also possible to register separated cell providers
			// using the method TableViewerColumn.setLabelProvider
			// see CellLabelProvider or StyledCellLabelProvider
		}
	}

	/**
	 * This part has the focus now (in the workbench). It must assign focus to
	 * one control inside it.
	 */
	@Focus
	public void setFocus() {
		this.searchBox.setFocus();
	}

	@Inject
	public void setTodo(@Optional @Named(IServiceConstants.ACTIVE_SELECTION) UserNode userNode) {
		// method may be called before the UI is created - check "viewer != null" is needed
		if (viewer != null && userNode != null) {
			viewer.setInput(userNode);
			if (!searchBox.isDisposed()) {
				searchBox.setText(userNode.getScreenName());
			}
		}		
	}

	public void cleanTimeline() {
		searchBox.setText("");
		viewer.refresh();
	}
}
