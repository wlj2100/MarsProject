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
    String status;
    int turns;
    int xcoord;
    int ycoord;
    
    // constructor
    public Module() {
    	super();
    }
    public Module(int code, String status, int turns, int xcoord, int ycoord) {
    	super();
    	this.code=code;
    	this.status=status;
    	this.turns=turns;
    	this.xcoord=xcoord;
    	this.ycoord=xcoord;
    }
    public Module(String moduleString) {
    	super();
    	JSONObject jO=(JSONObject)JSONParser.parseLenient(moduleString);
    	JSONNumber jN;
    	JSONString jS;
    	jN = (JSONNumber)jO.get("code");
    	this.code = (int)jN.doubleValue();
    	jS = (JSONString)jO.get("status");
    	this.status = jS.stringValue();
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
    public void setStatus(String status) {
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
    	aStringBuilder.append("code:").append(Integer.toString(this.code)).append(",status\"")
    	.append(this.status).append("\",turns:").append(Integer.toString(this.turns)).append(",X:")
    	.append(Integer.toString(this.xcoord)).append(",Y:").append(Integer.toString(this.ycoord));
		return aStringBuilder.toString();
    }
}
