package cz.cuni.mff.d3s.nprg044.twitter.ui.wizards.handlers;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;

import cz.cuni.mff.d3s.nprg044.twitter.ui.wizards.LoginWizard;

public class LoginHandler {

	@Execute
	public void execute(IWorkbench workbench, ISelectionService selectionService, @Named(IServiceConstants.ACTIVE_SHELL) Shell shell) {
		ISelection selection = selectionService.getSelection();
		IStructuredSelection structuredSelection = selection instanceof IStructuredSelection ? (IStructuredSelection) selection : StructuredSelection.EMPTY;
		
		LoginWizard loginWizard = new LoginWizard();
		loginWizard.init(workbench, structuredSelection);
	
		// launch the wizard programmatically: first create and then display
		// the WizardDialog object is responsible for presentation of the wizard
		WizardDialog dialog = new WizardDialog(shell, loginWizard);
		dialog.open();
	}

}
