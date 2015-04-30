
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
			loadBackground();
			
			final Image img1 = new Image("images/GreyPlain.jpg");
			img1.setVisible(true);
			final ImageElement greyPlain1 = ImageElement.as(img1.getElement());
			final Image img2 = new Image("images/GreyPlain.jpg");
			img2.setVisible(true);
			final ImageElement greyPlain2 = ImageElement.as(img2.getElement());
			final Image img3 = new Image("images/GreyPlain.jpg");
			img3.setVisible(true);
			final ImageElement greyPlain3 = ImageElement.as(img3.getElement());
			final Image img4 = new Image("images/GreyPlain.jpg");
			img4.setVisible(true);
			final ImageElement greyPlain4 = ImageElement.as(img4.getElement());
			final Image img5 = new Image("images/GreyPlain.jpg");
			img5.setVisible(true);
			final ImageElement greyPlain5 = ImageElement.as(img5.getElement());
			final Image img6 = new Image("images/GreyPlain.jpg");
			img6.setVisible(true);
			final ImageElement greyPlain6 = ImageElement.as(img6.getElement());
			final Image img7 = new Image("images/GreyPlain.jpg");
			img7.setVisible(true);
			final ImageElement greyPlain7 = ImageElement.as(img7.getElement());
			final Image img8 = new Image("images/GreyPlain.jpg");
			img8.setVisible(true);
			final ImageElement greyPlain8 = ImageElement.as(img8.getElement());
			final Image img9 = new Image("images/GreyPlain.jpg");
			img9.setVisible(true);
			final ImageElement greyPlain9 = ImageElement.as(img9.getElement());
			final Image img10 = new Image("images/GreyPlain.jpg");
			img10.setVisible(true);
			final ImageElement greyPlain10 = ImageElement.as(img10.getElement());
			final Image img11 = new Image("images/GreyPlain.jpg");
			img11.setVisible(true);
			final ImageElement greyPlain11 = ImageElement.as(img11.getElement());
			final Image img12 = new Image("images/GreyPlain.jpg");
			img12.setVisible(true);
			final ImageElement greyPlain12 = ImageElement.as(img12.getElement());
			final Image img13 = new Image("images/GreyPlain.jpg");
			img13.setVisible(true);
			final ImageElement greyPlain13 = ImageElement.as(img13.getElement());
			final Image img14 = new Image("images/GreyPlain.jpg");
			img14.setVisible(true);
			final ImageElement greyPlain14 = ImageElement.as(img14.getElement());
			final Image img15= new Image("images/GreyPlain.jpg");
			img15.setVisible(true);
			final ImageElement greyPlain15 = ImageElement.as(img15.getElement());
			final Image img16 = new Image("images/GreyPlain.jpg");
			img16.setVisible(true);
			final ImageElement greyPlain16 = ImageElement.as(img16.getElement());
			final Image img17 = new Image("images/GreyPlain.jpg");
			img17.setVisible(true);
			final ImageElement greyPlain17 = ImageElement.as(img17.getElement());
			final Image img18 = new Image("images/GreyPlain.jpg");
			img18.setVisible(true);
			final ImageElement greyPlain18 = ImageElement.as(img18.getElement());
			final Image img19 = new Image("images/GreyPlain.jpg");
			img19.setVisible(true);
			final ImageElement greyPlain19 = ImageElement.as(img19.getElement());
			final Image img20 = new Image("images/GreyPlain.jpg");
			img20.setVisible(true);
			final ImageElement greyPlain20 = ImageElement.as(img20.getElement());
			final Image img21 = new Image("images/GreyPlain.jpg");
			img21.setVisible(true);
			final ImageElement greyPlain21 = ImageElement.as(img21.getElement());
			final Image img22 = new Image("images/GreyPlain.jpg");
			img22.setVisible(true);
			final ImageElement greyPlain22 = ImageElement.as(img22.getElement());
			final Image img23 = new Image("images/GreyPlain.jpg");
			img23.setVisible(true);
			final ImageElement greyPlain23 = ImageElement.as(img23.getElement());
			final Image img24 = new Image("images/GreyPlain.jpg");
			img24.setVisible(true);
			final ImageElement greyPlain24 = ImageElement.as(img24.getElement());
			final Image img25 = new Image("images/GreyPlain.jpg");
			img25.setVisible(true);
			final ImageElement greyPlain25 = ImageElement.as(img25.getElement());
			final Image img26 = new Image("images/GreyPlain.jpg");
			img26.setVisible(true);
			final ImageElement greyPlain26 = ImageElement.as(img26.getElement());
			final Image img27 = new Image("images/GreyPlain.jpg");
			img27.setVisible(true);
			final ImageElement greyPlain27 = ImageElement.as(img27.getElement());
			final Image img28 = new Image("images/GreyPlain.jpg");
			img28.setVisible(true);
			final ImageElement greyPlain28 = ImageElement.as(img28.getElement());
			final Image img29 = new Image("images/GreyPlain.jpg");
			img29.setVisible(true);
			final ImageElement greyPlain29 = ImageElement.as(img29.getElement());
			final Image img30 = new Image("images/GreyPlain.jpg");
			img30.setVisible(true);
			final ImageElement greyPlain30 = ImageElement.as(img30.getElement());
			final Image img31 = new Image("images/GreyPlain.jpg");
			img31.setVisible(true);
			final ImageElement greyPlain31 = ImageElement.as(img31.getElement());
			final Image img32 = new Image("images/GreyPlain.jpg");
			img32.setVisible(true);
			final ImageElement greyPlain32 = ImageElement.as(img32.getElement());
			final Image img33 = new Image("images/GreyPlain.jpg");
			img33.setVisible(true);
			final ImageElement greyPlain33 = ImageElement.as(img33.getElement());
			final Image img34 = new Image("images/GreyPlain.jpg");
			img34.setVisible(true);
			final ImageElement greyPlain34 = ImageElement.as(img34.getElement());
			final Image img35 = new Image("images/GreyPlain.jpg");
			img35.setVisible(true);
			final ImageElement greyPlain35 = ImageElement.as(img35.getElement());
			final Image img36 = new Image("images/GreyPlain.jpg");
			img36.setVisible(true);
			final ImageElement greyPlain36 = ImageElement.as(img36.getElement());
			final Image img37 = new Image("images/GreyPlain.jpg");
			img37.setVisible(true);
			final ImageElement greyPlain37 = ImageElement.as(img37.getElement());
			final Image img38 = new Image("images/GreyPlain.jpg");
			img38.setVisible(true);
			final ImageElement greyPlain38 = ImageElement.as(img38.getElement());
			final Image img39 = new Image("images/GreyPlain.jpg");
			img39.setVisible(true);
			final ImageElement greyPlain39 = ImageElement.as(img39.getElement());
			final Image img40 = new Image("images/GreyPlain.jpg");
			img40.setVisible(true);
			final ImageElement greyPlain40 = ImageElement.as(img40.getElement());
			final Image img41 = new Image("images/GreyPlain.jpg");
			img41.setVisible(true);
			final ImageElement greyPlain41 = ImageElement.as(img41.getElement());
			final Image img42 = new Image("images/GreyPlain.jpg");
			img42.setVisible(true);
			final ImageElement greyPlain42 = ImageElement.as(img42.getElement());
			
			
			//Load dorm images
			final Image img43 = new Image("images/GreyDormitory.jpg");
			img43.setVisible(true);
			final ImageElement greyPlain43 = ImageElement.as(img43.getElement());
			img43.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain43, 150, 50);
					context.drawImage(greyPlain43, 250, 50);
					context.drawImage(greyPlain43, 300, 50);
					context.drawImage(greyPlain43, 400, 50);
					context.drawImage(greyPlain43, 150, 150);
					context.drawImage(greyPlain43, 250, 150);
					context.drawImage(greyPlain43, 350, 150);
					context.drawImage(greyPlain43, 400, 150);
					context.drawImage(greyPlain43, 150, 250);
					context.drawImage(greyPlain43, 200, 250);
					context.drawImage(greyPlain43, 350, 250);
					context.drawImage(greyPlain43, 450, 250);
					context.drawImage(greyPlain43, 150, 350);
					context.drawImage(greyPlain43, 250, 350);
					context.drawImage(greyPlain43, 400, 350);
					context.drawImage(greyPlain43, 450, 350);
					context.drawImage(greyPlain43, 150, 450);
					context.drawImage(greyPlain43, 250, 450);
					context.drawImage(greyPlain43, 400, 450);
					context.drawImage(greyPlain43, 350, 550);
				}
			});
			canvas.setVisible(true);
			img43.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img43);
			
			//display sanitation
			final Image img44 = new Image("images/GreySanitation.jpg");
			img44.setVisible(true);
			final ImageElement greyPlain44 = ImageElement.as(img44.getElement());
			img44.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain44, 200, 50);
					context.drawImage(greyPlain44, 350, 50);
					context.drawImage(greyPlain44, 200, 150);
					context.drawImage(greyPlain44, 450, 150);
					context.drawImage(greyPlain44, 250, 250);
					context.drawImage(greyPlain44, 400, 250);
					context.drawImage(greyPlain44, 200, 350);
					context.drawImage(greyPlain44, 350, 350);
					context.drawImage(greyPlain44, 200, 450);
					context.drawImage(greyPlain44, 350, 450);
				}
			});
			canvas.setVisible(true);
			img44.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img44);
			
			
			//display gym
			final Image img45 = new Image("images/GreyGym.jpg");
			img45.setVisible(true);
			final ImageElement greyPlain45 = ImageElement.as(img45.getElement());
			img45.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain45, 300, 150);
					context.drawImage(greyPlain45, 300, 250);
					context.drawImage(greyPlain45, 300, 350);
					context.drawImage(greyPlain45, 300, 450);
				}
			});
			canvas.setVisible(true);
			img45.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img45);
			
			
			//display gym
			final Image img46 = new Image("images/GreyAirLock.jpg");
			img46.setVisible(true);
			final ImageElement greyPlain46 = ImageElement.as(img46.getElement());
			img46.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain46, 500, 50);
					context.drawImage(greyPlain46, 0, 300);
					context.drawImage(greyPlain46, 550, 300);
					context.drawImage(greyPlain46, 500, 550);
				}
			});
			canvas.setVisible(true);
			img46.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img46);
			
			
			//display hospital
			final Image img47 = new Image("images/GreyMedical.jpg");
			img47.setVisible(true);
			final ImageElement greyPlain47 = ImageElement.as(img47.getElement());
			img47.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain47, 50, 350);
					context.drawImage(greyPlain47, 450, 550);
					context.drawImage(greyPlain47, 550, 350);
					context.drawImage(greyPlain47, 550, 100);
				}
			});
			canvas.setVisible(true);
			img47.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img47);
			
			//display control
			final Image img48 = new Image("images/Greycontrol.jpg");
			img48.setVisible(true);
			final ImageElement greyPlain48 = ImageElement.as(img48.getElement());
			img48.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain48, 450, 50);
					context.drawImage(greyPlain48, 550, 150);
					context.drawImage(greyPlain48, 550, 500);
					context.drawImage(greyPlain48, 150, 550);
				}
			});
			canvas.setVisible(true);
			img48.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img48);
			
			//display power
			final Image img49 = new Image("images/GreyPower.jpg");
			img49.setVisible(true);
			final ImageElement greyPlain49 = ImageElement.as(img49.getElement());
			img49.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain49, 450, 200);
					context.drawImage(greyPlain49, 550, 200);
					context.drawImage(greyPlain49, 550, 450);
					context.drawImage(greyPlain49, 200, 550);
				}
			});
			canvas.setVisible(true);
			img49.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img49);
			//Display canteen
			final Image img50 = new Image("images/GreyCanteen.jpg");
			img50.setVisible(true);
			final ImageElement greyPlain50 = ImageElement.as(img50.getElement());
			img50.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain50, 100, 50);
					context.drawImage(greyPlain50, 150, 250);
					context.drawImage(greyPlain50, 50, 500);
					context.drawImage(greyPlain50, 450, 450);
				}
			});
			canvas.setVisible(true);
			img50.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img50);
			
			
			final Image img51 = new Image("images/GreyFood.jpg");
			img51.setVisible(true);
			final ImageElement greyPlain51 = ImageElement.as(img51.getElement());
			img51.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain51, 50, 100);
					context.drawImage(greyPlain51, 50, 150);
					context.drawImage(greyPlain51, 50, 200);
					context.drawImage(greyPlain51, 50, 250);
					context.drawImage(greyPlain51, 50, 400);
					context.drawImage(greyPlain51, 50, 450);
					context.drawImage(greyPlain51, 150, 400);
					context.drawImage(greyPlain51, 100, 550);
					context.drawImage(greyPlain51, 450, 400);
					context.drawImage(greyPlain51, 550, 400);
				}
			});
			canvas.setVisible(true);
			img51.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img51);
			
			
			
			
			
			
			
			img1.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain1, 100, 100);
				}
			});
			canvas.setVisible(true);
			img1.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img1);
			
			img2.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain2, 100, 150);
				}
			});
			canvas.setVisible(true);
			img2.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img2);
			img3.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain3, 100, 200);
				}
			});
			canvas.setVisible(true);
			img3.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img3);
			img4.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain4, 100, 250);
				}
			});
			canvas.setVisible(true);
			img4.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img4);
			img5.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain5, 100, 300);
				}
			});
			canvas.setVisible(true);
			img5.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img5);
			img6.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain6, 100, 350);
				}
			});
			canvas.setVisible(true);
			img6.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img6);
			img7.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain7, 100, 400);
				}
			});
			canvas.setVisible(true);
			img7.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img7);
			img8.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain8, 100, 450);
				}
			});
			canvas.setVisible(true);
			img8.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img8);
			img9.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain9, 100, 500);
				}
			});
			canvas.setVisible(true);
			img9.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img9);
			
			img11.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain11, 150, 100);
				}
			});
			canvas.setVisible(true);
			img11.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img11);
			img12.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain12, 200, 100);
				}
			});
			canvas.setVisible(true);
			img12.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img12);
			img13.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain13, 250, 100);
				}
			});
			canvas.setVisible(true);
			img13.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img13);
			img14.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain14, 300, 100);
				}
			});
			canvas.setVisible(true);
			img14.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img14);
			img15.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain15, 350, 100);
				}
			});
			canvas.setVisible(true);
			img15.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img15);
			img16.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain16, 400, 100);
				}
			});
			canvas.setVisible(true);
			img16.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img16);
			img17.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain17, 450, 100);
				}
			});
			canvas.setVisible(true);
			img17.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img17);
			img18.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain18, 500, 100);
				}
			});
			canvas.setVisible(true);
			img18.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img18);
			img19.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain19, 50, 300);
				}
			});
			canvas.setVisible(true);
			img19.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img19);
			img20.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain20, 150, 300);
				}
			});
			canvas.setVisible(true);
			img20.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img20);
			img21.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain21, 200, 300);
				}
			});
			canvas.setVisible(true);
			img21.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img21);
			img22.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain22, 250, 300);
				}
			});
			canvas.setVisible(true);
			img22.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img22);
			img23.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain23, 300, 300);
				}
			});
			canvas.setVisible(true);
			img23.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img23);
			img24.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain24, 350, 300);
				}
			});
			canvas.setVisible(true);
			img24.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img24);
			img25.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain25, 400, 300);
				}
			});
			canvas.setVisible(true);
			img25.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img25);
			img26.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain26, 450, 300);
				}
			});
			canvas.setVisible(true);
			img26.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img26);
			img27.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain27, 500, 300);
				}
			});
			canvas.setVisible(true);
			img27.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img27);
			img28.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain28, 150, 500);
				}
			});
			canvas.setVisible(true);
			img28.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img28);
			img29.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain29, 200, 500);
				}
			});
			canvas.setVisible(true);
			img29.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img29);
			img30.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain30, 250, 500);
				}
			});
			canvas.setVisible(true);
			img30.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img30);
			img31.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain31, 300, 500);
				}
			});
			canvas.setVisible(true);
			img31.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img31);
			img32.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain32, 350, 500);
				}
			});
			canvas.setVisible(true);
			img32.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img32);
			img33.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain33, 400, 500);
				}
			});
			canvas.setVisible(true);
			img33.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img33);
			img34.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain34, 450, 500);
				}
			});
			canvas.setVisible(true);
			img34.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img34);
			img35.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain35, 500, 500);
				}
			});
			//im here
			canvas.setVisible(true);
			img35.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img35);
			img36.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain36, 500, 450);
				}
			});
			canvas.setVisible(true);
			img36.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img36);
			img37.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain37, 500, 400);
				}
			});
			canvas.setVisible(true);
			img37.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img37);
			img38.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain38, 500, 350);
				}
			});
			canvas.setVisible(true);
			img38.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img38);
			img39.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain39, 500, 250);
				}
			});
			canvas.setVisible(true);
			img39.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img39);
			img40.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain40, 500, 200);
				}
			});
			canvas.setVisible(true);
			img40.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img40);
			img41.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain41, 500, 150);
				}
			});
			canvas.setVisible(true);
			img41.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img41);			
			loadModuleImages();
			
		} else if (displayFullConfig2 == true) {
            loadBackground();
			
			final Image img1 = new Image("images/GreyPlain.jpg");
			img1.setVisible(true);
			final ImageElement greyPlain1 = ImageElement.as(img1.getElement());
			final Image img2 = new Image("images/GreyPlain.jpg");
			img2.setVisible(true);
			final ImageElement greyPlain2 = ImageElement.as(img2.getElement());
			final Image img3 = new Image("images/GreyPlain.jpg");
			img3.setVisible(true);
			final ImageElement greyPlain3 = ImageElement.as(img3.getElement());
			final Image img4 = new Image("images/GreyPlain.jpg");
			img4.setVisible(true);
			final ImageElement greyPlain4 = ImageElement.as(img4.getElement());
			final Image img5 = new Image("images/GreyPlain.jpg");
			img5.setVisible(true);
			final ImageElement greyPlain5 = ImageElement.as(img5.getElement());
			final Image img6 = new Image("images/GreyPlain.jpg");
			img6.setVisible(true);
			final ImageElement greyPlain6 = ImageElement.as(img6.getElement());
			final Image img7 = new Image("images/GreyPlain.jpg");
			img7.setVisible(true);
			final ImageElement greyPlain7 = ImageElement.as(img7.getElement());
			final Image img8 = new Image("images/GreyPlain.jpg");
			img8.setVisible(true);
			final ImageElement greyPlain8 = ImageElement.as(img8.getElement());
			final Image img9 = new Image("images/GreyPlain.jpg");
			img9.setVisible(true);
			final ImageElement greyPlain9 = ImageElement.as(img9.getElement());
			final Image img10 = new Image("images/GreyPlain.jpg");
			img10.setVisible(true);
			final ImageElement greyPlain10 = ImageElement.as(img10.getElement());
			final Image img11 = new Image("images/GreyPlain.jpg");
			img11.setVisible(true);
			final ImageElement greyPlain11 = ImageElement.as(img11.getElement());
			final Image img12 = new Image("images/GreyPlain.jpg");
			img12.setVisible(true);
			final ImageElement greyPlain12 = ImageElement.as(img12.getElement());
			final Image img13 = new Image("images/GreyPlain.jpg");
			img13.setVisible(true);
			final ImageElement greyPlain13 = ImageElement.as(img13.getElement());
			final Image img14 = new Image("images/GreyPlain.jpg");
			img14.setVisible(true);
			final ImageElement greyPlain14 = ImageElement.as(img14.getElement());
			final Image img15= new Image("images/GreyPlain.jpg");
			img15.setVisible(true);
			final ImageElement greyPlain15 = ImageElement.as(img15.getElement());
			final Image img16 = new Image("images/GreyPlain.jpg");
			img16.setVisible(true);
			final ImageElement greyPlain16 = ImageElement.as(img16.getElement());
			final Image img17 = new Image("images/GreyPlain.jpg");
			img17.setVisible(true);
			final ImageElement greyPlain17 = ImageElement.as(img17.getElement());
			final Image img18 = new Image("images/GreyPlain.jpg");
			img18.setVisible(true);
			final ImageElement greyPlain18 = ImageElement.as(img18.getElement());
			final Image img19 = new Image("images/GreyPlain.jpg");
			img19.setVisible(true);
			final ImageElement greyPlain19 = ImageElement.as(img19.getElement());
			final Image img20 = new Image("images/GreyPlain.jpg");
			img20.setVisible(true);
			final ImageElement greyPlain20 = ImageElement.as(img20.getElement());
			final Image img21 = new Image("images/GreyPlain.jpg");
			img21.setVisible(true);
			final ImageElement greyPlain21 = ImageElement.as(img21.getElement());
			final Image img22 = new Image("images/GreyPlain.jpg");
			img22.setVisible(true);
			final ImageElement greyPlain22 = ImageElement.as(img22.getElement());
			final Image img23 = new Image("images/GreyPlain.jpg");
			img23.setVisible(true);
			final ImageElement greyPlain23 = ImageElement.as(img23.getElement());
			final Image img24 = new Image("images/GreyPlain.jpg");
			img24.setVisible(true);
			final ImageElement greyPlain24 = ImageElement.as(img24.getElement());
			final Image img25 = new Image("images/GreyPlain.jpg");
			img25.setVisible(true);
			final ImageElement greyPlain25 = ImageElement.as(img25.getElement());
			final Image img26 = new Image("images/GreyPlain.jpg");
			img26.setVisible(true);
			final ImageElement greyPlain26 = ImageElement.as(img26.getElement());
			final Image img27 = new Image("images/GreyPlain.jpg");
			img27.setVisible(true);
			final ImageElement greyPlain27 = ImageElement.as(img27.getElement());
			final Image img28 = new Image("images/GreyPlain.jpg");
			img28.setVisible(true);
			final ImageElement greyPlain28 = ImageElement.as(img28.getElement());
			final Image img29 = new Image("images/GreyPlain.jpg");
			img29.setVisible(true);
			final ImageElement greyPlain29 = ImageElement.as(img29.getElement());
			final Image img30 = new Image("images/GreyPlain.jpg");
			img30.setVisible(true);
			final ImageElement greyPlain30 = ImageElement.as(img30.getElement());
			final Image img31 = new Image("images/GreyPlain.jpg");
			img31.setVisible(true);
			final ImageElement greyPlain31 = ImageElement.as(img31.getElement());
			final Image img32 = new Image("images/GreyPlain.jpg");
			img32.setVisible(true);
			final ImageElement greyPlain32 = ImageElement.as(img32.getElement());
			final Image img33 = new Image("images/GreyPlain.jpg");
			img33.setVisible(true);
			final ImageElement greyPlain33 = ImageElement.as(img33.getElement());
			final Image img34 = new Image("images/GreyPlain.jpg");
			img34.setVisible(true);
			final ImageElement greyPlain34 = ImageElement.as(img34.getElement());
			final Image img35 = new Image("images/GreyPlain.jpg");
			img35.setVisible(true);
			final ImageElement greyPlain35 = ImageElement.as(img35.getElement());
			final Image img36 = new Image("images/GreyPlain.jpg");
			img36.setVisible(true);
			final ImageElement greyPlain36 = ImageElement.as(img36.getElement());
			final Image img37 = new Image("images/GreyPlain.jpg");
			img37.setVisible(true);
			final ImageElement greyPlain37 = ImageElement.as(img37.getElement());
			final Image img38 = new Image("images/GreyPlain.jpg");
			img38.setVisible(true);
			final ImageElement greyPlain38 = ImageElement.as(img38.getElement());
			final Image img39 = new Image("images/GreyPlain.jpg");
			img39.setVisible(true);
			final ImageElement greyPlain39 = ImageElement.as(img39.getElement());
			final Image img40 = new Image("images/GreyPlain.jpg");
			img40.setVisible(true);
			final ImageElement greyPlain40 = ImageElement.as(img40.getElement());
			final Image img41 = new Image("images/GreyPlain.jpg");
			img41.setVisible(true);
			final ImageElement greyPlain41 = ImageElement.as(img41.getElement());
			final Image img42 = new Image("images/GreyPlain.jpg");
			img42.setVisible(true);
			final ImageElement greyPlain42 = ImageElement.as(img42.getElement());
			
		
			//Load dorm images
			final Image img43 = new Image("images/GreyDormitory.jpg");
			img43.setVisible(true);
			final ImageElement greyPlain43 = ImageElement.as(img43.getElement());
			img43.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain43, 1000, 100);
					context.drawImage(greyPlain43, 1000, 150);
					context.drawImage(greyPlain43, 1000, 250);
					context.drawImage(greyPlain43, 950, 250);
					context.drawImage(greyPlain43, 900, 250);
					context.drawImage(greyPlain43, 850, 250);
					context.drawImage(greyPlain43, 1000, 350);
					context.drawImage(greyPlain43, 850, 350);
					context.drawImage(greyPlain43, 850, 350);
					context.drawImage(greyPlain43, 200, 250);
					context.drawImage(greyPlain43, 900, 350);
					context.drawImage(greyPlain43, 1000, 500);
					context.drawImage(greyPlain43, 1000, 450);
					context.drawImage(greyPlain43, 1100, 450);
					context.drawImage(greyPlain43, 1100, 500);
					context.drawImage(greyPlain43, 400, 250);
					context.drawImage(greyPlain43, 450, 250);
					context.drawImage(greyPlain43, 300, 250);
					context.drawImage(greyPlain43, 250, 250);
					context.drawImage(greyPlain43, 500, 350);
					context.drawImage(greyPlain43, 300, 350);
					context.drawImage(greyPlain43, 350, 350);
					context.drawImage(greyPlain20, 1100,300);
				}
			});
			canvas.setVisible(true);
			img43.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img43);
			
			//display sanitation
			final Image img44 = new Image("images/GreySanitation.jpg");
			img44.setVisible(true);
			final ImageElement greyPlain44 = ImageElement.as(img44.getElement());
			img44.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain44, 250, 350);
					context.drawImage(greyPlain44, 350, 250);
					context.drawImage(greyPlain44, 450, 350);
					context.drawImage(greyPlain44, 800, 350);
					context.drawImage(greyPlain44, 800, 250);
					context.drawImage(greyPlain44, 950, 350);
					context.drawImage(greyPlain44, 1000, 400);
					context.drawImage(greyPlain44, 1100, 400);
					context.drawImage(greyPlain44, 1000, 200);
					context.drawImage(greyPlain44, 1100, 200);
				}
			});
			canvas.setVisible(true);
			img44.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img44);
			
			
			//display gym
			final Image img45 = new Image("images/GreyGym.jpg");
			img45.setVisible(true);
			final ImageElement greyPlain45 = ImageElement.as(img45.getElement());
			img45.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain45, 400, 350);
					context.drawImage(greyPlain45, 500, 250);
					context.drawImage(greyPlain45, 700, 350);
					context.drawImage(greyPlain45, 700, 250);
				}
			});
			canvas.setVisible(true);
			img45.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img45);
			
			
			//display gym
			final Image img46 = new Image("images/GreyAirLock.jpg");
			img46.setVisible(true);
			final ImageElement greyPlain46 = ImageElement.as(img46.getElement());
			img46.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain46, 600, 250);
					context.drawImage(greyPlain46, 600, 350);
					context.drawImage(greyPlain46, 1150, 300);
					context.drawImage(greyPlain46, 50, 300);
				}
			});
			canvas.setVisible(true);
			img46.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img46);
			
			
			//display hospital
			final Image img47 = new Image("images/GreyMedical.jpg");
			img47.setVisible(true);
			final ImageElement greyPlain47 = ImageElement.as(img47.getElement());
			img47.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain47, 1100, 350);
					context.drawImage(greyPlain47, 100, 250);
					context.drawImage(greyPlain47, 550, 350);
					context.drawImage(greyPlain47, 650, 250);
				}
			});
			canvas.setVisible(true);
			img47.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img47);
			
			//display control
			final Image img48 = new Image("images/Greycontrol.jpg");
			img48.setVisible(true);
			final ImageElement greyPlain48 = ImageElement.as(img48.getElement());
			img48.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain48, 100, 350);
					context.drawImage(greyPlain48, 550, 250);
					context.drawImage(greyPlain48, 650, 350);
					context.drawImage(greyPlain48, 1100, 250);
				}
			});
			canvas.setVisible(true);
			img48.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img48);
			
			//display power
			final Image img49 = new Image("images/GreyPower.jpg");
			img49.setVisible(true);
			final ImageElement greyPlain49 = ImageElement.as(img49.getElement());
			img49.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain49, 100, 100);
					context.drawImage(greyPlain49, 100, 400);
					context.drawImage(greyPlain49, 200, 250);
					context.drawImage(greyPlain49, 200, 350);
				}
			});
			canvas.setVisible(true);
			img49.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img49);
			//Display canteen
			final Image img50 = new Image("images/GreyCanteen.jpg");
			img50.setVisible(true);
			final ImageElement greyPlain50 = ImageElement.as(img50.getElement());
			img50.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain50, 150, 0);
					context.drawImage(greyPlain50, 150, 600);
					context.drawImage(greyPlain50, 1050, 0);
					context.drawImage(greyPlain50, 1050, 600);
				}
			});
			canvas.setVisible(true);
			img50.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img50);
			
			
			final Image img51 = new Image("images/GreyFood.jpg");
			img51.setVisible(true);
			final ImageElement greyPlain51 = ImageElement.as(img51.getElement());
			img51.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add

					context.drawImage(greyPlain51, 1100, 550);
					context.drawImage(greyPlain51, 1000, 550);
					context.drawImage(greyPlain51, 1100, 50);
					context.drawImage(greyPlain51, 1000, 50);
					context.drawImage(greyPlain51, 100, 550);
					context.drawImage(greyPlain51, 200, 550);
					context.drawImage(greyPlain51, 100, 500);
					context.drawImage(greyPlain51, 100, 50);
					context.drawImage(greyPlain51, 200, 100);
					context.drawImage(greyPlain51, 200, 50);
				}
			});
			canvas.setVisible(true);
			img51.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img51);
			
			
			
			
			
			
			
			img1.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain1, 150, 50);
				}
			});
			canvas.setVisible(true);
			img1.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img1);
			
			img2.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain2, 150, 100);
				}
			});
			canvas.setVisible(true);
			img2.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img2);
			img3.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain3, 150, 150);
				}
			});
			canvas.setVisible(true);
			img3.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img3);
			img4.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain4, 150, 200);
				}
			});
			canvas.setVisible(true);
			img4.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img4);
			img5.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain5, 150, 250);
				}
			});
			canvas.setVisible(true);
			img5.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img5);
			img6.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain6, 150, 300);
				}
			});
			canvas.setVisible(true);
			img6.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img6);
			img7.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain7, 150, 350);
				}
			});
			canvas.setVisible(true);
			img7.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img7);
			img8.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain8, 150, 400);
				}
			});
			canvas.setVisible(true);
			img8.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img8);
			img9.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain9, 150, 450);
				}
			});
			canvas.setVisible(true);
			img9.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img9);
			
			img11.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain11, 150, 500);
				}
			});
			canvas.setVisible(true);
			img11.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img11);
			img12.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain12, 150, 550);
				}
			});
			canvas.setVisible(true);
			img12.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img12);
			img13.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain13, 100, 300);
				}
			});
			canvas.setVisible(true);
			img13.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img13);
			img14.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain14, 200, 300);
				}
			});
			canvas.setVisible(true);
			img14.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img14);
			img15.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain15, 250, 300);
				}
			});
			canvas.setVisible(true);
			img15.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img15);
			img16.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain16, 300, 300);
				}
			});
			canvas.setVisible(true);
			img16.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img16);
			img17.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain17, 350, 300);
				}
			});
			canvas.setVisible(true);
			img17.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img17);
			img18.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain18, 400, 300);
				}
			});
			canvas.setVisible(true);
			img18.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img18);
			img19.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain19, 450, 300);
				}
			});
			canvas.setVisible(true);
			img19.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img19);
			img20.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain20, 500, 300);
				}
			});
			canvas.setVisible(true);
			img20.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img20);
			img21.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain21, 550, 300);
				}
			});
			canvas.setVisible(true);
			img21.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img21);
			img22.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain22, 600, 300);
				}
			});
			canvas.setVisible(true);
			img22.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img22);
			img23.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain23, 650, 300);
				}
			});
			canvas.setVisible(true);
			img23.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img23);
			img24.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain24, 800, 300);
				}
			});
			canvas.setVisible(true);
			img24.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img24);
			img25.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain25, 850, 300);
				}
			});
			canvas.setVisible(true);
			img25.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img25);
			img26.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain26, 900, 300);
				}
			});
			canvas.setVisible(true);
			img26.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img26);
			img27.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain27, 950, 300);
				}
			});
			canvas.setVisible(true);
			img27.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img27);
			img28.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain28, 1000, 300);
				}
			});
			canvas.setVisible(true);
			img28.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img28);
			img29.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain29, 1050, 300);
				}
			});
			canvas.setVisible(true);
			img29.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img29);
			img30.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain30, 1050, 250);
				}
			});
			canvas.setVisible(true);
			img30.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img30);
			img31.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain31, 1050, 200);
				}
			});
			canvas.setVisible(true);
			img31.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img31);
			img32.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain32, 1050, 150);
				}
			});
			canvas.setVisible(true);
			img32.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img32);
			img33.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain33, 1050, 100);
				}
			});
			canvas.setVisible(true);
			img33.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img33);
			img34.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain34, 1050, 50);
				}
			});
			canvas.setVisible(true);
			img34.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img34);
			img35.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain35, 1050, 350);
				}
			});
			//im here
			canvas.setVisible(true);
			img35.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img35);
			img36.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain36, 1050, 400);
				}
			});
			canvas.setVisible(true);
			img36.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img36);
			img37.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain37, 1050, 450);
				}
			});
			canvas.setVisible(true);
			img37.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img37);
			img38.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain38, 1050, 500);
				}
			});
			canvas.setVisible(true);
			img38.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img38);
			img39.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain39, 1050, 550);
				}
			});
			canvas.setVisible(true);
			img39.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img39);
			img40.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain40, 700, 300);
				}
			});
			canvas.setVisible(true);
			img40.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img40);
			img41.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain41, 750, 300);
				}
			});
			canvas.setVisible(true);
			img41.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img41);			
			loadModuleImages();
			
		} else if (displayMinConfig1 == true) {
			loadBackground();
			final Image img1 = new Image("images/GreyPlain.jpg");
			img1.setVisible(true);
			final ImageElement greyPlain1 = ImageElement.as(img1.getElement());
			img1.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain1, 300, 300);
				}
			});
			canvas.setVisible(true);
			img1.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img1);
			final Image img2 = new Image("images/GreyPlain.jpg");
			img2.setVisible(true);
			final ImageElement greyPlain2 = ImageElement.as(img2.getElement());
			img2.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain2, 300, 350);
				}
			});
			canvas.setVisible(true);
			img2.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img2);
			final Image img3 = new Image("images/GreyPlain.jpg");
			img3.setVisible(true);

			final ImageElement greyPlain3 = ImageElement.as(img3.getElement());
			img3.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain3, 300, 400);
				}
			});
			canvas.setVisible(true);
			img3.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img3);

			final Image img4 = new Image("images/Greycontrol.jpg");
			img4.setVisible(true);

			final ImageElement control = ImageElement.as(img4.getElement());
			img4.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(control, 250, 350);
				}
			});
			canvas.setVisible(true);
			img4.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img4);

			final Image img5 = new Image("images/GreySanitation.jpg");
			img5.setVisible(true);
			final ImageElement sanitation = ImageElement.as(img5.getElement());
			img5.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(sanitation, 250, 400);
				}
			});
			canvas.setVisible(true);
			img5.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img5);

			// Airlock
			final Image img6 = new Image("images/GreyAirLock.jpg");
			img6.setVisible(true);
			final ImageElement airlockImage = ImageElement
					.as(img6.getElement());
			img6.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(airlockImage, 300, 450);
				}
			});
			canvas.setVisible(true);
			img6.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img6);

			// Dorm
			final Image img7 = new Image("images/GreyDormitory.jpg");
			img7.setVisible(true);
			final ImageElement dormImage = ImageElement.as(img7.getElement());
			img7.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(dormImage, 350, 300);
				}
			});
			canvas.setVisible(true);
			img7.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img7);

			// power
			final Image img8 = new Image("images/GreyPower.jpg");
			img8.setVisible(true);
			final ImageElement powerImage = ImageElement.as(img8.getElement());
			img8.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(powerImage, 350, 350);
				}
			});
			canvas.setVisible(true);
			img8.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img8);

			// food
			final Image img9 = new Image("images/GreyCanteen.jpg");
			img9.setVisible(true);
			final ImageElement foodImage = ImageElement.as(img9.getElement());
			img9.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(foodImage, 300, 250);
				}
			});
			canvas.setVisible(true);
			img9.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img9);

			// storage
			final Image img10 = new Image("images/GreyFood.jpg");
			img10.setVisible(true);
			final ImageElement storageImage = ImageElement.as(img10
					.getElement());
			img10.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(storageImage, 250, 300);
				}
			});
			canvas.setVisible(true);
			img10.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img10);
            loadModuleImages();
		} else if (displayMinConfig2 == true) {
			loadBackground();
			final Image img1 = new Image("images/GreyPlain.jpg");
			img1.setVisible(true);
			final ImageElement greyPlain1 = ImageElement.as(img1.getElement());
			img1.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain1, 300, 300);
				}
			});
			canvas.setVisible(true);
			img1.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img1);
			final Image img2 = new Image("images/GreyPlain.jpg");
			img2.setVisible(true);
			final ImageElement greyPlain2 = ImageElement.as(img2.getElement());
			img2.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain2, 300, 350);
				}
			});
			canvas.setVisible(true);
			img2.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img2);
			final Image img3 = new Image("images/GreyPlain.jpg");
			img3.setVisible(true);

			final ImageElement greyPlain3 = ImageElement.as(img3.getElement());
			img3.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(greyPlain3, 300, 400);
				}
			});
			canvas.setVisible(true);
			img3.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img3);

			final Image img4 = new Image("images/Greycontrol.jpg");
			img4.setVisible(true);

			final ImageElement control = ImageElement.as(img4.getElement());
			img4.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(control, 250, 350);
				}
			});
			canvas.setVisible(true);
			img4.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img4);

			final Image img5 = new Image("images/GreySanitation.jpg");
			img5.setVisible(true);
			final ImageElement sanitation = ImageElement.as(img5.getElement());
			img5.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(sanitation, 350, 400);
				}
			});
			canvas.setVisible(true);
			img5.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img5);

			// Airlock
			final Image img6 = new Image("images/GreyAirLock.jpg");
			img6.setVisible(true);
			final ImageElement airlockImage = ImageElement
					.as(img6.getElement());
			img6.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(airlockImage, 300, 450);
				}
			});
			canvas.setVisible(true);
			img6.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img6);

			// Dorm
			final Image img7 = new Image("images/GreyDormitory.jpg");
			img7.setVisible(true);
			final ImageElement dormImage = ImageElement.as(img7.getElement());
			img7.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(dormImage, 250, 300);
				}
			});
			
			
			
			
			canvas.setVisible(true);
			img7.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img7);

			// power
			final Image img8 = new Image("images/GreyPower.jpg");
			img8.setVisible(true);
			final ImageElement powerImage = ImageElement.as(img8.getElement());
			img8.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(powerImage, 350, 350);
				}
			});
			canvas.setVisible(true);
			img8.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img8);

			// food
			final Image img9 = new Image("images/GreyCanteen.jpg");
			img9.setVisible(true);
			final ImageElement canteenImage = ImageElement.as(img9.getElement());
			img9.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(canteenImage, 350, 300);
				}
			});
			canvas.setVisible(true);
			img9.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img9);

			// storage
			final Image img10 = new Image("images/GreyFood.jpg");
			img10.setVisible(true);
			final ImageElement storageImage = ImageElement.as(img10
					.getElement());
			img10.addLoadHandler(new LoadHandler() {
				public void onLoad(LoadEvent event) { // fired by
														// RootPanel.get().add
					context.drawImage(storageImage, 300, 250);
				}
			});
			canvas.setVisible(true);
			img10.setVisible(false); // two line hack to ensure image is loaded
			RootPanel.get().add(img10);
			
			loadModuleImages();
		} else if (displayCurrentConfig == true) {
			loadBackground();
            loadModuleImages();
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