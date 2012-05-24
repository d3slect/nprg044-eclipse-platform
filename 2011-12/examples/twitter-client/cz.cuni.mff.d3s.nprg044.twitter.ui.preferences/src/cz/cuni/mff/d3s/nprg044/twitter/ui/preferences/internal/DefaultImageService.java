/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.internal;

import java.net.URL;

import org.eclipse.jface.resource.ImageDescriptor;

import cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.ImageService;

/**
 * @author Michal Malohlava
 *
 */
public class DefaultImageService implements ImageService {

	/**
	 * 
	 */
	public DefaultImageService() {
	}

	/* (non-Javadoc)
	 * @see cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.ImageService#storeImage(org.eclipse.jface.resource.ImageDescriptor)
	 */
	@Override
	public URL storeImage(ImageDescriptor imgDescriptor) {		
		return null;
	}

}
