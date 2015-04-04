/**
 * 
 */
package swt.examples;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * A simple example of form layout SWT.
 *  
 * @author Michal Malohlava
 * 
 */
public class Example02 {
	
	public static void main(String[] args) {
		// create display
		Display display = new Display();
		
		// top-level widget is a shell
		final Shell shell = new Shell(display);
		shell.setText("Form layout example");
		shell.setBounds(100,100, 220, 180);
		shell.setLayout(new FormLayout());	
		
		FormData formData = null;
		
		Button cancelButton = new Button(shell, SWT.PUSH);
		cancelButton.setText("Cancel");		
		// position the "Cancel" button into the bottom right corner (5 points margin)
		// top and left do not need to be specified, control will layout to its preferred size
		formData = new FormData();
		// we define the position as 100% of parent widget height minus 5 pixels offset
		formData.bottom = new FormAttachment(100, -5);
		formData.right = new FormAttachment(100, -5);		
		cancelButton.setLayoutData(formData);				
		
		Button okButton = new Button(shell, SWT.PUSH);
		okButton.setText("Ok");
		// put the "OK" button to the left from the "Cancel" button with a 5 pixels gap
		formData = new FormData();
		formData.bottom = new FormAttachment(100, -5);
		formData.right = new FormAttachment(cancelButton, -5);		
		okButton.setLayoutData(formData);
		
		Text textArea = new Text(shell, SWT.MULTI | SWT.BORDER);
		textArea.setText("A simple text ....");
		formData = new FormData();
		formData.top = new FormAttachment(0, 5);
		formData.left = new FormAttachment(0, 5);
		formData.right = new FormAttachment(100, -5);
		formData.bottom = new FormAttachment(cancelButton, -5);
		textArea.setLayoutData(formData);		

		shell.open();

		// main event loop
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		
		// dispose widgets
		display.dispose();
	}
}
