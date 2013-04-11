package com.canoo.codecamp.dolphinpi;

import org.opendolphin.core.client.comm.JavaFXUiThreadHandler;
import org.opendolphin.core.comm.DefaultInMemoryConfig;

public class ApplicationInMemoryStarter {
    public static void main(String[] args) throws Exception {
		if (1 < 2) {

			DefaultInMemoryConfig config = new DefaultInMemoryConfig();
			config.registerDefaultActions();
			config.getClientDolphin().getClientConnector().setUiThreadHandler(new JavaFXUiThreadHandler());
			registerApplicationActions(config);
			com.canoo.codecamp.dolphinpi.ApplicationGroovy.clientDolphin = config.getClientDolphin();
		}
        javafx.application.Application.launch(ApplicationGroovy.class);
    }

    private static void registerApplicationActions(DefaultInMemoryConfig config) {
        config.getServerDolphin().register(new ApplicationDirector());
    }

}
