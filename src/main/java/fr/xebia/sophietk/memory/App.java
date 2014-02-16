package fr.xebia.sophietk.memory;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import com.sun.jersey.simple.container.SimpleServerFactory;
import fr.xebia.sophietk.memory.service.ScoreService;

import java.io.Closeable;
import java.io.IOException;

public class App {

	public static final int PORT = 3000;

	public static void main(String[] args) throws Exception {
		Closeable server = startServer(PORT);

		try {
			System.out.println("Press any key to stop the service...");
			System.in.read();
		} finally {
			server.close();
		}
	}

	protected static Closeable startServer(int port) throws IOException {
		Injector injector = Guice.createInjector(new ServletModule() {
			@Override
			protected void configureServlets() {
				bind(ScoreService.class).in(Singleton.class);
				bind(JacksonJsonProvider.class).in(Scopes.SINGLETON);
				//serve("/*").with(GuiceContainer.class);
			}
		});
		ResourceConfig rc = new PackagesResourceConfig("fr.xebia.sophietk.memory.resource");
		IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory(rc, injector);

		//ResourceConfig resourceConfig = new DefaultResourceConfig(MemoryResource.class, JacksonJsonProvider.class);
		return SimpleServerFactory.create("http://0.0.0.0:" + port, rc, ioc);
	}

}
