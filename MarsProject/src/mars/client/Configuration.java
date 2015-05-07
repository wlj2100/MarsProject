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
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.cellview.client.HasKeyboardSelectionPolicy.KeyboardSelectionPolicy;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * @author Liangji
 */
public class Configuration {
	private final ArrayList<Module> minConfig1 = new ArrayList<Module>();
	private final ArrayList<Module> minConfig2 = new ArrayList<Module>();

	private final ArrayList<Module> list = new ArrayList<Module>();
	private final ArrayList<String> keyList = new ArrayList<String>();
	private int addCounter;
	private Storage localConfig = Storage.getLocalStorageIfSupported();
	private VerticalPanel vp = new VerticalPanel();
	private final FlexTable t = new FlexTable();
	private final CellTable<String> table = new CellTable<String>();
	private final SoundController soundController = new SoundController();
	private final Sound removeSound = soundController.createSound(
			Sound.MIME_TYPE_AUDIO_MPEG_MP3, "voice/configRemove.mp3");
	private final Sound removeAllSound = soundController.createSound(
			Sound.MIME_TYPE_AUDIO_MPEG_MP3, "voice/removeAllConfig.mp3");
	private final Sound addSound = soundController.createSound(
			Sound.MIME_TYPE_AUDIO_MPEG_MP3, "voice/addModule.mp3");
	private final Sound finishSound = soundController.createSound(
			Sound.MIME_TYPE_AUDIO_MPEG_MP3, "voice/configSave.mp3");
	private SimplePager pager;
	private int quality;

