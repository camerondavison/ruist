package com.a86c6f7964.ruist.jersey;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import javax.annotation.Nonnull;

/**
 *
 */
public final class JerseyModule extends AbstractModule {
    @Nonnull
    private final String packageName;

    public JerseyModule(@Nonnull final String packageName) {
        this.packageName = packageName;
    }

    @Override
    protected void configure() {
        install(new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/*").with(GuiceContainer.class);
            }
        });
        final ResourceConfig rc = new PackagesResourceConfig(packageName);
        for (final Class<?> resource : rc.getClasses()) {
            bind(resource);
        }
    }

}
