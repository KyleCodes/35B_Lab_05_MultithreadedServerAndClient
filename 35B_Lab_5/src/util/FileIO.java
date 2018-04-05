package util;

import model.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import debug.Debuggable;
import exception.AutoException;

public class FileIO implements Debuggable, Serializable {

	/////////////////////////////////////////
	// INSTANCE VARIABLES
	private static String[] metaData = new String[3];
	private static String[] optionTitles;
	private static int numOfProperties;
	private static int numOfChoices;

	/////////////////////////////////////////
	// GETTERS / SETTERS
	public static int getNumOfOpsets() {
		return Integer.parseInt(metaData[2]);
	}

	public static String[] getMetaData() {
		return metaData;
	}

	public static void setMetaData(String[] metaData) {
		FileIO.metaData = metaData;
	}

	public static String[] getOptionTitles() {
		return optionTitles;
	}

	public static void setOptionTitles(String[] optionTitles) {
		FileIO.optionTitles = optionTitles;
	}

	public static int getNumOfProperties() {
		return numOfProperties;
	}

	public static void setNumOfProperties(int numOfProperties) {
		FileIO.numOfProperties = numOfProperties;
	}

	public static int getNumOfChoices() {
		return numOfChoices;
	}

	public static void decrementNumOfChoices() {
		FileIO.numOfChoices--;
	}

	/////////////////////////////////////////
	// BUILDERS
	// calls text file parser, returns Automobile
	public Automobile buildAutomobile(String fileName) {
		if (DEBUG)
			System.out.println("Initializing build of Automobile from " + fileName);
		return this.readCarConfig(fileName);
	}

	/////////////////////////////////////////
	// EXCEPTION TESTERS
	private void metaDataExtract(String line) throws AutoException {
		if (DEBUG)
			System.out.println("Extracting Metadata from file");
		metaData = line.split(", ");
		if (metaData.length != 5)
			throw new AutoException();
	}

	private void testOpsetNames(String line, int numOfOptionSets) throws AutoException {
		optionTitles = line.split(", ");

		if (optionTitles.length != numOfOptionSets)
			throw new AutoException();
	}

	private void testOptionValidity(String[] temp) throws AutoException {
		if ((temp.length % 2 != 0))
			throw new AutoException();
	}

	/////////////////////////////////////////
	// FILE INTEREACTION

