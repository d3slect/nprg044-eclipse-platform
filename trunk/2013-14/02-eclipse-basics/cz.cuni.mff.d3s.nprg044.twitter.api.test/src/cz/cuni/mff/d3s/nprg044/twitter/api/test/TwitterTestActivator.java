package cz.cuni.mff.d3s.nprg044.twitter.api.test;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import cz.cuni.mff.d3s.nprg044.twitter.auth.TwitterAuthUtil;


/**
 * The activator class controls the plug-in life cycle
 */
public class TwitterTestActivator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "cz.cuni.mff.d3s.nprg044.twitter.api.test"; //$NON-NLS-1$

	// The shared instance
	private static TwitterTestActivator plugin;
	

	public TwitterTestActivator() {
		System.out.println("test activator constructor");
	}
	
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		
		System.out.println("test activator start");
		
		System.out.println("twitter API call: showUserStatus()");
		
		showUserStatus("vtipy");
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		
		System.out.println("test activator stop");
	}
	
	/**
	 * Returns the shared instance
	 */
	public static TwitterTestActivator getDefault() {
		return plugin;
	}

	protected void showUserStatus(String username) {
		try {
            Twitter twitter = TwitterAuthUtil.getTwitterInstance();		
            User user = twitter.showUser(username);
            if (user.getStatus() != null) {
                System.out.println("@" + user.getScreenName() + " - " + user.getStatus().getText());
            } else {
                // the user is protected
                System.out.println("@" + user.getScreenName() + " - <PROTECTED>");
            }            
        } catch (TwitterException te) {
            te.printStackTrace();
        }		
	}

}
