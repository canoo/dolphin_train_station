package com.canoo.codecamp.dolphinpi

import groovy.inspect.TextNode
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.Button
import javafx.scene.control.ButtonBuilder
import javafx.scene.control.LabelBuilder
import javafx.scene.control.TextArea
import javafx.scene.control.TextAreaBuilder
import javafx.scene.control.TextField
import javafx.scene.control.TextFieldBuilder
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel
import org.tbee.javafx.scene.layout.MigPane

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*
import static org.opendolphin.binding.JFXBinder.bind

class DetailViewFactory {
	static javafx.scene.Node newView(ClientPresentationModel selectedDeparture, ClientDolphin inClientDolphin){



		TextField departureTime, destination, trainNumber, track
		TextArea stopOvers
		Button einfahren, ausfahren, moveToTop

		MigPane migPane = new MigPane(
			"wrap 2, inset 10 10 10 10",                         // Layout Constraints
			"[pref!]10[fill, grow]",  // Column constraints
			"[]10[]10[]10[]10[fill, top, grow]10[]10[]",  // Column constraints
		)

		migPane.add(LabelBuilder.create().text("Uhrzeit").build())
		migPane.add(departureTime = TextFieldBuilder.create().build())

		migPane.add(LabelBuilder.create().text("in Richtung").build())
		migPane.add(destination = TextFieldBuilder.create().build())

		migPane.add(LabelBuilder.create().text("Fahrt").build())
		migPane.add(trainNumber = TextFieldBuilder.create().build())

		migPane.add(LabelBuilder.create().text("Gleis").build())
		migPane.add(track = TextFieldBuilder.create().build())

		migPane.add(LabelBuilder.create().text("Über").build())
		migPane.add(stopOvers = TextAreaBuilder.create().wrapText(true).build())

		migPane.add(einfahren = ButtonBuilder.create().text("Fährt ein").build())
		migPane.add(ausfahren = ButtonBuilder.create().text("Fährt aus").build(), "right, grow 0")

		migPane.add(moveToTop = ButtonBuilder.create().text("erster Eintrag auf Abfahrtstafel").build(), "span, grow")

		// binding:
		bindBidirectional(ATT_DEPARTURE_TIME, departureTime, selectedDeparture)
		bindBidirectional(ATT_DESTINATION, destination, selectedDeparture)
		bindBidirectional(ATT_TRAIN_NUMBER, trainNumber, selectedDeparture)
		bindBidirectional(ATT_TRACK, track, selectedDeparture)
		bindBidirectional(ATT_STOPOVERS, stopOvers, selectedDeparture)

		moveToTop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				inClientDolphin.send COMMAND_MOVE_TO_TOP, { pms ->

				}
			}
		});

		migPane
	}

	static void bindBidirectional(String propertyName, javafx.scene.Node textNode, ClientPresentationModel pm) {
		bind propertyName of pm to 'text' of textNode
		bind 'text' of textNode to propertyName of pm
	}

}
