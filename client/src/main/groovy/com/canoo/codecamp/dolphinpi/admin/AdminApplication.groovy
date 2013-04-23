package com.canoo.codecamp.dolphinpi.admin

import javafx.application.Application
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.opendolphin.core.Attribute
import org.opendolphin.core.client.ClientDolphin
import org.tbee.javafx.scene.layout.MigPane

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.getALL
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.INIT_SELECTED_DEPARTURE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.PULL
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.REDO
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.UNDO
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.EMPTY_DEPARTURE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.SELECTED_DEPARTURE

import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.ATT.SELECTED_DEPARTURE_ID
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.ATT.TOP_DEPARTURE_ON_BOARD
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.TYPE.PRESENTATION_STATE

public class AdminApplication extends Application {
	public static ClientDolphin clientDolphin;

	@Override
	public void start(Stage stage) throws Exception {
		initializePresentationModels()

		clientDolphin.send INIT_SELECTED_DEPARTURE, {
			stage.title = "Abfahrten ab Olten";

			Scene scene = new Scene(createStageRoot(), 1000, 400)
			scene.stylesheets << 'demo.css'

			stage.setScene(scene);
			stage.show();
		}

		clientDolphin.send PULL

		doAllBindings()
	}

	private static void initializePresentationModels () {
		clientDolphin.presentationModel(SELECTED_DEPARTURE, ALL)
		clientDolphin.presentationModel(EMPTY_DEPARTURE, ALL)
		Map attributeValueMap = [:]
		attributeValueMap.put(SELECTED_DEPARTURE_ID, EMPTY_DEPARTURE)
		attributeValueMap.put(TOP_DEPARTURE_ON_BOARD, EMPTY_DEPARTURE)
		clientDolphin.presentationModel(PRESENTATION_STATE,attributeValueMap)
	}

	private static Parent createStageRoot() {
		MigPane migPane = new MigPane("wrap 4", "", "[][fill]")
		migPane.add createButton("/save-icon.png")
		migPane.add createButton("/undo-icon.png", UNDO)
		migPane.add createButton("/redo-icon.png", REDO), "pushx"
		migPane.add TextFieldBuilder.create().styleClass("search-field").build(), "right"

		final SplitPane splitPane = SplitPaneBuilder.create()
				.dividerPositions([0.5] as double[])
				.items(
					MasterViewFactory.createMasterView(clientDolphin),
					DetailViewFactory.createDetailView(clientDolphin)
		 		)
		        .build()
		migPane.add splitPane, "span, grow, pushy"

		migPane
	}

	private static void doAllBindings(){
		def applicationState = clientDolphin[PRESENTATION_STATE]
		def selectedDeparture = clientDolphin[SELECTED_DEPARTURE]

		bindAttribute(applicationState[SELECTED_DEPARTURE_ID], { evt -> clientDolphin.apply clientDolphin[evt.newValue] to selectedDeparture })
	}

	private static ImageView createImageView(String filename) {
		return new ImageView(new Image(getClass().getResourceAsStream(filename)))
	}

	private static Button createButton(String iconFilename, String command=null) {
		ButtonBuilder.create()
				.graphic(createImageView(iconFilename))
				.styleClass("toolbar-button")
				.onAction({
							if (command != null) {
								clientDolphin.send(command)
							} else {
								println "not implemented yet"
							}
						} as EventHandler)
				.build()
	}

	// todo: move this kind of method to dolphin: "bind attribute to closure" and "bind 'propertyName' of pm to closure"
	public static void bindAttribute(Attribute attribute, Closure closure) {
		final listener = closure as PropertyChangeListener
		attribute.addPropertyChangeListener('value', listener)
		listener.propertyChange(new PropertyChangeEvent(attribute, 'value', attribute.value, attribute.value))
	}

}

