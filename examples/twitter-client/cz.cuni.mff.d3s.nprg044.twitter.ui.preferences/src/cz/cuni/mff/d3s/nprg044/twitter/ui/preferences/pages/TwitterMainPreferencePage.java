package cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.pages;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.TwitterImagesConstants;
import cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.TwitterPreferencesPlugin;

public class TwitterMainPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	public TwitterMainPreferencePage() {		
	}

	public TwitterMainPreferencePage(String title) {
		super(title);		
	}

	public TwitterMainPreferencePage(String title, ImageDescriptor image) {
		super(title, image);		
	}

	@Override
	public void init(IWorkbench workbench) {
		// define where the preferences for this page will be stored permanently
		setPreferenceStore(TwitterPreferencesPlugin.getDefault().getPreferenceStore());
	}

	// create body of the preference page
	@Override
	protected Control createContents(Composite parent) {
		// just the logo image
		
		final Composite composite = new Composite(parent, SWT.NONE);
		final FillLayout layout = new FillLayout();
		composite.setLayout(layout);
				
		final Image img = TwitterPreferencesPlugin.getDefault().getImageRegistry().get(TwitterImagesConstants.TWITTER_LOGO);				
		final Label label = new Label(parent, SWT.NONE);
		label.setImage(img);				
		
		return composite;
	}

}
