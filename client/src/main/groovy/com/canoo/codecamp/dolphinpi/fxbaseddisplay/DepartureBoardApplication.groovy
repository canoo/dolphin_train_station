package com.canoo.codecamp.dolphinpi.fxbaseddisplay

import javafx.application.Application
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.scene.paint.CycleMethod
import javafx.scene.paint.LinearGradient
import javafx.scene.paint.Stop
import javafx.stage.Stage
import org.opendolphin.core.client.ClientDolphin

import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.ALL
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.CMD.LONG_POLL
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.TYPE.BOARD_ITEM
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.pmId

public class DepartureBoardApplication extends Application {
	public static ClientDolphin clientDolphin;

	public DepartureBoardApplication() {
	}

	@Override
	public void start(Stage stage) throws Exception {
		initializePresentationModels()

		LinearGradient gradient = new LinearGradient(0, 0, 0, 600, false, CycleMethod.NO_CYCLE,
				new Stop(0.0, Color.rgb(28, 27, 22)),
				new Stop(0.25, Color.rgb(38, 37, 32)),
				new Stop(1.0, Color.rgb(28, 27, 22)));

		stage.setTitle("JavaFX - Abfahren ab Olten");
		stage.setScene(new Scene(createStageRoot(), 700, 200, gradient));
		stage.show();

		def longPoll = null
		longPoll = {
			clientDolphin.send LONG_POLL, longPoll
		}

		longPoll()
	}

	private static void initializePresentationModels () {
		(0..4).each {
			//todo: provide this kind of method on clientDolphin
			//PresentationModel pm = clientDolphin.presentationModel(pmId(TYPE_DEPARTURE_ON_BOARD, it), TYPE_DEPARTURE_ON_BOARD, ALL_ATTRIBUTES)

			Map<String, Object> attributeMap = [:]
			ALL.each {attr -> attributeMap[attr] = null}
			clientDolphin.presentationModel(pmId(BOARD_ITEM, it), BOARD_ITEM, attributeMap)
		}
	}

	private static Parent createStageRoot() {
		DepartureBoardViewFactory.createView(clientDolphin)
	}

}

