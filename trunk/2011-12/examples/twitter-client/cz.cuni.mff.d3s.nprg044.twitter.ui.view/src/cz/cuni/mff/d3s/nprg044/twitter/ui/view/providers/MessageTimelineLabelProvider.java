/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.view.providers;

import java.net.URL;
import java.util.HashMap;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import twitter4j.Status;
import twitter4j.User;

/**
 * @author Michal Malohlava
 *
 */
public class MessageTimelineLabelProvider extends LabelProvider implements ITableLabelProvider {
	
	private HashMap<User, Image> imageCache = new HashMap<User, Image>();

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (element instanceof Status) {
			Status status = (Status) element;
			
			switch(columnIndex) {
			case 0:
				return getImage(status);
			default:
				return null;
			}
		}
		
		if (element instanceof String && columnIndex == 0) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
		}
		
		return null;
	}

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Status) {
			Status status = (Status) element;
			
			switch (columnIndex) {			
			case 1:				
				return '@' + status.getUser().getScreenName();
			case 2:
				return status.getText();
			default:
				return null;
			}			
		}	
		
		if (element instanceof String && columnIndex == 2) {
			return (String) element;
		}
		
		return null;
	}
		
	public Image getImage(Status status) {
		User user = status.getUser();
		if (user != null && !imageCache.containsKey(user)) {
			
			URL imageUrl = user.getProfileImageURL();
			Image image = createImageFromURL(imageUrl);
			imageCache.put(user, image);
		}
		
		return imageCache.get(user);		
	}
	
	private Image createImageFromURL(URL url) {
		ImageDescriptor imgDescriptor =  ImageDescriptor.createFromURL(url);
		ImageData imgData = imgDescriptor.getImageData().scaledTo(64, 64);
		Display display = Display.getCurrent();			
		Image image = new Image(display, imgData);
		
		return image;			
	}
	
	@Override
	public void dispose() {
		// create images has to be disposed explicitly
		for (Image image : imageCache.values()) {
			image.dispose();
		}
	
		super.dispose();
	}
}
