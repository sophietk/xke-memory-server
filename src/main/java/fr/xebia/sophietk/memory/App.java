package fr.xebia.sophietk.memory;

import com.google.inject.Guice;
import com.google.inject.servlet.GuiceFilter;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import fr.xebia.sophietk.memory.resource.AdminResource;
import fr.xebia.sophietk.memory.resource.MemoryResource;
import fr.xebia.sophietk.memory.resource.ScoreResource;
import org.eclipse.jetty.server.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.EnumSet;

public class App {

	public static final int PORT = 3000;
	public static final String HEADER_ADMIN_PASS = "adminpass";
	private static String adminPass = HEADER_ADMIN_PASS;

	public static void main(String[] args) throws Exception {
		Server server = startServer(PORT);
		try {
			adminPass = args[0];
		} catch (ArrayIndexOutOfBoundsException ignored) {
		}

		try {
			System.out.println("Press any key to stop the service...");
			System.in.read();
		} finally {
			server.stop();
		}
	}

	public static Server startServer(int port) throws Exception {
		Guice.createInjector(new JerseyServletModule() {
			@Override
			protected void configureServlets() {
				bind(MemoryResource.class);
				bind(ScoreResource.class);
				bind(AdminResource.class);
				serve("/*").with(GuiceContainer.class);
				filter("/*").through(new Filter() {
					@Override
					public void init(FilterConfig filterConfig) throws ServletException {
					}

					@Override
					public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
						HttpServletResponse response = (HttpServletResponse) servletResponse;
						response.addHeader("Access-Control-Allow-Origin", "*");
						response.addHeader("Access-Control-Allow-Headers", HEADER_ADMIN_PASS + ",Content-Type");
						filterChain.doFilter(servletRequest, servletResponse);
					}

					@Override
					public void destroy() {
					}
				});
				filter("/admin/*").through(new Filter() {
					@Override
					public void init(FilterConfig filterConfig) throws ServletException {
					}

					@Override
					public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
						HttpServletRequest request = (HttpServletRequest) servletRequest;
						HttpServletResponse response = (HttpServletResponse) servletResponse;
						if (!"OPTIONS".equals(request.getMethod()) && !adminPass.equals(request.getHeader(HEADER_ADMIN_PASS))) {
							response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
							return;
						}
						filterChain.doFilter(servletRequest, servletResponse);
					}

					@Override
					public void destroy() {
					}
				});
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
