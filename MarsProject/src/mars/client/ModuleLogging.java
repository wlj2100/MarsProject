package mars.client;

import java.util.ArrayList;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * @author Kyle, Liangji
 */
public class ModuleLogging {

	private Module currentModule;
	private Storage moduleStore;
	private final ArrayList<Module> list = new ArrayList<Module>();
	private final CellTable<Module> table = new CellTable<Module>();
	private final FlexTable t = new FlexTable();
	private final VerticalPanel vp = new VerticalPanel();
	private final SoundController soundController = new SoundController();
	private final Sound sound = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
			"voice/test.mp3");

	public ModuleLogging() {
		moduleStore = Storage.getLocalStorageIfSupported();
		this.SavedModulesList();
	}

	public Storage getModuleLocal() {
		return moduleStore;
	}

	private void SavedModulesList() {
		if (moduleStore != null) {
			list.clear();
			for (int i = 0; i < moduleStore.getLength(); i += 1) {
				String key = moduleStore.key(i);
				if (!key.startsWith("c") && !key.startsWith("m")) {
					String value = moduleStore.getItem(key);
					list.add(new Module(value));
					// Window.alert(list.get(list.size() - 1).toString());
				}
			}
		} else {
			Window.alert("MODULE STORAGE IS NULL");
		}
	}

	public ArrayList<Module> getSavedModules() {
		return list;
	}

	public VerticalPanel getModuleLoginPanel() {
		moduleListtable();
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

		t.setText(0, 0, "Module Code");
		t.setText(0, 1, "Module Status");
		t.setText(0, 2, "Module Orientation");
		t.setText(0, 3, "Module X coordinate");
		t.setText(0, 4, "Module Y coordinate");
		t.setText(0, 5, "Save Module");

		// THE FIRST LIST BOX
		t.setWidget(1, 0, code);
		// THE SECOND LIST BOX
		t.setWidget(1, 1, status);
		// THE BUTTON
		t.setWidget(1, 2, orientation);
		t.setWidget(1, 3, xcord);
		t.setWidget(1, 4, ycord);
		t.setWidget(1, 5, save);

		// making the remove module by code and remove all modules buttons
		final Button removeAll = new Button("Remove All");
		final Button remove = new Button("Remove");
		final TextBox removeThisCode = new TextBox();
		// add the removal stuff to the table

		t.setText(2, 0, "Remove by code");
		t.setText(2, 1, "");
		t.setText(2, 2, "Delete all Modules");

		t.setWidget(3, 0, removeThisCode);
		t.setWidget(3, 1, remove);
		t.setWidget(3, 2, removeAll);

		remove.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				if (moduleStore != null) {
					String removeString = removeThisCode.getText();

					Module removedModule = new Module(moduleStore
							.getItem(removeString));
					Window.alert("module removed: " + removedModule.toString());
					for (int i = 0; i < list.size(); i++) {
						if ((list.get(i)).getCode() == removedModule.getCode()) {
							list.remove(i);
							break;
						}
					}
					if (sound.play()) {
						Window.alert("sound played");
					} else {
						Window.alert("sound does not played");
					}
					moduleStore.removeItem(removeString);
					removeThisCode.setText("");
					moduleListtable();
				}
			}
		});
		removeAll.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				if (moduleStore != null) {
					for(int i = 0; i < list.size(); i++) {
						Module removedModule = list.get(i);
						moduleStore.removeItem(Integer.toString(removedModule.getCode()));
					}
					list.clear();
					if (sound.play()) {
						Window.alert("sound played");
					} else {
						Window.alert("sound does not played");
					}
					moduleListtable();
				}
			}
		});
		// button listener for save button to make and save module
		save.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				int icode = Integer.parseInt(code.getText());
				int ixcord = Integer.parseInt(xcord.getText());
				int iycord = Integer.parseInt(ycord.getText());

				if (icode < 0 || icode > 200)
					Window.alert("Invalid Code!");
				else if (ixcord < 0 || ixcord > 600 || iycord < 0
						|| iycord > 800)
					Window.alert("Invalid coordinates!");
				else {
					// start of the bounds checking method
					// Window.alert(Integer.toString(status.getSelectedIndex()));
					if (moduleStore != null) {
						if (moduleStore.getItem(code.getText()) == null) {
							currentModule = new Module(icode, status.getSelectedIndex(),
									orientation.getSelectedIndex(), ixcord, iycord);
							moduleStore.setItem(
									Integer.toString(currentModule.getCode()),
									currentModule.toString());
							list.add(currentModule);
							if (sound.play()) {
								Window.alert("sound played");
							} else {
								Window.alert("sound does not played");
							}
							moduleListtable();
							Window.alert("Module Logged!");
						} else {
							Window.alert("duplicated module, cannot login");
						}

					} else {
						Window.alert("local storage does not exist!");
					}

					// end the get module from local storage method/section of
					// code
					code.setText("");
					status.setSelectedIndex(3);
					orientation.setSelectedIndex(0);
					xcord.setText("");
					ycord.setText("");

				}
			}
		});
		vp.add(t);
		vp.add(table);
		return vp;
	}

	// this method shows all the logged modules
	public void moduleListtable() {
		// clean the table
		while (table.getColumnCount() > 0) {
			table.removeColumn(0);
		}

		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// Add a text column to show the code.
		TextColumn<Module> codeColumn = new TextColumn<Module>() {
			@Override
			public String getValue(Module object) {
				return Integer.toString(object.getCode());
			}
		};
		table.addColumn(codeColumn, "code");

		// Add a text column to show the status.
		TextColumn<Module> statusColumn = new TextColumn<Module>() {
			@Override
			public String getValue(Module object) {
				return object.getStringStatus();
			}
		};
		table.addColumn(statusColumn, "status");

		// Add a text column to show the turns.
		TextColumn<Module> turnsColumn = new TextColumn<Module>() {
			@Override
			public String getValue(Module object) {
				return Integer.toString(object.getTurns());
			}
		};
		table.addColumn(turnsColumn, "turns");

		// Add a text column to show the x-coor.
		TextColumn<Module> xColumn = new TextColumn<Module>() {
			@Override
			public String getValue(Module object) {
				return Integer.toString(object.getX());
			}
		};
		table.addColumn(xColumn, "X");

		// Add a text column to show the y-coor.
		TextColumn<Module> yColumn = new TextColumn<Module>() {
			@Override
			public String getValue(Module object) {
				return Integer.toString(object.getY());
			}
		};
		table.addColumn(yColumn, "Y");

		// Add a selection model to handle user selection.
		final SingleSelectionModel<Module> selectionModel = new SingleSelectionModel<Module>();
		table.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						Module selected = selectionModel.getSelectedObject();
						if (selected != null) {
							Window.alert("You selected: " + selected.toString());
						}
					}
				});

		// Set the total row count. This isn't strictly necessary, but it
		// affects
		// paging calculations, so its good habit to keep the row count up to
		// date.
		table.setRowCount(list.size(), true);

		// Push the data into the widget.
		table.setRowData(0, list);
	}
}
