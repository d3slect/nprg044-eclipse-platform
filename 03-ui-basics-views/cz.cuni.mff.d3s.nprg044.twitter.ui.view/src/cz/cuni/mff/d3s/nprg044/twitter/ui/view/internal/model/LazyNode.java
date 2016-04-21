package cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.PlatformUI;

public abstract class LazyNode extends AbstractUserInfoViewNode {

	public static final AbstractUserInfoViewNode[] EMPTY = {};

	private AbstractUserInfoViewNode[] children;
	private TreeViewer viewer;

	protected LazyNode(TreeViewer tv) {
		this.viewer = tv;
	}

	@Override
	public final AbstractUserInfoViewNode getChild(int index) {
		if (children != null) {
			return index < children.length && index >= 0 ? children[index] : null;
		}

		// create an asynchronous job that will get data from Twitter
		Job workingJob = new Job("Getting Twitter info...") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					children = doQueryChildren(monitor);
				} catch (Exception e) {
					children = new AbstractUserInfoViewNode[] {
							new ErrorNode("Cannot get data because of " + e.getMessage(), LazyNode.this) };
				} finally {
					monitor.done();
				}

				forceViewerRefresh();

				return Status.OK_STATUS;
			}

		};

		// show progress bar
		workingJob.setUser(true);

		workingJob.schedule();

		return new PendingNode(getPendingMessage(), this);
	}

	protected void forceViewerRefresh() {
		// execute in the UI thread
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			@Override
			public void run() {
				viewer.refresh(LazyNode.this, true);
			}
		});
	}

	protected abstract AbstractUserInfoViewNode[] doQueryChildren(IProgressMonitor monitor);

	protected abstract String getPendingMessage();

	@Override
	public final int getNumberOfChildren() {
		if (children == null) {
			return 1;
		} else {
			return children.length;
		}
	}

	protected TreeViewer getTreeViewer() {
		return viewer;
	}
}
