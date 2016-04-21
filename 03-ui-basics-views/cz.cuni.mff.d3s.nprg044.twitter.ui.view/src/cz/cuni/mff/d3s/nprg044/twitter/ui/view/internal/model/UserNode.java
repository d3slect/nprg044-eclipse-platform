/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model;

import org.eclipse.jface.viewers.TreeViewer;

import twitter4j.Twitter;
import twitter4j.User;

public class UserNode extends AbstractUserInfoViewNode {
	private User user;
	private AbstractUserInfoViewNode parent;
	private FollowersNode followersNode;
	private FollowsNode followsNode;

	private String username;

	public static final int CHILDREN_COUNT = 2;

	public UserNode(String username, AbstractUserInfoViewNode parent, Twitter twitter, TreeViewer treeViewer) {
		this((User) null, parent, twitter, treeViewer);

		this.username = username;
	}

	public UserNode(User u, AbstractUserInfoViewNode parent, Twitter twitter, TreeViewer treeViewer) {
		this.user = u;
		this.parent = parent;

		setTwitter(twitter);

		this.followersNode = new FollowersNode(this, twitter, treeViewer);
		this.followsNode = new FollowsNode(this, twitter, treeViewer);
	}

	public User getUser() {
		return user;
	}

	public FollowersNode getFollowersNode() {
		return this.followersNode;
	}

	public FollowsNode getFollowsNode() {
		return this.followsNode;
	}

	public String getScreenName() {
		return user == null ? username : user.getScreenName();
	}

	@Override
	public String getTitle() {
		return getScreenName();
	}

	@Override
	public AbstractUserInfoViewNode getParent() {
		return parent;
	}

	@Override
	public int getNumberOfChildren() {
		return CHILDREN_COUNT;
	}

	@Override
	public AbstractUserInfoViewNode getChild(int index) {
		switch (index) {
		case 0:
			return followersNode;
		case 1:
			return followsNode;
		default:
			return null;
		}
	}
}
