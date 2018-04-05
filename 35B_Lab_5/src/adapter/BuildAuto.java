package adapter;

import java.util.Properties;

import model.Automobile;
import server.AutoServer;

public class BuildAuto extends ProxyAutomobile implements CreateAuto, UpdateAuto, ChooseAuto, DataBaseAuto, MultiThreadUpdate, AutoServer 
{

}
