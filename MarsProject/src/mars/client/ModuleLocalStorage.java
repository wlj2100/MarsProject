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
	public void addModule(Module module) {
		moduleStore.setItem(module.returnType()+Integer.toString(moduleStore.getLength()+1), module.toString());
	}
	//todo
}
