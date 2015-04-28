package mars.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;

/**
 * @author Kyle, Liangji
 */
public class ModuleLogging {
		
	private Module currentModule;
	private Storage moduleStore;
	private ArrayList<Module> list;
	private Module storedModule;
	
	public ModuleLogging() {
		moduleStore = Storage.getLocalStorageIfSupported(); 
		list= new ArrayList<Module>();
	}
	
	public Storage getModuleLocal() {
		return moduleStore;
	}
	
	 public ArrayList<Module> getSavedModules()
	 {
		 if (moduleStore != null) { 
			 list.clear();
		//NOTE: when we iterate through this, we can possibly add the modules to the "currentmodulelist" or whatever
			 for (int i = 0; i < moduleStore.getLength(); i += 1) { 
				 String key = moduleStore.key(i);
					if (!key.startsWith("c")) {
						// Window.alert(key);
						String value = moduleStore.getItem(key);
						// Window.alert(value);
						list.add(new Module(value));
						Window.alert(list.get(list.size() - 1).toString());
					}
			 }
		 } else{
			 Window.alert("MODULE STORAGE IS NULL");
		 }
		 return list;
	 }
	 
	 
	public FlexTable getTable() {

		// Make a new list box, adding a few items to it.
		
	    final TextBox code = new TextBox();
	
	    final ListBox status = new ListBox();
	    status.addItem("Undamaged");
	    status.addItem("Repairable");
	    status.addItem("Unrepairable");
	    status.addItem("Undefined");
	    status.setVisibleItemCount(1);
	    status.setSelectedIndex(3);

	    final ListBox orientation = new ListBox();
	    orientation.addItem("0");
	    orientation.addItem("1");
	    orientation.addItem("2");
	    orientation.setVisibleItemCount(1);

	    final TextBox xcord = new TextBox();

	    final TextBox ycord = new TextBox();

	    final Button save = new Button("Save");
	    final FlexTable t = new FlexTable();
	    t.setText(0, 0, "Module Code");
	    t.setText(0, 1, "Module Status");
	    t.setText(0, 2, "Module Orientation");
	    t.setText(0, 3, "Module X coordinate");
	    t.setText(0, 4, "Module Y coordinate");
	    t.setText(0, 5, "Save Module");

	  //THE FIRST LIST BOX
	    t.setWidget(1, 0, code);
	  //THE SECOND LIST BOX
	    t.setWidget(1, 1, status);
	    //THE BUTTON
	    t.setWidget(1, 2, orientation);
	    t.setWidget(1, 3, xcord);
	    t.setWidget(1, 4, ycord);
	    t.setWidget(1, 5, save);


	    //making the remove module by code and remove all modules buttons
	    final Button removeAll = new Button("Remove All");
	    final Button remove = new Button("Remove");
	    final TextBox removeThisCode = new TextBox();
	    //add the removal stuff to the table
	    
	    t.setText(2,0, "Remove by code");
	    t.setText(2,1,"");
	    t.setText(2,2,"Delete all Modules");
	    
	    t.setWidget(3, 0, removeThisCode);
	    t.setWidget(3, 1, remove);
	    t.setWidget(3, 2, removeAll);
	    
	    remove.addClickHandler(new ClickHandler() {
			 public void onClick(final ClickEvent event) { 
					 if (moduleStore != null) {
						 Module removedModule = new Module(moduleStore.getItem(removeThisCode.getText()));
						 Window.alert("module removed: "+moduleStore.getItem(removeThisCode.getText()));
						 list.remove(removedModule);
						 moduleStore.removeItem(removeThisCode.getText());
						 removeThisCode.setText("");
					 }
					 }
			 }
		 );
		 removeAll.addClickHandler(new ClickHandler() {
			 public void onClick(final ClickEvent event) {
					 if (moduleStore != null) {
						 moduleStore.clear();
						 list.clear();
					 }
				 }
			 }
		 );
		//button listener for save button to make and save module
		save.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				int icode = Integer.parseInt(code.getText());
				int ixcord = Integer.parseInt(xcord.getText());
				int iycord = Integer.parseInt(ycord.getText());
				
				if(icode < 0 || icode > 200)
					Window.alert("Invalid Code!");
				else if (ixcord < 0 || ixcord > 100 || iycord < 0 || iycord > 50)
					Window.alert("Invalid coordinates!");
				else
				{
					//start of the bounds checking method
					Window.alert(Integer.toString(status.getSelectedIndex()));
					if (moduleStore != null) {
						if (moduleStore.getItem(code.getText())==null) {
							currentModule = new Module(Integer.parseInt(code.getText()), status.getSelectedIndex(), orientation.getSelectedIndex(), Integer.parseInt(xcord.getText()), Integer.parseInt(ycord.getText()));
							Window.alert(currentModule.toString());
							moduleStore.setItem(Integer.toString(currentModule.getCode()), currentModule.toString());
							list.add(currentModule);
							Window.alert("Module Logged!");
						} else {
							Window.alert("duplicated module, cannot login");
						}
						
					} else {
						Window.alert("local storage does not exist!");
					}
					 		    
					 //end the get module from local storage method/section of code
					code.setText("");
					status.setSelectedIndex(3);
					orientation.setSelectedIndex(0);
					xcord.setText("");
					ycord.setText("");
					
				}
			}
		});
		return t;
	}
	

}
