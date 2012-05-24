/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.pages;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.TwitterImagesConstants;
import cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.TwitterPreferencesPlugin;

/**
 * @author Michal Malohlava
 *
 */
public class TwitterMainPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	
	/**
	 * 
	 */
	public TwitterMainPreferencePage() {		
	}

	/**
	 * @param title
	 */
	public TwitterMainPreferencePage(String title) {
		super(title);
	}

	/**
	 * @param title
	 * @param image
	 */
	public TwitterMainPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(TwitterPreferencesPlugin.getDefault().getPreferenceStore());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createContents(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.NONE);
		final FillLayout layout = new FillLayout();
		composite.setLayout(layout);
				
		final Image img = TwitterPreferencesPlugin.getDefault().getImageRegistry().get(TwitterImagesConstants.TWITTER_LOGO);				
		final Label label = new Label(parent, SWT.NONE);
		label.setImage(img);				
		
		return composite;
	}

}
