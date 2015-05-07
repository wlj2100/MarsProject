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
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MarsMap {
	static final String unsupportedBrowser = "Your browser does not support the HTML5 Canvas";
	static final int HEIGHT = 750;
	static final int yHelper = HEIGHT - 50;
	static final int WIDTH = 1800;
	Canvas canvas;
	private ModuleLogging log;
	private Configuration config;
	private int xGravity = 0;
	private int yGravity = 0;
	private final ArrayList<Module> moduleList = new ArrayList<Module>();
	VerticalPanel panel = new VerticalPanel();
	Context2d context;
	private final SoundController soundController = new SoundController();
	private final Sound minSound1 = soundController.createSound(
			Sound.MIME_TYPE_AUDIO_MPEG_MP3, "voice/minConfig1.mp3");
	private final Sound minSound2 = soundController.createSound(
			Sound.MIME_TYPE_AUDIO_MPEG_MP3, "voice/minConfig2.mp3");
	private final Sound currentSound = soundController.createSound(
			Sound.MIME_TYPE_AUDIO_MPEG_MP3, "voice/displayModule.mp3");
	private final Sound configSound = soundController.createSound(
			Sound.MIME_TYPE_AUDIO_MPEG_MP3, "voice/displayConfig.mp3");
	private Storage localStore = Storage.getLocalStorageIfSupported();
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
		final TextBox xcord = new TextBox();
		final TextBox ycord = new TextBox();
		final FlexTable table = new FlexTable();
		final Button setGravButton = new Button("Move Center Of Gravity");
		setGravButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				setxGravity(Integer.parseInt(xcord.getText()));
				setyGravity(Integer.parseInt(ycord.getText()));
				loadBackground();
				loadConfigImages();

			}
		});
		table.setText(0, 0, "Set X center of Gravity");
		table.setText(0, 1, "Set Y center of Gravity");
		table.setWidget(1, 0, xcord);
		table.setWidget(1, 1, ycord);
		table.setWidget(1, 2, setGravButton);
		final Button buildButton = new Button("Build Config");
		final Button displayMinConfig1 = new Button(
				"Display Minimun Configuration 1");
		displayMinConfig1.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				moduleList.clear();
				moduleList.addAll(config.ConfigToList("minConfig1"));
				// list = config.ConfigToList("minConfig1");
				loadBackground();
				loadConfigImages();
				minSound1.play();
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
				loadConfigImages();
				minSound2.play();
			}
		});
		final Button displayCurrent = new Button("Display Current Modules");
		displayCurrent.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// list = log.getSavedModules();
				moduleList.clear();
				moduleList.addAll(log.getSavedModules());
				if (meetConfigRequire(getTypeNum(moduleList),
						getTypeNum(config.ConfigToList("minConfig2")))) {
					Window.alert("Minimum configuration can be built!");
					displayMinConfig1.setVisible(true);
					displayMinConfig2.setVisible(true);
					buildButton.setVisible(true);
				} else {
					displayMinConfig1.setVisible(false);
					displayMinConfig2.setVisible(false);
					buildButton.setVisible(false);
				}
				loadBackground();
				loadModuleImages();
				currentSound.play();
			}
		});
		final TextBox configKey = new TextBox();
		final Button showConfig = new Button("show Config");
		showConfig.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				moduleList.clear();
				moduleList.addAll(config.ConfigToList(configKey.getText()));
				loadBackground();
				loadConfigImages();
				configSound.play();
			}
		});
		
		buildButton.addClickHandler(new ClickHandler() {
			public void onClick(final ClickEvent event) {
				int moveSize = 0;
				ArrayList<Module> localList = log.getSavedModules();
				// list of config: moduleList
				for (int i = 0; i < moduleList.size(); i++) {
					for (int j = 0; j < localList.size(); j++) {
						Module configModule = moduleList.get(i);
						Module localModule = localList.get(j);
						if (configModule.getType() == localModule.getType()
								&& (localModule.getStringStatus().equals(
										"Undamaged") || localModule
										.getStringStatus().equals("Repairable"))) {
							localList.remove(j);
							j--;
							localStore.removeItem(Integer.toString(localModule.getCode()));
							moveSize = moveSize + Math.abs(localModule.getX()-configModule.getX()) +Math.abs(localModule.getY()-configModule.getY());
							localModule.setXcoord(configModule.getX());
							localModule.setYcoord(configModule.getY());
							localStore.setItem(Integer.toString(localModule.getCode()), localModule.toString());
							break;
						}
					}
				}
				moduleList.clear();
				moduleList.addAll(log.getSavedModules());
				loadBackground();
				loadModuleImages();
				Window.alert("move size is: " + Integer.toString(moveSize));
				currentSound.play();
			}
		});
		final FlexTable t = new FlexTable();
		t.setWidget(0, 0, displayCurrent);
		t.setWidget(0, 1, displayMinConfig1);
		t.setWidget(0, 2, displayMinConfig2);
		t.setWidget(0, 3, buildButton);
		t.setWidget(1, 0, configKey);
		t.setWidget(1, 1, showConfig);
		displayMinConfig1.setVisible(false);
		displayMinConfig2.setVisible(false);
		buildButton.setVisible(false);
		marsPanel.add(t, DockPanel.WEST);
		marsPanel.add(table, DockPanel.SOUTH);
		return marsPanel;
	}

	public int getYHelper() {
		return MarsMap.yHelper;
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
								getYHelper() - moduleList.get(b - 1).getY()
										* 50);
					}
				}
			});

			canvas.setVisible(true);
			images.get(i - 1).setVisible(false);
			RootPanel.get().add(images.get(i - 1));
		}

	}

	public void loadConfigImages() {
		final ArrayList<Image> images = new ArrayList<Image>();
		for (int i = 1; i <= moduleList.size() + 1; i++) {
			images.add(new Image(moduleList.get(i - 1).getGreyImageName()));
			images.get(i - 1).addLoadHandler(new LoadHandler() {
				public void onLoad(final LoadEvent event) {
					for (int b = 1; b <= moduleList.size() + 1; b++) {
						context.drawImage(
								ImageElement.as(moduleList.get(b - 1)
										.getGreyImage().getElement()),
								(getxGravity() + moduleList.get(b - 1).getX()) * 50,
								getYHelper()
										- (getyGravity() + moduleList
												.get(b - 1).getY()) * 50);
					}
				}
			});

			canvas.setVisible(true);
			images.get(i - 1).setVisible(false);
			RootPanel.get().add(images.get(i - 1));
		}

	}

	public void loadBackground() {
		context.clearRect(0, 0, MarsMap.WIDTH, MarsMap.HEIGHT);
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

	public int getxGravity() {
		return xGravity;
	}

	public void setxGravity(int xGravity) {
		this.xGravity = xGravity;
	}

	public int getyGravity() {
		return yGravity;
	}

	public void setyGravity(int yGravity) {
		this.yGravity = yGravity;
	}

	private boolean meetConfigRequire(ArrayList<Integer> moduleTypeList,
			ArrayList<Integer> configTypeList) {
		boolean flag = true;
		for (int i = 0; i < 10; i++) {
			if (moduleTypeList.get(i) < configTypeList.get(i)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/*
	 * @parameter moduleList -- a list contain modules
	 */
	// "Plain";"Dormitory";"Sanitation";"Food";"Gym";"Canteen";"Power";"Control";"AirLock";"Medical";
	public ArrayList<Integer> getTypeNum(ArrayList<Module> aModuleList) {
		ArrayList<Integer> typeList = new ArrayList<Integer>();
		// initial the num of type
		for (int i = 0; i < 10; i++) {
			typeList.add(0);
		}
		// get the type of each entry of moduleList and add to the typeList
		for (int i = 0; i < aModuleList.size(); i++) {
			if (aModuleList.get(i).getType().equals("Plain")) {
				typeList.set(0, typeList.get(0) + 1);
			} else if (aModuleList.get(i).getType().equals("Dormitory")) {
				typeList.set(1, typeList.get(1) + 1);
			} else if (aModuleList.get(i).getType().equals("Sanitation")) {
				typeList.set(2, typeList.get(2) + 1);
			} else if (aModuleList.get(i).getType().equals("Food")) {
				typeList.set(3, typeList.get(3) + 1);
			} else if (aModuleList.get(i).getType().equals("Gym")) {
				typeList.set(4, typeList.get(4) + 1);
			} else if (aModuleList.get(i).getType().equals("Canteen")) {
				typeList.set(5, typeList.get(5) + 1);
			} else if (aModuleList.get(i).getType().equals("Power")) {
				typeList.set(6, typeList.get(6) + 1);
			} else if (aModuleList.get(i).getType().equals("Control")) {
				typeList.set(7, typeList.get(7) + 1);
			} else if (aModuleList.get(i).getType().equals("AirLock")) {
				typeList.set(8, typeList.get(8) + 1);
			} else if (aModuleList.get(i).getType().equals("Medical")) {
				typeList.set(9, typeList.get(9) + 1);
			} else {
				Window.alert("module with notype occures, plz check the local storage!");
			}
		}
		return typeList;
	}
}