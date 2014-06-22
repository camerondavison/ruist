package com.a86c6f7964.ruist.jetty;

import com.a86c6f7964.ruist.jersey.Jersey;
import com.google.common.util.concurrent.Service.Listener;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.inject.Inject;

/**
 *
 */
class JettyListener extends Listener {
    private final Logger log = LoggerFactory.getLogger(JettyListener.class);
    @Nonnull
    private final Server server;
    @Nonnull
    private final String packageName;

    @Inject
    JettyListener(@Nonnull @Jetty final Server server, @Nonnull @Jersey String packageName) {
        this.server = server;
        this.packageName = packageName;
    }

    @Override
    public void starting() {
        log.info("Starting jersey for package " + packageName);

        final ServletHolder sh = new ServletHolder(ServletContainer.class);
        sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
        sh.setInitParameter("com.sun.jersey.config.property.packages", packageName);//Set the package where the services reside
        sh.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true");

        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        context.addServlet(sh, "/*");
    }
}
