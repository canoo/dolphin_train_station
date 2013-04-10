package com.canoo.codecamp.dolphinpi.servlet;

import org.opendolphin.core.server.ServerDolphin;
import org.opendolphin.server.adapter.DolphinServlet;
import com.canoo.codecamp.dolphinpi.ApplicationDirector;

/**
 * For real server mode, this servlet acts as entry point for all communication.
 */
public class ApplicationServlet extends DolphinServlet{
    @Override
    protected void registerApplicationActions(ServerDolphin serverDolphin) {
        serverDolphin.register(new ApplicationDirector());
    }
}
