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

public class MessageTimelineLabelProvider extends LabelProvider implements ITableLabelProvider {

	// cache images for twitter users
	private HashMap<User, Image> imageCache = new HashMap<User, Image>();

	// return image (a part of the label) for the given column
	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (element instanceof Status) {
			Status status = (Status) element;

			// we want to show image only in the first column
			switch (columnIndex) {
			case 0:
				return getImage(status);
			default:
				return null;
			}
		}

		if (element instanceof String && columnIndex == 0) {
			// get some built-in warning image
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJS_WARN_TSK);
		}

		return null;
	}

	// return text part of the label for the given column
	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Status) {
			Status status = (Status) element;

			// show user name in the second column and message text (status) in
			// the third column
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

		if (element instanceof String && columnIndex == 2) {
			return (String) element;
		}

		return null;
	}

	public Image getImage(Status status) {
		User user = status.getUser();

		if (user != null && !imageCache.containsKey(user)) {
			try {
				// load image
				URL imageUrl = new URL(user.getProfileImageURL());
				Image image = createImageFromURL(imageUrl);
				imageCache.put(user, image);
			} catch (Exception e) {
			}
		}

		return imageCache.get(user);
	}

	private Image createImageFromURL(URL url) {
		ImageDescriptor imgDescriptor = ImageDescriptor.createFromURL(url);
		ImageData imgData = imgDescriptor.getImageData().scaledTo(64, 64);
		Display display = Display.getCurrent();
		Image image = new Image(display, imgData);

		return image;
	}

	@Override
	public void dispose() {
		// created images have to be disposed explicitly
		// we must free all resources
		for (Image image : imageCache.values()) {
			image.dispose();
		}

		super.dispose();
	}
}
