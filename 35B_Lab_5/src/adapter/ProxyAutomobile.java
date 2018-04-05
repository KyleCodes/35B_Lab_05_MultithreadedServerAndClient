package adapter;

import java.io.Serializable;
import model.Properties;

import debug.Debuggable;
import model.Automobile;
import model.Fleet;
import scale.EditOptionSets;
import scale.EditOptions;
import scale.PrintUpdate;
import util.FileIO;


public abstract class ProxyAutomobile implements Serializable, Debuggable {
	/////////////////////////////////////////
	// INSTANCE VARIABLES
	private static Fleet fleet = new Fleet();
	private static Automobile auto;
	private FileIO FIO = new FileIO();

	/////////////////////////////////////////
	// CONSTRUCTORS

	/////////////////////////////////////////
	// GETTERS / SETTERS

	/////////////////////////////////////////
	// METHODS

	/////////////////////////////////////////
	// CREATEAUTO INTERFACE
	public void buildAuto(String fileName) {
		if (DEBUG)
			System.out.println("Building " + fileName);
		auto = FIO.buildAutomobile(fileName);
		fleet.addAuto(auto.getMakeModelYear(), auto);
	}

	public void printAuto(String modelName) {
		if (DEBUG)
			System.out.println("Printing");
		auto.print();
	}

	/////////////////////////////////////////
	// UPDATE AUTO INTERFACE
	public void updateOptionSetName(String modelName, String OptionSetName, String newName) {
		System.out.println("Updating OpSet name");

		if (DEBUG) {
			System.out.println("auto name: " + auto.getMakeModelYear());
			System.out.println("modelname: " + modelName);
			System.out.println("opset lngth" + auto.getOpset().size());
			System.out.println("opset name" + auto.getOptionSetName(0));
		}

		if (auto.getMakeModelYear().equalsIgnoreCase(modelName)) {
			for (int i = 0; i < auto.getOpset().size(); i++) {
				if (auto.getOptionSetName(i).equalsIgnoreCase(OptionSetName)) {
					auto.setOptionSetName(newName, i);
					System.out.println("Update Successful");
				}
			}
		} else
			System.out.println("That model name doesn't exist");
	}

	public void updateOptionPrice(String modelName, String OptionSetName, String Option, int newPrice) {
		System.out.println("Updating Option Price");
		boolean success = false;

		if (auto.getMakeModelYear().equalsIgnoreCase(modelName)) {
			for (int i = 0; i < auto.getOpset().size(); i++) {
				if (success == true)
					break;
				if (auto.getOptionSetName(i).equalsIgnoreCase(OptionSetName)) {
					for (int j = 0; j < auto.getOptionArrayList(i).size(); j++) {
						if (auto.getOptionName(i, j).equalsIgnoreCase(Option)) {
							auto.setOptionPrice(i, j, newPrice);
							System.out.println("Update Successful");
							success = true;
							break;
						}
					}
				}
			}
		} else
			System.out.println("That model name deoesn't exist");
	}
	
	/////////////////////////////////////////
	// CHOOSEAUTO INTERFACE
	public void setChoice(String opsetName, String option) {
		auto.setOptionChoice(opsetName, option);
	}
	
	public void removeChoice(String option) {
		
	}
	
	public void getChoice(String option) {
		auto.getOptionChoice(option);
	}
	
	public void printChoices() {
		auto.printChoices();
	}
	
	public void setOwner(String name) {
		auto.setOwner(name);
	}
	
	public String getMakeModelYear() {
		return auto.getMakeModelYear();
	}
	
	/////////////////////////////////////////
	// DATABASEAUTO INTERFACE
	public void addAuto(String MMY, Automobile auto) {			
		fleet.addAuto(MMY, auto);
	}
	
	public Automobile retrieveAuto(String makeModelYear) {
		return fleet.retrieveAuto(makeModelYear);
	}
	
	public void updateAuto(String makeModelYear, Automobile auto) {
		fleet.updateAuto(makeModelYear, auto);
	}
	
	public void deleteAuto(String makeModelYear) {
		fleet.deleteAuto(makeModelYear);
	}
	
	public void printList() {
		fleet.printList();
	}
	
	public String toString() {
		return fleet.toString();
	}
	
	/////////////////////////////////////////
	// MULTITHREAD UPDATE INTERFACE
	public void editOptions(String newOptionTitle, int newPrice, String optionSetTitle, String oldOptionTitle, Automobile auto) {
		EditOptions edit = new EditOptions(newOptionTitle, newPrice, optionSetTitle, oldOptionTitle, auto);
		
		Thread thread = new Thread(edit);
		thread.start();
	}
	
	public void editOptionSets(String oldOptionSetTitle, String newOptionSetTitle, Automobile auto) {
		EditOptionSets edit = new EditOptionSets(oldOptionSetTitle, newOptionSetTitle, auto);
		
		Thread thread = new Thread(edit);
		thread.start();
	}
	
	public void printUpdate(Automobile auto) {
		PrintUpdate print = new PrintUpdate(auto);
		
		//Thread thread = 
		new Thread(print).start();
		//thread.start();
	}

	/////////////////////////////////////////
	// AUTOSERVER INTERFACE

	public boolean buildAutoFromProperties(Properties props) {
		Automobile auto = FIO.buildAutoFromProperties(props);
		
		if (fleet.addAuto(auto.getMakeModelYear(), auto))
			return true;
		else
			return false;
	}
	
}