	/*
	 * parses text file. uses counter to keep track of line collects data into
	 * variables and passes them to automobile constructor after .txt is processed
	 */
	public Automobile readCarConfig(String fileName) {
		if (DEBUG)
			System.out.println("Parsing car configuration");

		Automobile auto = new Automobile(); 
		String[] name = new String[3]; // 1 - 2 - 3
		String basePrice = ""; 		  // 4
		OptionSet[] opset = null; 	  // 5

		try {
			int counter = 0;
			FileReader file = new FileReader(fileName);
			BufferedReader buff = new BufferedReader(file);

			if (DEBUG)
				System.out.println("Opening file");

			while (counter != (numOfProperties + 2)) {

				String line = buff.readLine();
				if (DEBUG)
					System.out.println("Reading: " + line);
				
				if (counter == 0) // metadata
				{

					try {
						metaDataExtract(line);
					} catch (AutoException e) { // Error testing section
						new AutoException().FixErr(1);
					}

					name[0] = metaData[0];
					name[1] = metaData[1];
					name[2] = metaData[2];
					basePrice = metaData[3];
					numOfProperties = Integer.parseInt(metaData[4]);
					opset = new OptionSet[numOfProperties];

					if (DEBUG)
						System.out.println("Metadata successfully configured");

					counter++;
				}

				else if (counter == 1) // OptionSetNames
				{
					if (DEBUG)
						System.out.println("Assigning OptionSet titles");

					try {
						this.testOpsetNames(line, numOfProperties);
					} catch (AutoException e) {
						e.FixErr(3);
					}

					for (int i = 0; i < numOfProperties; i++) { // initializes OptionSets and assigns their names
						opset[i] = auto.createOptionSet();
						opset[i].setOptionType(optionTitles[i]);
					}
					counter++;
				}

				else if (counter > 1) // optionSet choices
				{
					if (DEBUG)
						System.out.println("Beginning creation of Options");

					String[] temp = line.split(", ");

					numOfChoices = temp.length;

					try {
						this.testOptionValidity(temp);
					} catch (AutoException e) {
						e.FixErr(2);
					}

					String[] choiceName = new String[(numOfChoices / 2)];
					// splits line into choice / price info
					String[] price = new String[(numOfChoices / 2)];
					int[] priceArrayInt = new int[(numOfChoices / 2)];

					if (DEBUG)
						System.out.println("Extracting titles and prices");

					for (int i = 0; i < numOfChoices; i++) {
						if (i % 2 == 0)
							choiceName[(i / 2)] = temp[i]; // even indexes correspond to names
						else
							price[(i / 2)] = temp[i]; // odd indexes correspond to prices
					}

					for (int i = 0; i < price.length; i++) {
						priceArrayInt[i] = Integer.parseInt(price[i]);
					}
					
					opset[(counter - 2)].buildOptionArray(choiceName, priceArrayInt); // creates array of Options inside
																				// particular OptionSet
					if (DEBUG)
						System.out.println("Option array successfully assigned to OptionSet");

					counter++;
				}

			}
			buff.close();
		} catch (IOException e) {
			System.out.println("Error -- " + e.toString());
		}
		
		ArrayList<OptionSet> opSetList = new ArrayList<OptionSet>(Arrays.asList(opset));
		int basePriceInt = Integer.parseInt(basePrice);
		
		
		
		auto = new Automobile(name, opSetList, basePriceInt); // name, price, OptionSets passed to Automobile constructor
		if (DEBUG)
			System.out.println("Autombile successfully created!");

		return auto;
	}

	public Properties readProperties(String fileName) {
		if (DEBUG)
			System.out.println("Parsing car configuration");

		Properties props = new Properties(); 
		String[] name = new String[3]; // 1 - 2 - 3
		String basePrice = ""; 		  // 4
		OptionSet[] opset = null; 	  // 5

		try {
			int counter = 0;
			FileReader file = new FileReader(fileName);
			BufferedReader buff = new BufferedReader(file);

			if (DEBUG)
				System.out.println("Opening file");

			while (counter != (numOfProperties + 2)) {

				String line = buff.readLine();
				if (DEBUG)
					System.out.println("Reading: " + line);
				
				if (counter == 0) // metadata
				{

					try {
						metaDataExtract(line);
					} catch (AutoException e) { // Error testing section
						new AutoException().FixErr(1);
					}

					name[0] = metaData[0];
					name[1] = metaData[1];
					name[2] = metaData[2];
					basePrice = metaData[3];
					numOfProperties = Integer.parseInt(metaData[4]);
					opset = new OptionSet[numOfProperties];

					if (DEBUG)
						System.out.println("Metadata successfully configured");

					counter++;
				}

				else if (counter == 1) // OptionSetNames
				{
					if (DEBUG)
						System.out.println("Assigning OptionSet titles");

					try {
						this.testOpsetNames(line, numOfProperties);
					} catch (AutoException e) {
						e.FixErr(3);
					}

					for (int i = 0; i < numOfProperties; i++) { // initializes OptionSets and assigns their names
						opset[i] = props.createOptionSet();
						opset[i].setOptionType(optionTitles[i]);
					}
					counter++;
				}

				else if (counter > 1) // optionSet choices
				{
					if (DEBUG)
						System.out.println("Beginning creation of Options");

					String[] temp = line.split(", ");

					numOfChoices = temp.length;

					try {
						this.testOptionValidity(temp);
					} catch (AutoException e) {
						e.FixErr(2);
					}

					String[] choiceName = new String[(numOfChoices / 2)];
					// splits line into choice / price info
					String[] price = new String[(numOfChoices / 2)];
					int[] priceArrayInt = new int[(numOfChoices / 2)];

					if (DEBUG)
						System.out.println("Extracting titles and prices");

					for (int i = 0; i < numOfChoices; i++) {
						if (i % 2 == 0)
							choiceName[(i / 2)] = temp[i]; // even indexes correspond to names
						else
							price[(i / 2)] = temp[i]; // odd indexes correspond to prices
					}

					for (int i = 0; i < price.length; i++) {
						priceArrayInt[i] = Integer.parseInt(price[i]);
					}
					
					opset[(counter - 2)].buildOptionArray(choiceName, priceArrayInt); // creates array of Options inside
																				// particular OptionSet
					if (DEBUG)
						System.out.println("Option array successfully assigned to OptionSet");

					counter++;
				}

			}
			buff.close();
		} catch (IOException e) {
			System.out.println("Error -- " + e.toString());
		}
		
		ArrayList<OptionSet> opSetList = new ArrayList<OptionSet>(Arrays.asList(opset));
		int basePriceInt = Integer.parseInt(basePrice);
		
		
		
		props = new Properties(name, opSetList, basePriceInt); // name, price, OptionSets passed to Automobile constructor
		if (DEBUG)
			System.out.println("Properties successfully created!");

		return props;
	}
	
