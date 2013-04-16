package com.canoo.codecamp.dolphinpi

import javafx.application.Application
import org.opendolphin.core.client.comm.JavaFXUiThreadHandler
import org.opendolphin.core.comm.DefaultInMemoryConfig

def config = new DefaultInMemoryConfig()
config.clientDolphin.clientConnector.uiThreadHandler = new JavaFXUiThreadHandler()
config.serverDolphin.registerDefaultActions()
config.getServerDolphin().register(new ApplicationDirector());

AdminApplication.clientDolphin = config.getClientDolphin();

Application.launch(AdminApplication.class);


