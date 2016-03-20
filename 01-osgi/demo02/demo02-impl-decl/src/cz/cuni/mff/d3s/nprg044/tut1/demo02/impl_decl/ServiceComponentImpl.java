package cz.cuni.mff.d3s.nprg044.tut1.demo02.impl_decl;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;

import cz.cuni.mff.d3s.nprg044.tut1.demo02.api.IHelloService;
import cz.cuni.mff.d3s.nprg044.tut1.demo02.api.IService;

public class ServiceComponentImpl implements IService {

	private IHelloService helloService;

	@Override
	public void test() {
		System.out.println("Test service!");
	}
	
	public void activate() {
		System.out.println("Service component activated!");		
	}
	
	public void bindHelloService(IHelloService helloService) {
		System.out.println("Binding hello service to: " + helloService);
		
		this.helloService = helloService;
		
		// use hello service
		this.helloService.greet();
	}
	
	public void bindCF(ComponentFactory cf) {
		System.out.println("Binding component factory to: " + cf);
		
		Dictionary dict = new Properties() {{ put("greeting", "hi"); }};
		
		// get component instance
		System.out.println("Creating new instance of the component");
		ComponentInstance ci = cf.newInstance(dict);
	}
	
	public void unbindCF(ComponentFactory cf) {		
	}

}
