package cz.cuni.mff.dsrg.log.api;

public interface ILogTarget {
	
	void write(String message);
	
	String getName();
}
