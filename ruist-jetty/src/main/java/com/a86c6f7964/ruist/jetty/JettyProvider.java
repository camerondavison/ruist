package com.a86c6f7964.ruist.jetty;

import com.google.inject.servlet.GuiceFilter;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.servlet.DispatcherType;
import java.net.InetSocketAddress;
import java.util.EnumSet;

/**
 *
 */
@Singleton
public class JettyProvider implements Provider<Server> {

    @Nonnull
    private final InetSocketAddress address;

    @Inject
    JettyProvider(@Nonnull @Jetty final InetSocketAddress address) {
        this.address = address;
    }

    @Override
    public Server get() {
        final Server s = new Server(address);
        final ServletContextHandler h = new ServletContextHandler(s, "/", false, false);
        h.addFilter(GuiceFilter.class, "/*", EnumSet.of(DispatcherType.REQUEST));
        h.addServlet(DefaultServlet.class, "/");
        return s;
    }
}
