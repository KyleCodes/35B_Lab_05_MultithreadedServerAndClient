package client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import debug.Debuggable;
import model.Automobile;
import model.Properties;
import util.FileIO;
import model.OptionSet;

public class CarModelOptionsIO implements Debuggable{
	
	public Properties parseProperties(String fileName) {
		FileIO FIO = new FileIO();
		return FIO.readProperties(fileName);
	}
	
	public void selectAutoChoices(Automobile auto) {
		Scanner stdInput = new Scanner(System.in);
		String input;
		
		auto.print();
		System.out.println("C: //////////////////////////////////////////////");
		System.out.println("C:         Car Configuration Menu        \nC:");
		System.out.println("C: Enter username: ");
		System.out.print  ("C: ----> ");
		input = stdInput.nextLine();
		auto.setOwner(input);
		
		for (int i = 0; i < auto.getNumOfProperties(); i++) {
			System.out.println("C: Enter choice selection for " + auto.getOptionSetName(i));
			System.out.print  ("C: ----> ");
			input = stdInput.nextLine();
			auto.setOptionChoice(auto.getOptionSetName(i), input);
		}
		
		System.out.println("C: Successfully configered choices");
	}
}
