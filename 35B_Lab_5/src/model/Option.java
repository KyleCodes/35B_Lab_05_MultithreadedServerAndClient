package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import debug.Debuggable;

public class Option implements Debuggable, Serializable {
	private String optionTitle;
	private int price;

	protected Option() {
		if (DEBUG)
			System.out.println("Constructing default option");
	}

	protected Option(String optionTitle, int price) {
		if (DEBUG)
			System.out.println("Constructing Option");
		this.optionTitle = optionTitle;
		this.price = price;
	}

	protected ArrayList<Option> returnOptionArray(String[] optionTitles, int[] prices) // returns an array of Options, using
	{																			// input from text file that's been sorted into name/price arrays
		if (DEBUG)
			System.out.println("Creating array of Options");
		int numOfOptions = optionTitles.length;
		Option[] arr = new Option[numOfOptions];

		for (int i = 0; i < numOfOptions; i++)
			arr[i] = new Option(optionTitles[i], prices[i]);

		return new ArrayList<Option>(Arrays.asList(arr));
	}

	protected String getOptionTitle() {
		return optionTitle;
	}

	protected void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	protected int getPrice() {
		return price;
	}

	protected void setPrice(int price) {
		this.price = price;
	}

	protected void print() {
		System.out.printf("\t   - $%s \t%s		\n", this.price, this.optionTitle);
	}
}