package com.a88c6f7964.ruist.status;

import com.google.common.collect.ImmutableSet;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.Provides;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.servlet.ServletModule;
import com.indeed.status.core.Dependency;
import com.indeed.status.web.AbstractDaemonCheckReportServlet;

import javax.annotation.Nonnull;
import javax.inject.Singleton;
import java.util.Set;

/**
 *
 */
public class StatusWebModule extends AbstractModule {

    @Nonnull
    private final Set<Class<? extends Dependency>> deps;

    public StatusWebModule() {
        this(ImmutableSet.<Class<? extends Dependency>>of());
    }

    public StatusWebModule(@Nonnull final Set<Class<? extends Dependency>> deps) {
        this.deps = deps;
    }

    @Override
    protected void configure() {
        install(new ServletModule() {
            @Override
            protected void configureServlets() {
                serve("/private/healthcheck/live").with(Key.get(AbstractDaemonCheckReportServlet.class, Live.class));
                serve("/private/healthcheck").with(Key.get(AbstractDaemonCheckReportServlet.class, Background.class));
            }
        });
        bind(RuistDependencyManager.class).annotatedWith(Live.class).to(RuistDependencyManager.class).asEagerSingleton();
        bind(RuistDependencyManager.class).annotatedWith(Background.class).to(RuistDependencyManager.class).asEagerSingleton();

        final LinkedBindingBuilder<Dependency> builder = Multibinder.newSetBinder(binder(), Dependency.class).addBinding();
        for (final Class<? extends Dependency> dep : deps) {
            builder.to(dep);
        }
    }

    @Singleton
    @Provides
    @Live
    AbstractDaemonCheckReportServlet getLive(@Nonnull @Live final RuistDependencyManager mgr,
                                             @Nonnull final Set<Dependency> deps) {
        for (final Dependency dep : deps) {
            mgr.addDependency(dep);
        }
        return new RuistStatusServlet(mgr);
    }

    @Singleton
    @Provides
    @Background
    AbstractDaemonCheckReportServlet getBackground(@Nonnull @Background final RuistDependencyManager mgr,
                                                   @Nonnull final Set<Dependency> deps) {
        for (final Dependency dep : deps) {
            mgr.launchPinger(dep);
        }
        return new RuistStatusServlet(mgr);
    }
}
