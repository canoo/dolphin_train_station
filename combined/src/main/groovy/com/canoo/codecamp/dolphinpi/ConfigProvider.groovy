package com.canoo.codecamp.dolphinpi

import org.opendolphin.core.client.comm.BlindCommandBatcher
import org.opendolphin.core.client.comm.InMemoryClientConnector
import org.opendolphin.core.client.comm.JavaFXUiThreadHandler
import org.opendolphin.core.comm.DefaultInMemoryConfig

class ConfigProvider {

	DefaultInMemoryConfig newConfig() {
		def config = new DefaultInMemoryConfig()
		def batcher = new BlindCommandBatcher(deferMillis: 400, mergeValueChanges: true)
		config.clientDolphin.clientConnector = new InMemoryClientConnector(config.clientDolphin, batcher)

		//config.clientDolphin.clientConnector.sleepMillis = 100
		config.clientDolphin.clientConnector.serverConnector = config.getServerDolphin().serverConnector


		config.clientDolphin.clientConnector.uiThreadHandler = new JavaFXUiThreadHandler()
		config.serverDolphin.registerDefaultActions()

		config.getServerDolphin().register(new ApplicationDirector());

		return config
	}
}
