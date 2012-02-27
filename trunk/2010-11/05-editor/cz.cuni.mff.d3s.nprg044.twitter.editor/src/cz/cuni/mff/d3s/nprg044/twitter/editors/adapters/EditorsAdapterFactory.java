package cz.cuni.mff.d3s.nprg044.twitter.editors.adapters;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.ui.views.properties.IPropertySource;

public class EditorsAdapterFactory implements IAdapterFactory {

	private static final Class[] SUPPORTED_ADAPTERS = { IPropertySource.class };

    @Override
    public Object getAdapter(Object adaptableObject, Class adapterType) {
            if  (IPropertySource.class.equals(adapterType)) {
                    return new TypedRegionPropertySource((ITypedRegion) adaptableObject);
            }
            return null;
    }

    @Override
    public Class[] getAdapterList() {
            return SUPPORTED_ADAPTERS;
    }

}
