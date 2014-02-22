package fr.xebia.sophietk.memory.resource;

import com.sun.jersey.api.client.Client;
import fr.xebia.sophietk.memory.App;
import org.eclipse.jetty.server.Server;
import org.junit.After;
import org.junit.Before;

public abstract class ServerResourceTest {

	protected static final int TEST_PORT = 3001;
	protected static final String TEST_APP_ROOT = "http://0.0.0.0:" + TEST_PORT;
	protected static final String IP_PATTERN = "\\b(?:\\d{1,3}\\.){3}\\d{1,3}\\b";

	protected Client client = Client.create();
	protected Server server;

	@Before
	public void setUp() throws Exception {
		server = App.startServer(TEST_PORT);
	}

	@After
	public void tearDown() throws Exception {
		if (server != null) server.stop();
	}
}
