package com.canoo.codecamp.dolphinpi

import javafx.scene.control.TableColumnBuilder
import javafx.scene.control.TableViewBuilder
import javafx.scene.control.cell.PropertyValueFactory
import org.opendolphin.core.client.ClientPresentationModel
import javafx.collections.ObservableList

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*


class MasterViewFactory {

	static javafx.scene.Node newMasterView(ObservableList<ClientPresentationModel> data){
		TableViewBuilder.create().items(data).columns(
			TableColumnBuilder.create().text("Uhrzeit").cellValueFactory(new PropertyValueFactory(ATT_DEPARTURE_TIME)).build(),
			TableColumnBuilder.create().text("Fahrt").cellValueFactory(new PropertyValueFactory(ATT_TRAIN_NUMBER)).build(),
			TableColumnBuilder.create().text("in Richtung").cellValueFactory(new PropertyValueFactory(ATT_DESTINATION)).build(),
			TableColumnBuilder.create().text("Status").cellValueFactory(new PropertyValueFactory(ATT_STATUS)).build(),
		).build()

	}

}
