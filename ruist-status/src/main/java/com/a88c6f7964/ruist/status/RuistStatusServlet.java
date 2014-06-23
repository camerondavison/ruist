package com.a88c6f7964.ruist.status;

import com.indeed.status.core.AbstractDependencyManager;
import com.indeed.status.web.AbstractDaemonCheckReportServlet;

import javax.annotation.Nonnull;
import javax.servlet.ServletConfig;

/**
 *
 */
class RuistStatusServlet extends AbstractDaemonCheckReportServlet {
    private final RuistDependencyManager manager;

    RuistStatusServlet(@Nonnull final RuistDependencyManager manager) {
        this.manager = manager;
    }

    @Override
    protected AbstractDependencyManager newManager(final ServletConfig config) {
        return manager;
    }
}
