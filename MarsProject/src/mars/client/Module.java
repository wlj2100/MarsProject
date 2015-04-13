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
    public Module(int code, String status, int turns, int xcoord, int ycoord) {
    	super();
    	this.code=code;
    	this.status=status;
    	this.turns=turns;
    	this.xcoord=xcoord;
    	this.ycoord=xcoord;
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
