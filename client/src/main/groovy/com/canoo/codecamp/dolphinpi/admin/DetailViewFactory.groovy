package com.canoo.codecamp.dolphinpi.admin

import org.opendolphin.core.Attribute
import org.opendolphin.core.PresentationModel
import org.opendolphin.core.client.ClientAttribute
import org.opendolphin.core.client.ClientDolphin
import org.tbee.javafx.scene.layout.MigPane
import com.aquafx_project.AquaFx
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.scene.Parent
import javafx.scene.control.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.opendolphin.core.PresentationModel
import org.opendolphin.core.Tag
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel
import org.tbee.javafx.scene.layout.MigPane
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DEPARTURE_TIME
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DESTINATION
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DRIVE_IN
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DRIVE_OUT
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.FIRST_ONE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.POSITION
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.STATUS
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.STOPOVERS
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.TRACK
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.TRAIN_NUMBER
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.CHANGE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.MOVE_TO_TOP
import static com.canoo.codecamp.dolphinpi.DepartureConstants.CMD.PULL
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.BUTTONS
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.SELECTED_DEPARTURE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.DEPARTURES
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.APPROACHING
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.IN_STATION
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.HAS_LEFT
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.ATT.LANGUAGE
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.ATT.TOP_DEPARTURE_ON_BOARD
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.TYPE.PRESENTATION_STATE
import static com.canoo.codecamp.dolphinpi.admin.AdminApplication.bindAttribute
import static org.opendolphin.binding.JFXBinder.bind
import static org.opendolphin.binding.JFXBinder.unbind

class DetailViewFactory {
	static Parent createDetailView(ClientDolphin inClientDolphin) {
		PresentationModel selectedDeparture = inClientDolphin[SELECTED_DEPARTURE]
		PresentationModel applicationState = inClientDolphin[PRESENTATION_STATE]
		PresentationModel buttonsPM = inClientDolphin[BUTTONS]
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
        incoming = ButtonBuilder.create().build()
        leaving = ButtonBuilder.create().build()
        moveToTop = ButtonBuilder.create().build()

        bind FIRST_ONE of buttonsPM to 'text' of moveToTop
        bind DRIVE_IN of buttonsPM to 'text' of incoming
        bind DRIVE_OUT of buttonsPM to  'text' of leaving


        migPane.add(incoming)
        migPane.add(leaving, "right, grow 0")
        migPane.add(moveToTop, "span, grow")



        moveToTop.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				inClientDolphin.send MOVE_TO_TOP


			}
		});

		incoming.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				selectedDeparture.getAt(STATUS).setValue(buttonsPM[IN_STATION].value)

            }
		});
		bind STATUS of selectedDeparture to 'disabled' of incoming, {
			!buttonsPM[APPROACHING].value.equals(it)                                     //if anything but approaching than disable
		}

		leaving.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent actionEvent) {
				selectedDeparture.getAt(STATUS).setValue(buttonsPM[HAS_LEFT].value)
			}
		});
		bind STATUS of selectedDeparture to 'disabled' of leaving, {   //if anything but in station than disable
			!buttonsPM[IN_STATION].equals(it)
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

			boolean matches = !newVal || newVal ==~ regex
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

		def label = LabelBuilder.create().build()
		migPane.add(label)
		migPane.add(textInput)
		//todo: bind bidirectional to support multi-language
		bind propertyName, Tag.LABEL of pm to 'text' of label

		bindBidirectional(propertyName, textInput, pm)
	}

}
