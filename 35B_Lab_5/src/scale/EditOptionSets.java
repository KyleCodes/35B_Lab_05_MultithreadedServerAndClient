package scale;

import java.io.Serializable;

import debug.Debuggable;
import model.Automobile;

public class EditOptionSets implements Serializable, Debuggable, Runnable {
	/////////////////////////////////////////
	// INSTANCE VARIABLES
	private String newOptionSetTitle;
	private String oldOptionSetTitle;
	private Automobile auto;
	
	
	/////////////////////////////////////////
	// CONSTRUCTOR
	public EditOptionSets(String newOptionSetTitle, String oldOptionSetTitle, Automobile auto) {
		this.newOptionSetTitle = newOptionSetTitle;
		this.oldOptionSetTitle = oldOptionSetTitle;
		this.auto = auto;
	}
	
	/////////////////////////////////////////
	// METHOD
	public void run() {
		auto.editOptionSet(oldOptionSetTitle, newOptionSetTitle);
	}
}
