package adapter;

import model.Automobile;

public interface ChooseAuto {
	public void setChoice(String opsetName, String option);
	
	public void removeChoice(String option);
	
	public void getChoice(String option);
	
	public void printChoices();
	
	public void setOwner(String name);
	
	public String getMakeModelYear();
	
}
