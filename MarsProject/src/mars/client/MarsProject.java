package mars.client;

import mars.map.MarsMap;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SplitLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MarsProject implements EntryPoint {

	private final ModuleLogging moduleLogging = new ModuleLogging();
	private final MarsMap map = new MarsMap(moduleLogging.getModuleLocal());
	private final TenDayAlert tenday = new TenDayAlert();
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// simple login
		// username: MarsUser
		// password: 123
		
		final Button submitButton = new Button("Submit");
		final TextBox nameField = new TextBox();
		final PasswordTextBox passwordField = new PasswordTextBox();
		nameField.setText("MarsUser");
		passwordField.setText("123");

		// We can add style names to widgets
		submitButton.addStyleName("submitButton");

		// Add the nameField and submitButton to the RootPanel
		VerticalPanel vp = new VerticalPanel();
		vp.add(new Label("Username:"));
		vp.add(nameField);
		vp.add(new Label("Password:"));
		vp.add(passwordField);
		vp.add(submitButton);
		// Use RootPanel.get() to get the entire body element
		RootPanel.get().add(vp);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();

		// Create a handler for the sendButton, nameField and passwordField
		class MyHandler implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendDataToServer();
			}

			/**
			 * Fired when the user types in the nameField.
			 */
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendDataToServer();
				}
			}

			/**
			 * Send the name from the nameField to the server and wait for a response.
			 */
			private void sendDataToServer() {
				// First, we validate the input.
				String nameToServer = nameField.getText();
				String passwordToServer = passwordField.getText();
				// Then, we send the input to the server.
				submitButton.setEnabled(false);
				if (nameToServer.equals("MarsUser")&&passwordToServer.equals("123")) {
					RootPanel.get().clear();
					Window.alert("login successfully");
					showApp();
				} else {
					submitButton.setEnabled(true);
					Window.alert("invalid username or password");
				}

			}
		}
		// Add a handler to send the name to the server
		MyHandler handler = new MyHandler();
		submitButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
		
		
	}
	
	// here is the main app
	private void showApp() {
		
		DockPanel splitPanel = new DockPanel();
		splitPanel.add(moduleLogging.getTable(),DockPanel.NORTH);
		splitPanel.add(tenday.getAlert(), DockPanel.NORTH);

        final Button testModuleLogin = new Button("test");
        testModuleLogin.addClickHandler(new ClickHandler(){
        	public void onClick(ClickEvent event) {
        		moduleLogging.getSavedModules();
        	}
        });
        
		splitPanel.add(testModuleLogin,DockPanel.NORTH);
		VerticalPanel vp2 = new VerticalPanel();
		vp2.add(map.getMarsPanel());
		
       
       
        final TabLayoutPanel p = new TabLayoutPanel(1.5, Unit.EM);
        p.add(splitPanel,"loginmodule");
        p.add(vp2, "Display");       
      
        RootLayoutPanel.get().add(p);
	}
	
	
}
