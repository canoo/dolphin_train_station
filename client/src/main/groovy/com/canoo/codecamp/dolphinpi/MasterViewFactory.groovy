package com.canoo.codecamp.dolphinpi

import javafx.beans.property.SimpleStringProperty
import javafx.beans.value.ObservableValue
import javafx.collections.ObservableList
import javafx.scene.control.TableColumn
import javafx.scene.control.TableColumnBuilder
import javafx.scene.control.TableViewBuilder
import javafx.util.Callback
import org.opendolphin.core.client.ClientPresentationModel

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

class MasterViewFactory {

	static javafx.scene.Node newMasterView(ObservableList<ClientPresentationModel> data){

		TableViewBuilder.create().items(data).columns(
			TableColumnBuilder.create().text("Uhrzeit").cellValueFactory(newCallback(ATT_DEPARTURE_TIME)).build(),
			TableColumnBuilder.create().text("Fahrt").cellValueFactory(newCallback(ATT_TRAIN_NUMBER)).build(),
			TableColumnBuilder.create().text("in Richtung").cellValueFactory(newCallback(ATT_DESTINATION)).build(),
			TableColumnBuilder.create().text("Status").cellValueFactory(newCallback(ATT_STATUS)).build(),
		).build()


	}

	public static Callback newCallback(String inPropertyName) {
		def result = new Callback<TableColumn.CellDataFeatures<ClientPresentationModel, String>, ObservableValue<String>>() {

			String propertyName

			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<ClientPresentationModel, String> cellDataFeatures) {
				ClientPresentationModel pm = cellDataFeatures.getValue()
				println "PM: $pm"
				if (pm != null) {
					return new SimpleStringProperty(pm.findAttributeByPropertyName(propertyName).value as String);
				} else {
					return new SimpleStringProperty("<no name>");
				}
			}
		}
		result.propertyName = inPropertyName
		result
	}

}