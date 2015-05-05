package mars.client;

import java.util.ArrayList;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * @author Liangji
 */
public class Configuration {
	private final ArrayList<Module> minConfig1 = new ArrayList<Module>();
	private final ArrayList<Module> minConfig2 = new ArrayList<Module>();
	private final ArrayList<Module> maxConfig1 = new ArrayList<Module>();
	private final ArrayList<Module> maxConfig2 = new ArrayList<Module>();
	private final ArrayList<Module> list = new ArrayList<Module>();
	private final ArrayList<String> keyList = new ArrayList<String>();
	private int addCounter;
	private Storage localConfig = Storage.getLocalStorageIfSupported();
	private VerticalPanel vp = new VerticalPanel();
	private final FlexTable t = new FlexTable();
	private final CellTable<String> table = new CellTable<String>();
	private final SoundController soundController = new SoundController();
	private final Sound removeSound = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
			"voice/test.mp3");
	private final Sound removeAllSound = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
			"voice/test.mp3");
	private final Sound addSound = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
			"voice/test.mp3");
	private final Sound finishSound = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
			"voice/test.mp3");

	public Configuration() {
		minConfig1.add(new Module(1,1,1,1,1));
		minConfig1.add(new Module(51,1,1,100,200));
		minConfig2.add(new Module(71,1,1,0,0));
		localConfig.setItem("minConfig1", listToConfig(minConfig1));
		localConfig.setItem("minConfig2", listToConfig(minConfig2));
		localConfig.setItem("maxConfig1", listToConfig(maxConfig1));
		localConfig.setItem("maxConfig2", listToConfig(maxConfig2));
		addCounter = 0;
		
		// TODO
	}
	

	public VerticalPanel getConfigPanel() {
		configListtable();
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

		final Button add = new Button("add");
		final Button finish = new Button("finish");

		t.setText(0, 0, "Module Code");
		t.setText(0, 1, "Module Status");
		t.setText(0, 2, "Module Orientation");
		t.setText(0, 3, "Module X coordinate");
		t.setText(0, 4, "Module Y coordinate");
		t.setText(0, 5, "add Module");
		t.setText(0, 6, "finish adding");

		// THE FIRST LIST BOX
		t.setWidget(1, 0, code);
		// THE SECOND LIST BOX
		t.setWidget(1, 1, status);
		// THE BUTTON
		t.setWidget(1, 2, orientation);
		t.setWidget(1, 3, xcord);
		t.setWidget(1, 4, ycord);
		t.setWidget(1, 5, add);
		t.setWidget(1, 6, finish);

		// making the remove module by code and remove all modules buttons
		final Button removeAll = new Button("Remove All Config");
		final Button remove = new Button("Remove");
		final TextBox removeThisCode = new TextBox();
		// add the removal stuff to the table

		t.setText(2, 0, "Remove by code");
		t.setText(2, 1, "Remove the Config");
		t.setText(2, 2, "Delete all Config");

		t.setWidget(3, 0, removeThisCode);
		t.setWidget(3, 1, remove);
		t.setWidget(3, 2, removeAll);

		remove.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				if (localConfig != null) {
					String removeString = removeThisCode.getText();
					Window.alert("config removed: "
							+ localConfig.getItem(removeString));
					localConfig.removeItem(removeString);
					removeThisCode.setText("");
					configListtable();
					if (removeSound.play()) {
						Window.alert("sound played");
					} else {
						Window.alert("sound does not played");
					}
				}
			}
		});
		removeAll.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				if (localConfig != null) {
					for (int i = 0; i < localConfig.getLength(); i++) {
						if ((localConfig.key(i)).startsWith("c")) {
							localConfig.removeItem(localConfig.key(i));
						}
					}
				}
				if (removeAllSound.play()) {
					Window.alert("sound played");
				} else {
					Window.alert("sound does not played");
				}
				configListtable();
			}
		});
		add.addClickHandler(new ClickHandler() {
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
					if (localConfig != null) {
						boolean flag = false;
						Module currentModule = new Module(icode, status
								.getSelectedIndex(), orientation
								.getSelectedIndex(), ixcord, iycord);
						// avoid duplicate module
						if (!list.isEmpty()) {
							for (int i = 0; i < list.size(); i++) {
								if ((list.get(i)).getCode() == currentModule
										.getCode()) {
									flag = true;
									break;
								}
							}
						}
						if (!flag) {
							list.add(currentModule);
							Window.alert("Module added!");
							if (addSound.play()) {
								Window.alert("sound played");
							} else {
								Window.alert("sound does not played");
							}
						} else {
							Window.alert("duplicated module, cannot add");
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
		finish.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				Window.alert(listToConfig(list));
				localConfig.setItem("c" + Integer.toString(addCounter),
						listToConfig(list));
				addCounter++;
				list.clear();
				configListtable();
				if (finishSound.play()) {
					Window.alert("sound played");
				} else {
					Window.alert("sound does not played");
				}
			}
		});
		vp.add(t);
		vp.add(table);
		return vp;
	}

	private void configKey() {
		keyList.clear();
		for (int i = 0; i < localConfig.getLength(); i++) {
			if (localConfig.key(i).startsWith("c")) {
				keyList.add(localConfig.key(i));
			}
		}
	}

	public ArrayList<Module> ConfigToList(String key) {
		ArrayList<Module> list = new ArrayList<Module>();
		String configString = localConfig.getItem(key);
		JSONArray jA = null;
		try {
			jA = (JSONArray) JSONParser.parseLenient(configString);
		} catch (NullPointerException e1) {
			Window.alert("null" + e1.getMessage());
		} catch (IllegalArgumentException e2) {
			Window.alert("illegal" + e2.getMessage());
		}
		for (int i = 0; i < jA.size(); i++) {
			JSONObject jO = (JSONObject) jA.get(i);
			JSONNumber jN;
			JSONString jS;
			Module module = new Module();
			jN = (JSONNumber) jO.get("code");
			module.setCode((int) jN.doubleValue());
			jS = (JSONString) jO.get("status");
			module.setTheString(jS.stringValue());
			jN = (JSONNumber) jO.get("turns");
			module.setTurns((int) jN.doubleValue());
			jN = (JSONNumber) jO.get("X");
			module.setXcoord((int) jN.doubleValue());
			jN = (JSONNumber) jO.get("Y");
			module.setYcoord((int) jN.doubleValue());
			list.add(module);
		}
		return list;
	}

	private String listToConfig(ArrayList<Module> list) {
		StringBuilder aStringBuilder = new StringBuilder();
		aStringBuilder.append("[");
		for (int i = 0; i < list.size(); i++) {
			aStringBuilder.append((list.get(i)).toString()).append(",");
		}
		// delete extra ","
		aStringBuilder.deleteCharAt(aStringBuilder.length() - 1);
		aStringBuilder.append("]");
		return aStringBuilder.toString();
	}

	// this method shows all the logged configuration
	public void configListtable() {
		configKey();
		// clean the table
		while (table.getColumnCount() > 0) {
			table.removeColumn(0);
		}

		table.setKeyboardSelectionPolicy(KeyboardSelectionPolicy.ENABLED);

		// Add a text column to show the code.
		TextColumn<String> keyColumn = new TextColumn<String>() {
			@Override
			public String getValue(String object) {
				return object;
			}
		};
		table.addColumn(keyColumn, "config name");

		// Add a text column to show the code.
		TextColumn<String> moduleColumn = new TextColumn<String>() {
			@Override
			public String getValue(String object) {
				return localConfig.getItem(object);
			}
		};
		table.addColumn(moduleColumn, "modules in config");

		// Add a selection model to handle user selection.
		final SingleSelectionModel<String> selectionModel = new SingleSelectionModel<String>();
		table.setSelectionModel(selectionModel);
		selectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
					public void onSelectionChange(SelectionChangeEvent event) {
						String selected = selectionModel.getSelectedObject();
						if (selected != null) {
							Window.alert("You selected: " + selected);
						}
					}
				});

		// Set the total row count. This isn't strictly necessary, but it
		// affects
		// paging calculations, so its good habit to keep the row count up to
		// date.
		table.setRowCount(keyList.size(), true);

		// Push the data into the widget.
		table.setRowData(0, keyList);
	}
	
	public ArrayList<String> getConfigList() {
		return keyList;
	}
}
