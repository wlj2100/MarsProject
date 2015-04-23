/**
 * 
 */
package mars.client;
import com.google.gwt.storage.client.Storage;
/**
 * @author Liangji
 *
 */
public class ModuleLocalStorage {
	Storage moduleStore;
	
	//constructor
	public ModuleLocalStorage() {
		moduleStore=Storage.getLocalStorageIfSupported();
	}
	
	//key = type+number
	public void addModule(String type, Module module) {
		moduleStore.setItem(type+Integer.toString(moduleStore.getLength()+1), module.toString());
	}
	
	public String getKey(int i) {
		return moduleStore.key(i);
	}
	
	public String getModule(String key) {
		return moduleStore.getItem(key);
	}
	
	public void deleteModule(String key) {
		moduleStore.removeItem(key);
	}
}
