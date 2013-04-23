package com.canoo.codecamp.dolphinpi.admin

import com.canoo.codecamp.dolphinpi.DepartureConstants
import com.canoo.codecamp.dolphinpi.PresentationStateConstants
import javafx.beans.value.ChangeListener
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.control.TableColumn
import javafx.scene.control.TableColumnBuilder
import javafx.scene.control.TableView
import javafx.scene.control.TableViewBuilder
import javafx.scene.control.cell.TextFieldTableCell
import javafx.util.Callback
import org.opendolphin.core.ModelStoreEvent
import org.opendolphin.core.PresentationModel
import org.opendolphin.core.Tag
import org.opendolphin.core.client.ClientAttributeWrapper
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel

import static com.canoo.codecamp.dolphinpi.admin.AdminApplication.bindAttribute
import static org.opendolphin.binding.JavaFxUtil.cellEdit

class MasterViewFactory {

	static Parent createMasterView(ClientDolphin clientDolphin) {
		ObservableList<ClientPresentationModel> data = FXCollections.observableArrayList()
		PresentationModel applicationState = clientDolphin[PresentationStateConstants.TYPE.PRESENTATION_STATE]
		PresentationModel selectedDeparture = clientDolphin[DepartureConstants.SPECIAL_ID.SELECTED_DEPARTURE]

		TableView table = TableViewBuilder.create()
				.items(data)
				.columns(
					createColumn(selectedDeparture, DepartureConstants.ATT.DEPARTURE_TIME),
					createColumn(selectedDeparture, DepartureConstants.ATT.TRAIN_NUMBER),
					createColumn(selectedDeparture, DepartureConstants.ATT.DESTINATION),
					createColumn(selectedDeparture, DepartureConstants.ATT.STATUS, false),
					createColumn(selectedDeparture, DepartureConstants.ATT.TRACK),
					)
				.columnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY)
				.editable(true)
				.build()

		def selectedPMId = applicationState[PresentationStateConstants.ATT.SELECTED_DEPARTURE_ID]

		// on selection change update the selectedDepartureId
		table.selectionModel.selectedItemProperty().addListener({ o, oldVal, selectedPM ->
			selectedPMId.value = selectedPM == null ? DepartureConstants.SPECIAL_ID.EMPTY_DEPARTURE : selectedPM.id
		} as ChangeListener)

		// change table selection whenever the selectedDepartureId changes
		bindAttribute(selectedPMId, { evt ->
			final pmId = evt.newValue
			if (pmId == DepartureConstants.SPECIAL_ID.EMPTY_DEPARTURE) {
				table.getSelectionModel().clearSelection()
			} else {
				table.getSelectionModel().select(clientDolphin[pmId])
			}
		})

		clientDolphin.addModelStoreListener(DepartureConstants.TYPE.DEPARTURE, { ModelStoreEvent evt ->
			if(evt.type == ModelStoreEvent.Type.ADDED){
				data << evt.presentationModel
			}
			else {
				data.remove(evt.presentationModel)
			}
		})

		return table
	}

	static TableColumn createColumn(ClientPresentationModel selectedDeparture, String inPropertyName, boolean editable=true) {
		TableColumn col = TableColumnBuilder.create()
				.cellFactory(TextFieldTableCell.forTableColumn())
				.cellValueFactory({ row -> new ClientAttributeWrapper(row.value[inPropertyName]) } as Callback)
				.onEditCommit(cellEdit(inPropertyName, { it }) as EventHandler)
				.editable(editable)
				.build()

		bindAttribute selectedDeparture.getAt(inPropertyName, Tag.LABEL), { evt -> col.setText(evt.newValue) }

		col
	}

}
