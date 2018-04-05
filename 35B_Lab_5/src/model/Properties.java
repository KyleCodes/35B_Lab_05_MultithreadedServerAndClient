package model;

import java.io.Serializable;
import java.util.ArrayList;

import debug.Debuggable;
import model.Option;
import model.OptionSet;

public class Properties implements Debuggable, Serializable {
	/////////////////////////////////////////
	// INSTANCE VARIABLES
	private int numOfProperties;
	private int basePrice;
	private String make;
	private String model;
	private String year;
	private ArrayList<OptionSet> opset;

	/////////////////////////////////////////
	// CONSTRUCTORS
	public Properties() {}
	
	public Properties(String[] name, ArrayList<OptionSet> opset, int basePrice) {
		if (DEBUG)
			System.out.println("Constructing Properties");
		this.make = name[0];
		this.model = name[1];
		this.year = name[2];
		this.opset = opset;
		this.numOfProperties = this.opset.size();
		this.basePrice = basePrice;
	}

	/////////////////////////////////////////
	// GETTERS / SETTERS
	public String getMakeModelYear() {
		return this.make + " " + this.model + " " + this.year;
	}
	
	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public int getNumOfProperties() {
		return numOfProperties;
	}

	public void setNumOfProperties(int numOfProperties) {
		this.numOfProperties = numOfProperties;
	}

	public ArrayList<OptionSet> getOpset() {
		return opset;
	}

	public void setOpset(ArrayList<OptionSet> opset) {
		this.opset = opset;
	}

	public int getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(int basePrice) {
		this.basePrice = basePrice;
	}

	public String getOptionSetName(int n) {
		return this.opset.get(n).getOptionType();
	}

	public void setOptionSetName(String newName, int i) {
		this.opset.get(i).setOptionType(newName);
	}

	public String getOptionName(int i, int j) {
		return this.opset.get(i).getOptions().get(j).getOptionTitle();
	}

	public void setOptionName(String newName, int i, int j) {
		this.opset.get(i).getOptions().get(j).setOptionTitle(newName);
	}
	
	public ArrayList<Option> getOptionArrayList(int i) {
		return this.opset.get(i).getOptions();
	}
	
	public int getOptionPrice(int i, int j) {
		return this.opset.get(i).getOptions().get(j).getPrice();
	}

	public void setOptionPrice(int i, int j, int newPrice) {
		this.opset.get(i).getOptions().get(j).setPrice(newPrice);
	}

	/////////////////////////////////////////
	// METHODS
	public OptionSet createOptionSet() {
		return new OptionSet();
	}

	public void print() {	
		System.out.println("=========================================\n");
		System.out.println("\t" + make + " " + model + " " + year);
		System.out.println("");
		System.out.println("Base Price: $" + this.basePrice);
		System.out.println("Options  : " + this.numOfProperties);
		System.out.println("");
		for (int i = 0; i < numOfProperties; i++)
			this.opset.get(i).print();
		System.out.println();
	}

}
