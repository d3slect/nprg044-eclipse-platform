package cz.cuni.mff.d3s.nprg044.twitter.ui.view.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

import cz.cuni.mff.d3s.nprg044.twitter.ui.view.TwitterMessageTimelineView;

public class CleanTimelineView extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// get the active workbench part when the event occurred
		IWorkbenchPart part = HandlerUtil.getActivePart(event);
		// this handler for the delete command applies only to message timeline
		// view
		if (part instanceof TwitterMessageTimelineView) {
			((TwitterMessageTimelineView) part).cleanTimeline();
		}
		return null;
	}

}
