package com.canoo.codecamp.dolphinpi.fxbaseddisplay

import javafx.collections.FXCollections
import javafx.scene.Parent
import javafx.scene.control.TableColumnBuilder
import javafx.scene.control.TableView
import javafx.scene.control.TableViewBuilder
import javafx.util.Callback
import org.opendolphin.core.client.ClientAttributeWrapper
import org.opendolphin.core.client.ClientDolphin

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

class DepartureBoardViewFactory {

	static Parent createView(ClientDolphin clientDolphin) {
		def departuresOnBoard = FXCollections.observableArrayList()
		departuresOnBoard.addAll(clientDolphin.findAllPresentationModelsByType(TYPE_DEPARTURE_ON_BOARD))

		TableViewBuilder.create()
				.items(departuresOnBoard)
				.columns(
					createColumn(ATT_DEPARTURE_TIME, "Uhrzeit"),
					createColumn(ATT_TRAIN_NUMBER,   "Fahrt"),
					createColumn(ATT_DESTINATION,    "Richtung"),
					createColumn(ATT_STATUS,         "Status"),
					createColumn(ATT_TRACK,          "Gleis"),
				 )
				.columnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY)
				.build()
	}

	static createColumn(String inPropertyName, String inTitle) {
		TableColumnBuilder.create()
				.text(inTitle)
				.cellValueFactory({ row -> new ClientAttributeWrapper(row.value[inPropertyName]) } as Callback)
				.build()
	}

}