	public Automobile buildAutoFromProperties(Properties props) {
		Automobile auto = new Automobile();
		
		auto.setNumOfProperties(props.getNumOfProperties());
		auto.setBasePrice(props.getBasePrice());
		auto.setMake(props.getMake());
		auto.setModel(props.getModel());
		auto.setYear(props.getYear());
		auto.setOpset(props.getOpset());

		return auto;
	}
	
	// Creates file-paths from Automobile name
	// replaces any spaces in the Car's name with '_' to make path handling easier +
	// system portable
	public String pathCreate(String path) {
		String newPath = new String();

		for (int i = 0; i < path.length(); i++) {
			if (path.charAt(i) != ' ')
				newPath += path.charAt(i);
			else if (path.charAt(i) == ' ')
				newPath += "_";

			newPath += path.charAt(i);
		}

		return path;
	}

	// Outputs Serialized Automobile
	public void serialOutput(String path, Automobile auto) {
		// path = pathCreate(path);
		try {
			File file = new File(path);

			if (DEBUG) {
				System.out.println("=========================================");
				System.out.println("\nCreating Serial File");
				System.out.println("Path: " + path + auto.getMakeModelYear() + ".dat");
			}

			FileOutputStream outStream = new FileOutputStream(path + auto.getMakeModelYear() + ".dat");
			ObjectOutputStream objectOutputFile = new ObjectOutputStream(outStream);

			objectOutputFile.writeObject(auto);
			objectOutputFile.close();
			outStream.close();

		} catch (IOException e) {
			System.out.println("Error -- " + e.toString());
		}

		if (DEBUG)
			System.out.println("Object successfully serialized!\n\n");
	}

	// Deserializes .dat file into an Automobile object
	public Automobile serialInput(String path) {
		Automobile auto = new Automobile();
		// path = pathCreate(path);

		if (DEBUG) {
			System.out.println("Reading in serial file");
			System.out.println("Path: " + path);
		}

		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(path));
			auto = (Automobile) in.readObject();
			in.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (DEBUG)
			System.out.println("Serial file successfully read!");

		return auto;
	}

	public void errorLog(int errNum, String errMsg) {

		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		String message = (timeStamp + "\tERROR # " + errNum + ": " + errMsg + "\n");
		String path = ("ErrorLogs/");
		if (DEBUG)
			System.out.println("Writing error message to " + path);

		try {
			File file = new File("ErrorLogs/");
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ERROR_LOG.txt", true)));
			writer.write(message);
			writer.close();
			if (DEBUG)
				System.out.println("Successfully Logged!");

		} catch (IOException e) {
			System.out.println("Error -- " + e.toString());
		}
	}
}
