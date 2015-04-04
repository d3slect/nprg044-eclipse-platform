/**
 * 
 */
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

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model.UserNode;

/**
 * @author Michal Malohlava
 *
 */
public class MessageTimelineContentProvider implements
		IStructuredContentProvider {
	
	private final static String[] EMPTY_CONTENT = new String[] {"There is no message to show..."}; 
	
	private Viewer viewer;
	private ProgressBar progressBar;
	
	private KeyListener keyListener = new KeyAdapter() {
		private String username;
		
		public void keyReleased(KeyEvent e) {
			if (e.keyCode == SWT.CR || e.keyCode == SWT.KEYPAD_CR) {
				if (e.widget instanceof Text) {
					String newUsername = ((Text) e.widget).getText();
					if (!newUsername.equals(username)) {
						username = newUsername;
						// run in UI thread
						e.display.asyncExec(new Runnable() {							
							@Override
							public void run() {
								// IMPORTANT it is required to check if the widget is not disposed
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

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.viewer = viewer;
		
		if (oldInput == newInput) 
			return;
		
		if (oldInput instanceof Control) {
			Control c = (Control) oldInput;
			if (!c.isDisposed()) {
				((Control) oldInput).removeKeyListener(keyListener);
			}
		}
		if (newInput instanceof Control) {
			Control c = (Control) newInput;
			if (!c.isDisposed()) {
				((Control) newInput).addKeyListener(keyListener);
//				username = ((Text) newInput).getText();
			}
		} 
	}

	@Override
	public Object[] getElements(Object inputElement) {
		String username = getUsername(inputElement);
		
		if (username == null || "".equals(username)) {
			return EMPTY_CONTENT;			
		}
		
		// TODO put this code into a dedicated operation and Job to demonstrate communication with UI
		Twitter twitter = new TwitterFactory().getInstance();
        try {        	
            //List<Status> statuses = twitter.getUserTimeline(username);
        	List<Status> statuses = new ArrayList<Status>();
        	if (progressBar!=null) { progressBar.setMaximum(1); progressBar.setSelection(0); }
        	User user = twitter.showUser(username);
        	if (user != null) {
        		statuses.add(twitter.showStatus(user.getStatus().getId()));
        		if (progressBar!=null) progressBar.setSelection(1);
        	}
        	
            if (!statuses.isEmpty()) {
            	return statuses.toArray();
            } else {
            	return EMPTY_CONTENT;
            }
        } catch (Exception e) {
        	return new String[] {e.getMessage()};
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
