/**
 * 
 */
package cz.cuni.mff.dsrg.log.impl;

import java.util.LinkedList;
import java.util.List;

import cz.cuni.mff.dsrg.log.api.ILogTarget;
import cz.cuni.mff.dsrg.log.api.ILogger;

/**
 * @author Michal Malohlava
 *
 */
public class LoggerImpl implements ILogger {
	
	List<ILogTarget> logTargets = new LinkedList<ILogTarget>();

	@Override
	public void info(String message) {
		for (ILogTarget target : logTargets) {
			target.write("[" + target.getName() +  "]: " + message);
		}		
	}
	
	public void addLogTarget(ILogTarget t) {
		logTargets.add(t);
	}
	
	public void removeLogTarget(ILogTarget t) {
		logTargets.remove(t);
	}
}
