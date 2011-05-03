package cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model;

import twitter4j.Twitter;

public class FollowersNode extends AbstractUserInfoViewNode {
	
	private UserNode user; 
	
	public FollowersNode(UserNode parent, Twitter twitter) {
		this.user = parent;
		setTwitter(twitter);
	}

	@Override
	public AbstractUserInfoViewNode getParent() {
		return this.user;
	}

	@Override
	public String getTitle() {
		return "Followers";		
	}

	@Override
	public int getNumberOfChildren() {
		return -1;
	}

	@Override
	public AbstractUserInfoViewNode getChild(int index) {
		return null;
	}

	@Override
	public boolean hasChildren() {
		return true;
	}

}
