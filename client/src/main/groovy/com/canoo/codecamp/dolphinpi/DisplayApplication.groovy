package com.canoo.codecamp.dolphinpi

import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.control.SplitPaneBuilder
import javafx.stage.Stage
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.COMMAND_GET_ALL_DEPARTURES

public class DisplayApplication extends javafx.application.Application {
	public static ClientDolphin clientDolphin;

	javafx.collections.ObservableList<ClientPresentationModel> allDepartures = FXCollections.observableArrayList()



	public DisplayApplication() {
	}

	@Override
	public void start(Stage stage) throws Exception {
		def longPoll = null
		longPoll = {
			println "calling long poll"
			clientDolphin.send ApplicationConstants.COMMAND_LONG_POLL, longPoll }

		stage.setTitle("Departures of Olten");

		javafx.scene.Node root = setupStage();
		addClientSideAction();



		setupBinding();


		stage.setScene(new Scene(root, 500, 400));
		stage.setTitle(getClass().getName());
		stage.show();

		clientDolphin.send COMMAND_GET_ALL_DEPARTURES, { pms ->
			for (pm in pms) {
				allDepartures << pm
			}
		}

		longPoll()

		clientDolphin.addModelStoreListener { println it }

	}

	private javafx.scene.Node setupStage() {
		MasterViewFactory.newMasterView(allDepartures)
	}

	private void setupBinding() {
	}

	private void addClientSideAction() {
	}
}
