package com.canoo.codecamp.dolphinpi

import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.control.SplitPaneBuilder
import javafx.stage.Stage
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

public class AdminApplication extends javafx.application.Application {
	public static ClientDolphin clientDolphin;

	javafx.collections.ObservableList<ClientPresentationModel> allDepartures = FXCollections.observableArrayList()

	def selectedDeparture = clientDolphin.presentationModel(SELECTED_DEPARTURE, ALL_ATTRIBUTES)
	def topDeparture = clientDolphin.presentationModel(TOP_DEPARTURE, [ATT_DOMAIN_ID: -1])

	public AdminApplication() {
	}

	@Override
	public void start(Stage stage) throws Exception {

		stage.setTitle("Departures of Olten");

		javafx.scene.Node root = setupStage();
		addClientSideAction();

		setupBinding();

		stage.setScene(new Scene(root, 1000, 400));
		stage.setTitle(getClass().getName());
		stage.show();

		clientDolphin.send COMMAND_GET_ALL_DEPARTURES, { pms ->
			for (pm in pms) {
				allDepartures << pm
			}
		}


	}

	private javafx.scene.Node setupStage() {
		double[] divs = [0.5].toArray()
		SplitPaneBuilder.create()
		.dividerPositions(divs)
		.items(
			MasterViewFactory.newMasterView(allDepartures, selectedDeparture, clientDolphin),
			DetailViewFactory.newView(selectedDeparture, clientDolphin),
		)
		.build()
	}

	private void setupBinding() {
	}

	private void addClientSideAction() {
	}
}

