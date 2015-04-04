package cz.cuni.mff.d3s.nprg044.log.impl;

import java.util.LinkedList;
import java.util.List;

import org.osgi.service.component.ComponentContext;

import cz.cuni.mff.d3s.nprg044.log.api.ILogger;
import cz.cuni.mff.d3s.nprg044.log.api.ILogTarget;

public class LoggerImpl implements ILogger {

	private String prefix = null; 
	private List<ILogTarget> logTargets = new LinkedList<ILogTarget>();

	public void info(String message) {
		for (ILogTarget target : logTargets) {
			target.write(prefix + "[" + target.getName() +  "]: " + message);
		}
	}
	
	public void addLogTarget(ILogTarget t) {
		logTargets.add(t);
	}
	
	public void removeLogTarget(ILogTarget t) {
		logTargets.remove(t);
	}

	protected void activate(ComponentContext ctx) {
		System.out.println("LoggerImpl activated");
		
		prefix = (String) ctx.getProperties().get("logPrefix");
		if (prefix == null) prefix = "";
	}

}
