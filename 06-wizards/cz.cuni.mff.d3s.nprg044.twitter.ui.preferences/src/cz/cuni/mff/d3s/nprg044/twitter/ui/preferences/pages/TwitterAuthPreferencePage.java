/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.pages;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;
import org.osgi.service.prefs.BackingStoreException;

import cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.TwitterPreferencesConstants;
import cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.TwitterPreferencesPlugin;

/**
 * @author Michal Malohlava
 *
 */
public class TwitterAuthPreferencePage extends FieldEditorPreferencePage implements
		IWorkbenchPreferencePage {
	
	public TwitterAuthPreferencePage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(new ScopedPreferenceStore(ConfigurationScope.INSTANCE, TwitterPreferencesPlugin.PLUGIN_ID));
		setDescription("Application Authentification Tokens - GLOBAL PROPERTY");
	}

	@Override
	protected void createFieldEditors() {
		addField(new StringFieldEditor(TwitterPreferencesConstants.OATH_CONSUMER_KEY, "Consumer key", getFieldEditorParent()));
		addField(new StringFieldEditor(TwitterPreferencesConstants.OATH_CONSUMER_SECRET, "Consumer secret", getFieldEditorParent()));		
	}
	
	@Override
	public boolean performOk() {		
		boolean result =  super.performOk();
		
		
		try {
			ConfigurationScope.INSTANCE.getNode(TwitterPreferencesPlugin.PLUGIN_ID).flush();
		} catch (BackingStoreException e) {
			e.printStackTrace();
			
			result = false;
		}		
		
		return result;
	}
}
