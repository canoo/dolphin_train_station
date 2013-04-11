package com.canoo.codecamp.dolphinpi

import javafx.scene.control.LabelBuilder
import org.opendolphin.core.client.ClientPresentationModel

class DetailViewFactory {
	static javafx.scene.Node newView(){
		LabelBuilder.create().text("my label").build()
	}
}
