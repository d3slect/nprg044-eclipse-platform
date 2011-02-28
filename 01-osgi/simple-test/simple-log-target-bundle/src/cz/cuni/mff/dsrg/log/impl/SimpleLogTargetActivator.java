package cz.cuni.mff.dsrg.log.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import cz.cuni.mff.dsrg.log.api.ILogTarget;

public class SimpleLogTargetActivator implements BundleActivator {
	
	private ServiceRegistration registration;

	@Override
	public void start(BundleContext context) throws Exception {
		SimpleLogTargetImpl logTargetImpl = new SimpleLogTargetImpl();
		
		registration = 
				context.registerService(
								ILogTarget.class.getName(), 
								logTargetImpl, 
								null);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		registration.unregister();
	}

}
