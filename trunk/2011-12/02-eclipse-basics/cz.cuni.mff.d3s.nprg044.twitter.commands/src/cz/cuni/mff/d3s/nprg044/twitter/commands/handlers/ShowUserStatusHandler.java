package cz.cuni.mff.d3s.nprg044.twitter.commands.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class ShowUserStatusHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public ShowUserStatusHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		Shell shell = window.getShell();
		
		InputDialog inDialog = new InputDialog(shell, "Twitter status", "Type Twitter username:", "vtipy", null);
		if (inDialog.open() == Dialog.OK) {
			String username = inDialog.getValue();
			
			String status = getUserStatus(username);
		
			MessageDialog.openInformation(
					shell,
					"Twitter Status",
					status);
		}
		return null;
	}
	
	protected String getUserStatus(String username) {
		String result = null;
		
		try {
            Twitter twitter = new TwitterFactory().getInstance();
            User user = twitter.showUser(username);
            if (null != user.getStatus()) {            	
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
