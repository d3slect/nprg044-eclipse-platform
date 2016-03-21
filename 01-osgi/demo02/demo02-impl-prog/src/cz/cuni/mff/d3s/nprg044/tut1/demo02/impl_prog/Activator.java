package cz.cuni.mff.d3s.nprg044.tut1.demo02.impl_prog;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import cz.cuni.mff.d3s.nprg044.tut1.demo02.api.IService;

public class Activator implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		IService svcImpl = new ServiceImpl();
	
		context.registerService(IService.class.getName(), svcImpl, null);		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
	
	}

}
