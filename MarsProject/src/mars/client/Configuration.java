package mars.client;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author Liangji
 */
public class Configuration {
	private final ArrayList<Module> minConfig1 = new ArrayList<Module>();
	private final ArrayList<Module> minConfig2 = new ArrayList<Module>();
	private final ArrayList<Module> maxConfig1 = new ArrayList<Module>();
	private final ArrayList<Module> maxConfig2 = new ArrayList<Module>();
	private int addCounter;
	private Storage localConfig = Storage.getLocalStorageIfSupported();
	private VerticalPanel vp = new VerticalPanel();
	private final FlexTable t = new FlexTable();
	private final CellTable<Module> table = new CellTable<Module>();

	public Configuration() {
		localConfig.setItem("minConfig1", listToConfig(minConfig1));
		localConfig.setItem("minConfig2", listToConfig(minConfig2));
		localConfig.setItem("maxConfig1", listToConfig(maxConfig1));
		localConfig.setItem("maxConfig2", listToConfig(maxConfig2));
		addCounter = 0;
		// TODO
	}
	
	public VerticalPanel getConfigPanel() {
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

		final Button save = new Button("add");
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
		t.setWidget(1, 5, save);
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

		//TODO
		vp.add(t);
		vp.add(table);
		return vp;
	}
	private void addConfig(ArrayList<Module> list) {
		localConfig.setItem("c" + Integer.toString(addCounter),
				listToConfig(list));
		addCounter++;
	}

	public ArrayList<Module> ConfigToList(String key) {
		ArrayList<Module> list = new ArrayList<Module>();
		Object obj = JSONParser.parseLenient(localConfig.getItem(key));
		JSONArray jA = (JSONArray) obj;
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
		aStringBuilder.deleteCharAt(aStringBuilder.length());
		aStringBuilder.append("]");
		return aStringBuilder.toString();
	}
}
