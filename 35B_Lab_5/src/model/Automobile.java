package model;

import java.io.Serializable;
import java.util.ArrayList;

import debug.Debuggable;

public class Automobile implements Debuggable, Serializable {
	/////////////////////////////////////////
	// INSTANCE VARIABLES
	private int numOfProperties;
	private int basePrice;
	private String make;
	private String model;
	private String year;
	private ArrayList<OptionSet> opset;
	private ArrayList<Option> choices;
	
	private String owner;
	
	private boolean available;

	/////////////////////////////////////////
	// CONSTRUCTORS
	public Automobile() {
		choices = new ArrayList<Option>();
	}

	public Automobile(String[] name, ArrayList<OptionSet> opset, int basePrice) {
		if (DEBUG)
			System.out.println("Constructing Automobile");
		this.make = name[0];
		this.model = name[1];
		this.year = name[2];
		this.opset = opset;
		this.numOfProperties = this.opset.size();
		this.basePrice = basePrice;
		choices = new ArrayList<Option>();
		this.available = false;
	}

	/////////////////////////////////////////
	// GETTERS / SETTERS
	public synchronized String getMakeModelYear() {
		return (this.make + " " + this.model + " " + this.year);
	}
	
	public synchronized String getMake() {
		return make;
	}

	public synchronized void setMake(String make) {
		this.make = make;
	}

	public synchronized String getModel() {
		return model;
	}

	public synchronized void setModel(String model) {
		this.model = model;
	}

	public synchronized String getYear() {
		return year;
	}

	public synchronized void setYear(String year) {
		this.year = year;
	}

	public synchronized int getNumOfProperties() {
		return numOfProperties;
	}

	public synchronized void setNumOfProperties(int numOfProperties) {
		this.numOfProperties = numOfProperties;
	}

	public synchronized ArrayList<OptionSet> getOpset() {
		return opset;
	}

	public synchronized void setOpset(ArrayList<OptionSet> opset) {
		this.opset = opset;
	}

	public synchronized ArrayList<Option> getChoices() {
		return choices;
	}

	public synchronized void setChoices(ArrayList<Option> choices) {
		this.choices = choices;
	}

	public synchronized int getBasePrice() {
		return basePrice;
	}

	public synchronized void setBasePrice(int basePrice) {
		this.basePrice = basePrice;
	}

	public synchronized String getOptionSetName(int n) {
		return this.opset.get(n).getOptionType();
	}
	
	public synchronized String[] getOptionNames(int n) {
		String[] arr = this.opset.get(n).getOptionTitles();
		
		return arr;
	}

	public synchronized void setOptionSetName(String newName, int i) {
		this.opset.get(i).setOptionType(newName);
	}

	public synchronized String getOptionName(int i, int j) {
		return this.opset.get(i).getOptions().get(j).getOptionTitle();
	}

	public synchronized void setOptionName(String newName, int i, int j) {
		this.opset.get(i).getOptions().get(j).setOptionTitle(newName);
	}
	
	public synchronized void editOption(String optionSetName, String oldOptionName, String newOptionName, int newPrice) {
		int[] ij = new int[2];
		
		for (int i = 0; i < this.opset.size(); i++) {
			if (this.opset.get(i).getOptionType().equalsIgnoreCase(optionSetName)) 
			{
				ij[0] = i;
				for(int j = 0; j < this.opset.get(i).getOptions().size(); j++) {
					if (this.opset.get(i).getOptions().get(j).getOptionTitle().equalsIgnoreCase(oldOptionName))
						ij[1] = j;
				}
			}
		}
		
		this.setOptionName(newOptionName, ij[0], ij[1]);
		this.setOptionPrice(ij[0], ij[1], newPrice);
		
		this.available = true;
		notifyAll();
	}
	
	public synchronized void editOptionSet(String oldOptionSetTitle, String newOptionSetTitle) {
		
		for (int i = 0; i < this.opset.size(); i++) {
			if (this.opset.get(i).getOptionType().equalsIgnoreCase(oldOptionSetTitle))
				this.opset.get(i).setOptionType(newOptionSetTitle);
		}
	}
	
	public synchronized ArrayList<Option> getOptionArrayList(int i) {
		return this.opset.get(i).getOptions();
	}

