package com.canoo.codecamp.dolphinpi

import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.control.LabelBuilder
import javafx.scene.control.SplitPaneBuilder
import javafx.scene.layout.BorderPaneBuilder
import javafx.scene.layout.Pane
import javafx.scene.layout.PaneBuilder
import javafx.scene.layout.Priority
import javafx.scene.layout.VBox
import javafx.scene.layout.VBoxBuilder
import javafx.stage.Stage
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

public class AdminApplication extends javafx.application.Application {
	public static ClientDolphin clientDolphin;

	javafx.collections.ObservableList<ClientPresentationModel> allDepartures = FXCollections.observableArrayList()

	def selectedDeparture = clientDolphin.presentationModel(SELECTED_DEPARTURE, ALL_ATTRIBUTES)

	javafx.collections.ObservableList<ClientPresentationModel> departuresOnBoard = FXCollections.observableArrayList()

	public AdminApplication() {
	}

	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle("Departures of Olten");

		javafx.scene.Node root = setupStage();
		addClientSideAction();

		departuresOnBoard << clientDolphin.presentationModel(DEPARTURE_ON_BOARD_1, ALL_ATTRIBUTES)
		departuresOnBoard << clientDolphin.presentationModel(DEPARTURE_ON_BOARD_2, ALL_ATTRIBUTES)
		departuresOnBoard << clientDolphin.presentationModel(DEPARTURE_ON_BOARD_3, ALL_ATTRIBUTES)
		departuresOnBoard << clientDolphin.presentationModel(DEPARTURE_ON_BOARD_4, ALL_ATTRIBUTES)
		departuresOnBoard << clientDolphin.presentationModel(DEPARTURE_ON_BOARD_5, ALL_ATTRIBUTES)


		setupBinding();


		stage.setScene(new Scene(root, 800, 400));
		stage.setTitle(getClass().getName());
		stage.show();

		clientDolphin.send COMMAND_GET_ALL_DEPARTURES, { pms ->
			for (pm in pms) {
				allDepartures << pm
			}
		}


	}

	private javafx.scene.Node setupStage() {
		SplitPaneBuilder.create()
		.items(
			MasterViewFactory.newMasterView(allDepartures, selectedDeparture, clientDolphin),
			DetailViewFactory.newView(selectedDeparture, clientDolphin),
			DepartureBoardViewFactory.newView(departuresOnBoard),
		)
		.build()

//		BorderPaneBuilder.create().center(MasterViewFactory.newMasterView(allDepartures)).build()
	}

	private void setupBinding() {
	}

	private void addClientSideAction() {
	}
}

