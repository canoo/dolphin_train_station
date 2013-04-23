package com.canoo.codecamp.dolphinpi.fxbaseddisplay

import javafx.collections.FXCollections
import javafx.scene.Parent
import javafx.scene.control.TableColumnBuilder
import javafx.scene.control.TableView
import javafx.scene.control.TableViewBuilder
import javafx.util.Callback
import org.opendolphin.core.client.ClientAttributeWrapper
import org.opendolphin.core.client.ClientDolphin

import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.DEPARTURE_TIME
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.DESTINATION
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.STATUS
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.TRACK
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.TRAIN_NUMBER
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.TYPE.BOARD_ITEM

class DepartureBoardViewFactory {

	static Parent createView(ClientDolphin clientDolphin) {
		def departuresOnBoard = FXCollections.observableArrayList()
		departuresOnBoard.addAll(clientDolphin.findAllPresentationModelsByType(BOARD_ITEM))

		TableViewBuilder.create()
				.items(departuresOnBoard)
				.columns(
					createColumn(DEPARTURE_TIME, "Uhrzeit"),
					createColumn(TRAIN_NUMBER,   "Fahrt"),
					createColumn(DESTINATION,    "Richtung"),
					createColumn(STATUS,         "Status"),
					createColumn(TRACK,          "Gleis"),
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
