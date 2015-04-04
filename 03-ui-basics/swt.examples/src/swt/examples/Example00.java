package swt.examples;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;

/**
 * A toy SWT example.
 * 
 * @author Michal Malohlava
 * 
 */
public class Example00 {

	public static void main(String[] args) {
		// top-level window
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout (new FillLayout());
		shell.setText("SWT API Example");
		shell.setBounds(100, 100, 340, 300);
		
		final Group group = new Group(shell, SWT.SHADOW_ETCHED_IN);
		group.setLayout (new GridLayout(1,true));		
		group.setText("SWT Widgets");
		
		final Label label = new Label(group, SWT.CENTER);
		label.setText("This is a Label");
		// To cover whole area prescribed by parent group
		// label.setBounds(group.getClientArea());
		
		final Button button = new Button(group, SWT.PUSH);
		button.setText("This is a push button");
		
		final Button checkBox = new Button(group, SWT.CHECK);
		checkBox.setText("This is a check box");
		
		final Spinner spinner = new Spinner(group, SWT.READ_ONLY);
		spinner.setMinimum(3);
		spinner.setMaximum(10);
		spinner.setIncrement(1);
		
		final List list = new List(group, SWT.BORDER);
		list.setItems(new String[] { "Eclipse Indigo", "Eclipse Helios", "Eclipse Galileo"});		
				
		
		spinner.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {				
				if (e.widget == spinner) {
					int desiredNum = spinner.getSelection();
					int currentNum = list.getItemCount();
					if (desiredNum > currentNum) {
						for (int i=currentNum; i<desiredNum; i++) {
							list.add("Item #" + (i+1));
						}
					} else {
						for (int i=currentNum; i>desiredNum; i--) {
							list.remove(i-1);
						}
					}
					
					list.pack();				
				}
			}
		});		
		
				
		shell.open();
		//shell.pack();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
