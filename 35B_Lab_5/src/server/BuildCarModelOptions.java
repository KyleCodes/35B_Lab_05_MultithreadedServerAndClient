package server;

import java.io.Serializable;

import adapter.BuildAuto;
import adapter.DataBaseAuto;
import debug.Debuggable;
import model.Automobile;
import util.FileIO;
import model.Properties;

public class BuildCarModelOptions implements Debuggable, Serializable {
	
	/////////////////////////////////////////
	// METHODS
	public boolean buildAutoFromProperties(Properties props) {
		AutoServer autoServer = new BuildAuto();
		
		if (autoServer.buildAutoFromProperties(props))
			return true;
		else
			return false;
	}
	
	public String getModelList() {
		DataBaseAuto lhm = new BuildAuto();
		System.out.println(lhm.toString());
		return lhm.toString();	
	}
	
	public void loadAutos() { 
		String[] fileNames = {"ford_focus_wgn_ztw_2010.txt", "bmw_330i_coupe_2004.txt"};
		FileIO FIO = new FileIO();
		DataBaseAuto lhm = new BuildAuto();
		Automobile auto;
		
		for (int i = 0; i < fileNames.length; i++) {
			auto = FIO.buildAutomobile(fileNames[i]);
			if (auto == null)
				System.out.println("Auto is null");
			else
				//auto.print();
			lhm.addAuto(auto.getMakeModelYear(), auto);
		}

	}
	
}
