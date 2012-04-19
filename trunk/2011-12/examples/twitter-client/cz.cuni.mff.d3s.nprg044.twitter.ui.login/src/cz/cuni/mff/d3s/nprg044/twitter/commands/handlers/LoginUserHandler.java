/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.commands.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
import cz.cuni.mff.d3s.nprg044.twitter.ui.dialogs.LoginDialog;

/**
 * @author michal
 *
 */
public class LoginUserHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell shell = HandlerUtil.getActiveShell(event);
		
		LoginDialog loginDialog = new LoginDialog(shell, "Login the user", "Type your credentials", "", null);
		if (loginDialog.open() == LoginDialog.OK) {
			String username = loginDialog.getUsername();
			String password = loginDialog.getPassword();
			
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true);
//			cb.setOA
//			cb.setPassword(password);			
			
			Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			try {
				User user = twitter.verifyCredentials();
				System.out.println(user);
			} catch (TwitterException e) {			
				e.printStackTrace();
			}			
			
		}
		
		// must be null - see documentation
		return null;
	}
	
}
