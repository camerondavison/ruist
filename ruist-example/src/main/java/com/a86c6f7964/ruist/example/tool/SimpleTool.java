package com.a86c6f7964.ruist.example.tool;

import com.a86c6f7964.ruist.core.Ruist;
import com.a86c6f7964.ruist.core.Ruister;
import com.a86c6f7964.ruist.jetty.JettyServerModule;
import com.a86c6f7964.ruist.varexport.VarExportModule;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.indeed.util.varexport.Export;
import com.indeed.util.varexport.VarExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public class SimpleTool {
    private static final Logger log = LoggerFactory.getLogger(SimpleTool.class);

    public static void main(String[] args) throws Exception {
        final Injector injector = Guice.createInjector(
                new JettyServerModule(new InetSocketAddress("localhost", 9900)),
                Ruister.services(SimpleService.class),
                new VarExportModule()
        );

        // start
        injector.getInstance(Ruist.class).main().join();
    }

    public static class SimpleService extends AbstractScheduledService {
        private volatile long i = 0;

        public SimpleService() {
            VarExporter.forNamespace(getClass().getSimpleName()).includeInGlobal().export(this, "");
        }

        @Export(name = "i")
        public long getI() {
            return i;
        }

        @Override
        protected void runOneIteration() throws Exception {
            i++;
            log.info("Running Simple Service!");
        }

        @Override
        protected Scheduler scheduler() {
            return Scheduler.newFixedRateSchedule(0, 1, TimeUnit.SECONDS);
        }
    }
}
