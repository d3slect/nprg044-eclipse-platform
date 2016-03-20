package cz.cuni.mff.d3s.nprg044.tut1.demo02.impl_decl;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;

import cz.cuni.mff.d3s.nprg044.tut1.demo02.api.IHelloService;

public class HelloServiceFactoryImpl implements IHelloService {

	private String greeting = null;

	@Override
	public String getGreeting() {
		return greeting;
	}

	@Override
	public void greet() {
		System.out.println(getGreeting());
	}
	
	/* called preferably to #activate(BundleContext bcx); */
	private void activate(ComponentContext ctx) {
		System.out.println("Factory component activation (via ComponentContext): " + ctx);
		this.greeting = (String) ctx.getProperties().get("greeting"); 
	}
	
	private void activate(BundleContext bcx) {
		System.out.println("Factory component activation (via BundleContext): " + bcx);
	}

}
