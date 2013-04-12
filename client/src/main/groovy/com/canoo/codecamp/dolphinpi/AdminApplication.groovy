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

	private DeparturesBoardApplicationModel departuresModel

	public AdminApplication() {
	}

	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle("Departures of Olten");

		departuresModel = new DeparturesBoardApplicationModel(clientDolphin: clientDolphin).initialize()

		javafx.scene.Node root = setupStage();
		addClientSideAction();

		setupBinding();

		stage.setScene(new Scene(root, 1600, 400));
		stage.setTitle(getClass().getName());
		stage.show();

		clientDolphin.send COMMAND_GET_ALL_DEPARTURES, { pms ->
			for (pm in pms) {
				allDepartures << pm
			}
		}


	}

	private javafx.scene.Node setupStage() {
		double[] divs = [0.4, 0.6].toArray()
		SplitPaneBuilder.create()
		.dividerPositions(divs)
		.items(
			MasterViewFactory.newMasterView(allDepartures, selectedDeparture, clientDolphin),
			DetailViewFactory.newView(selectedDeparture, clientDolphin),
			DepartureBoardViewFactory.newView(departuresModel),
		)
		.build()

//		BorderPaneBuilder.create().center(MasterViewFactory.newMasterView(allDepartures)).build()
	}

	private void setupBinding() {
	}

	private void addClientSideAction() {
	}
}

