package cz.mff.cuni.d3s.nprg044.tut1.test01;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;

import cz.mff.cuni.d3s.nprg044.tut1.test01.api.IHelloService;

public class FactoryComponentImpl implements IHelloService {

	String greeting = null;
	
	/* called preferably to #actvite(BundleContext bcx); */
	private void activate(ComponentContext ctx) {
		System.err.println("Component factory activation (via ComponentContex): " + ctx);
		this.greeting = (String) ctx.getProperties().get("greeting"); 
	}
	
	private void activate(BundleContext blx) {
		System.err.println("Component factory activation (via BundleContex): " + blx);
	}

	@Override
	public String getGreeting() {		
		return greeting;
	}
	
	@Override
	public void greet() {
		System.out.println(getGreeting());		
	}
}
