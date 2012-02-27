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
			
			System.err.println("test01-test.SimpleServiceCustomizer:IService added..");
			service.test();
			Activator.this.simpleService = service;
			
			return service;
		}

		@Override
		public void modifiedService(ServiceReference reference, Object service) {
		}

		@Override
		public void removedService(ServiceReference reference, Object service) {
			System.err.println("test01-test.SimpleServiceCustomizer:IService removed..");
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
		System.err.println("test01-test.Activator.start(): opening tracker for IService");
		simpleServiceTracker.open();
		
		// grab the service
		simpleService = (IService) simpleServiceTracker.getService();

		if(simpleService != null) {
			System.err.println("test01-test.Activator.start(): IService tracked directly after opening the tracker");
			simpleService.test();
		} 
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		if(simpleService != null) {
			System.err.println("test01-test.Activator.stop(): closing the tracker");
			simpleService.test();
		}
		
		// close the service tracker
		simpleServiceTracker.close();
		simpleServiceTracker = null;
		
		simpleService = null;
	}

}
