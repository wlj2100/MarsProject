package mars.map;

import mars.client.Module;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.Context2d;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MarsMap {
	static final String unsupportedBrowser = "Your browser does not support the HTML5 Canvas";
	static final int height = 900;
	static final int width = 1600;
	Canvas canvas;
	private Storage localStorage;
	
	VerticalPanel panel = new VerticalPanel();
	Context2d context;
	public MarsMap(Storage localStorage){
		this.localStorage = localStorage;
		canvas = Canvas.createIfSupported();
		if (canvas == null) {
			//RootPanel.get(divTagId).add(new Label(unsupportedBrowser));
			return;
		}
		createCanvas();
	}
	private void createCanvas(){

		canvas.setWidth(width + "px");
		canvas.setHeight(height + "px");
		canvas.setCoordinateSpaceWidth(width);
		canvas.setCoordinateSpaceHeight(height);
		//RootPanel.get(divTagId).add(canvas); for raw use of canvas

		context = canvas.getContext2d(); // a rendering context

       

		final Image img = new Image("images/crater.jpg");
		img.setVisible(true);
		final ImageElement crater = ImageElement.as(img.getElement());
		img.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) { // fired by RootPanel.get().add
				context.drawImage(crater,0,0);
			}
		}); 
	    canvas.setVisible(true);
		//panel.add(canvas);
		
	//	RootPanel.get().add(canvas);
	    img.setVisible(false);     // two line hack to ensure image is loaded
		RootPanel.get().add(img);  // image must be on page to fire load event 
	
	}
	public Canvas getCanvas(){
		return canvas;
	}
	public void addModuleToMap(Module moduleType){
		Module adModule = moduleType;
		final Image moduleImage;
	   if(moduleType.getCode() < 20){
		   moduleImage = new Image("images/Canteen.jpg");
	   } else if(moduleType.getCode() > 20){
		   moduleImage = new Image("images/Sanitation.jpg");
	   } else if(moduleType.getCode() < 20){
		   moduleImage = new Image("images/Airlock.jpg");
	   } else if(moduleType.getCode() < 20){
		   moduleImage = new Image("images/Control.jpg");
	   } else if(moduleType.getCode() < 20){
		   moduleImage = new Image("images/Food.jpg");
	   } else if(moduleType.getCode() < 20){
		   moduleImage = new Image("images/Gym.jpg");
	   } else if(moduleType.getCode() < 20){
		   moduleImage = new Image("images/Plain.jpg");
	   } else if(moduleType.getCode() < 20){
		   moduleImage = new Image("images/Power.jpg");
		   
	   } else{
		   moduleImage = new Image("images/Power.jpg");
	   }
	   moduleImage.setVisible(true);
		final ImageElement module = ImageElement.as(moduleImage.getElement());
		final int x = adModule.getX();
		moduleImage.addLoadHandler(new LoadHandler() {
			public void onLoad(LoadEvent event) {
				//final int x= xPos;
				//final int y = yPos; // fired by RootPanel.get().add
				context.drawImage(module,x,x);
			}
		}); 
	   moduleImage.setVisible(false);     // two line hack to ensure image is loaded
		RootPanel.get().add(moduleImage);
	}
     
	
	
}