package mars.server;

import mars.client.GreetingService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {

	public String greetServer(String name, String password) throws IllegalArgumentException {

		// Escape data from the client to avoid cross-site script vulnerabilities.
		name = escapeHtml(name);
		password = escapeHtml(password);
		// the user name is MarsUser
		if (name=="Mars User"&& password=="123") {
            return "Hello, user: " + name + "<br><br>your password is: " + password;
		} else {
			return "bad username or password!";
		}
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}
}
