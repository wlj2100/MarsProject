package mars.client;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;

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
		if(this.status == 1)
			theString = "Repairable";
		if(this.status == 2)
			theString = "Unrepairable";
		else theString = "";
		return theString;
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
    	super();
    	JSONObject jO=(JSONObject)JSONParser.parseLenient(moduleString);
    	JSONNumber jN;
    	jN = (JSONNumber)jO.get("code");
    	this.code = (int)jN.doubleValue();
    	jN = (JSONNumber)jO.get("status");
    	this.status = (int)jN.doubleValue();
    	jN = (JSONNumber)jO.get("turns");
    	this.turns = (int)jN.doubleValue();
    	jN = (JSONNumber)jO.get("X");
    	this.xcoord = (int)jN.doubleValue();
    	jN = (JSONNumber)jO.get("Y");
    	this.ycoord = (int)jN.doubleValue();
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
    	aStringBuilder.append("code:").append(Integer.toString(this.code)).append(", status\"")
    	.append(this.theString).append("\", turns:").append(Integer.toString(this.turns)).append(", X:")
    	.append(Integer.toString(this.xcoord)).append(", Y:").append(Integer.toString(this.ycoord));
		return aStringBuilder.toString();
    }
}
