package adapter;

public interface UpdateAuto {
	public void updateOptionSetName(String modelName, String OptionSetName, String newName);

	public void updateOptionPrice(String modelName, String OptionSetName, String Option, int price);
	
}
