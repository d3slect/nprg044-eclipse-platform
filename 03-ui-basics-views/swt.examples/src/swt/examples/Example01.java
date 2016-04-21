/**
 * 
 */
package swt.examples;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

/**
 * A simple example of using SWT.
 *  
 * @author Michal Malohlava
 */
public class Example01 {
	
	public static void main(String[] args) {
		// create display
		Display display = new Display();
		// top-level widget is a shell
		final Shell shell = new Shell(display);

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
				@Override
				public void handleEvent(Event e) {
					Button s = new Button(c, SWT.PUSH);
					s.setText("By click " + index[0]);
					index[0]++;
					Control[] children = c.getChildren();
					s.moveAbove(children[3]);
					// recompute layout of widgets containing 's'
					shell.layout(new Control[]{ s });				
				}
			});
		
		// define a global key listener for the whole display
		display.addFilter(SWT.KeyDown, new Listener() {
				@Override
				public void handleEvent(Event event) {
					if(event.character == 'a' || event.character == 'A') {
						Button s = new Button(c, SWT.PUSH);
						s.setText("By button " + index[0]);
						index[0]++;
						Control[] children = c.getChildren();
						s.moveAbove(children[3]);
						shell.layout(new Control[] { s });					
					}				
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
