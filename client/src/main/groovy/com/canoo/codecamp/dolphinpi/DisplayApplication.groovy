package com.canoo.codecamp.dolphinpi

import eu.hansolo.fx.departureboard.DepartureBoard
import eu.hansolo.fx.departureboard.Row
import javafx.collections.FXCollections
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.stage.Stage
import jfxtras.labs.scene.control.gauge.SplitFlap
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

public class DisplayApplication extends javafx.application.Application {
	public static ClientDolphin clientDolphin;

	javafx.collections.ObservableList<ClientPresentationModel> allDepartures = FXCollections.observableArrayList()

	private DeparturesBoardApplicationModel departuresModel

	public DisplayApplication() {
	}

	@Override
	public void start(Stage stage) throws Exception {

		departuresModel = new DeparturesBoardApplicationModel(clientDolphin: clientDolphin)
		departuresModel.initialize()

		def longPoll = null
		longPoll = {
			clientDolphin.send ApplicationConstants.COMMAND_LONG_POLL, longPoll }

		stage.setTitle("Departures of Olten");

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

		clientDolphin.send COMMAND_GET_ALL_DEPARTURES, { pms ->
			for (pm in pms) {
				allDepartures << pm
			}
		}

		longPoll()
	}

	private javafx.scene.Node setupStage() {
		DepartureBoardViewFactory.newView(departuresModel)
	}

	private javafx.scene.Node setupStageGerrit() {
//		DepartureBoardViewFactory.newView(departuresModel)

		DepartureBoard departureBoard = new DepartureBoard()
		departureBoard.addRow(new Row());
		departureBoard.addRow(new Row());
		departureBoard.addRow(new Row());
		departureBoard.addRow(new Row());
		departureBoard.addRow(new Row());
//		departureBoard.addRow(new Row("12", "05", "ZURICH", "LH1969", "A01"));

		departureBoard.getRows().get(0)

	}

	private void setupBinding() {
	}

	private void addClientSideAction() {
	}
}

