package cz.cuni.mff.d3s.nprg044.twitter.ui.view.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

public class ShowMessageDetails extends AbstractHandler implements IHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView("org.eclipse.ui.views.PropertySheet");
		} catch (PartInitException e) {
			throw new ExecutionException(e.getMessage(), e);
		}		
		return null;
	}

}
