package com.canoo.codecamp.dolphinpi

import javafx.scene.control.Button
import javafx.scene.control.ButtonBuilder
import javafx.scene.control.LabelBuilder
import javafx.scene.control.TextArea
import javafx.scene.control.TextAreaBuilder
import javafx.scene.control.TextField
import javafx.scene.control.TextFieldBuilder
import org.opendolphin.core.client.ClientPresentationModel
import org.tbee.javafx.scene.layout.MigPane

class DetailViewFactory {
	static javafx.scene.Node newView(){

		TextField uhrZeit, richtung, fahrt, gleis
		TextArea ueber
		Button einfahren, ausfahren, moveToTop

		MigPane migPane = new MigPane(
			"wrap 2, inset 10 10 10 10",                         // Layout Constraints
			"[pref!]10[fill, grow]",  // Column constraints
			"[]10[]10[]10[]10[fill, top, grow]10[]10[]",  // Column constraints
		)

		migPane.add(LabelBuilder.create().text("Uhrzeit").build())
		migPane.add(uhrZeit = TextFieldBuilder.create().build())

		migPane.add(LabelBuilder.create().text("in Richtung").build())
		migPane.add(richtung = TextFieldBuilder.create().build())

		migPane.add(LabelBuilder.create().text("Fahrt").build())
		migPane.add(fahrt = TextFieldBuilder.create().build())

		migPane.add(LabelBuilder.create().text("Gleis").build())
		migPane.add(gleis = TextFieldBuilder.create().build())

		migPane.add(LabelBuilder.create().text("Über").build())
		migPane.add(ueber = TextAreaBuilder.create().build())

		migPane.add(einfahren = ButtonBuilder.create().text("Fährt ein").build())
		migPane.add(ausfahren = ButtonBuilder.create().text("Fährt aus").build(), "right, grow 0")

		migPane.add(moveToTop = ButtonBuilder.create().text("erster Eintrag auf Abfahrtstafel").build(), "span, grow")

		migPane
	}
}
