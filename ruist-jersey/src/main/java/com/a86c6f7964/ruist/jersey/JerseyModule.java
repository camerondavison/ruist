package com.a86c6f7964.ruist.jersey;

import com.a86c6f7964.ruist.jetty.JerseyBridge;
import com.a86c6f7964.ruist.jetty.JettyServerModule;
import com.google.inject.AbstractModule;

import javax.annotation.Nonnull;
import java.net.InetSocketAddress;

/**
 *
 */
public final class JerseyModule extends AbstractModule {
    @Nonnull
    private final InetSocketAddress address;
    @Nonnull
    private final String packageName;

    public JerseyModule(@Nonnull final String packageName, @Nonnull final InetSocketAddress address) {
        this.address = address;
        this.packageName = packageName;
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Jersey.class).to(packageName);
        install(new JettyServerModule(address));

        install(new JerseyBridge());
    }

}
