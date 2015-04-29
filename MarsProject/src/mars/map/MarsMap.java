
package mars.map;

import java.util.ArrayList;
import java.util.List;

import mars.client.Module;
import mars.client.ModuleLogging;

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
	static final int HEIGHT = 650;
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
		} else if (displayFullConfig2 == true) {
			loadBackground();
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

			final Image img5 = new Image("images/GreyGym.jpg");
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

		} else if (displayMinConfig2 == true) {
			loadBackground();
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