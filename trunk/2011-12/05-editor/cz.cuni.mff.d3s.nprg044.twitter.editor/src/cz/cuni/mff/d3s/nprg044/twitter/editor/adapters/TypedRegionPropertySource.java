package cz.cuni.mff.d3s.nprg044.twitter.editor.adapters;

import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

public class TypedRegionPropertySource implements IPropertySource {
	
	private static final String TYPED_REGION_VALUE_ID = "typedregion.value";
    private static final String TYPED_REGION_OFFSET_ID = "typed.region.offset";
    private static final String TYPED_REGION_LENGTH_ID = "typed.region.length";
    
    private ITypedRegion typedRegion;  
    private IPropertyDescriptor[] propertyDescriptors;

	public TypedRegionPropertySource(ITypedRegion adaptableObject) {
		this.typedRegion = adaptableObject;
	}

	@Override
	public Object getEditableValue() {
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		if (propertyDescriptors == null) {
			IPropertyDescriptor value = new PropertyDescriptor(TYPED_REGION_VALUE_ID, "Region value");
			IPropertyDescriptor offset = new PropertyDescriptor(TYPED_REGION_OFFSET_ID, "Region offset");
			IPropertyDescriptor length = new PropertyDescriptor(TYPED_REGION_LENGTH_ID, "Region length");
			
			propertyDescriptors = new IPropertyDescriptor[] {value, offset, length};
		}
		
		return propertyDescriptors;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (TYPED_REGION_VALUE_ID.equals(id)) {
			return "N/A";
		} else if (TYPED_REGION_OFFSET_ID.equals(id)) {
			return typedRegion.getOffset();
		} else if (TYPED_REGION_LENGTH_ID.equals(id)) {
			return typedRegion.getLength();					
		}
		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
		// TODO Auto-generated method stub
		
	}

}
