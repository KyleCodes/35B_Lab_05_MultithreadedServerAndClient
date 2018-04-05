package adapter;

import model.Automobile;
import scale.EditOptionSets;
import scale.EditOptions;
import scale.PrintUpdate;

public interface MultiThreadUpdate 
{
	public void editOptions(String newOptionTitle, int newPrice, String optionSetTitle, String oldOptionTitle, Automobile auto);
	
	public void editOptionSets(String newOptionSetTitle, String oldOptionSetTitle, Automobile auto);
	
	public void printUpdate(Automobile auto);
	
}
