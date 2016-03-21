package cz.cuni.mff.d3s.nprg044.log.impl;

import cz.cuni.mff.d3s.nprg044.log.api.ILogTarget;

public class SimpleLogTargetImpl implements ILogTarget {

	@Override
	public void write(String message) {
		System.out.println(message);
	}

	@Override
	public String getName() {
		return "System out";
	}

}
