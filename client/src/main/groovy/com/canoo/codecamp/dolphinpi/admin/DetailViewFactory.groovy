package com.canoo.codecamp.dolphinpi.admin

import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.animation.TimelineBuilder
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.control.*
import javafx.util.Duration
import org.opendolphin.core.Tag
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel
import org.tbee.javafx.scene.layout.MigPane

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*
import static org.opendolphin.binding.JFXBinder.bind

class DetailViewFactory {

	static addAttributeEditor(MigPane migPane, javafx.scene.Node inNode, String propertyName, ClientPresentationModel pm) {
		def label
		migPane.add(label = LabelBuilder.create().build())
		migPane.add(inNode)

		bind propertyName, Tag.LABEL of pm to 'text' of label

		bindBidirectional(propertyName, inNode, pm)
	}

	static javafx.scene.Node newView(ClientPresentationModel selectedDeparture, ClientPresentationModel topDeparture, ClientDolphin inClientDolphin){


		MigPane migPane = new MigPane(
			"wrap 2, inset 30 30 30 30",// Layout Constraints
			"[pref!]10[fill, grow]",    // Column constraints
			"[pref!]10[pref!]10[pref!]10[pref!]10[top, grow, fill]30[]10[]",  // Row constraints
		)

		// binding:
		inClientDolphin.send COMMAND_INIT_SELECTED_DEPARTURE, { pms ->
			Button einfahren, ausfahren, moveToTop, undo, redo

			[ATT_DEPARTURE_TIME, ATT_DESTINATION, ATT_TRAIN_NUMBER, ATT_TRACK].each { String pn ->
				addAttributeEditor(migPane, TextFieldBuilder.create().build(), pn, selectedDeparture)
			}
			addAttributeEditor(migPane, TextAreaBuilder.create().wrapText(true).build(), ATT_STOPOVERS, selectedDeparture)

			migPane.add(einfahren = ButtonBuilder.create().text("Fährt ein").build())
			migPane.add(ausfahren = ButtonBuilder.create().text("Fährt aus").build(), "right, grow 0")

			migPane.add(moveToTop = ButtonBuilder.create().text("erster Eintrag auf Abfahrtstafel").build(), "span, grow")

			moveToTop.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					inClientDolphin.send COMMAND_MOVE_TO_TOP
				}
			});

			einfahren.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					selectedDeparture.getAt(ATT_STATUS).setValue(STATUS_IN_STATION)
				}
			});

			bind ATT_STATUS of selectedDeparture to 'disabled' of einfahren, {
				!STATUS_APPROACHING.equals(it)
			}

			ausfahren.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent actionEvent) {
					selectedDeparture.getAt(ATT_STATUS).setValue(STATUS_HAS_LEFT)
				}
			});
			bind ATT_STATUS of selectedDeparture to 'disabled' of ausfahren, {
				!STATUS_IN_STATION.equals(it)
			}

			bind ATT_DOMAIN_ID of topDeparture to 'disabled' of moveToTop, {
				def selectedPosition = selectedDeparture.getAt(ATT_POSITION).value
				it == selectedPosition
			}
			bind ATT_POSITION of selectedDeparture to 'disabled' of moveToTop, {
				def domainId = topDeparture.getAt(ATT_DOMAIN_ID).value
				it == domainId
			}
		}

		putStyle(migPane, true, 'pane')

		migPane
	}


	static void bindBidirectional(String propertyName, javafx.scene.Node textNode, ClientPresentationModel pm) {
		bind propertyName of pm to 'text' of textNode
		bind 'text' of textNode to propertyName of pm, {  newVal ->
			String regex = pm.getAt(propertyName, Tag.REGEX)?.value
			if (!regex) return newVal

			boolean matches = newVal ==~ regex
			putStyle(textNode, !matches, 'invalid')

			if (!matches) {
				Timeline tl = TimelineBuilder.create().cycleCount(2).autoReverse(false).keyFrames(
					new KeyFrame(Duration.millis(25), new KeyValue(textNode.translateXProperty(), 3)),
					new KeyFrame(Duration.millis(75), new KeyValue(textNode.translateXProperty(), -3)),
					new KeyFrame(Duration.millis(100), new KeyValue(textNode.translateXProperty(), 0)),
				).build()
				tl.play()
			}

			return matches ? newVal : pm.getAt(propertyName).value
		}

	}

	static void putStyle(node, boolean addOrRemove, String styleClassName) {
		if (addOrRemove) {
			node.styleClass.add(styleClassName)
		} else {
			node.styleClass.remove(styleClassName)
		}
	}
}
