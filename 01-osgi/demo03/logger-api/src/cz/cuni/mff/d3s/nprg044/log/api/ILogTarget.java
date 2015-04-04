package cz.cuni.mff.d3s.nprg044.log.api;

public interface ILogTarget {
	
	void write(String message);
	
	String getName();
	
}
