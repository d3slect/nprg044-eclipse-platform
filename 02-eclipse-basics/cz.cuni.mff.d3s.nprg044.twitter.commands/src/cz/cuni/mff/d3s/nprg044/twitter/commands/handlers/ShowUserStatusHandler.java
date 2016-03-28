package cz.cuni.mff.d3s.nprg044.twitter.commands.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import cz.cuni.mff.d3s.nprg044.twitter.auth.TwitterAuthUtil;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class ShowUserStatusHandler {

	@Execute
	public void execute(Shell shell) {
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
