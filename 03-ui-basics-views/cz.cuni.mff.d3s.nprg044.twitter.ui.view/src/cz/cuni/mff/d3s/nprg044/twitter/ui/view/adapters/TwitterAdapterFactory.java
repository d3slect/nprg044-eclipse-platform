package cz.cuni.mff.d3s.nprg044.twitter.ui.view.adapters;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;

import cz.cuni.mff.d3s.nprg044.twitter.ui.view.properties.TwitterStatusPropertySource;

import twitter4j.Status;

public class TwitterAdapterFactory implements IAdapterFactory {

	private static final Class<?>[] SUPPORTED_ADAPTERS = { IPropertySource.class };

	@Override
	public Object getAdapter(Object adaptableObject, Class adapterType) {
		if (IPropertySource.class.equals(adapterType)) {
			return new TwitterStatusPropertySource((Status) adaptableObject);
		}
		return null;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return SUPPORTED_ADAPTERS;
	}

}
