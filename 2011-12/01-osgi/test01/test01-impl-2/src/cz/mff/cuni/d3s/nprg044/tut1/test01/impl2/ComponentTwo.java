package cz.mff.cuni.d3s.nprg044.tut1.test01.impl2;

import cz.mff.cuni.d3s.nprg044.tut1.test01.api.IService;

public class ComponentTwo implements IService {

	@Override
	public void test() {
		System.out.println("test01-impl-2.ComponentTwo.test(): This is a test!");		
	}
	
	private void activate() {
		System.err.println("test01-impl-2.ComponentTwo.activate():Component activated..");
	}

}
