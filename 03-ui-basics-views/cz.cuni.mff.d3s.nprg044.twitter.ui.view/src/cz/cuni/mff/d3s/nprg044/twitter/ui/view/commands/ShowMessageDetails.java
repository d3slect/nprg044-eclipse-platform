package cz.cuni.mff.d3s.nprg044.twitter.ui.view.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

public class ShowMessageDetails extends AbstractHandler {

	// response for a particular selection in the context menu
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			// show the properties dialog
			HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView("org.eclipse.ui.views.PropertySheet");
		} catch (PartInitException e) {
			throw new ExecutionException(e.getMessage(), e);
		}
		return null;
	}

}
