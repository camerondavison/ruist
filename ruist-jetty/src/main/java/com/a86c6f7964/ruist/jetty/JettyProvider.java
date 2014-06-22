package com.a86c6f7964.ruist.jetty;

import com.google.common.util.concurrent.Service.Listener;
import org.eclipse.jetty.server.Server;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.net.InetSocketAddress;

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
        return new Server(address);
    }
}
