/**
 * 
 */
package cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.pages;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.TwitterPreferencesConstants;
import cz.cuni.mff.d3s.nprg044.twitter.ui.preferences.TwitterPreferencesPlugin;

/**
 * @author Michal Malohlava
 *
 */
public class TwitterImageServicePreferencePage extends
		FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	public TwitterImageServicePreferencePage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		// setup preference store - local plugin store
		setPreferenceStore(TwitterPreferencesPlugin.getDefault().getPreferenceStore());
		
		// set title		
		setDescription("Twitter Image services - LOCAL CONFIG");
	}

	@Override
	protected void createFieldEditors() {
		String[][] registeredServices = getRegisteredImageServices();
		addField(new RadioGroupFieldEditor(TwitterPreferencesConstants.DEFAULT_IMAGE_SERVICE, "Image service", 2, registeredServices, getFieldEditorParent(), true));		
	}
	
	private String[][] getRegisteredImageServices() {
		String[][] result = null;
		
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("cz.cuni.mff.d3s.nprg044.twitter.imageService");
		result = new String[elements.length][];
		int i = 0;
		for(IConfigurationElement element : elements) {			
			result[i] = new String[] {element.getAttribute("name") , element.getAttribute("id")};
			i++;
		}
		
		return result;		
	}
}
