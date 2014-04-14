package cz.cuni.mff.d3s.nprg044.twitter.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Input dialog with OK and Cancel buttons. Note that the dialog has no
 * visual representation (no widgets) until it is told to open.
 */
public class LoginDialog extends TitleAreaDialog {
	 
	// title of the dialog
	private String title;
	
	// the message to display
	private String message;
	
	private String username = "";
	private String password = "";
	
	// the input validator
	private ILoginValidator validator;
	
	private Button okButton;
	
	// input text widget for username.
	private Text textUsername;
	
	private Text textPassword;
	
	private String errorMessage;
	
	private String initialUsername;
	
	
	/**
	 * @param initialValue The initial input value
	 */
	public LoginDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialUsername, ILoginValidator validator) {
		super(parentShell);
		
		this.title = dialogTitle;
		this.message = dialogMessage;
		
		this.initialUsername = initialUsername;
		
		this.validator = validator != null ?  validator : new DefaultEmptyUsernameValidator();
		
		// we want temporary modal dialog attached to the parent window
		setShellStyle(getShellStyle() | SWT.SHEET);
	}
	
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			username = textUsername.getText();
			password = textPassword.getText();
		}
		else {
			username = null;
			password = null;
		}
		super.buttonPressed(buttonId);
	}
	
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (title != null) {
			shell.setText(title);
		}
	}
	
	// create the OK and Cancel buttons in the dialog button bar
	protected void createButtonsForButtonBar(Composite parent) {		
		okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
		okButton.setEnabled(false);
		
		createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
		
		// do this here because setting the text will "enable" the OK button
		textUsername.setFocus();
		
		if (initialUsername != null && !initialUsername.isEmpty()) {
			textUsername.setText(initialUsername);
			textUsername.selectAll();
		}
	}
	
	// create upper part of the dialog (widgets)
	protected Control createDialogArea(Composite parent) {
		Composite parentComposite = (Composite) super.createDialogArea(parent);
		
		Composite contents = new Composite(parentComposite, SWT.NONE);
		contents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		
		setTitle(title);
		setMessage(message);
		
		// add label to the composite widget "contents"
		new Label(contents, getLabelStyle()).setText("Username");
		
		textUsername = new Text(contents, getInputTextStyle());
		textUsername.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (e.widget == textUsername) {
					okButton.setEnabled(validateInput());
				}
			}
		});
		
		new Label(contents, getLabelStyle()).setText("Password");
		
		// we want the password text to be hidden
		textPassword = new Text(contents, getInputTextStyle() | SWT.PASSWORD);
		
		// apply the same font as in dialog to all widgets inside "parentComposite"
		Dialog.applyDialogFont(parentComposite);
		
		Point defaultMargins = LayoutConstants.getMargins();
		
		// create layout factory with specific configuration and apply the layout to the widget "contents"
		GridLayoutFactory.fillDefaults().numColumns(2).margins(defaultMargins.x, defaultMargins.y).generateLayout(contents);
		
		return contents;
	}
	
	protected Button getOkButton() {
		return okButton;
	}
	
	protected Text getTextUsername() {
		return textUsername;
	}
	
	protected Text getTextPassword() {
		return textPassword;
	}
	
	protected ILoginValidator getValidator() {
		return validator;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	/**
	 * This method validates the user input.
	 * This hook method is called whenever the text changes in the input field.
	 */
	protected boolean validateInput() {
		String errorMessage = null;
		
		if (validator != null) {
			errorMessage = validator.isValid(textUsername.getText());
		}
		
		if (errorMessage == null || "".equals(errorMessage)) {
			setErrorMessage(null);
			return true;
		} 
		else {
			setErrorMessage(errorMessage);
			return false;
		}
	}
    
	protected int getInputTextStyle() {
		return SWT.SINGLE | SWT.BORDER;
	}
	
	protected int getLabelStyle() {
		return SWT.SHADOW_IN | SWT.LEFT;
	}
	
	public static class DefaultEmptyUsernameValidator implements ILoginValidator {
		@Override
		public String isValid(String username) {
			if (username == null || "".equals(username)) {
				return "User name cannot be empty!";
			}
			
			return null;
		}
	}
}
