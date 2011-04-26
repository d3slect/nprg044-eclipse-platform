/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.view;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

/**
 * @author michal
 *
 */
public class TwitterMessageTimelineView extends ViewPart {
	private static final String[] COLUMN_NAMES = {"#", "username", "message"};
	private static final int[] COLUMN_WIDTHS = {30, 100, 200};
	private TableViewer viewer;
	private Text searchBox;

	/**
	 * 
	 */
	public TwitterMessageTimelineView() {	
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
				
		viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
		createColumns(viewer);
		viewer.setContentProvider(new MessageTimelineContentProvider());
		viewer.setLabelProvider(new MessageTimelineLabelProvider());
//		viewer.setSorter(new NameSorter());
		// the input for the content provider
		viewer.setInput(searchBox);		
		viewer.getControl().setLayoutData(new GridData(GridData.FILL_BOTH));

		// tuning of the underlying table		
		viewer.getTable().setLinesVisible(true);		
		viewer.getTable().setHeaderVisible(true);
		
		// make selection available to others
		getSite().setSelectionProvider(viewer);
	}
	
	private void createColumns(TableViewer tableViewer) {
		for (int i = 0; i < COLUMN_NAMES.length; i++) {
			TableViewerColumn tvColumn = new TableViewerColumn(tableViewer, SWT.NULL);
			TableColumn column = tvColumn.getColumn();
			column.setWidth(COLUMN_WIDTHS[i]);
			column.setText(COLUMN_NAMES[i]);
			// I can also here register separated cell providers via calling TableViewerColumn.setLabelProvider
			// - @see CellLabelProvider or StyledCellLabelProvider 			
		}		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
	 */
	@Override
	public void setFocus() {
		this.searchBox.setFocus();
	}
	
	

}
