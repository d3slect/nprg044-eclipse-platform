package cz.cuni.mff.d3s.nprg044.twitter.wizards;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewMessageWizard extends Wizard implements INewWizard {
	
	public static final String ID = "cz.cuni.mff.d3s.nprg044.twitter.wizards.NewMessageWizard";
	
	private MessageWizardPage messagePage;

	public NewMessageWizard() {
		setWindowTitle("Post a new message");
		setNeedsProgressMonitor(true);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}
	
	@Override
	public void addPages() {
		messagePage = new MessageWizardPage("Message");
		addPage(messagePage);
	}	

	@Override
	public boolean performFinish() {		
		if (messagePage.canFlipToNextPage()) {
			System.out.println("Message posted: " + messagePage.getMessageText());			
			return true;
		} else {
			return false;
		}
	}

}
