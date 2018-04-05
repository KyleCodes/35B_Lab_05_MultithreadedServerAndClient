package scale;

import java.io.Serializable;

import debug.Debuggable;
import model.Automobile;

public class PrintUpdate implements Debuggable, Serializable, Runnable {
	Automobile auto;
	
	/////////////////////////////////////////
	// CONSTRUCTOR
	public PrintUpdate(Automobile auto) {
		this.auto = auto;
	}
	
	/////////////////////////////////////////
	// METHOD
	public void run() {
		this.auto.print();
	}
	
}
