package mars.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;

public class TenDayAlert {
	private final int TIMER = 846000000;
	private Timer t;
	public TenDayAlert(){
			t = new Timer() {
            @Override
            public void run() {
                Window.alert( "WARNING! \n" + " 10 days have passed since the milometer \n" +
                              "device on the lift rover has been calibrated." );
            }
        };
        t.schedule(this.TIMER);
	}
	
	public Button getAlert() {
		 final Button resetAlert = new Button("resetAlert");
	        resetAlert.addClickHandler(new ClickHandler() {
	        	public void onClick(ClickEvent event) {
	        		if(!t.isRunning()) {
	        			t.schedule(846000000);
	        		} else {
	        			Window.alert("10 Day Alert is runing right now!");
	        		}
				}
	        });
	     return resetAlert;
	}
}
