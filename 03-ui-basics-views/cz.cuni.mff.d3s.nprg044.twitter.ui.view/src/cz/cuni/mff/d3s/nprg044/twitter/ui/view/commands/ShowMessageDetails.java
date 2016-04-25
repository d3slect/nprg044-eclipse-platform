package cz.cuni.mff.d3s.nprg044.twitter.ui.view.commands;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

public class ShowMessageDetails {

	private static final String PROPERTY_PART_ID = "org.eclipse.ui.views.PropertySheet";
	
	@Execute
	public void execute(EPartService partService) {
		partService.showPart(PROPERTY_PART_ID, PartState.ACTIVATE);
	}
	
}
