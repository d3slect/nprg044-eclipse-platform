package cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.TreeViewer;

import twitter4j.Twitter;

public class FollowersNode extends LazyNode {
	
	private UserNode user; 
	
	public FollowersNode(UserNode parent, Twitter twitter, TreeViewer treeViewer) {
		super(treeViewer);
		
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
	protected AbstractUserInfoViewNode[] doQueryChildren(IProgressMonitor monitor) {		
		return EMPTY;
	}

	@Override
	protected String getPendingMessage() {	
		return "Computing followers...";
	}

}
