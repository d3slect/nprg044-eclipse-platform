/**
 * 
 */
package cz.cuni.mff.dsrg.log.impl;

import cz.cuni.mff.dsrg.log.api.ILogTarget;

/**
 * Simple log target.
 * 
 * @author Michal Malohlava
 *
 */
public class SimpleLogTargetImpl implements ILogTarget {

	@Override
	public String getName() {
		return "System out";
	}

	@Override
	public void write(String message) {
		System.out.println(message);		
	}

}
