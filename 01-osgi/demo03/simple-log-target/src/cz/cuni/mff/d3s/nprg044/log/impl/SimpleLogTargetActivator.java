package cz.cuni.mff.d3s.nprg044.log.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import cz.cuni.mff.d3s.nprg044.log.api.ILogTarget;

public class SimpleLogTargetActivator implements BundleActivator {

	private ServiceRegistration<?> registration;
	
	public void start(BundleContext context) throws Exception {
		SimpleLogTargetImpl logTargetImpl = new SimpleLogTargetImpl();
		
		registration = context.registerService(ILogTarget.class.getName(), logTargetImpl, null);

	}

	public void stop(BundleContext bundleContext) throws Exception {
		registration.unregister();
	}

}
