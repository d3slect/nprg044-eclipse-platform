/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model;

import twitter4j.Twitter;
import twitter4j.User;

/**
 * @author michal
 * 
 */
public class UserNode extends AbstractUserInfoViewNode {
	private User user;
	private AbstractUserInfoViewNode parent;
	private FollowersNode followersNode;
	private FollowsNode followsNode;

	public static final int CHILDREN_COUNT = 2;

	public UserNode(User u, Twitter twitter) {
		this(u, null, twitter);
	}

	public UserNode(User u, AbstractUserInfoViewNode parent, Twitter twitter) {
		this.user = u;
		this.parent = parent;
		setTwitter(twitter);

		this.followersNode = new FollowersNode(this, twitter);		
		this.followsNode = new FollowsNode(this, twitter);
		
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

	@Override
	public String getTitle() {
		return user.getScreenName();
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
