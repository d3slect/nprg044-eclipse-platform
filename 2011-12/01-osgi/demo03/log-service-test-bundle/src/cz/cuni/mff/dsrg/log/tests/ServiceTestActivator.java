package cz.cuni.mff.dsrg.log.tests;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import cz.cuni.mff.dsrg.log.api.ILogger;

public class ServiceTestActivator implements BundleActivator, Runnable {
	private ServiceTracker tracker;
	private Thread testThread;
	
	public void start(BundleContext context) throws Exception {
		System.out.println("ServiceTestActivator.start()");
		tracker = new ServiceTracker(context, ILogger.class.getName(), null);
		
		tracker.open();
		
		testThread = new Thread(this);
		testThread.start();
	}

	public void stop(BundleContext context) throws Exception {
		System.out.println("ServiceTestActivator.stop()");
		tracker.close();
		
		testThread = null;
	}

	@Override
	public synchronized void run() {
		Thread currentThread = Thread.currentThread();
		int counter = 0;
		
		while(testThread == currentThread) {
			ILogger logger = (ILogger) tracker.getService();
			
			if (logger != null) {
				logger.info("Log message n. " + counter);
				counter++;
			} else {
				//System.err.println("No logger");
			}
			
			try {
				wait(2000);
			} catch (InterruptedException e) {}			
		}		
		
		System.err.print("Test thread exiting...");
	}

}
