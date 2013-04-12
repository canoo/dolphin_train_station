package com.canoo.codecamp.dolphinpi

import javafx.collections.FXCollections
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel
import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

class DeparturesBoardApplicationModel {

	ClientDolphin clientDolphin;

	javafx.collections.ObservableList<ClientPresentationModel> departuresOnBoard = FXCollections.observableArrayList()

	DeparturesBoardApplicationModel initialize() {
		departuresOnBoard << clientDolphin.presentationModel(DEPARTURE_ON_BOARD_1, ALL_ATTRIBUTES)
		departuresOnBoard << clientDolphin.presentationModel(DEPARTURE_ON_BOARD_2, ALL_ATTRIBUTES)
		departuresOnBoard << clientDolphin.presentationModel(DEPARTURE_ON_BOARD_3, ALL_ATTRIBUTES)
		departuresOnBoard << clientDolphin.presentationModel(DEPARTURE_ON_BOARD_4, ALL_ATTRIBUTES)
		departuresOnBoard << clientDolphin.presentationModel(DEPARTURE_ON_BOARD_5, ALL_ATTRIBUTES)

		this
	}
}
