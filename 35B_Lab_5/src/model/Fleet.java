package model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import debug.Debuggable;

public class Fleet implements Debuggable, Serializable {
	/////////////////////////////////////////
	// INSTANCE VARIABLES
	private LinkedHashMap<String, Automobile> lhm;
	
	
	/////////////////////////////////////////
	// CONSTRUCTORS
	public Fleet() {
		lhm = new LinkedHashMap<String, Automobile>();
	}
	
	
	/////////////////////////////////////////
	// GETTERS / SETTERS

	
	
	/////////////////////////////////////////
	// METHODS
	public boolean addAuto(String makeModelYear, Automobile auto) {
		
		lhm.put(makeModelYear, auto);
		
		if (lhm.toString() != null)
			return true;
		else
			return false;

	}
	
	public Automobile retrieveAuto(String makeModelYear) {
		return lhm.get(makeModelYear);
	}
	
	public void updateAuto(String makeModelYear, Automobile auto) {
		lhm.replace(makeModelYear, auto);
	}
	
	public void deleteAuto(String makeModelYear) {
		lhm.remove(makeModelYear);
	}
	
	/////////////////////////////////////////
	// PRINT
	public void print() {
		Set<String> keys = lhm.keySet();
		for (String k:keys) {
			lhm.get(k).print();
		}
	}
	
	public void printList() {
		Set<String> keys = lhm.keySet();
		for (String k:keys) {
			lhm.get(k).printInfo();
		}
	}
	
	public String toString() {
		Iterator<Automobile> it = lhm.values().iterator();
		StringBuffer buffer = new StringBuffer();
		while (it.hasNext()) {
			buffer.append(it.next().toString());
			buffer.append("#");
		}
		return buffer.toString();
		
	}
}
