package cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model;

import java.util.ArrayList;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class FollowsNode extends AbstractUserInfoViewNode {

	private static final String[] FOLLOWS = {"mmalohlava", "srakyi", "matushiq" };
	private UserNode user;

	private ArrayList<AbstractUserInfoViewNode> follows = new ArrayList<AbstractUserInfoViewNode>(
			4);
	private long cursor = -1;

	public FollowsNode(UserNode parent, Twitter twitter) {
		this.user = parent;
		setTwitter(twitter);
	}

	@Override
	public AbstractUserInfoViewNode getParent() {
		return this.user;
	}

	@Override
	public String getTitle() {
		return "Follows";
	}

	@Override
	public int getNumberOfChildren() {
		return -1;
	}

	@Override
	public AbstractUserInfoViewNode getChild(int index) {
		if (follows.isEmpty()) {
			for (String s : FOLLOWS) {
				User user;
				try {
					user = getTwitter().showUser(s);
				} catch (TwitterException e) {
					user = null;
				}
				
				if (user != null) {
					UserNode node = new UserNode(user, this, getTwitter());
					follows.add(node);
				} else {
					ErrorNode node = new ErrorNode(
							"Cannot fetch information for user ID = " + s,
							this);
					follows.add(node);
				}
			}			
		}
		return index < 3 ? follows.get(index) : null;
	}

	@Override
	public boolean hasChildren() {
		return true;
	}
}
