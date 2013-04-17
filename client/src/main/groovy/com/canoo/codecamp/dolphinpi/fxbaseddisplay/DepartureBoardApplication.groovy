package com.canoo.codecamp.dolphinpi.fxbaseddisplay

import com.canoo.codecamp.dolphinpi.ApplicationConstants
import com.canoo.codecamp.dolphinpi.DepartureBoardApplicationModel
import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.stage.Stage
import org.opendolphin.core.client.ClientDolphin

public class DepartureBoardApplication extends Application {
	public static ClientDolphin clientDolphin;

	private DepartureBoardApplicationModel departuresModel

	public DepartureBoardApplication() {
	}

	@Override
	public void start(Stage stage) throws Exception {

		departuresModel = new DepartureBoardApplicationModel(clientDolphin: clientDolphin)
		departuresModel.initialize()

		def longPoll = null
		longPoll = {
			clientDolphin.send ApplicationConstants.COMMAND_LONG_POLL, longPoll }

		stage.setTitle("JavaFX - Departures of Olten");

		javafx.scene.Node root = setupStage();
		addClientSideAction();

		setupBinding();

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
		DepartureBoardViewFactory.newView(departuresModel)
	}

	private void setupBinding() {
	}

	private void addClientSideAction() {
	}
}

