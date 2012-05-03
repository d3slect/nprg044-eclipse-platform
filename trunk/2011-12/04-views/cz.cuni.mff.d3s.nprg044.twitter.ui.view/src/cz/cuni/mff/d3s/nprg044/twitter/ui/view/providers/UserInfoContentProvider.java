/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.view.providers;

import org.eclipse.jface.viewers.ILazyTreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Text;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model.AbstractUserInfoViewNode;
import cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model.UserNode;

/**
 * @author michal
 * 
 * TODO improve this by async retrieve of data
 *
 */
public class UserInfoContentProvider implements ILazyTreeContentProvider {
	
	private TreeViewer viewer;
	private Twitter twitter = new TwitterFactory().getInstance();

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (oldInput == newInput) {
			return;
		}
			
		this.viewer = (TreeViewer) viewer;
	}

	@Override
	public void updateElement(Object parent, int index) {
		if (parent instanceof Text) {
			handleTextControl((Text) parent, index);			
		} else if (parent instanceof AbstractUserInfoViewNode) {
			handleNode((AbstractUserInfoViewNode) parent, index);
		}
	}
	
	private void handleTextControl(Text parent, int index) {
		String username = parent.getText();
		User user = getUser(username);		
		if (user != null) {
			UserNode userNode = new UserNode(user, null, twitter, viewer);			
			viewer.replace(parent, index, userNode);
			viewer.setChildCount(userNode, UserNode.CHILDREN_COUNT);
		} else {
			String msg = "cannot retrieve data" ;
			viewer.replace(parent, index, msg);
			viewer.setHasChildren(msg, false);
		}
	}

	private void handleNode(AbstractUserInfoViewNode parent, int index) {
		AbstractUserInfoViewNode node = parent.getChild(index);
		if (node != null) {
			viewer.replace(parent, index, node);
			int numOfChildren = node.getNumberOfChildren();
			viewer.setChildCount(node, numOfChildren);							
		}				
	}

	@Override
	public void updateChildCount(Object element, int currentChildCount) {
		if (element instanceof Text) {
			this.viewer.setChildCount(element, 1);			
		} else if (element instanceof AbstractUserInfoViewNode) {
			AbstractUserInfoViewNode node = (AbstractUserInfoViewNode) element;
			int numOfChildren = node.getNumberOfChildren();
			viewer.setChildCount(node, numOfChildren);			
		} 
	}

	@Override
	public Object getParent(Object element) {		
		if (element instanceof AbstractUserInfoViewNode) {
			return ((AbstractUserInfoViewNode) element).getParent();
		}
		
		return null;
	}
	
	private User getUser(String username) {
		Twitter twitter = new TwitterFactory().getInstance();
		try {
			return twitter.showUser(username);
		} catch (TwitterException e) {
			return null;
		}
	}	
}
