package adapter;

import model.Automobile;

public interface DataBaseAuto {
	public void addAuto(String makeModelYear, Automobile auto);
	
	public Automobile retrieveAuto(String makeModelYear);
	
	public void updateAuto(String makeModelYear, Automobile auto);
	
	public void deleteAuto(String makeModelYear);
	
	public void printList();
	
	public String toString();
}
