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
 * Kyle
 */
public class ModuleLogging {
		
	private Module currentModule;
	private Storage moduleStore;
	
	public ModuleLogging() {
		moduleStore = Storage.getLocalStorageIfSupported(); 
	}
	
	public Storage getModuleLocal() {
		return moduleStore;
	}
	
	 public ArrayList<Module> getSavedModules()
	 {
		 ArrayList<Module> list= new ArrayList<Module>();
		 Storage moduleStore = Storage.getLocalStorageIfSupported();
		 moduleStore = Storage.getLocalStorageIfSupported(); 
		 if (moduleStore != null) { 
		//NOTE: when we iterate through this, we can possibly add the modules to the "currentmodulelist" or whatever
			 for (int i = 0; i < moduleStore.getLength(); i += 1) { 
				 String key = moduleStore.key(i); 
				 String value = moduleStore.getItem(key); 
				 String[] values = value.split(" ");
				 Module storedModule = new Module(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]));
			 	list.add(storedModule);
			 	Window.alert(storedModule.toString());
			 }
		 }
		 else{
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

	    Button save = new Button("Save");
	    FlexTable t = new FlexTable();
	    t.setText(0, 0, "Module Code");
	    t.setText(0, 1, "Module Status");
	    t.setText(0, 2, "Module Orientation");
	    t.setText(0, 3, "Module X coordinate");
	    t.setText(0, 4, "Module Y coordinate");
	    t.setText(2, 2, "Save Module");

	  //THE FIRST LIST BOX
	    t.setWidget(1, 0, code);
	  //THE SECOND LIST BOX
	    t.setWidget(1, 1, status);
	    //THE BUTTON
	    t.setWidget(1, 2, orientation);
	    t.setWidget(1, 3, xcord);
	    t.setWidget(1, 4, ycord);
	    t.setWidget(3, 2, save);


	    //making the remove module by code and remove all modules buttons
	    final Button removeAll = new Button("Remove All");
	    final Button remove = new Button("Remove");
	    final TextBox removeThisCode = new TextBox();
	    //add the removal stuff to the table
	    
	    t.setText(4,0, "Remove by code");
	    t.setText(4,1,"");
	    t.setText(4,2,"Delete all Modules");
	    
	    t.setWidget(5, 0, removeThisCode);
	    t.setWidget(5, 1, remove);
	    t.setWidget(5, 2, removeAll);
	    
	    remove.addClickHandler(new ClickHandler() {
			 public void onClick(final ClickEvent event) {
					Storage moduleStore = Storage.getLocalStorageIfSupported(); 
					 if (moduleStore != null) {
						 moduleStore.removeItem(removeThisCode.getText());
						 removeThisCode.setText("");
					 }
					 }
			 }
		 );
		 removeAll.addClickHandler(new ClickHandler() {
			 public void onClick(final ClickEvent event) {
					Storage moduleStore = Storage.getLocalStorageIfSupported(); 
					 if (moduleStore != null) {
					 moduleStore.clear();
					 }
					 }
			 }
		 );
	    	    //RootPanel.get().add(t);
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
					currentModule = new Module(Integer.parseInt(code.getText()), status.getSelectedIndex(), orientation.getSelectedIndex(), Integer.parseInt(xcord.getText()), Integer.parseInt(ycord.getText()));
					
					if (moduleStore != null) {
						moduleStore.setItem(Integer.toString(currentModule.getCode()), currentModule.toString());
						Window.alert(currentModule.toString());
					}
					 		    
					 //end the get module from local storage method/section of code
					code.setText("");
					status.setSelectedIndex(3);
					orientation.setSelectedIndex(0);
					xcord.setText("");
					ycord.setText("");
					Window.alert("Module Logged!");
				}
			}
		});
		return t;
	}
	

}
