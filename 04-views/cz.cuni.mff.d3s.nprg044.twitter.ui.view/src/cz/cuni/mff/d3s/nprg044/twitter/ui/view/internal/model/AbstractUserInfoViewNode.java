package cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model;

import twitter4j.Twitter;

abstract public class AbstractUserInfoViewNode {
	
	private Twitter twitter;
	
	public abstract String getTitle(); 
	
	public abstract AbstractUserInfoViewNode getParent();
			
	public abstract int getNumberOfChildren();
	
	public abstract AbstractUserInfoViewNode getChild(int index); 

	public final boolean hasChildren() {
		int count = getNumberOfChildren();
		
		if (count > 0 || count < 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public String getDecoration() {
		return null;		
	}
	
	@Override
	public String toString() {
		return getTitle();
	}

	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}

	public Twitter getTwitter() {
		return twitter;
	}
}
