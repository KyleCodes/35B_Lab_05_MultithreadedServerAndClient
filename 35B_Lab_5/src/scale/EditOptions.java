package scale;

import java.io.Serializable;

import debug.Debuggable;
import model.Automobile;

public class EditOptions implements Debuggable, Serializable, Runnable {
	/////////////////////////////////////////
	// INSTANCE VARIABLES
	private String newOptionTitle;
	private int newPrice;
	private String optionSetTitle;
	private String oldOptionTitle;
	private Automobile auto;
	
	
	/////////////////////////////////////////
	// CONSTRUCTOR
	public EditOptions(String newOptionTitle, int newPrice, String optionSetTitle, String oldOptionTitle, Automobile auto) {
		this.newOptionTitle = newOptionTitle;
		this.newPrice = newPrice;
		this.optionSetTitle = optionSetTitle;
		this.oldOptionTitle = oldOptionTitle;
		this.auto = auto;
		
		//run();
	}
	
	/////////////////////////////////////////
	// GETTERS / SETTERS
	
	/////////////////////////////////////////
	// METHODS
	public void run() {
		if (DEBUG)
			System.out.println("Running Option update procedure");
		auto.editOption(optionSetTitle, oldOptionTitle, newOptionTitle, newPrice);
	}

}
