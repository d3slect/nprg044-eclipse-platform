package cz.cuni.mff.d3s.nprg044.twitter.commands.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import cz.cuni.mff.d3s.nprg044.twitter.auth.TwitterAuthUtil;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class ShowUserStatusHandler extends AbstractHandler {

	public ShowUserStatusHandler()
	{
	}
	
	/**
	 * The command has been executed, so we can extract the needed information
	 * from the application context.
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException 
	{
		// get the active workbench window
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		
		// get window shell ("window managed by the OS window manager")
		Shell shell = window.getShell();
		
		// create simple input dialog
		InputDialog inDialog = new InputDialog(shell, "Twitter status", "Write Twitter username:", "vtipy", null);
		
		// open the window and check result
		if (inDialog.open() == Dialog.OK) {
			// get the string written by the user
			String username = inDialog.getValue();
		
			// get twitter status of the user
			String status = getUserStatus(username);
		
			// show message to the user
			MessageDialog.openInformation(shell, "Twitter Status", status);
		}
		
		return null;
	}
	
	protected String getUserStatus(String username) {
		String result = null;
		
		try {
			// initialize twitter library and get the main handle
            Twitter twitter = TwitterAuthUtil.getTwitterInstance();
            
            // get information about the user
            User user = twitter.showUser(username);
            
            if (user.getStatus() != null) {
            	// print user's status
                result = "@" + user.getScreenName() + " - " + user.getStatus().getText();
            } else {
                // the user is protected
                result = "@" + user.getScreenName() + " - <PROTECTED>";
            }            
        } catch (TwitterException te) {
            te.printStackTrace();
        }
        
        return result;
	}
}
