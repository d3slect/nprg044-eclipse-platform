package cz.cuni.mff.d3s.nprg044.log.test;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import cz.cuni.mff.d3s.nprg044.log.api.ILogger;

public class ServiceTestActivator implements BundleActivator, Runnable {

	private ServiceTracker<Object, Object> tracker;
	private Thread testThread;
	private ILogger logger;
	
	@Override
	public void start(BundleContext context) throws Exception {		
		System.out.println("ServiceTestActivator start");
		
		// create a service tracker
		tracker = new ServiceTracker<Object, Object>(context, ILogger.class.getName(), new SimpleServiceCustomizer(context));

		// track the log service
		System.out.println("opening tracker for ILogger");		
		tracker.open();		
	}

	@Override
	public void stop(BundleContext context) throws Exception {		
		System.out.println("ServiceTestActivator stop");
		
		// close the service tracker
		tracker.close();
		
		testThread = null;
		logger = null;
	}
	
	@Override
	public void run() {
		System.out.print("Test thread started");
		
		Thread currentThread = Thread.currentThread();
		int counter = 0;
		
		while (testThread == currentThread) {
		
			if (logger != null) {
				logger.info("Log message number " + counter);
				counter++;
			} else {
				//System.err.println("No logger");
			}
			
			try {
				synchronized (this)	{
					wait(2000);
				}
			} catch (InterruptedException e) {}			
		}		
		
		System.out.print("Test thread exiting");
	}

	private class SimpleServiceCustomizer implements ServiceTrackerCustomizer<Object, Object> {
		
		private BundleContext ctx;
		
		public SimpleServiceCustomizer(BundleContext ctx) {
			this.ctx = ctx;
		}

		@Override
		public Object addingService(ServiceReference<Object> reference) {
			System.out.println("SimpleServiceCustomizer: ILogger adding");
			
			// grab the service
			logger = (ILogger) this.ctx.getService(reference);
			
			System.out.println("SimpleServiceCustomizer: ILogger found");
			
			testThread = new Thread(ServiceTestActivator.this);
			testThread.start();
			
			return logger;
		}

		@Override
		public void modifiedService(ServiceReference<Object> reference, Object service) {
		}

		@Override
		public void removedService(ServiceReference<Object> reference, Object service) {
			System.out.println("SimpleServiceCustomizer: ILogger removed");
		}		
	}

}
