package cz.mff.cuni.d3s.nprg044.tut1.test01;

import java.util.Dictionary;
import java.util.Properties;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.log.LogService;

import cz.mff.cuni.d3s.nprg044.tut1.test01.api.IHelloService;
import cz.mff.cuni.d3s.nprg044.tut1.test01.api.IService;

public class ComponentOne implements IService {

	private LogService logService;	
	private IHelloService helloService;

	@Override
	public void test() {
		System.out.println("-----> Service -----");		
	}
	
	private void setLogService(LogService logService) {
		System.err.println("test01-impl.ComponentOne.setLogService() to: " + logService);
		this.logService = logService;
	}
	
	private void unsetLogService(LogService logService) {
		System.err.println("test01-impl.ComponentOne.unsetLogService() to null ");
		this.logService = null;
	}
	
	private void bindHelloService(IHelloService helloService) {
		System.err.println("test01-impl.ComponentOne.bindHelloService() to: " + helloService);
		this.helloService = helloService;
		
		// use hello service
		this.helloService.greet();
	}
	
	private void bindCF(ComponentFactory cf) {
		System.err.println("test01-impl.ComponentOne.bindCF() to: " + cf);
		Dictionary dict = new Properties() {{ put("greeting", "hi"); }};
		
		// get component instance
		System.err.println("test01-impl.ComponentOne.bindCF(): creating a new instance of component" );
		ComponentInstance ci = cf.newInstance(dict);
		// get service
		//  is already get via bindHelloService
	}
	
	private void unbindCF(ComponentFactory cf) {		
	}
	
	public void activate() {
		logService.log(LogService.LOG_INFO, "Component activate!");		
	}
	
	public void activate(ComponentContext ctx) {
		logService.log(LogService.LOG_INFO, "Component activate, component context:=> " + ctx);		
	}

}
