package com.a86c6f7964.ruist.example.jersey;

import com.a86c6f7964.ruist.core.Ruist;
import com.a86c6f7964.ruist.jersey.JerseyModule;
import com.a86c6f7964.ruist.varexport.VarExportModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

import java.net.InetSocketAddress;

/**
 *
 */
public class SimpleJersey {

    public static void main(String[] args) throws Exception {
        final Injector injector = Guice.createInjector(
                new VarExportModule(), // order matters! jersey is /* so will pick up everything left
                new JerseyModule("com.a86c6f7964.ruist.example.jersey", new InetSocketAddress("localhost", 9900))
        );

        // start
        injector.getInstance(Ruist.class).main().join();
    }
}
