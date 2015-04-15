package mars.client;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;

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
	
	public void reSchedule() {
		if(!t.isRunning()) {
			t.schedule(this.TIMER);
		} else {
			Window.alert("10 Day Alert is runing right now!");
		}
	}
}
