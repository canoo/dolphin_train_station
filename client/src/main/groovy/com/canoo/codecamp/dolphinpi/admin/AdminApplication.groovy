package com.canoo.codecamp.dolphinpi.admin

import javafx.collections.FXCollections
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.ButtonBuilder
import javafx.scene.control.SplitPaneBuilder
import javafx.scene.control.TextFieldBuilder
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.Stage
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel
import org.tbee.javafx.scene.layout.MigPane

import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

public class AdminApplication extends javafx.application.Application {
	public static ClientDolphin clientDolphin;

	javafx.collections.ObservableList<ClientPresentationModel> allDepartures = FXCollections.observableArrayList()

	def selectedDepartureId = clientDolphin.presentationModel(SELECTED_DEPARTURE_ID, [ATT_ID])
	def selectedDeparture = clientDolphin.presentationModel(SELECTED_DEPARTURE, com.canoo.codecamp.dolphinpi.ApplicationConstants.ALL_ATTRIBUTES)
	def topDeparture = clientDolphin.presentationModel(TOP_DEPARTURE, [ATT_DOMAIN_ID: -1])

	public AdminApplication() {
	}

	@Override
	public void start(Stage stage) throws Exception {

		selectedDepartureId[ATT_ID].addPropertyChangeListener('value', new PropertyChangeListener() {
			@Override
			void propertyChange(final PropertyChangeEvent evt) {
				def id = evt.newValue

				def pm = clientDolphin.modelStore.findPresentationModelById(id)
				clientDolphin.apply pm to selectedDeparture
			}
		})

		stage.setTitle("Departures of Olten");

		javafx.scene.Node root = setupStage();
		addClientSideAction();

		setupBinding();

		Scene scene = new Scene(root, 1000, 400)
		scene.stylesheets << 'demo.css'

		stage.setScene(scene);
		stage.setTitle(getClass().getName());
		stage.show();

		clientDolphin.send COMMAND_GET_ALL_DEPARTURES, { pms ->
			for (pm in pms) {
				allDepartures << pm
			}
		}
	}

	private javafx.scene.Node setupStage() {
		MigPane migPane = new MigPane("wrap 4", "", "[][fill, grow]")

		migPane.add(ButtonBuilder.create()
				.graphic(createImageView("/save-icon.png"))
				.styleClass("toolbar-button")
				.onAction({println "not implemented"} as EventHandler)
				.build())
		migPane.add(ButtonBuilder.create()
				.graphic(createImageView("/undo-icon.png"))
				.styleClass("toolbar-button")
				.onAction({clientDolphin.send(COMMAND_UNDO)} as EventHandler)
				.build())
		migPane.add(ButtonBuilder.create()
				.graphic(createImageView("/redo-icon.png"))
				.styleClass("toolbar-button")
				.onAction({clientDolphin.send(COMMAND_REDO)} as EventHandler)
				.build(), "push")
		migPane.add(TextFieldBuilder.create().build(), "right")


		double[] divs = [0.5].toArray()
		final splitPane = SplitPaneBuilder.create()
				.dividerPositions(divs)
				.items(
				MasterViewFactory.newMasterView(allDepartures, selectedDepartureId, clientDolphin),
				DetailViewFactory.newView(selectedDeparture, topDeparture, clientDolphin)
		         )
		        .build()
		migPane.add(splitPane, "span, grow")
		migPane
	}

	private ImageView createImageView(String filename) {
		return new ImageView(new Image(getClass().getResourceAsStream(filename)))
	}

	private void setupBinding() {
	}

	private void addClientSideAction() {
	}
}

