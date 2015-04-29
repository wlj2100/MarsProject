package mars.client;


import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

/**
 * Displays the current weather status from Wunderground
 *
 * @author Kyle Freese
 *
 */
public class Weather{
    
 /* Initialization */
    
    
    private final JsonpRequestBuilder jsonp;
    private FlowPanel weatherTable;
    private String visString;
    private String tempString;
    private Image wunderLogo;
    private Label weatherHeader;
    private String stringTemp;
    private String stringVis;

  
    /**
     * The Weather Constructor.
     * Obtains a JSON Object containing the current weather conditions
     * and creates a panel to display those conditions.
     */
    public Weather(){
        
        String url= URL.encode("http://api.wunderground.com/api/a22fd2e281653ddf/conditions/q/55805.json?");
        
        
        //Next section is performing JSON request to wunderground servers and calling update methods.
        this.jsonp = new JsonpRequestBuilder();
        this.jsonp.setCallbackParam("callback");
        this.jsonp.requestObject(url,  new AsyncCallback<JavaScriptObject>() {
            public void onFailure(final Throwable caught){
                Window.alert("Json onFailure");
            }
          
            @Override          
            public void onSuccess(final JavaScriptObject s) {
                JSONObject obj = new JSONObject(s);
                update( obj.toString() );              
            }     
        });
    
        
        this.weatherTable = new FlowPanel();
        this.weatherTable.setStyleName("Weather");
        this.visString = new String("Visibility: ");
        this.tempString = new String("Temperature:  ");
        
        this.wunderLogo = new Image();
        this.wunderLogo.setUrl("images/WunderGroundLogo.jpg");
        
        this.weatherHeader = new Label("Weather Conditions:");
        
        

    }
    
 /* Methods */
  
    /**
     * Getter (Accessor) for the weather panel.
     */
    public FlowPanel getWeather() {
        return this.weatherTable;
    }
    
    /**
     * Parses a JSONObject for relevant values then 
     * adds the values to the weatherTable.
     * @param conditions_ - The current weather conditions
     */
    public void update(final String conditions_){
        
        JSONObject conditions = (JSONObject) JSONParser.parseLenient(conditions_);
        String current = conditions.get("current_observation").toString();
        conditions = (JSONObject) JSONParser.parseLenient( current );
        
        this.stringTemp = conditions.get("temp_c").toString();
        this.stringVis = conditions.get("visibility_km").toString();
        
        
        

        this.weatherTable.add(this.weatherHeader);
        this.weatherTable.add(new Label(this.tempString + "  " + this.stringTemp + " Degrees C"));
        this.weatherTable.add(new Label(this.visString + this.stringVis + " Km"));
        
        this.weatherTable.add(this.wunderLogo);
        
  
    }
    
    
}
