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
import org.opendolphin.core.Tag
import org.opendolphin.core.client.ClientAttributeWrapper
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*
import static com.canoo.codecamp.dolphinpi.admin.AdminApplication.bindAttribute
import static org.opendolphin.binding.JavaFxUtil.cellEdit

class MasterViewFactory {

	private static ClientDolphin clientDolphin

	static javafx.scene.Node newMasterView(ObservableList<ClientPresentationModel> data, ClientPresentationModel selectedDepartureId, ClientDolphin inClientDolphin) {
		clientDolphin = inClientDolphin
		TableView table = TableViewBuilder.create()
				.items(data)
				.columns(
					createColumn(ATT_DEPARTURE_TIME),
					createColumn(ATT_TRAIN_NUMBER),
					createColumn(ATT_DESTINATION),
					createColumn(ATT_STATUS, false),
					createColumn(ATT_TRACK),
					)
				.columnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY)
				.editable(true)
				.build()

		// on selection change update the selectedDepartureId
		table.selectionModel.selectedItemProperty().addListener({ o, oldVal, selectedPM ->
			selectedDepartureId[ATT_ID].value = selectedPM == null ? EMPTY_DEPARTURE : selectedPM.id
		} as ChangeListener)

		// change table selection whenever the selectedDepartureId changes
		bindAttribute(selectedDepartureId[ATT_ID], { evt ->
			final pmId = evt.newValue
			if (pmId == EMPTY_DEPARTURE) {
				table.getSelectionModel().clearSelection()
			} else {
				table.getSelectionModel().select(inClientDolphin[pmId])
			}
		})

		return table
	}

	static TableColumn createColumn(String inPropertyName, boolean editable=true) {
		TableColumn col = TableColumnBuilder.create()
				.cellFactory(TextFieldTableCell.forTableColumn())
				.cellValueFactory({ row -> new ClientAttributeWrapper(row.value[inPropertyName]) } as Callback)
				.onEditCommit(cellEdit(inPropertyName, { it }) as EventHandler)
				.editable(editable)
				.build()

		bindAttribute(clientDolphin[SELECTED_DEPARTURE].getAt(inPropertyName, Tag.LABEL), {evt -> col.setText(evt.newValue)})

		col
	}

}