	public synchronized int getOptionChoicePrice(String optionSetName) {
		int p = 0;
		boolean found = false;
		
		for (int i = 0; i < opset.size(); i++) {
			if (opset.get(i).getOptionType().equalsIgnoreCase(optionSetName)) {
				p = choices.get(i).getPrice();
				found = true;
			}
		}
		
		if (found)
			System.out.println("Choice was found");
		else
			System.out.println("Choice wasn't found");
		
		return p;
	}
	
	public synchronized int getOptionPrice(int i, int j) {
		return this.opset.get(i).getOptions().get(j).getPrice();
	}

	public synchronized void setOptionPrice(int i, int j, int newPrice) {
		this.opset.get(i).getOptions().get(j).setPrice(newPrice);
	}
	
	public synchronized void setOptionChoice(String optionSetName, String optionName) {
		boolean added = false;

		for (int i = 0; i < opset.size(); i++) {
			if (opset.get(i).getOptionType().equalsIgnoreCase(optionSetName)) {
				for (int j = 0; j < opset.get(i).getOptions().size(); j++) {
					if (opset.get(i).getOptions().get(j).getOptionTitle().equalsIgnoreCase(optionName)) {
						choices.add(opset.get(i).getOptions().get(j));
						added = true;	
					}
				}
			}
		}
		
		if (added)
			System.out.println("Choice successfully added");
		else
			System.out.println("Choice wasn't added");
	}
	
	public synchronized String getOptionChoice(String optionSetName) {
		String name = new String();
		boolean found = false;
		
		for (int i = 0; i < opset.size(); i++) {
			if (opset.get(i).getOptionType().equalsIgnoreCase(optionSetName)) {
				name = choices.get(i).getOptionTitle();
				found = true;
			}
		}
		
		if (found)
			System.out.println("Choice was found");
		else
			System.out.println("Choice wasn't found");
		
		return name;
	}
	
	public synchronized String getOwner() {
		return owner;
	}

	public synchronized void setOwner(String owner) {
		this.owner = owner;
	}

	/////////////////////////////////////////
	// METHODS
	public synchronized OptionSet createOptionSet() {
		return new OptionSet();
	}
	
	public synchronized int getChoicingPrice() {
		int total = 0;
		
		for (int i = 0; i < choices.size(); i++) {
			total += choices.get(i).getPrice();
		}
		
		return total;
	}
	
	public String getTotalPriceString() {
		int total = (this.getBasePrice() + this.getChoicingPrice());
		String s = ("$ " + total);
		return s;
	}

	public void addOpset() {
		// not writing until Collections can be used. For now changes will be made in
		// the .txt file
	}

	public void deleteOpset() {
		// not writing until Collections can be used.
	}

	public void addOption() {
		// not writing until Collections can be used.
	}

	public void deleteOption() {
		// not writing until Collections can be used.
	}

	public synchronized void print() {	
		
		System.out.println("=========================================\n");
		System.out.println("\t" + make + " " + model + " " + year);
		System.out.println("");
		System.out.println("Base Price: $" + this.basePrice);
		System.out.println("Options   :  " + this.numOfProperties);
		System.out.println("");
		for (int i = 0; i < numOfProperties; i++)
			this.opset.get(i).print();
		System.out.println();
		
	}
	
	public synchronized void printChoices() {
		System.out.println("=========================================\n");
		System.out.println("\t" + owner + "'s " + make + " " + model + " " + year);
		System.out.println("");
		System.out.println("\t\t Chosen Options:");
		System.out.println("");
		for (int i = 0; i < choices.size(); i++) {
			choices.get(i).print();
		}
		System.out.println("");
		System.out.println("\t\tBASE PRICE: $" + this.getBasePrice());
		System.out.println("\t\tFEATURES  : $" + this.getChoicingPrice());
		System.out.println("\t\tTOTAL     : $" + (this.getBasePrice() + this.getChoicingPrice()));
		System.out.println("");
	}
	
	public synchronized void printInfo() {
		System.out.println(this.owner + " | " + this.getMakeModelYear() + " | $" + (this.getBasePrice() + this.getChoicingPrice()));
	}

	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("$" + this.getBasePrice() + "\t");
		buffer.append(this.getMakeModelYear());
	
		return buffer.toString();
	}
}
