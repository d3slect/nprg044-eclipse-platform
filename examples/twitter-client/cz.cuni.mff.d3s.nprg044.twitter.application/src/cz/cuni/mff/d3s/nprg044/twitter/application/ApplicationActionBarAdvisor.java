package cz.cuni.mff.d3s.nprg044.twitter.application;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {
	
    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void fillMenuBar(IMenuManager menuBar) {    	
    	MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
		menuBar.add(helpMenu);
    }

}
