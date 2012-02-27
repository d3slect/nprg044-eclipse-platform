package cz.cuni.mff.d3s.nprg044.twitter.wizards;

import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class MessageWizardPage extends WizardPage {

	public static final int MAX_CHAR = 150;
	
	private TextViewer messageEditor;
	private Label characterCount;

	protected MessageWizardPage(String pageName) {
		super(pageName);
		setTitle("Post a new message");
		setDescription("Post twitte message. It should not contains more than 150characters!");
		
		setPageComplete(false);
	}

	@Override
	public void createControl(Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		composite.setLayout(layout);
		setControl(composite);
		
		messageEditor = new TextViewer(composite, SWT.BORDER | SWT.MULTI);
		messageEditor.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));		
		messageEditor.getTextWidget().addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				updateCharactersCount(messageEditor.getTextWidget().getText().length());				
			}
		});
		
		characterCount = new Label(composite, SWT.NONE);
		characterCount.setText(MAX_CHAR + " characters");				
		characterCount.setLayoutData(new GridData(SWT.RIGHT, SWT.BEGINNING, false, false));		
	}

	protected void updateCharactersCount(int length) {		
		characterCount.setText((MAX_CHAR-length) + " characters");
		
		if (validatePage()) {
			setPageComplete(true);				
		} else {
			setPageComplete(false);
		}
	}
	
	protected boolean validatePage() {
		int length = messageEditor.getTextWidget().getText().length();
		
		if (length > 0 && length <= MAX_CHAR) {
			setErrorMessage(null);
			return true;
		} else {
			setErrorMessage("Character count has to be in range (0..150>" );
			return false;
		}
	}
	
	@Override
	public boolean canFlipToNextPage() {		
		return validatePage();
	}

	public String getMessageText() {		
		return messageEditor.getDocument().get();
	}

}
