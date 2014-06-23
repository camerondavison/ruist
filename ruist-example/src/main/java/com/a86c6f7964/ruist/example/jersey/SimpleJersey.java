package com.a86c6f7964.ruist.example.jersey;

import com.a86c6f7964.ruist.core.Ruist;
import com.a86c6f7964.ruist.core.Ruister;
import com.a86c6f7964.ruist.example.tool.SimpleTool;
import com.a86c6f7964.ruist.example.tool.SimpleTool.SimpleService;
import com.a86c6f7964.ruist.jersey.JerseyModule;
import com.a86c6f7964.ruist.jetty.JettyServerModule;
import com.a86c6f7964.ruist.varexport.VarExportModule;
import com.a88c6f7964.ruist.status.StatusWebModule;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.indeed.status.core.Dependency;
import com.indeed.status.core.PingableDependency;
import com.indeed.status.core.Urgency;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class SimpleJersey {
    private static final Logger log = LoggerFactory.getLogger(SimpleJersey.class);

    public static void main(String[] args) throws Exception {
        final Injector injector = Guice.createInjector(
                Ruister.services(SimpleService.class),

                new VarExportModule(),
                new StatusWebModule(ImmutableSet.<Class<? extends Dependency>>of(SimpleDep.class)),

                // order matters! jersey is /* so will pick up everything left
                new JerseyModule("com.a86c6f7964.ruist.example.jersey"),
                new JettyServerModule(new InetSocketAddress("localhost", 9900))
        );

        // start
        injector.getInstance(Ruist.class).main().join();
    }

    public static class SimpleDep extends PingableDependency {

        public SimpleDep() {
            super("SD", "simple dep", Urgency.REQUIRED);
        }

        @Override
        public void ping() throws Exception {
            log.info("PING simple dep");
        }
    }

    public static class SimpleService extends AbstractScheduledService {
        @Override
        protected void runOneIteration() throws Exception {
            log.info("Running!");
        }

        @Override
        protected Scheduler scheduler() {
            return Scheduler.newFixedRateSchedule(0, 1, TimeUnit.SECONDS);
        }
    }
}
