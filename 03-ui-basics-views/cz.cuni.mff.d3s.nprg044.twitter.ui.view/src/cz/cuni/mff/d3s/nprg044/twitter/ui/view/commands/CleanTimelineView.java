package cz.cuni.mff.d3s.nprg044.twitter.ui.view.commands;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.services.IServiceConstants;

import cz.cuni.mff.d3s.nprg044.twitter.ui.view.TwitterMessageTimelineView;

public class CleanTimelineView {

	@CanExecute
    public boolean canExecute(@Named(IServiceConstants.ACTIVE_PART) MPart activePart) {
		return activePart.getObject() instanceof TwitterMessageTimelineView;
    }	
	
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_PART) MPart activePart) {
		((TwitterMessageTimelineView) activePart.getObject()).cleanTimeline();
	}

}
