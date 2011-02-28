package cz.cuni.mff.d3s.nprg044.tut1.test01.test;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import cz.mff.cuni.d3s.nprg044.tut1.test01.api.IService;

public class Activator implements BundleActivator {

	private ServiceTracker simpleServiceTracker;
	private IService simpleService;
	
	private class SimpleServiceCustomizer implements ServiceTrackerCustomizer {
		
		private BundleContext ctx;
		
		public SimpleServiceCustomizer(BundleContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public Object addingService(ServiceReference reference) {
			IService service = (IService) this.ctx.getService(reference);
			
			service.test();
			Activator.this.simpleService = service;
			
			return service;
		}

		@Override
		public void modifiedService(ServiceReference reference, Object service) {
		}

		@Override
		public void removedService(ServiceReference reference, Object service) {
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		
		// create a tracker and track the log service
		simpleServiceTracker = 
			new ServiceTracker(context, IService.class.getName(), new SimpleServiceCustomizer(context));
		simpleServiceTracker.open();
		
		// grab the service
		simpleService = (IService) simpleServiceTracker.getService();

		if(simpleService != null) {
			System.err.println("Hurray");
			simpleService.test();
		} else {
//			simpleServiceTracker.
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		if(simpleService != null)
			simpleService.test();
		
		// close the service tracker
		simpleServiceTracker.close();
		simpleServiceTracker = null;
		
		simpleService = null;
	}

}
