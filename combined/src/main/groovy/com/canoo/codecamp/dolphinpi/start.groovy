package com.canoo.codecamp.dolphinpi

import org.opendolphin.core.client.comm.JavaFXUiThreadHandler
import org.opendolphin.core.comm.DefaultInMemoryConfig
import com.canoo.codecamp.dolphinpi.MainRegistrarAction
import com.canoo.codecamp.dolphinpi.MainView

def config = new DefaultInMemoryConfig()
config.clientDolphin.clientConnector.uiThreadHandler = new JavaFXUiThreadHandler()
config.serverDolphin.registerDefaultActions()

config.serverDolphin.register(new MainRegistrarAction())

MainView.show(config.clientDolphin)
