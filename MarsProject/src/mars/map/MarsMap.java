
package mars.map;

import java.util.ArrayList;
import java.util.List;

import mars.client.Module;
import mars.client.ModuleLogging;

import com.allen_sauer.gwt.voices.client.Sound;
import com.allen_sauer.gwt.voices.client.SoundController;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MarsMap {
	static final String unsupportedBrowser = "Your browser does not support the HTML5 Canvas";
	static final int HEIGHT = 750;
	static final int WIDTH = 1800;
	Canvas canvas;
	private ModuleLogging log;
	private ArrayList<Module> list;
	private boolean displayFullConfig1 = false;
	private boolean displayFullConfig2 = false;
	private boolean displayMinConfig1 = false;
	private boolean displayMinConfig2 = false;
	private boolean displayCurrentConfig = true;
	VerticalPanel panel = new VerticalPanel();
	Context2d context;
	private final SoundController soundController = new SoundController();
	private final Sound minSound1 = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
			"voice/test.mp3");
	private final Sound minSound2 = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
			"voice/test.mp3");
	private final Sound maxSound1 = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
			"voice/test.mp3");
	private final Sound maxSound2 = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
			"voice/test.mp3");
	private final Sound currentSound = soundController.createSound(Sound.MIME_TYPE_AUDIO_MPEG_MP3,
			"voice/test.mp3");

	public MarsMap(ModuleLogging logger) {
		this.log = logger;
		list = log.getSavedModules();
		canvas = Canvas.createIfSupported();
		if (canvas == null) {
			// RootPanel.get(divTagId).add(new Label(unsupportedBrowser));
			return;
		}
		//getSavedModules();
		createCanvas();
	}

