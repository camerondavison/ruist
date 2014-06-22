package com.a86c6f7964.ruist.jetty;

import com.google.common.util.concurrent.Service.Listener;
import com.google.inject.AbstractModule;

/**
 * todo: make this more generic, and not tied to jetty
 */
public class JerseyBridge extends AbstractModule {
    @Override
    protected void configure() {
        bind(Listener.class).annotatedWith(Jetty.class).to(JettyListener.class);
    }
}
