package cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.pages;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.service.prefs.BackingStoreException;

import cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.TwitterPreferencesConstants;
import cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.TwitterPreferencesPlugin;

public class TwitterAuthPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public TwitterAuthPreferencePage() {
		// we use a grid layout here
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		// define where the preferences for this page will be stored permanently
		// we use the configuration scope -> they will be stored inside "<eclipse root>/configuration"
		setPreferenceStore(new ScopedPreferenceStore(ConfigurationScope.INSTANCE, TwitterPreferencesPlugin.PLUGIN_ID));
		
		setDescription("Application Authentification Tokens - GLOBAL PROPERTY");
	}

	// create editor for each preference field/value
	@Override
	protected void createFieldEditors() {
		// we have two string values
		addField(new StringFieldEditor(TwitterPreferencesConstants.OATH_CONSUMER_KEY, "Consumer key", getFieldEditorParent()));
		addField(new StringFieldEditor(TwitterPreferencesConstants.OATH_CONSUMER_SECRET, "Consumer secret", getFieldEditorParent()));		
	}
	
	@Override
	public boolean performOk() {		
		boolean result =  super.performOk();
				
		try {
			// save all changes in preferences associated with the plugin
			ConfigurationScope.INSTANCE.getNode(TwitterPreferencesPlugin.PLUGIN_ID).flush();
		}
		catch (BackingStoreException e) {
			e.printStackTrace();			
			result = false;
		}		
		
		return result;
	}
}
