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
		// top-level widget is shell
		final Shell shell = new Shell(display);
		shell.setBounds(100,100, 220, 180);
		shell.setLayout(new FormLayout());	
		
		FormData formData = null;
		
		Button cancelButton = new Button(shell, SWT.PUSH);
		cancelButton.setText("Cancel");
		formData = new FormData();
		// top, left do not need to be specified, control will layout to its prefered size
		formData.bottom = new FormAttachment(100, -5);
		formData.right = new FormAttachment(100, -5);		
		cancelButton.setLayoutData(formData);				
		
		Button okButton = new Button(shell, SWT.PUSH);
		okButton.setText("Ok");
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
