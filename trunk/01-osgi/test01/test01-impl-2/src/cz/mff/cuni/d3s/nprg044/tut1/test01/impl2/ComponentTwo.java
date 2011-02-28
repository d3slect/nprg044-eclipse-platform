package cz.mff.cuni.d3s.nprg044.tut1.test01.impl2;

import cz.mff.cuni.d3s.nprg044.tut1.test01.api.IService;

public class ComponentTwo implements IService {

	@Override
	public void test() {
		System.out.println("test #2");		
	}
	
	private void activate() {
		System.out.println("Activating component implementation #2");
	}

}
