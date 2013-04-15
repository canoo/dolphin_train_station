package com.canoo.codecamp.dolphinpi

import javafx.collections.FXCollections
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

class DeparturesBoardApplicationModel {

	ClientDolphin clientDolphin;

	final javafx.collections.ObservableList<ClientPresentationModel> departuresOnBoard = FXCollections.observableArrayList()

	DeparturesBoardApplicationModel initialize() {
		(1..5).each {
			departuresOnBoard << clientDolphin.presentationModel(pmId(TYPE_DEPARTURE_ON_BOARD, it), ALL_ATTRIBUTES)
		}

		this
	}
}
