/**
 * 
 */
package swt.examples;

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.*;

/**
 * @author michal
 * 
 */
public class Example01 {
	
	public static void main(String[] args) {
		// create display
		Display display = new Display();
		// top-level widget is shell
		final Shell shell = new Shell(display);

		// create a GridLayout
		shell.setLayout(new GridLayout());
		final Composite c = new Composite(shell, SWT.NONE);
		GridLayout layout = new GridLayout(3, true);
		c.setLayout(layout);

		for (int i = 0; i < 5; i++) {
			Button b = new Button(c, SWT.PUSH | SWT.FLAT);
			b.setText("Button " + i);
		}

		Button b = new Button(shell, SWT.PUSH);
		b.setText("add a new button at row 2 column 1");
		final int[] index = new int[1];
		b.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				Button s = new Button(c, SWT.PUSH);
				s.setText("Special " + index[0]);
				index[0]++;
				Control[] children = c.getChildren();
				s.moveAbove(children[3]);
				shell.layout(new Control[] { s });
			}
		});

		shell.open();
		shell.pack();
		// main event loop
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		// dispose widgets
		display.dispose();
	}
}
