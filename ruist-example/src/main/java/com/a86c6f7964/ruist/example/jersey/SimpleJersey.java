package com.a86c6f7964.ruist.example.jersey;

import com.a86c6f7964.ruist.core.Ruist;
import com.a86c6f7964.ruist.jersey.JerseyModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 *
 */
public class SimpleJersey {

    public static void main(String[] args) throws Exception {
        final Injector injector = Guice.createInjector(
                new JerseyModule("com.a86c6f7964.ruist.example.jersey", new InetSocketAddress("localhost", 9900))
        );

        // start
        injector.getInstance(Ruist.class).main().join();
    }
}
