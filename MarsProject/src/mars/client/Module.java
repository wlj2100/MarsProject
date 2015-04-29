package mars.client;

//import javax.lang.model.type.UnknownTypeException;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;

/**
 * @author Liangji
 *
 */
public class Module {

	// module properties
	int code;
	int status;
	int turns;
	int xcoord;
	int ycoord;
	String theString;

	// accessor

	public int getCode() {
		return this.code;
	}

	public String getStringStatus() {
		return theString;
	}

	private void convertIntStatus() {
		if (this.status == 0)
			theString = "Undamaged";
		else if (this.status == 1)
			theString = "Repairable";
		else if (this.status == 2)
			theString = "Unrepairable";
		else
			theString = "Undefined";
	}

	public int getStatus() {
		return this.status;
	}

	private void convertStringStatus() {
		if (theString == "Undamaged") {
			this.status = 0;
		} else if (theString == "Repairable") {
			this.status = 1;
		} else if (theString == "Unrepairable") {
			this.status = 2;
		} else {
			this.status = 3;
		}
	}

	public int getTurns() {
		return this.turns;
	}

	public int getX() {
		return this.xcoord;
	}

	public int getY() {
		return this.ycoord;
	}

	// constructor
	public Module() {
		super();
	}

	public Module(int code, int status, int turns, int xcoord, int ycoord) {
		super();
		this.code = code;
		this.status = status;
		this.turns = turns;
		this.xcoord = xcoord;
		this.ycoord = xcoord;
		this.convertIntStatus();
	}

	public Module(String aString) {
		convert(aString);
	}

	// string constructor helper with json string
	private void convert(String moduleString) {
		JSONObject jO = (JSONObject) JSONParser.parseLenient(moduleString);
		JSONNumber jN;
		JSONString jS;
		double d;
		String s;
		jN = (JSONNumber) jO.get("code");
		d = jN.doubleValue();
		// Window.alert(Double.toString(d));
		this.code = (int) d;
		jS = (JSONString) jO.get("status");
		s = jS.stringValue();
		// Window.alert(s);
		this.theString = s;
		jN = (JSONNumber) jO.get("turns");
		d = jN.doubleValue();
		// Window.alert(Double.toString(d));
		this.turns = (int) d;
		jN = (JSONNumber) jO.get("X");
		d = jN.doubleValue();
		// Window.alert(Double.toString(d));
		this.xcoord = (int) d;
		jN = (JSONNumber) jO.get("Y");
		d = jN.doubleValue();
		// Window.alert(Double.toString(d));
		this.ycoord = (int) d;
		this.convertStringStatus();
	}

	// mutator
	public void setCode(int code) {
		this.code = code;
	}

	public void setStatus(int status) {
		this.status = status;
		this.convertIntStatus();
	}

	public void setTurns(int turns) {
		this.turns = turns;
	}

	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}

	public void setYcoord(int ycoord) {
		this.ycoord = ycoord;
	}

	public void setTheString(String string) {
		this.theString = string;
		this.convertStringStatus();
	}
    public String getImageName(){
    	if(this.code<=40 && this.code >=1){
    		return "images/Plain.jpg";
    	} else{
    		return "";
    	}
    }
    	

	// toString method
	@Override
	public String toString() {
		StringBuilder aStringBuilder = new StringBuilder();
		aStringBuilder.append("{\"code\":").append(Integer.toString(this.code))
				.append(",\"status\":\"").append(this.theString)
				.append("\",\"turns\":").append(Integer.toString(this.turns))
				.append(",\"X\":").append(Integer.toString(this.xcoord))
				.append(",\"Y\":").append(Integer.toString(this.ycoord))
				.append("}");
		return aStringBuilder.toString();
	}
}
