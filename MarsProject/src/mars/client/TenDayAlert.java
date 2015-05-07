package mars.client;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;

public class TenDayAlert {
    private final int TIMER = 60000;
    private long initialTime;
    private final SoundController soundController = new SoundController();
	private final Sound reset = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
			"voice/alertReset.mp3");
private Timer t;

	public TenDayAlert() {
		t = new Timer() {
			@Override
    public void run() {
				Window.alert("WARNING! \n"
						+ " 10 days have passed since the milometer \n"
 						+ "device on the lift rover has been calibrated.");
			}
		};
		t.schedule(this.TIMER);
		initialTime = System.currentTimeMillis();
  }

	public Button getAlert() {
		final Button resetAlert = new Button("resetAlert");
		resetAlert.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!t.isRunning()) {
					t.schedule(TIMER);
					reset.play();
				} else {
					Window.alert("10 Day Alert is runing right now!\n" + "time remains: " + getTimeRemain() + " seconds");
					
				}
			}
		});
		return resetAlert;
	}
	
	private String getTimeRemain() {
		return Long.toString((this.TIMER - System.currentTimeMillis() + initialTime)/1000);
  }
}