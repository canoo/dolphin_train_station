package com.canoo.codecamp.dolphinpi.servlet;

import org.opendolphin.core.server.ServerDolphin;
import org.opendolphin.server.adapter.DolphinServlet;
import com.canoo.codecamp.dolphinpi.ApplicationDirector;

import java.util.logging.Level;

/**
 * For real server mode, this servlet acts as entry point for all communication.
 */
public class ApplicationServlet extends DolphinServlet{
    @Override
    protected void registerApplicationActions(ServerDolphin serverDolphin) {
		java.util.logging.Logger.getGlobal().setLevel(Level.ALL);
        serverDolphin.register(new ApplicationDirector());
    }
}
