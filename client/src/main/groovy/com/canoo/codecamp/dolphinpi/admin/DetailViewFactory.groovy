package com.canoo.codecamp.dolphinpi.admin

import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.animation.TimelineBuilder
import javafx.event.ActionEvent
import javafx.event.Event
import javafx.event.EventHandler
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.util.Duration
import org.opendolphin.core.Tag
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel
import org.tbee.javafx.scene.layout.MigPane

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*
import static com.canoo.codecamp.dolphinpi.admin.AdminApplication.bindAttribute
import static org.opendolphin.binding.JFXBinder.bind

class DetailViewFactory {

	static javafx.scene.Node newView(ClientPresentationModel selectedDeparture, ClientPresentationModel topDeparture, ClientDolphin inClientDolphin) {
		MigPane migPane = new MigPane(
				"wrap 2, inset 30 30 30 30",// Layout Constraints
				"[pref!]10[fill, grow]",    // Column constraints
				"[pref!]10[pref!]10[pref!]10[pref!]10[top, grow, fill]30[]10[]",  // Row constraints
		)

		Button einfahren, ausfahren, moveToTop

		[ATT_DEPARTURE_TIME, ATT_DESTINATION, ATT_TRAIN_NUMBER, ATT_TRACK].each { String pn ->
			addAttributeEditor(migPane, TextFieldBuilder.create().build(), pn, selectedDeparture)
		}
		addAttributeEditor(migPane, TextAreaBuilder.create().wrapText(true).build(), ATT_STOPOVERS, selectedDeparture)

		//todo: get rid of these hard coded Strings
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

		bindAttribute selectedDeparture[ATT_POSITION], { evt -> migPane.setDisable(evt.newValue == null) }

		putStyle(migPane, true, 'pane')

		migPane
	}


	private static void bindBidirectional(String propertyName, javafx.scene.Node textNode, ClientPresentationModel pm) {
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

			return matches ? newVal : pm[propertyName].value
		}
	}

	private static void putStyle(node, boolean addOrRemove, String styleClassName) {
		if (addOrRemove) {
			node.styleClass.add(styleClassName)
		} else {
			node.styleClass.remove(styleClassName)
		}
	}

	private static addAttributeEditor(MigPane migPane, TextInputControl textInput, String propertyName, ClientPresentationModel pm) {
		textInput.addEventHandler(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
			@Override
			void handle(KeyEvent t) {
				if(t.getCode() == KeyCode.ESCAPE){
					final caretPos = textInput.getCaretPosition()
					String text = pm[propertyName].value
					textInput['text'] = text
					textInput.positionCaret Math.min(caretPos, text.length())
				}
			}
		})

		def label
		migPane.add(label = LabelBuilder.create().build())
		migPane.add(textInput)
		//todo: bind bidirectional to support multi-language
		bind propertyName, Tag.LABEL of pm to 'text' of label

		bindBidirectional(propertyName, textInput, pm)
	}

}
