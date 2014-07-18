package com.a86c6f7964.ruist.core;

import com.google.common.util.concurrent.Service;
import com.google.common.util.concurrent.ServiceManager;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Set;
import java.util.concurrent.TimeUnit;
//import java.util.concurrent.TimeoutException;

/**
 *
 */
@Singleton
public class Ruist {
    @Nonnull
    private final Set<Service> services;

    @Inject
    Ruist(@Nonnull final Set<Service> services) {
        this.services = services;
    }

    public Thread main() throws TimeoutException {
        final Thread t = new Thread() {
            @Override
            public void run() {
                final ServiceManager manager = new ServiceManager(services);
                Runtime.getRuntime().addShutdownHook(new Thread() {
                    @Override
                    public void run() {
                        try {
                            manager.stopAsync().awaitStopped(10, TimeUnit.SECONDS);
                        } catch (TimeoutException e) {
                            throw new RuntimeException("Waited for 10 seconds to stop, exiting", e);
                        }
                    }
                });

                try {
                    manager.startAsync().awaitHealthy(10, TimeUnit.SECONDS);
                    manager.awaitStopped();
                } catch (Exception e) {
                    throw new RuntimeException("Unable to become healthy in 10 seconds", e);
                } finally {
                    manager.stopAsync();
                }
            }
        };
        t.start();
        return t;
    }
}
