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
	public ModuleLocalStorage() {
		moduleStore=Storage.getLocalStorageIfSupported();
	}
	public void addModule(String type, Module module) {
		moduleStore.setItem(type+Integer.toString(moduleStore.getLength()+1), module.toString());
	}
	//todo
}
