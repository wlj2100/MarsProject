package mars.client;

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
    	//
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
