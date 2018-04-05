package model;

import java.io.Serializable;
import java.util.ArrayList;

import debug.Debuggable;

public class OptionSet implements Debuggable, Serializable {
	/////////////////////////////////////////
	// INSTANCE VARIABLES
	private int numOfOptions;
	private String OptionName;
	private ArrayList<Option> options;
	private Option opt = new Option();

	/////////////////////////////////////////
	// CONSTRUCTORS
	protected OptionSet() {
		if (DEBUG)
			System.out.println("Constructing default OptionSet");
	}

	protected OptionSet(String optionType) {
		if (DEBUG)
			System.out.println("Constructing OptionSet - name only");
		this.OptionName = optionType;
		for (int i = 0; i < options.size(); i++) {
			options.add(i, new Option());
		}
	}

	protected OptionSet(String optionType, ArrayList<Option> options) {
		if (DEBUG)
			System.out.println("Constructing OptionSet");
		this.OptionName = optionType;
		this.options = options;
		this.numOfOptions = this.options.size();
	}

	/////////////////////////////////////////
	// GETTERS / SETTERS
	protected int getNumOfOptions() {
		return numOfOptions;
	}

	protected void setNumOfOptions(int numOfOptions) {
		this.numOfOptions = numOfOptions;
	}

	protected ArrayList<Option> getOptions() {
		return options;
	}

	protected void setOptions(ArrayList<Option> options) {
		this.options = options;
	}

	protected String getOptionType() {
		return OptionName;
	}

	public void setOptionType(String optionType) {
		this.OptionName = optionType;
	}

	protected Option getOpt() {
		return opt;
	}

	protected void setOpt(Option opt) {
		this.opt = opt;
	}

	public String[] getOptionTitles() {
		String[] arr = new String[this.options.size()];
		
		for (int i = 0; i < arr.length; i++) {
			arr[i] = options.get(i).getOptionTitle();
		}
		
		return arr;
	}
	
	/////////////////////////////////////////
	// METHODS
	protected void buildInner() {
		if (DEBUG)
			System.out.println("Creating an Option inside " + OptionName);
		this.opt = new Option();
	}

	public void buildOptionArray(String[] optionTitles, int[] prices) // calls inner class method, assigns Option
																			// array to this OptionSet
	{
		if (DEBUG)
			System.out.println("Copying array of Options to " + OptionName);
		this.options = opt.returnOptionArray(optionTitles, prices);
	}

	protected void addOption() {
		// not writing until Collections can be used. For now changes will be made in
		// the .txt file
	}

	protected void deleteOption() {
		// not writing until Collections can be used. For now changes will be made in
		// the .txt file
	}

	protected void print() {
		System.out.println("\t____________________________");
		System.out.println("\t" + this.OptionName);
		for (int i = 0; i < options.size(); i++)
			options.get(i).print();
	}

}
