/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.InputDialog;
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
 * Login dialog for Twitter.
 * 
 * Based on <code>InputDialog</code>.
 * 
 * @author michal
 * 
 * @see InputDialog  
 *
 */
public class LoginDialog extends TitleAreaDialog {
    /**
     * The title of the dialog.
     */
    private String title;

    /**
     * The message to display, or <code>null</code> if none.
     */
    private String message;

    /**
     * The user name.
     */
    private String username = "";

    /**
     * The user password.
     */
    private String password = "";   

    /**
     * The input validator, or <code>null</code> if none.
     */
    private ILoginValidator validator;

    /**
     * Ok button widget.
     */
    private Button okButton;

    /**
     * Input text widget for username.
     */
    private Text textUsername;
    
    /**
     * Input text widget for password.
     */    
    private Text textPassword;
        
    /**
     * Error message string.
     */
    private String errorMessage;
    
    private String initialUsername;
    
    
    /**
     * Creates an input dialog with OK and Cancel buttons. Note that the dialog
     * will have no visual representation (no widgets) until it is told to open.
     * <p>
     * Note that the <code>open</code> method blocks for input dialogs.
     * </p>
     * 
     * @param parentShell
     *            the parent shell, or <code>null</code> to create a top-level
     *            shell
     * @param dialogTitle
     *            the dialog title, or <code>null</code> if none
     * @param dialogMessage
     *            the dialog message, or <code>null</code> if none
     * @param initialValue
     *            the initial input value, or <code>null</code> if none
     *            (equivalent to the empty string)
     * @param validator
     *            an input validator, or <code>null</code> if none
     */
    public LoginDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialUsername, ILoginValidator validator) {
        super(parentShell);
        this.title = dialogTitle;
        this.message = dialogMessage;
        
        this.initialUsername = "333";//initialUsername;
        this.validator = validator != null ?  validator : new DefaultEmptyUsernameValidator();
        
        setShellStyle(getShellStyle() | SWT.SHEET);
    }

    /*
     * (non-Javadoc) Method declared on Dialog.
     */
    protected void buttonPressed(int buttonId) {
        if (buttonId == IDialogConstants.OK_ID) {
            username = textUsername.getText();
            password = textPassword.getText();
        } else {
            username = null;
            password = null;
        }
        super.buttonPressed(buttonId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        if (title != null) {
			shell.setText(title);
		}
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    protected void createButtonsForButtonBar(Composite parent) {
        // create OK and Cancel buttons by default
        okButton = createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
        okButton.setEnabled(false);
        
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
        //do this here because setting the text will set enablement on the ok
        // button
        textUsername.setFocus();
        if (initialUsername != null && !initialUsername.isEmpty()) {
            textUsername.setText(initialUsername);
            textUsername.selectAll();
        }
    }

    /*
     * (non-Javadoc) Method declared on Dialog.
     */
    protected Control createDialogArea(Composite parent) {
        // create composite
        Composite parentComposite = (Composite) super.createDialogArea(parent);
        
        Composite contents = new Composite(parentComposite, SWT.NONE);
		contents.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		setTitle(title);
		setMessage(message);
        
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
        
        textPassword= new Text(contents, getInputTextStyle() | SWT.PASSWORD);        
        
        Dialog.applyDialogFont(parentComposite);
        
        Point defaultMargins = LayoutConstants.getMargins();
		GridLayoutFactory.fillDefaults().numColumns(2).margins(defaultMargins.x, defaultMargins.y).generateLayout(contents);
        
        return contents;
    }

    /**
     * Returns the ok button.
     * 
     * @return the ok button
     */
    protected Button getOkButton() {
        return okButton;
    }

    /**
     * Returns the username text area.
     * 
     * @return the text area
     */
    protected Text getTextUsername() {
        return textUsername;
    }
    
    /**
     * Returns the password text area.
     * 
     * @return the text area
     */    
    protected Text getTextPassword() {
        return textPassword;
    }

    /**
     * Returns the validator.
     * 
     * @return the validator
     */
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
     * Validates the input.
     * <p>
     * The default implementation of this framework method delegates the request
     * to the supplied input validator object; if it finds the input invalid,
     * the error message is displayed in the dialog's message line. This hook
     * method is called whenever the text changes in the input field.
     * </p>
     */
    protected boolean validateInput() {
        String errorMessage = null;
        if (validator != null) {
            errorMessage = validator.isValid(textUsername.getText());
        }
        // Bug 16256: important not to treat "" (blank error) the same as null
        // (no error)
        if (errorMessage == null || "".equals(errorMessage)) { 
        	setErrorMessage(null);        	
        	return true;
        } else {
        	setErrorMessage(errorMessage);        	
        	return false;
        }
    }
    
	/**
	 * Returns the style bits that should be used for the input text field.
	 * Defaults to a single line entry. Subclasses may override.
	 * 
	 * @return the integer style bits that should be used when creating the
	 *         input text
	 * 
	 * @since 3.4
	 */
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
