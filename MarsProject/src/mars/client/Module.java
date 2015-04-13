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
    	return;
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
    	//TODO
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
    public String returnType() {
    	//TODO
    	return null;
    }
    
    @Override
    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     * "code:1,status:\"undamaged\",turns:0,X:5,Y:5"
     * "{code:1,status:\"undamaged\",turns:0,X:5,Y:5},"
     */
    public String toString(){
		return status;
    	// todo
    }
}
