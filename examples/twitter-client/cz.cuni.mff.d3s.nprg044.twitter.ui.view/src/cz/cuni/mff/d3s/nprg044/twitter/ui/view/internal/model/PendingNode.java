package cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model;

public final class PendingNode extends AbstractUserInfoViewNode {
	
	private String msg;	
	private AbstractUserInfoViewNode parent;
	
	public PendingNode(String msg, AbstractUserInfoViewNode parent) {
		this.msg = msg;
		this.parent = parent;
	}	

	@Override
	public String getTitle() {		
		return msg;
	}

	@Override
	public AbstractUserInfoViewNode getParent() {		
		return parent;
	}

	@Override
	public int getNumberOfChildren() {		
		return 0;
	}

	@Override
	public AbstractUserInfoViewNode getChild(int index) {
		return null;
	}
}
