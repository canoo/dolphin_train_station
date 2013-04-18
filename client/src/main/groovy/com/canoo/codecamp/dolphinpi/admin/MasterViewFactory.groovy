package com.canoo.codecamp.dolphinpi.admin

import javafx.beans.value.ChangeListener
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.control.TableColumn
import javafx.scene.control.TableColumnBuilder
import javafx.scene.control.TableView
import javafx.scene.control.TableViewBuilder
import javafx.scene.control.cell.TextFieldTableCell
import javafx.util.Callback
import org.opendolphin.core.PresentationModel
import org.opendolphin.core.client.ClientAttributeWrapper
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*
import static org.opendolphin.binding.JavaFxUtil.cellEdit

class MasterViewFactory {

	static javafx.scene.Node newMasterView(ObservableList<ClientPresentationModel> data, ClientPresentationModel selectedDepartureId, ClientDolphin inClientDolphin) {
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
		result.setEditable(true)

		// on selection change update the selectedDepartureId
		result.selectionModel.selectedItemProperty().addListener({ o, oldVal, selectedPM ->
			println o
			println oldVal
			println selectedPM
			selectedDepartureId[ATT_ID].value = selectedPM.id
		} as ChangeListener)


		// change table selection whenever the selectedDepartureId changes
		selectedDepartureId[ATT_ID].addPropertyChangeListener('value', new PropertyChangeListener() {
			@Override
			void propertyChange(final PropertyChangeEvent evt) {
				String pmId = evt.newValue
				PresentationModel pm = inClientDolphin.modelStore.findPresentationModelById(pmId)
				result.getSelectionModel().select(pm[ATT_POSITION].value)
			}
		})

		return result
	}

	static TableColumn newTableColumn(String inPropertyName, String inTitle) {
		TableColumnBuilder.create()
				.text(inTitle)
				.cellFactory(TextFieldTableCell.forTableColumn())
				.cellValueFactory({ row -> new ClientAttributeWrapper(row.value[inPropertyName]) } as Callback)
				.onEditCommit(cellEdit(inPropertyName, { it }) as EventHandler)
				.editable(true)
				.build()
	}

}
