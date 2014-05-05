package cz.cuni.mff.d3s.nprg044.twitter.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class LoginWizard extends Wizard implements INewWizard {

	public static final String ID = "cz.cuni.mff.d3s.nprg044.twitter.ui.wizards.LoginWizard";
	
	private LoginPage loginPage;

	public LoginWizard() {
		setWindowTitle("T4Eclipse - Login");
		setNeedsProgressMonitor(true);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}
	
	@Override
	public void addPages() {
		loginPage = new LoginPage();		
		addPage(loginPage);
	}

	@Override
	public boolean performFinish() {
		try {
			// perform some longer-time running action with a progress bar at the end 
			getContainer().run(true, false, new IRunnableWithProgress() {				
					@Override
					public void run(IProgressMonitor monitor) throws InvocationTargetException,	InterruptedException {
						performLogin(monitor);
					}
				});
		} 
		catch (InvocationTargetException e) {
			e.printStackTrace();			
			return false;
		} 
		catch (InterruptedException e) {			
			e.printStackTrace();			
			return false;
		}
		
		return true;		
	}
	
	private void performLogin(IProgressMonitor monitor) throws InterruptedException {
		// create progress monitor for three work units (steps) 
		monitor.beginTask("Login to Twitter...", 3);
		
		try {
			String username = loginPage.getUsername();
			String password = loginPage.getPassword();
			
			for (int i=0; i < 3; i++ ) {
				// TODO connect to Twitter
				// TODO save username and password				
				Thread.sleep(1000);
				// another step finished
				monitor.worked(1);
			}
		} 
		finally {
			monitor.done();
		}		
	}
}