	public Configuration() {
		minConfig1.add(new Module(1, 0, 0, 4, 4));
		minConfig1.add(new Module(1, 0, 0, 4, 5));
		minConfig1.add(new Module(1, 0, 0, 4, 6));
		minConfig1.add(new Module(80, 0, 0, 5, 4));
		minConfig1.add(new Module(154, 0, 0, 5, 5));
		minConfig1.add(new Module(174, 0, 0, 4, 7));
		minConfig1.add(new Module(100, 0, 0, 3, 6));
		minConfig1.add(new Module(144, 0, 0, 4, 3));
		minConfig1.add(new Module(120, 0, 0, 3, 4));
		minConfig1.add(new Module(164, 0, 0, 3, 5));

		minConfig2.add(new Module(1, 0, 0, 4, 4));
		minConfig2.add(new Module(1, 0, 0, 4, 5));
		minConfig2.add(new Module(1, 0, 0, 4, 6));
		minConfig2.add(new Module(80, 0, 0, 3, 4));
		minConfig2.add(new Module(154, 0, 0, 5, 5));
		minConfig2.add(new Module(174, 0, 0, 4, 7));
		minConfig2.add(new Module(100, 0, 0, 3, 6));
		minConfig2.add(new Module(144, 0, 0, 5, 4));
		minConfig2.add(new Module(120, 0, 0, 4, 3));
		minConfig2.add(new Module(164, 0, 0, 3, 5));

		localConfig.setItem("minConfig1", listToConfig(minConfig1));
		localConfig.setItem("minConfig2", listToConfig(minConfig2));
		addCounter = 0;

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
		status.setSelectedIndex(0);

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
		
		final Label qualityLabel = new Label("please input key");
		final TextBox qualityText = new TextBox();
		final Button checkQuality = new Button("config check");
		
		t.setWidget(4, 0, qualityLabel);
		t.setWidget(4, 1, qualityText);
		t.setWidget(4, 2, checkQuality);

		remove.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				if (localConfig != null) {
					String removeString = removeThisCode.getText();
					Window.alert("config removed: "
							+ localConfig.getItem(removeString));
					localConfig.removeItem(removeString);
					removeThisCode.setText("");
					configListtable();
					removeSound.play();
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
				configListtable();
				removeAllSound.play();
			}
		});
		add.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				int icode = Integer.parseInt(code.getText());
				int ixcord = Integer.parseInt(xcord.getText());
				int iycord = Integer.parseInt(ycord.getText());

				if (icode < 1 || icode > 190)
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
							addSound.play();
						} else {
							Window.alert("duplicated module, cannot add");
						}
					} else {
						Window.alert("local storage does not exist!");
					}
					// end the get module from local storage method/section of
					// code
					code.setText("");
					status.setSelectedIndex(0);
					orientation.setSelectedIndex(0);
					xcord.setText("");
					ycord.setText("");
				}
			}
		});
		finish.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				Window.alert(listToConfig(list));
				for (int i = 0; i < localConfig.getLength(); i++) {
					if (("c" + Integer.toString(addCounter)).equals(localConfig.key(i))) {
						addCounter++;
					}
				}
				localConfig.setItem("c" + Integer.toString(addCounter),
						listToConfig(list));
				addCounter++;
				list.clear();
				configListtable();
				finishSound.play();
			}
		});
		checkQuality.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				Window.alert(getConfigQuality(qualityText.getText()));
			}
		});
		vp.add(t);
		vp.add(pager);
		vp.add(table);
		return vp;
	}

	private void configKey() {
		keyList.clear();
		for (int i = 0; i < localConfig.getLength(); i++) {
			if (localConfig.key(i).startsWith("c") || localConfig.key(i).startsWith("m")) {
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
		// table.setRowCount(keyList.size(), true);

		// Push the data into the widget.
		// table.setRowData(0, keyList);
		ListDataProvider<String> dataProvider = new ListDataProvider<String>();
		dataProvider.addDataDisplay(table);
		dataProvider.setList(keyList);
		pager = new SimplePager();
		pager.setDisplay(table);
		pager.setPageSize(10);
	}

	public ArrayList<String> getConfigList() {
		return keyList;
	}

	public String getConfigQuality(String key) {
		setQuality(100);
		StringBuilder aStringBuilder = new StringBuilder();
		ArrayList<Module> modulelist = this.ConfigToList(key);
		// rule1 Sanitation not next to Canteen
		if (isNextTo(modulelist, "Sanitation", "Canteen")) {
			aStringBuilder.append("Sanitation cannot next to Canteen!\n");
		}
		// rule2 Sanitation not next to Food & water storage
		if (isNextTo(modulelist, "Sanitation", "food")) {
			aStringBuilder.append("Sanitation cannot next to food!\n");
		}
		// rule3 Airlock not next to Dormitory
		if (isNextTo(modulelist, "AirLock", "Dormitory")) {
			aStringBuilder.append("AirLock cannot next to Dormitory!\n");
		}
		// rule8 A Gym & Relaxation module should be next to a Sanitation module
		// (horizontally)
		aStringBuilder.append(isHorizonNext(modulelist, "Gym", "Sanitation"));
		// rule9 One Medical module should be next to one Airlock module
		// (diagonal)
		aStringBuilder.append(isDiagonalNext(modulelist, "Medical", "AirLock"));
		/*
		 * 10. Food & Water storage modules should be located near Canteen
		 * modules. a. near means ��no more than 3 Plain modules away�� 11.
		 * Dormitory wings should have Sanitation modules in the ratio of 1
		 * Sanitation module for every 2 Dormitory modules.
		 */
		// get score
		aStringBuilder.append("quality score is: ").append(
				Integer.toString(getQuality()));
		return aStringBuilder.toString();
	}
	
	private String isDiagonalNext(ArrayList<Module> aList, String type1, String type2) {
		boolean flag = true;
		for (int i = 0; i < aList.size(); i++) {
			if (aList.get(i).getType().equals(type1)) {
				for (int j = 0; j < aList.size(); j++) {
					if (aList.get(j).getType().equals(type2)) {
						if (!(getDiffX(aList.get(i), aList.get(j)) == 1 && getDiffY(aList.get(i), aList.get(j)) == 1)) {
							flag = false;
							setQuality(getQuality() - 2);
						} 
					}
				}
			}
		}
		if (flag) {
			return "";
		} else {
			return "One Medical module should be next to one Airlock module!\n";
		}
	}
	private String isHorizonNext(ArrayList<Module> aList, String type1, String type2) {
		boolean flag = true;
		for (int i = 0; i < aList.size(); i++) {
			if (aList.get(i).getType().equals(type1)) {
				for (int j = 0; j < aList.size(); j++) {
					if (aList.get(j).getType().equals(type2)) {
						//Window.alert(Integer.toString(getDiffX(aList.get(i), aList.get(j))));
						if (!(getDiffX(aList.get(i), aList.get(j)) == 1 && getDiffY(aList.get(i), aList.get(j)) == 0)) {
							flag = false;
							setQuality(getQuality() - 2);
						} 
					}
				}
			}
		}
		if (flag) {
			return "";
		} else {
			return "Gym & Relaxation module should be horizontally next to a Sanitation module!\n";
		}
	}

	private boolean isNextTo(ArrayList<Module> aList, String type1, String type2) {
		boolean flag = false;
		for (int i = 0; i < aList.size(); i++) {
			if (aList.get(i).getType().equals(type1)) {
				for (int j = 0; j < aList.size(); j++) {
					if (aList.get(j).getType().equals(type2)) {
						if (isNextToHelper(aList.get(i), aList.get(j))) {
							setQuality(getQuality() - 5);
							flag = true;
						}
					}
				}
			}
		}
		return flag;

	}

	private boolean isNextToHelper(Module module1, Module module2) {
		
		if ((getDiffX(module1, module2) + getDiffY(module1, module2)) > 2) {
			return false;
		} else if ((getDiffX(module1, module2) + getDiffY(module1, module2)) == 2){
			if ((getDiffX(module1, module2) == 0 && getDiffY(module1, module2) == 2)
					|| (getDiffX(module1, module2) == 2 && getDiffY(module1,
							module2) == 0)) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}

	private int getDiffX(Module module1, Module module2) {
		//Window.alert(Integer.toString(Math.abs(module1.getX() - module2.getX())));
		return Math.abs(module1.getX() - module2.getX());
	}

	private int getDiffY(Module module1, Module module2) {
		//Window.alert(Integer.toString(Math.abs(module1.getY() - module2.getY())));
		return Math.abs(module1.getY() - module2.getY());
	}

	/**
	 * @return the quality
	 */
	private int getQuality() {
		return quality;
	}

	/**
	 * @param quality
	 *            the quality to set
	 */
	private void setQuality(int quality) {
		this.quality = quality;
	}
}
