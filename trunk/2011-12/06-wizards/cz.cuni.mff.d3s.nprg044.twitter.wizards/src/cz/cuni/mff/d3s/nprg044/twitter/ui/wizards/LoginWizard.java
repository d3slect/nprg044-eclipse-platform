/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

/**
 * @author Michal Malohlava
 *
 */
public class LoginWizard extends Wizard implements INewWizard {
	
	public static final String ID = "cz.cuni.mff.d3s.nprg044.twitter.ui.wizards.LoginWizard";
	private LoginPage loginPage;

	/**
	 * 
	 */
	public LoginWizard() {
		setWindowTitle("T4Eclipse - Login");
		setNeedsProgressMonitor(true);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {		
	}
	
	@Override
	public void addPages() {
		loginPage = new LoginPage();		
		addPage(loginPage);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		
		try {
			getContainer().run(true, false, new IRunnableWithProgress() {
				
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					performLogin(monitor);
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();			
			return false;
		} catch (InterruptedException e) {			
			e.printStackTrace();
			
			return false;
		}
		
		return true;
	}

	private void performLogin(IProgressMonitor monitor) throws InterruptedException {
		monitor.beginTask("Login to Twitter...", 3);
		
		try {
			String username = loginPage.getUsername();
			String password = loginPage.getPassword();
			for (int i=0; i < 3; i++ ) {
				// TODO put here code connecting to Twitter
				// TODO put here save of username & password				
				Thread.sleep(1000);
				monitor.worked(1);
			}
		} finally {		
			monitor.done();
		}		
	}
}
