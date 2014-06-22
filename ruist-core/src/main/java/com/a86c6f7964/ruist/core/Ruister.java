package com.a86c6f7964.ruist.core;

import com.google.common.util.concurrent.Service;
import com.google.inject.AbstractModule;
import com.google.inject.Module;
import com.google.inject.multibindings.Multibinder;

import javax.annotation.Nonnull;

/**
 *
 */
public final class Ruister {
    private Ruister() {}

    public static Module services(@Nonnull final Class<? extends Service> clazz) {
        return new AbstractModule() {
            @Override
            protected void configure() {
                Multibinder.newSetBinder(binder(), Service.class).addBinding().to(clazz);
            }
        };
    }

    public static Module services(@Nonnull final Class<? extends Service> clazz1,
                                  @Nonnull final Class<? extends Service> clazz2) {
        return new AbstractModule() {
            @Override
            protected void configure() {
                final Multibinder<Service> b = Multibinder.newSetBinder(binder(), Service.class);
                b.addBinding().to(clazz1);
                b.addBinding().to(clazz2);
            }
        };
    }

    public static Module services(@Nonnull final Class<? extends Service> clazz1,
                                  @Nonnull final Class<? extends Service> clazz2,
                                  @Nonnull final Class<? extends Service> clazz3) {
        return new AbstractModule() {
            @Override
            protected void configure() {
                final Multibinder<Service> b = Multibinder.newSetBinder(binder(), Service.class);
                b.addBinding().to(clazz1);
                b.addBinding().to(clazz2);
                b.addBinding().to(clazz3);
            }
        };
    }
}
