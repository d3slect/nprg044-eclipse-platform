package cz.cuni.mff.d3s.nprg044.twitter.ui.view.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.handlers.HandlerUtil;

import cz.cuni.mff.d3s.nprg044.twitter.ui.view.TwitterMessageTimelineView;

public class CleanTimelineView extends AbstractHandler{

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPart part = HandlerUtil.getActivePart(event);
		if (part instanceof TwitterMessageTimelineView) {
			((TwitterMessageTimelineView) part).cleanTimeline();
		}
		return null;
	}

}
