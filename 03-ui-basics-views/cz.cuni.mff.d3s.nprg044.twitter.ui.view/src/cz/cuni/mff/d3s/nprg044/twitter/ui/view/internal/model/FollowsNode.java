package cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model;

import java.util.ArrayList;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.TreeViewer;

import twitter4j.Twitter;

public class FollowsNode extends LazyNode {

	private static final String[] TEST_FOLLOWS = { "novinky", "idnes", "nyt" };
	private UserNode user;

	public FollowsNode(UserNode parent, Twitter twitter, TreeViewer treeViewer) {
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
		return "Follows";
	}

	@Override
	protected AbstractUserInfoViewNode[] doQueryChildren(IProgressMonitor monitor) {
		int numberOfFollows = TEST_FOLLOWS.length;
		ArrayList<UserNode> follows = new ArrayList<UserNode>(4);

		monitor.beginTask("Getting follows", numberOfFollows);

		for (String s : TEST_FOLLOWS) {
			try {
				UserNode userNode = new UserNode(s, this, getTwitter(), getTreeViewer());
				follows.add(userNode);
				monitor.worked(1);
			} catch (Exception e) {
			}
		}

		return follows.toArray(new AbstractUserInfoViewNode[follows.size()]);
	}

	@Override
	protected String getPendingMessage() {
		return "Computing follows...";
	}
}
