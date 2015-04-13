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
	final String moduleKey = "module";
	public ModuleLocalStorage() {
		moduleStore=Storage.getLocalStorageIfSupported();
	}
	public void addModule(Module module) {
		moduleStore.setItem(moduleKey+Integer.toString(moduleStore.getLength()+1), module.toString());
	}
	//todo
}
