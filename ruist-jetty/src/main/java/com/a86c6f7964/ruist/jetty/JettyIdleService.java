package com.a86c6f7964.ruist.jetty;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.MoreExecutors;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

/**
 *
 */
@Singleton
class JettyIdleService extends AbstractIdleService {
    private static final Logger log = LoggerFactory.getLogger(JettyIdleService.class);

    @Nonnull
    private final Server server;

    @Inject
    JettyIdleService(@Nonnull @Jetty final Server server) {
        this.server = server;
    }

    @com.google.inject.Inject(optional = true)
    void injectListener(@Jetty Listener listener) {
        this.addListener(listener, MoreExecutors.sameThreadExecutor());
    }

    @Override
    protected void startUp() throws Exception {
        log.info("Starting up at [" + connectionString() + "]");
        server.start();
    }

    private String connectionString() {
        return Joiner.on(',').join(Iterables.transform(Arrays.asList(server.getConnectors()), new Function<Connector, Object>() {
            @Nullable
            @Override
            public String apply(@Nullable final Connector connector) {
                if (connector != null) {
                    return connector.getHost() + ":" + connector.getPort();
                } else {
                    return "null";
                }
            }
        }));
    }

    @Override
    protected void shutDown() throws Exception {
        log.info("Shutting down");
        server.stop();
        log.info("Shutdown");
    }
}
