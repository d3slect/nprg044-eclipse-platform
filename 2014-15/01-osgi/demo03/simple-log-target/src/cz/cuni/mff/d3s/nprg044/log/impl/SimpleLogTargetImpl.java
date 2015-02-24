package cz.cuni.mff.d3s.nprg044.log.impl;

import cz.cuni.mff.d3s.nprg044.log.api.ILogTarget;

public class SimpleLogTargetImpl implements ILogTarget {

	public void write(String message) {
		System.out.println(message);
	}

	public String getName() {
		return "System out";
	}

}
