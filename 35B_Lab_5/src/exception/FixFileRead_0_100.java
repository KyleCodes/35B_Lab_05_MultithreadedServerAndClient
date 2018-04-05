package exception;

import java.util.Scanner;

import adapter.BuildAuto;
import adapter.CreateAuto;
import adapter.UpdateAuto;
import debug.Debuggable;
import util.FileIO;

public class FixFileRead_0_100 implements Debuggable {
	
	private BuildAuto a1 = new BuildAuto();
	private Scanner input = new Scanner(System.in);
	private FileIO FIO = new FileIO();
	
	public void fix1() {
		String meta[] = new String[3];
		System.out.println("ERROR: INVALID METADATA");
		System.out.println("Please enter by hand:");
		System.out.print("Name  :");
		meta[0] =  input.nextLine();
		System.out.print("Base $:");
		meta[1] =  input.nextLine();
		System.out.print("OPSets:");
		meta[2] =  input.nextLine();
		
		FIO.setMetaData(meta);
	}
	
	public void fix2() {
		System.out.println("ODD NUMBER OF OPTIONS, SUBTRACTING ONE");
		System.out.println("\t Hit enter to confirm");
		input.nextLine();
		FIO.decrementNumOfChoices();
	}
	
	public void fix3() {
		int num = FIO.getNumOfOpsets();
		String titles[] = new String[num];
		System.out.println("ERROR: NUMBER OF OPSET NAMES /= NUMBER IN FIRST LINE");
		System.out.println("Please enter " + num + " Titles");
		
		for (int i = 0; i < num; i++)
		{
			System.out.print( (i + 1) + ": ");
			titles[i] = input.nextLine();
		}
		FIO.setOptionTitles(titles);
		FIO.setNumOfProperties(num);
	}
	
	public void fix4() {
		System.out.println("");
	}
	
	public void fix5() {
		System.out.println("");
	}
}
