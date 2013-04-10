package com.canoo.codecamp.dolphinpi;

import org.opendolphin.core.client.comm.JavaFXUiThreadHandler;
import org.opendolphin.core.comm.DefaultInMemoryConfig;

public class ApplicationInMemoryStarter {
    public static void main(String[] args) throws Exception {
        DefaultInMemoryConfig config = new DefaultInMemoryConfig();
        config.registerDefaultActions();
        config.getClientDolphin().getClientConnector().setUiThreadHandler(new JavaFXUiThreadHandler());
        registerApplicationActions(config);
        com.canoo.codecamp.dolphinpi.Application.clientDolphin = config.getClientDolphin();
        javafx.application.Application.launch(com.canoo.codecamp.dolphinpi.Application.class);
    }

    private static void registerApplicationActions(DefaultInMemoryConfig config) {
        config.getServerDolphin().register(new ApplicationDirector());
    }

}
