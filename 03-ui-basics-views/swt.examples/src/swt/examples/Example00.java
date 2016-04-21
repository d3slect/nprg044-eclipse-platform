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
 */
public class Example00 {

	public static void main(String[] args) {
		// create the top-level window
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText("SWT API Example");
		// set the window position and size
		shell.setBounds(100, 100, 340, 300);

		// group of widgets with a visible border
		Group group = new Group(shell, SWT.SHADOW_ETCHED_IN);
		group.setLayout(new GridLayout(1, true));
		group.setText("SWT Widgets");

		Label label = new Label(group, SWT.CENTER);
		label.setText("This is a Label");

		// cover the whole area of the parent group
		// label.setBounds(group.getClientArea());

		Button button = new Button(group, SWT.PUSH);
		button.setText("This is a push button");

		Button checkBox = new Button(group, SWT.CHECK);
		checkBox.setText("This is a check box");

		// widget for specifying numeric values that has small arrow buttons
		// (increment, decrement)
		final Spinner spinner = new Spinner(group, SWT.READ_ONLY);
		spinner.setMinimum(3);
		spinner.setMaximum(10);
		spinner.setIncrement(1);

		final List list = new List(group, SWT.BORDER);
		list.setItems(new String[] { "Eclipse Indigo", "Eclipse Helios", "Eclipse Galileo" });

		spinner.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (e.widget == spinner) {
					// modify the list content based on the value defined in the
					// spinner
					int desiredNum = spinner.getSelection();
					int currentNum = list.getItemCount();
					if (desiredNum > currentNum) {
						for (int i = currentNum; i < desiredNum; i++) {
							list.add("Item #" + (i + 1));
						}
					} else {
						for (int i = currentNum; i > desiredNum; i--) {
							list.remove(i - 1);
						}
					}

					// resize the list to match current elements
					list.pack();
				}
			}
		});

		shell.open();
		// shell.pack();

		// main event loop
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}

		display.dispose();
	}

}
