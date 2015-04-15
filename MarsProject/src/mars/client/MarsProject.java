package mars.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MarsProject implements EntryPoint {
	
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		if (login()) {
			RootPanel.get().clear();
		} else {
			;
		}
	}
	
	public boolean login() {
		final Button submitButton = new Button("Submit");
		final TextBox nameField = new TextBox();
		final PasswordTextBox passwordField = new PasswordTextBox();
		nameField.setText("MarsUser");
		passwordField.setText("123");
		final Label flagLabel = new Label();

		// We can add style names to widgets
		submitButton.addStyleName("submitButton");

		// Add the nameField and submitButton to the RootPanel
		VerticalPanel vp = new VerticalPanel();
		vp.add(new Label("Username:"));
		vp.add(nameField);
		vp.add(new Label("password:"));
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
				greetingService.greetServer(nameToServer, passwordToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								submitButton.setEnabled(true);
							}
							public void onSuccess(String result) {
								flagLabel.setText(result);
							}
						});
			}
		}
		// Add a handler to send the name to the server

		MyHandler handler = new MyHandler();
		submitButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
		
	    if (flagLabel.getText().equals("good")) {
	    	vp.clear();
	    	return true;
	    } else {
	    	submitButton.setEnabled(true);
	    	return false;
	    }
    }
}
