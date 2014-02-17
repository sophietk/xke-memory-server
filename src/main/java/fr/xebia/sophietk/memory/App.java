package fr.xebia.sophietk.memory;

import com.google.inject.Guice;
import com.google.inject.servlet.GuiceFilter;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import fr.xebia.sophietk.memory.resource.MemoryResource;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.webapp.WebAppContext;

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
		Server server = new Server(port);
		WebAppContext appContext = new WebAppContext();
		FilterHolder guiceFilter = new FilterHolder(Guice.createInjector(new JerseyServletModule() {
			@Override
			protected void configureServlets() {
				bind(MemoryResource.class);

				serve("/*").with(GuiceContainer.class);
			}
		}).getInstance(GuiceFilter.class));
		//appContext.addEventListener(new GuiceConfiguration());
		appContext.addFilter(guiceFilter, "/*", 0);
		appContext.setResourceBase("nothing");
		server.setHandler(appContext);
		server.start();

		return server;
	}

}
