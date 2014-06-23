package com.a86c6f7964.ruist.jetty;

import com.google.common.util.concurrent.Service;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import org.eclipse.jetty.server.Server;

import javax.annotation.Nonnull;
import java.net.InetSocketAddress;

/**
 *
 */
public final class JettyServerModule extends AbstractModule {
    @Nonnull
    private final InetSocketAddress address;

    public JettyServerModule(@Nonnull final InetSocketAddress address) {
        this.address = address;
    }

    @Override
    protected void configure() {
        bind(InetSocketAddress.class).annotatedWith(Jetty.class).toInstance(address);
        bind(Server.class).annotatedWith(Jetty.class).toProvider(JettyProvider.class).asEagerSingleton();
        Multibinder.newSetBinder(binder(), Service.class).addBinding().to(JettyIdleService.class);
    }
}
