package cz.cuni.mff.d3s.nprg044.twitter.ui.wizards.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import cz.cuni.mff.d3s.nprg044.twitter.ui.wizards.LoginWizard;

public class LoginHandler extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		
		LoginWizard loginWizard = new LoginWizard();
		loginWizard.init(window.getWorkbench(), selection instanceof IStructuredSelection ? (IStructuredSelection) selection : StructuredSelection.EMPTY);
	
		// launch the wizard programmatically: first create and then display
		// the WizardDialog object is responsible for presentation of the wizard
		WizardDialog dialog = new WizardDialog(window.getShell(), loginWizard);
		dialog.open();

		return null;
	}

}
