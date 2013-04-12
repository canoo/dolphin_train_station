package com.canoo.codecamp.dolphinpi

import javafx.scene.control.TableColumnBuilder
import javafx.scene.control.TableView
import javafx.scene.control.TableViewBuilder
import javafx.util.Callback
import org.opendolphin.core.client.ClientAttributeWrapper
import org.opendolphin.core.client.ClientPresentationModel

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

class DepartureBoardViewFactory {
	static javafx.scene.Node newView(
		javafx.collections.ObservableList<ClientPresentationModel> data
	){
		TableView result = TableViewBuilder.create()
			.items(data)
			.columns(
			newTableColumn(ATT_DEPARTURE_TIME, "Uhrzeit"),
			newTableColumn(ATT_TRAIN_NUMBER, "Fahrt"),
			newTableColumn(ATT_DESTINATION, "Richtung"),
			newTableColumn(ATT_STATUS, "Status"),
			newTableColumn(ATT_TRACK, "Gleis"),
		)
			.columnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY).build()

	}


	static newTableColumn(String inPropertyName, String inTitle) {
		TableColumnBuilder.create()
			.text(inTitle)
			.cellValueFactory({ row -> new ClientAttributeWrapper(row.value[inPropertyName]) } as Callback)
			.build()
	}

}
