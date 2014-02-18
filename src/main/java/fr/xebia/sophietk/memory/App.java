package fr.xebia.sophietk.memory;

import com.google.inject.Guice;
import com.google.inject.servlet.GuiceFilter;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import fr.xebia.sophietk.memory.resource.MemoryResource;
import fr.xebia.sophietk.memory.resource.ScoreResource;
import org.eclipse.jetty.server.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.util.EnumSet;

public class App {

	public static final int PORT = 3000;

	public static void main(String[] args) throws Exception {
		Server server = startServer(PORT);

		try {
			System.out.println("Press any key to stop the service...");
			System.in.read();
		} finally {
			server.stop();
		}
	}

	protected static Server startServer(int port) throws Exception {
		Guice.createInjector(new JerseyServletModule() {
			@Override
			protected void configureServlets() {
				bind(MemoryResource.class);
				bind(ScoreResource.class);
				serve("/*").with(GuiceContainer.class);
			}
		});

		Server server = new Server(port);
		ServletContextHandler handler = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
		handler.addFilter(GuiceFilter.class, "/*", EnumSet.allOf(DispatcherType.class));
		handler.addServlet(DefaultServlet.class, "/");
		server.start();

		return server;
	}

}
