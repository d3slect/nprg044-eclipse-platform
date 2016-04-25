package cz.cuni.mff.d3s.nprg044.twitter.ui.view.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;

import cz.cuni.mff.d3s.nprg044.twitter.auth.TwitterAuthUtil;
import cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model.UserNode;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.User;

public class MessageTimelineContentProvider implements IStructuredContentProvider {

	private static final String[] EMPTY_CONTENT = new String[] { "There is no message to show..." };

	private Viewer viewer;
	private ProgressBar progressBar;

	private KeyListener keyListener = new KeyAdapter() {
		private String username;

		@Override
		public void keyReleased(KeyEvent e) {
			// watch for "Enter" keys
			if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
				// but only for text input widgets (some other widgets can be
				// there)
				if (e.widget instanceof Text) {
					String newUsername = ((Text) e.widget).getText();
					if (!newUsername.equals(username)) {
						username = newUsername;
						// run in the UI thread
						e.display.asyncExec(new Runnable() {
							@Override
							public void run() {
								// it is necessary to check that the widget is
								// not disposed
								if (!viewer.getControl().isDisposed()) {
									viewer.refresh();
								}
							}
						});
					}
				}
			}
		};
	};

	public MessageTimelineContentProvider() {
		super();
	}

	public MessageTimelineContentProvider(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	@Override
	public void dispose() {
	}

	/**
	 * Notification that a different control is now the input for this provider.
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;

		if (oldInput == newInput)
			return;

		// remove listener for the old input of a control type
		if (oldInput instanceof Control) {
			Control c = (Control) oldInput;
			if (!c.isDisposed()) {
				((Control) oldInput).removeKeyListener(keyListener);
			}
		}

		// we must now listen for keys on a different control/widget
		// register listener for the new input of a control type
		if (newInput instanceof Control) {
			Control c = (Control) newInput;
			if (!c.isDisposed()) {
				((Control) newInput).addKeyListener(keyListener);
			}
		}
	}

	// the parameter 'inputElement' is the one supplied to 'setInput'
	@Override
	public Object[] getElements(Object inputElement) {
		String username = getUsername(inputElement);
		if (username == null || username.equals("")) {
			return EMPTY_CONTENT;
		}

		Twitter twitter = TwitterAuthUtil.getTwitterInstance();

		// create a list of user statuses to be displayed in the viewer (table)
		try {
			List<Status> statuses = new ArrayList<Status>();

			if (progressBar != null) {
				// set the range (0,1) and the current position (0)
				progressBar.setMaximum(1);
				progressBar.setSelection(0);
			}

			User user = twitter.showUser(username);
			if (user != null && user.getStatus() != null) {
				statuses.add(twitter.showStatus(user.getStatus().getId()));
				// we have the user status -> update our progress bar
				if (progressBar != null)
					progressBar.setSelection(1);
			}

			if (!statuses.isEmpty()) {
				return statuses.toArray();
			} else {
				return EMPTY_CONTENT;
			}
		} catch (Exception e) {
			return new String[] { e.getMessage() };
		}
	}

	private String getUsername(Object inputElement) {
		if (inputElement instanceof Text) {
			return ((Text) inputElement).getText();
		} else if (inputElement instanceof UserNode) {
			return ((UserNode) inputElement).getScreenName();
		}
		return null;
	}

}
