/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.wizards;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author Michal Malohlava
 *
 */
public class LoginPage extends WizardPage {
	
	/**
     * The title of the dialog.
     */
    private String title;
    
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
    
    private String initialUsername = "";
    private String initialPassword = "";
    private String username;
    private String password;


	protected LoginPage() {		
		super("Login");
		
		setDescription("Type your credentials");
		setPageComplete(false);
	}
	
	public LoginPage(String initialUsername, String initialPassword) {
		this(); 
		
		this.initialUsername = initialUsername;
		this.initialPassword = initialPassword;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {		
		        
        final Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		setControl(composite);
		
        new Label(composite, getLabelStyle()).setText("Username");        
                
        textUsername = new Text(composite, getInputTextStyle());
        textUsername.setText(initialUsername);
        textUsername.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
            	if (e.widget == textUsername) {
            		setPageComplete(validatePage());
            	}
            }
        });
        
        new Label(composite, getLabelStyle()).setText("Password");
        
        textPassword= new Text(composite, getInputTextStyle() | SWT.PASSWORD);
        textPassword.setText(initialPassword);
        textPassword.addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
            	if (e.widget == textPassword) {
            		setPageComplete(validatePage());
            	}
            }
        });
        
        
        Dialog.applyDialogFont(composite);
        
        Point defaultMargins = LayoutConstants.getMargins();
		GridLayoutFactory.fillDefaults().numColumns(2).margins(defaultMargins.x, defaultMargins.y).generateLayout(composite);		
	}
	
	protected int getInputTextStyle() {
		return SWT.SINGLE | SWT.BORDER;
	}
	
    protected int getLabelStyle() {
		return SWT.SHADOW_IN | SWT.LEFT;
	}
    
    protected boolean validatePage() {
    	username = textUsername.getText();
    	password = textPassword.getText();
    	
		if (username != null && password != null && !"".equals(username) && !"".equals(password)) {
			setErrorMessage(null);
			return true;
		} else {
			setErrorMessage("Username and password cannot be empty" );
			return false;
		}
    	    	
    }

	public String getUsername() {		
		return username;
	}
	
	public String getPassword() {
		return password;	
	}

}
