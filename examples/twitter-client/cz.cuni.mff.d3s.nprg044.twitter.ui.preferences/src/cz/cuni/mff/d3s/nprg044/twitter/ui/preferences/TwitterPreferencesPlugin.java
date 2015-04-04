package cz.cuni.mff.d3s.nprg044.twitter.ui.preferences;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @author Michal Malohlava
 *
 */
public class TwitterPreferencesPlugin extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "cz.cuni.mff.d3s.nprg044.twitter.ui.preferences"; //$NON-NLS-1$
	
	private static TwitterPreferencesPlugin PLUGIN;
	
	/**
	 * 
	 */
	public TwitterPreferencesPlugin() {
	}
	
	@Override
	public void start(BundleContext context) throws Exception {		
		super.start(context);				
		PLUGIN = this;
		
		initImages();
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		PLUGIN = null;
		super.stop(context);
	}
	
	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TwitterPreferencesPlugin getDefault() {
		return PLUGIN;
	}
	
	private void initImages() {
		Bundle bundle = Platform.getBundle(PLUGIN_ID);
		IPath path = new Path("icons/twitter-logo.jpg");
		URL url = FileLocator.find(bundle, path, null); 
		getImageRegistry().put(TwitterImagesConstants.TWITTER_LOGO, ImageDescriptor.createFromURL(url));		
	}
}