/*	public void getSavedModules() {
		if (localStorage != null) {
			list.clear();
			// NOTE: when we iterate through this, we can possibly add the
			// modules to the "currentmodulelist" or whatever
			for (int i = 0; i < localStorage.getLength(); i += 1) {
				String key = localStorage.key(i);
				if (!key.startsWith("c")) {
					// Window.alert(key);
					String value = localStorage.getItem(key);
					// Window.alert(value);
					list.add(new Module(value));
					Window.alert(list.get(list.size() - 1).toString());
				}
			}
		} else {
			Window.alert("MODULE STORAGE IS NULL");
		}

	}*/

	private void createCanvas() {

		canvas.setWidth(WIDTH + "px");
		canvas.setHeight(HEIGHT + "px");
		canvas.setCoordinateSpaceWidth(WIDTH);
		canvas.setCoordinateSpaceHeight(HEIGHT);
		// RootPanel.get(divTagId).add(canvas); for raw use of canvas

		context = canvas.getContext2d(); // a rendering context

		final Image img = new Image("images/crater.jpg");
		img.setVisible(true);
		final ImageElement crater = ImageElement.as(img.getElement());
		img.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) { // fired by
													// RootPanel.get().add
				context.drawImage(crater, 0, 0);
			}
		});
		canvas.setVisible(true);
		// panel.add(canvas);

		// RootPanel.get().add(canvas);
		img.setVisible(false); // two line hack to ensure image is loaded
		RootPanel.get().add(img); // image must be on page to fire load event

	}

	public Canvas getCanvas() {
		return canvas;
	}

	public void addModuleToMap(Module moduleType) {
		Module adModule = moduleType;
		final Image moduleImage;
		if (moduleType.getCode() < 20) {
			moduleImage = new Image("images/Canteen.jpg");
		} else if (moduleType.getCode() > 20) {
			moduleImage = new Image("images/Sanitation.jpg");
		} else if (moduleType.getCode() < 20) {
			moduleImage = new Image("images/Airlock.jpg");
		} else if (moduleType.getCode() < 20) {
			moduleImage = new Image("images/Control.jpg");
		} else if (moduleType.getCode() < 20) {
			moduleImage = new Image("images/Food.jpg");
		} else if (moduleType.getCode() < 20) {
			moduleImage = new Image("images/Gym.jpg");
		} else if (moduleType.getCode() < 20) {
			moduleImage = new Image("images/Plain.jpg");
		} else if (moduleType.getCode() < 20) {
			moduleImage = new Image("images/Power.jpg");

		} else {
			moduleImage = new Image("images/Power.jpg");
		}
		moduleImage.setVisible(true);
		final ImageElement module = ImageElement.as(moduleImage.getElement());
		final int x = adModule.getX();
		moduleImage.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				// final int x= xPos;
				// final int y = yPos; // fired by RootPanel.get().add
				context.drawImage(module, x, x);
			}
		});
		moduleImage.setVisible(false); // two line hack to ensure image is
										// loaded
		RootPanel.get().add(moduleImage);
	}

	public DockPanel getMarsPanel() {
		DockPanel marsPanel = new DockPanel();
		marsPanel.add(getCanvas(), DockPanel.NORTH);
		final Button displayConfig1 = new Button("Display Full Configuration 1");
		displayConfig1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				displayFullConfig1 = true;
				displayFullConfig2 = false;
				displayMinConfig1 = false;
				displayMinConfig2 = false;
				displayCurrentConfig = false;
				updateMap();
				minSound1.play();
			}
		});
		final Button displayConfig2 = new Button("Display Full Configuration 2");
		displayConfig2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				displayFullConfig1 = false;
				displayFullConfig2 = true;
				displayMinConfig1 = false;
				displayMinConfig2 = false;
				displayCurrentConfig = false;
				updateMap();
				minSound2.play();
			}
		});
		final Button displayConfig3 = new Button(
				"Display Minimun Configuration 1");
		displayConfig3.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				displayFullConfig1 = false;
				displayFullConfig2 = false;
				displayMinConfig1 = true;
				displayMinConfig2 = false;
				displayCurrentConfig = false;
				updateMap();
				maxSound1.play();
			}
		});
		final Button displayConfig4 = new Button(
				"Display Minimun Configuration 2");
		displayConfig4.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				displayFullConfig1 = false;
				displayFullConfig2 = false;
				displayMinConfig1 = false;
				displayMinConfig2 = true;
				displayCurrentConfig = false;
				updateMap();
				maxSound2.play();
			}
		});
		final Button displayCurrent = new Button(
				"Display Current Configuration");
		displayCurrent.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				displayFullConfig1 = false;
				displayFullConfig2 = false;
				displayMinConfig1 = false;
				displayMinConfig2 = false;
				displayCurrentConfig = true;
				updateMap();
				currentSound.play();
			}
		});
		marsPanel.add(displayConfig4, DockPanel.WEST);
		marsPanel.add(displayConfig3, DockPanel.WEST);
		marsPanel.add(displayConfig1, DockPanel.EAST);
		marsPanel.add(displayConfig2, DockPanel.EAST);
		marsPanel.add(displayCurrent, DockPanel.SOUTH);

		return marsPanel;
	}

	void updateMap() {
		if (displayFullConfig1 == true) {
			
		} else if (displayFullConfig2 == true) {
            
		} else if (displayMinConfig1 == true) {
			
		}

	}

	public void loadModuleImages() {
		final ArrayList<Image> images = new ArrayList<Image>();
		for(int i = 1; i<=list.size()+1;i++){
		    images.add(new Image(list.get(i-1).getImageName()));
			images.get(i-1).addLoadHandler( new LoadHandler(){
				public void onLoad(final LoadEvent event){
					for(int b = 1; b<=list.size()+1;b++){
					context.drawImage(ImageElement.as(list.get(b-1).getImage().getElement()), list.get(b-1).getX(), list.get(b-1).getY());	
					}
				}
			});
		
			canvas.setVisible(true);
			images.get(i-1).setVisible(false);
			RootPanel.get().add(images.get(i-1));
		}
	
	}
	public void loadBackground(){
		context.clearRect(0, 0, this.WIDTH, this.HEIGHT);
		final Image img = new Image("images/crater.jpg");
		img.setVisible(true);
		final ImageElement crater = ImageElement.as(img.getElement());
		img.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) { 
				context.drawImage(crater, 0, 0);
			}
		});
		canvas.setVisible(true);
		img.setVisible(false); // two line hack to ensure image is loaded
		RootPanel.get().add(img); // image must be on page to fire load
									// event
	}
	
	
}