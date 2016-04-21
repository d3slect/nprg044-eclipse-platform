package cz.cuni.mff.d3s.nprg044.twitter.ui.view.properties;

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;

import twitter4j.Status;

public class TwitterStatusPropertySource implements IPropertySource {
	private static final String MSG_ID = "twitter.prop.msg";
	private static final String RETWEET_COUNT_ID = "twitter.prop.retweet.count";

	private Status status;
	private IPropertyDescriptor[] propertyDescriptors;

	public TwitterStatusPropertySource(Status status) {
		this.status = status;
	}

	@Override
	public Object getEditableValue() {
		return null;
	}

	@Override
	public IPropertyDescriptor[] getPropertyDescriptors() {
		if (propertyDescriptors == null) {
			// define supported properties
			IPropertyDescriptor descMessage = new PropertyDescriptor(MSG_ID, "Message");
			IPropertyDescriptor retweetCount = new PropertyDescriptor(RETWEET_COUNT_ID, "Retweet count");
			propertyDescriptors = new IPropertyDescriptor[] { descMessage, retweetCount };
		}

		return propertyDescriptors;
	}

	@Override
	public Object getPropertyValue(Object id) {
		if (id.equals(MSG_ID)) {
			return status.getText();
		} else if (id.equals(RETWEET_COUNT_ID)) {
			return status.getRetweetCount();
		}

		return null;
	}

	@Override
	public boolean isPropertySet(Object id) {
		return false;
	}

	@Override
	public void resetPropertyValue(Object id) {
	}

	@Override
	public void setPropertyValue(Object id, Object value) {
	}

}
