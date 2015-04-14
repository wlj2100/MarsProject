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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
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
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button submitButton = new Button("Submit");
		final TextBox nameField = new TextBox();
		final PasswordTextBox passwordField = new PasswordTextBox();
		nameField.setText("Mars User");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		submitButton.addStyleName("submitButton");

		// Add the nameField and submitButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		RootPanel.get("nameFieldContainer").add(nameField);
		RootPanel.get("submitButtonContainer").add(submitButton);
		RootPanel.get("passwordFieldContainer").add(passwordField);
		RootPanel.get("errorLabelContainer").add(errorLabel);

		// Focus the cursor on the name field when the app loads
		nameField.setFocus(true);
		nameField.selectAll();
		//
		// Create the popup dialog box
				final DialogBox dialogBox = new DialogBox();
				dialogBox.setText("Remote Procedure Call");
				dialogBox.setAnimationEnabled(true);
				final Button closeButton = new Button("Close");
				// We can set the id of a widget by accessing its Element
				closeButton.getElement().setId("closeButton");
				final HTML serverResponseLabel = new HTML();
				VerticalPanel dialogVPanel = new VerticalPanel();
				dialogVPanel.addStyleName("dialogVPanel");
				dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
				dialogVPanel.add(serverResponseLabel);
				dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
				dialogVPanel.add(closeButton);
				dialogBox.setWidget(dialogVPanel);
				// Add a handler to close the DialogBox
				closeButton.addClickHandler(new ClickHandler() {
					public void onClick(ClickEvent event) {
						dialogBox.hide();
						submitButton.setEnabled(true);
						submitButton.setFocus(true);
					}
				});

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
				errorLabel.setText("");
				String nameToServer = nameField.getText();
				String passwordToServer = passwordField.getText();

				// Then, we send the input to the server.
				submitButton.setEnabled(false);
				serverResponseLabel.setText("");
				greetingService.greetServer(nameToServer, passwordToServer,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								// Show the RPC error message to the user
								dialogBox
										.setText("Remote Procedure Call - Failure");
								serverResponseLabel
										.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(SERVER_ERROR);
								dialogBox.center();
								closeButton.setFocus(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Remote Procedure Call");
								serverResponseLabel
										.removeStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML(result);
								dialogBox.center();
								closeButton.setFocus(true);
							}
						});
			}
		}
		// Add a handler to send the name to the server

		MyHandler handler = new MyHandler();
		submitButton.addClickHandler(handler);
		nameField.addKeyUpHandler(handler);
	}
}
