package mars.client;

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
	
    //accessor

    public int getCode() {
    	return this.code;
    }
    public String getStringStatus()
	{
		if(this.status == 0)
			theString = "Undamaged";
		else if(this.status == 1)
			theString = "Repairable";
		else if(this.status == 2)
			theString = "Unrepairable";
		else 
			theString = "Undefined";
		return theString;
	}
    
    public int getStatus() {
    	if (theString == "Undamaged") {
    		this.status=0;
    	} else if (theString == "Repairable") {
    		this.status=1;
    	} else if (theString == "Unrepairable") {
    		this.status = 2;
    	} else {
    		this.status = 3;
    	}
    	return this.status;
    }
    
    public int getTurns() {
    	return this.turns;
    }
    public int getX(){
    	return this.xcoord;
    }
    public int getY(){
    	return this.ycoord;
    }
    // constructor
    public Module() {
    	super();
    }
    public Module(int code, int status, int turns, int xcoord, int ycoord) {
    	super();
    	this.code=code;
    	this.status=status;
    	this.turns=turns;
    	this.xcoord=xcoord;
    	this.ycoord=xcoord;
    	this.getStringStatus();
    }
	public Module(String moduleString) {
    	Object obj = null;
    	try {
    		obj = JSONParser.parseLenient(moduleString);
    	} catch (IllegalArgumentException e) {
    		System.out.println(e);
    	}
    	System.out.println(obj);
    	JSONObject jO = (JSONObject)obj;
    	if (jO == null) {
    		System.out.println("bad json");
    	} else {
    		System.out.println("good json");
    		System.out.println(jO.toString());
    	}
    	JSONNumber jN;
    	JSONString jS;
    	jN = (JSONNumber)jO.get("code");
    	this.code = (int)jN.doubleValue();
    	System.out.println(Integer.toString(this.code));
    	jS = (JSONString)jO.get("status");
    	this.theString = jS.stringValue();
    	jN = (JSONNumber)jO.get("turns");
    	this.turns = (int)jN.doubleValue();
    	jN = (JSONNumber)jO.get("X");
    	this.xcoord = (int)jN.doubleValue();
    	jN = (JSONNumber)jO.get("Y");
    	this.ycoord = (int)jN.doubleValue();
    	
    	
    	/*
    	Window.alert(moduleString);
    	String[] values = moduleString.split(" ");
		this.code = Integer.parseInt(values[0]);
		this.theString = values[1];
		this.turns = Integer.parseInt(values[2]);
		this.xcoord = Integer.parseInt(values[3]);
		this.ycoord = Integer.parseInt(values[4]);
		this.theString = this.getStringStatus();
		*/
    }
    
    //mutator
    public void setCode(int code) {
    	this.code=code;
    }
    public void setStatus(int status) {
    	this.status=status;
    }
    public void setTurns(int turns) {
    	this.turns=turns;
    }
    public void setXcoord(int xcoord) {
    	this.xcoord=xcoord;
    }
    public void setYcoord(int ycoord) {
    	this.ycoord=ycoord;
    }
    
    //toString method
    @Override
    public String toString(){
    	StringBuilder aStringBuilder = new StringBuilder();
    	aStringBuilder.append("{\"code\":").append(Integer.toString(this.code)).append(",\"status\":\"")
    	.append(this.theString).append("\",\"turns\":").append(Integer.toString(this.turns)).append(",\"X\":")
    	.append(Integer.toString(this.xcoord)).append(",\"Y\":").append(Integer.toString(this.ycoord)).append("}");
		return aStringBuilder.toString();
    }
}
