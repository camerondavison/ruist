package com.a86c6f7964.ruist.varexport;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.ServletModule;
import com.indeed.util.varexport.servlet.ViewExportedVariablesServlet;

/**
 *
 */
public class VarExportModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/private/v").with(new ViewExportedVariablesServlet());
            }
        });
    }
}
