package com.canoo.codecamp.dolphinpi.swingbaseddisplay

import com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board.DepartureBoard
import org.opendolphin.core.Attribute
import org.opendolphin.core.PresentationModel
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel

import javax.swing.*
import java.awt.*
import java.beans.PropertyChangeListener
import java.util.List

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

public class DepartureBoardFrame extends JFrame {
	private final ClientDolphin clientDolphin;
	private final List departuresOnBoard

	private DepartureBoard board

	public DepartureBoardFrame(ClientDolphin clientDolphin) {
		super("Swing - Abfahrten ab Olten")
		departuresOnBoard = new ArrayList<>(clientDolphin.findAllPresentationModelsByType(TYPE_DEPARTURE_ON_BOARD))

		this.clientDolphin = clientDolphin
	}

	public void createAndShow() {
		def longPoll = null
		longPoll = {
			clientDolphin.send COMMAND_LONG_POLL, longPoll
		}

		board = new DepartureBoard()
		add(board);

		doAllBindings()

		setBackground(Color.BLACK)
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setVisible(true);

		longPoll()
	}

	void doAllBindings() {
		(0..4).each { posOnBoard ->
			PresentationModel pm = departuresOnBoard[posOnBoard]
			final row = board.rows[posOnBoard]
			bind(pm[ATT_DEPARTURE_TIME], {
										   Collection<String> newValue = ((String) it.newValue).split(":")
										   row.setHour        newValue.first()
										   row.setMinute      newValue.last()
										 })
			bind pm[ATT_DESTINATION],    { row.destination  = it.newValue as String }
			bind pm[ATT_TRACK],          { row.setTrack       it.newValue as String }
			bind pm[ATT_STATUS],         { row.blinking     = it.newValue == STATUS_IN_STATION }
		}
	}

	static void bind(Attribute attribute, Closure closure) {
		attribute.addPropertyChangeListener('value', closure as PropertyChangeListener)
	}

}