/**
 * 
 */
package mars.client;

import java.util.ArrayList;

import com.google.gwt.storage.client.Storage;

/**
 * @author Liangji
 * this class is for save and load habitat configuration
 * the astronaut should be able to sav a selected configuration, exit the system, then load the saved configuration
 * related to HTML5 local storage
 */
public class Configuration {
	private final ArrayList<Module> minConfig1 = new ArrayList<Module>();
	private final ArrayList<Module> minConfig2 = new ArrayList<Module>();
	private final ArrayList<Module> maxConfig1 = new ArrayList<Module>();
	private final ArrayList<Module> maxConfig2 = new ArrayList<Module>();
	private int i;
	private ArrayList<Module> list;
	private Storage localConfig= Storage.getLocalStorageIfSupported();
	public Configuration() {
		localConfig= Storage.getLocalStorageIfSupported();
		//TODO
	}
	
	public void  addConfig(ArrayList<Module> list) {
		this.list = list;
		localConfig.setItem("config" + Integer.toString(i), listToConfig());
		i++;
	}
	
	public ArrayList<Module> getConfig() {
		//TODO
		return list;
	}
	
	public String listToConfig() {
		StringBuilder aStringBuilder = new StringBuilder();
		//TODO
		return aStringBuilder.toString();
	}
}
