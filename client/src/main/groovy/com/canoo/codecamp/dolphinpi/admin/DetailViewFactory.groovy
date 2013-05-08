package com.canoo.codecamp.dolphinpi.admin

import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.animation.TimelineBuilder
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.util.Duration
import org.opendolphin.core.PresentationModel
import org.opendolphin.core.Tag
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel
import org.tbee.javafx.scene.layout.MigPane

import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DEPARTURE_TIME
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DESTINATION
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.POSITION
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.STATUS
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.STOPOVERS
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.TRACK
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.TRAIN_NUMBER
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.MOVE_TO_TOP
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.SELECTED_DEPARTURE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.STATUS.APPROACHING
import static com.canoo.codecamp.dolphinpi.DepartureConstants.STATUS.HAS_LEFT
import static com.canoo.codecamp.dolphinpi.DepartureConstants.STATUS.IN_STATION

import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.ATT.TOP_DEPARTURE_ON_BOARD
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.TYPE.PRESENTATION_STATE

import static com.canoo.codecamp.dolphinpi.admin.AdminApplication.bindAttribute
import static org.opendolphin.binding.JFXBinder.bind

class DetailViewFactory {

	static Parent createDetailView(ClientDolphin inClientDolphin) {
		PresentationModel selectedDeparture = inClientDolphin[SELECTED_DEPARTURE]
		PresentationModel applicationState = inClientDolphin[PRESENTATION_STATE]
		MigPane migPane = new MigPane(
				"wrap 2, inset 30 30 30 30",// Layout Constraints
				"[pref!]10[fill, grow]",    // Column constraints
				"[pref!]10[pref!]10[pref!]10[pref!]10[top, grow, fill]30[]10[]",  // Row constraints
		)

		Button incoming, leaving, moveToTop

		[DEPARTURE_TIME, DESTINATION, TRAIN_NUMBER, TRACK].each { String pn ->
			addAttributeEditor(migPane, TextFieldBuilder.create().build(), pn, selectedDeparture)
		}
		addAttributeEditor(migPane, TextAreaBuilder.create().wrapText(true).build(), STOPOVERS, selectedDeparture)

		//todo: get rid of these hard coded Strings
		migPane.add(incoming = ButtonBuilder.create().text("Fährt ein").build())
		migPane.add(leaving = ButtonBuilder.create().text("Fährt aus").build(), "right, grow 0")

		migPane.add(moveToTop = ButtonBuilder.create().text("erster Eintrag auf Abfahrtstafel").build(), "span, grow")

		moveToTop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				inClientDolphin.send MOVE_TO_TOP
			}
		});

		incoming.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				selectedDeparture.getAt(STATUS).setValue(IN_STATION)
			}
		});

		bind STATUS of selectedDeparture to 'disabled' of incoming, {
			!APPROACHING.equals(it)
		}

		leaving.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				selectedDeparture.getAt(STATUS).setValue(HAS_LEFT)
			}
		});
		bind STATUS of selectedDeparture to 'disabled' of leaving, {
			!IN_STATION.equals(it)
		}

		bind TOP_DEPARTURE_ON_BOARD of applicationState to 'disabled' of moveToTop, {
			def selectedPosition = selectedDeparture.getAt(POSITION).value
			it == selectedPosition
		}

		bind POSITION of selectedDeparture to 'disabled' of moveToTop, {
			def domainId = applicationState[TOP_DEPARTURE_ON_BOARD].value
			it == domainId
		}

		bindAttribute selectedDeparture[POSITION], { evt -> migPane.setDisable(evt.newValue == null) }

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
				Util.shake(textNode)
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
					textInput.positionCaret(Math.min(caretPos, text.length()))
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
