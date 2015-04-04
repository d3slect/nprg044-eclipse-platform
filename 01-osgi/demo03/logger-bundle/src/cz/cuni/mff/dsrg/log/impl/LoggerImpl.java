/**
 * 
 */
package cz.cuni.mff.dsrg.log.impl;

import java.util.LinkedList;
import java.util.List;

import org.osgi.service.component.ComponentContext;

import cz.cuni.mff.dsrg.log.api.ILogTarget;
import cz.cuni.mff.dsrg.log.api.ILogger;

/**
 * 
 * @author Michal Malohlava
 */
public class LoggerImpl implements ILogger {
	
	private String prefix = null; 
	private List<ILogTarget> logTargets = new LinkedList<ILogTarget>();
	
	protected void activate(ComponentContext ctx) {
		System.out.println("LoggerImpl.activate()");
		
		prefix = (String) ctx.getProperties().get("logPrefix");
		prefix = prefix != null ? prefix : "";
	}

	@Override
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
}
