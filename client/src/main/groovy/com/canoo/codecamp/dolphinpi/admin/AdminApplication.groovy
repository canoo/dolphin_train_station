package com.canoo.codecamp.dolphinpi.admin

import javafx.application.Application
import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ButtonBuilder
import javafx.scene.control.SplitPane
import javafx.scene.control.SplitPaneBuilder
import javafx.scene.control.TextFieldBuilder
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.opendolphin.core.Attribute
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel
import org.tbee.javafx.scene.layout.MigPane

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

public class AdminApplication extends Application {
	public static ClientDolphin clientDolphin;

	javafx.collections.ObservableList<ClientPresentationModel> allDepartures = FXCollections.observableArrayList()

	def selectedDepartureId = clientDolphin.presentationModel(SELECTED_DEPARTURE_ID, [ATT_ID: EMPTY_DEPARTURE])
	def selectedDeparture = clientDolphin.presentationModel(SELECTED_DEPARTURE, ALL_ATTRIBUTES)
	def emptyDeparture = clientDolphin.presentationModel(EMPTY_DEPARTURE, ALL_ATTRIBUTES)
	def topDeparture = clientDolphin.presentationModel(TOP_DEPARTURE, [ATT_DOMAIN_ID: EMPTY_DEPARTURE])


	@Override
	public void start(Stage stage) throws Exception {
		stage.title = "Abfahren ab Olten";

		clientDolphin.send COMMAND_INIT_SELECTED_DEPARTURE, {
			javafx.scene.Node root = setupStage()

			Scene scene = new Scene(root, 1000, 400)
			scene.stylesheets << 'demo.css'

			stage.setScene(scene);
			stage.show();

			root.requestLayout()
		}

		clientDolphin.send COMMAND_GET_ALL_DEPARTURES, { pms ->
			pms.each {allDepartures << it}
		}

		bindAttribute(selectedDepartureId[ATT_ID], { evt -> clientDolphin.apply clientDolphin[evt.newValue] to selectedDeparture })
	}

	private javafx.scene.Node setupStage() {
		MigPane migPane = new MigPane("wrap 4", "", "[][fill]")
		migPane.add createButton("/save-icon.png")
		migPane.add createButton("/undo-icon.png", COMMAND_UNDO)
		migPane.add createButton("/redo-icon.png", COMMAND_REDO), "pushx"
		migPane.add TextFieldBuilder.create().build(), "right"

		final SplitPane splitPane = SplitPaneBuilder.create()
				.dividerPositions([0.5] as double[])
				.items(
					MasterViewFactory.newMasterView(allDepartures, selectedDepartureId, clientDolphin),
					DetailViewFactory.newView(selectedDeparture, topDeparture, clientDolphin)
		 		)
		        .build()
		migPane.add splitPane, "span, grow, pushy"

		migPane
	}

	private ImageView createImageView(String filename) {
		return new ImageView(new Image(getClass().getResourceAsStream(filename)))
	}

	private Button createButton(String iconFilename, String command=null) {
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

	public static void bindAttribute(Attribute attribute, Closure closure) {
		final listener = closure as PropertyChangeListener
		attribute.addPropertyChangeListener('value', listener)
		listener.propertyChange(new PropertyChangeEvent(attribute, 'value', attribute.value, attribute.value))
	}

}

