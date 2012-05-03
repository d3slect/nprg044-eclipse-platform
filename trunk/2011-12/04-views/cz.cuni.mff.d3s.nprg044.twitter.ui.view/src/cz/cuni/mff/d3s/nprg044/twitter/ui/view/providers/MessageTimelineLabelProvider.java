/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.view.providers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import twitter4j.Status;

/**
 * @author michal
 *
 */
public class MessageTimelineLabelProvider extends LabelProvider implements ITableLabelProvider {

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (element instanceof Status && columnIndex == 0) {
			return getImage(element);
		}
		
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Status) {
			Status status = (Status) element;
			
			switch (columnIndex) {
			case 0:
				return null;
			case 1:				
				return '@' + status.getUser().getScreenName();
			case 2:
				return status.getText();
			default:
				return null;
			}			
		}	
		
		return null;
	}
	
	@Override
	public Image getImage(Object element) {		
		return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
	}
}
