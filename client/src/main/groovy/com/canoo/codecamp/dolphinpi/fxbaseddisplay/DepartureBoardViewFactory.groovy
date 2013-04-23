package com.canoo.codecamp.dolphinpi.fxbaseddisplay

import com.canoo.codecamp.dolphinpi.BoardItemConstants
import javafx.collections.FXCollections
import javafx.scene.Parent
import javafx.scene.control.TableColumnBuilder
import javafx.scene.control.TableView
import javafx.scene.control.TableViewBuilder
import javafx.util.Callback
import org.opendolphin.core.client.ClientAttributeWrapper
import org.opendolphin.core.client.ClientDolphin

class DepartureBoardViewFactory {

	static Parent createView(ClientDolphin clientDolphin) {
		def departuresOnBoard = FXCollections.observableArrayList()
		departuresOnBoard.addAll(clientDolphin.findAllPresentationModelsByType(BoardItemConstants.TYPE.BOARD_ITEM))

		TableViewBuilder.create()
				.items(departuresOnBoard)
				.columns(
					createColumn(BoardItemConstants.ATT.DEPARTURE_TIME, "Uhrzeit"),
					createColumn(BoardItemConstants.ATT.TRAIN_NUMBER,   "Fahrt"),
					createColumn(BoardItemConstants.ATT.DESTINATION,    "Richtung"),
					createColumn(BoardItemConstants.ATT.STATUS,         "Status"),
					createColumn(BoardItemConstants.ATT.TRACK,          "Gleis"),
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
