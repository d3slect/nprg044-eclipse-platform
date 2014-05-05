package cz.cuni.mff.d3s.nprg044.twitter.ui.view.providers;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import cz.cuni.mff.d3s.nprg044.twitter.ui.view.internal.model.ErrorNode;

public class UserInfoLabelProvider extends LabelProvider {
	
	@Override
	public Image getImage(Object element) {
		if (element instanceof ErrorNode) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		}
		return super.getImage(element);
	}
	
	@Override
	public String getText(Object element) {
		return super.getText(element);
	}

}
