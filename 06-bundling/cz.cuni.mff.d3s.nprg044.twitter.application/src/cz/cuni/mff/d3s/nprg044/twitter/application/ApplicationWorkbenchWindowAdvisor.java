package cz.cuni.mff.d3s.nprg044.twitter.application;

import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;


public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }
    
    // define initial attributes of the window (size, what components are displayed)
    public void preWindowOpen() {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(600, 500));
        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);        
    }
    
    @Override
    public void openIntro() {    	
    	super.openIntro();
    }
    
    @Override
    public void postWindowRestore() throws WorkbenchException {    	
    	super.postWindowRestore();
    	
    	cleanUpEditorArea();
    }
}
