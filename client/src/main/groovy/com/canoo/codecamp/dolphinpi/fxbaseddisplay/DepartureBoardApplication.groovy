package com.canoo.codecamp.dolphinpi.fxbaseddisplay

import com.canoo.codecamp.dolphinpi.ApplicationConstants
import javafx.application.Application
import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.stage.Stage
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

public class DepartureBoardApplication extends Application {
	public static ClientDolphin clientDolphin;

	private javafx.collections.ObservableList<ClientPresentationModel> departuresOnBoard = FXCollections.observableArrayList()

	public DepartureBoardApplication() {
	}

	@Override
	public void start(Stage stage) throws Exception {
		(0..4).each {
			departuresOnBoard << clientDolphin.presentationModel(pmId(TYPE_DEPARTURE_ON_BOARD, it), ALL_ATTRIBUTES)
		}

		def longPoll = null
		longPoll = {
			clientDolphin.send ApplicationConstants.COMMAND_LONG_POLL, longPoll
		}

		stage.setTitle("JavaFX - Departures of Olten");

		javafx.scene.Node root = setupStage();

		LinearGradient gradient = new LinearGradient(0, 0, 0, 600, false, CycleMethod.NO_CYCLE,
				new Stop(0.0, Color.rgb(28, 27, 22)),
				new Stop(0.25, Color.rgb(38, 37, 32)),
				new Stop(1.0, Color.rgb(28, 27, 22)));

		stage.setScene(new Scene(root, 700, 200, gradient));
		stage.setTitle(getClass().getName());
		stage.show();

		longPoll()
	}

	private javafx.scene.Node setupStage() {
		DepartureBoardViewFactory.newView(departuresOnBoard)
	}

}

