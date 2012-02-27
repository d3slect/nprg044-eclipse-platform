package cz.cuni.mff.d3s.nprg044.twitter.api.test;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 * The activator class controls the plug-in life cycle
 */
public class TwitterTestActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "cz.cuni.mff.d3s.nprg044.twitter.api.test"; //$NON-NLS-1$

	// The shared instance
	private static TwitterTestActivator plugin;
	
	/**
	 * The constructor
	 */
	public TwitterTestActivator() {
		System.out.println("test activator ctor()");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		System.out.println("test activator start()");
		
		System.out.println("twitter API call: showUserStatus()");
		
		showUserStatus("mmalohlava");
		showUserStatus("srakyi");
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		
		System.out.println("test activator stop()");
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TwitterTestActivator getDefault() {
		return plugin;
	}
	
	protected void showUserStatus(String username) {
		try {
            Twitter twitter = new TwitterFactory().getInstance();
            User user = twitter.showUser(username);
            if (null != user.getStatus()) {            	
                System.out.println("@" + user.getScreenName() + " - " + user.getStatus().getText());
            } else {
                // the user is protected
                System.out.println("@" + user.getScreenName());
            }            
        } catch (TwitterException te) {
            te.printStackTrace();
        } 		
	}
}
