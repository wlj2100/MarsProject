package mars.map;

import java.util.ArrayList;
import java.util.List;

import mars.client.Configuration;
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
	static final int yHelper = HEIGHT - 50;
	static final int WIDTH = 1800;
	Canvas canvas;
	private ModuleLogging log;
	private Configuration config;
	private final ArrayList<Module> moduleList = new ArrayList<Module>();
	VerticalPanel panel = new VerticalPanel();
	Context2d context;
	private final SoundController soundController = new SoundController();
	private final Sound minSound1 = soundController.createSound(
			Sound.MIME_TYPE_AUDIO_MPEG_MP3, "voice/test.mp3");
	private final Sound minSound2 = soundController.createSound(
			Sound.MIME_TYPE_AUDIO_MPEG_MP3, "voice/test.mp3");
	private final Sound maxSound1 = soundController.createSound(
			Sound.MIME_TYPE_AUDIO_MPEG_MP3, "voice/test.mp3");
	private final Sound maxSound2 = soundController.createSound(
			Sound.MIME_TYPE_AUDIO_MPEG_MP3, "voice/test.mp3");
	private final Sound currentSound = soundController.createSound(
			Sound.MIME_TYPE_AUDIO_MPEG_MP3, "voice/test.mp3");

	public MarsMap(ModuleLogging logger, Configuration config) {
		this.log = logger;
		this.config = config;
		canvas = Canvas.createIfSupported();
		if (canvas == null) {
			// RootPanel.get(divTagId).add(new Label(unsupportedBrowser));
			return;
		}
		// getSavedModules();
		createCanvas();
	}

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

	public DockPanel getMarsPanel() {
		DockPanel marsPanel = new DockPanel();
		marsPanel.add(getCanvas(), DockPanel.NORTH);
		final Button displayFullConfig1 = new Button(
				"Display Full Configuration 1");
		displayFullConfig1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				moduleList.clear();
				moduleList.addAll(config.ConfigToList("maxConfig1"));
				loadBackground();
				loadModuleImages();
				minSound1.play();
			}
		});
		final Button displayFullConfig2 = new Button(
				"Display Full Configuration 2");
		displayFullConfig2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				moduleList.clear();
				moduleList.addAll(config.ConfigToList("maxConfig2"));
				// list = config.ConfigToList("maxConfig1");
				loadBackground();
				loadModuleImages();
				minSound1.play();
				minSound2.play();
			}
		});
		final Button displayMinConfig1 = new Button(
				"Display Minimun Configuration 1");
		displayMinConfig1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				moduleList.clear();
				moduleList.addAll(config.ConfigToList("minConfig1"));
				// list = config.ConfigToList("minConfig1");
				loadBackground();
				loadModuleImages();
				minSound1.play();
				maxSound1.play();
			}
		});
		final Button displayMinConfig2 = new Button(
				"Display Minimun Configuration 2");
		displayMinConfig2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				moduleList.clear();
				moduleList.addAll(config.ConfigToList("minConfig2"));
				// list = config.ConfigToList("minConfig2");
				loadBackground();
				loadModuleImages();
				minSound1.play();
				maxSound2.play();
			}
		});
		final Button displayCurrent = new Button("Display Current Modules");
		displayCurrent.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// list = log.getSavedModules();
				moduleList.clear();
				moduleList.addAll(log.getSavedModules());
				loadBackground();
				loadModuleImages();
				minSound1.play();
				maxSound2.play();
				currentSound.play();
			}
		});
		marsPanel.add(displayMinConfig2, DockPanel.WEST);
		marsPanel.add(displayMinConfig1, DockPanel.WEST);
		marsPanel.add(displayFullConfig1, DockPanel.EAST);
		marsPanel.add(displayFullConfig2, DockPanel.EAST);
		marsPanel.add(displayCurrent, DockPanel.SOUTH);

		return marsPanel;
	}

	public int getYHelper() {
		return this.yHelper;
	}

	public void loadModuleImages() {
		final ArrayList<Image> images = new ArrayList<Image>();
		for (int i = 1; i <= moduleList.size() + 1; i++) {
			images.add(new Image(moduleList.get(i - 1).getImageName()));
			images.get(i - 1).addLoadHandler(new LoadHandler() {
				public void onLoad(final LoadEvent event) {
					for (int b = 1; b <= moduleList.size() + 1; b++) {
						context.drawImage(
								ImageElement.as(moduleList.get(b - 1)
										.getImage().getElement()),
								(moduleList.get(b - 1).getX()) * 50,
								getYHelper() - moduleList.get(b - 1).getY() * 50);
					}
				}
			});

			canvas.setVisible(true);
			images.get(i - 1).setVisible(false);
			RootPanel.get().add(images.get(i - 1));
		}

	}

	public void loadBackground() {
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