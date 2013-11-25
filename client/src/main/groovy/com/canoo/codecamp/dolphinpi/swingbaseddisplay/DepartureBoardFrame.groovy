package com.canoo.codecamp.dolphinpi.swingbaseddisplay

import com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board.DepartureBoard
import org.opendolphin.core.Attribute
import org.opendolphin.core.PresentationModel
import org.opendolphin.core.client.ClientDolphin

import javax.swing.*
import java.awt.*
import java.beans.PropertyChangeListener
import java.util.List

import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.DEPARTURE_TIME
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.DESTINATION
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.STATUS
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.TRACK
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.CMD.LONG_POLL
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.STATUS.IN_STATION
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.TYPE.BOARD_ITEM
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.*


public class DepartureBoardFrame extends JFrame {
	private final ClientDolphin clientDolphin;
	private final List departuresOnBoard
	private DepartureBoard board

	public DepartureBoardFrame(ClientDolphin clientDolphin) {
		super("Swing - Abfahrten ab Olten")
		departuresOnBoard = new ArrayList<>(clientDolphin.findAllPresentationModelsByType(BOARD_ITEM))

		this.clientDolphin = clientDolphin
	}

	public void createAndShow() {
		def longPoll = null
		longPoll = {
			clientDolphin.send LONG_POLL, longPoll
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
			bind(pm[DEPARTURE_TIME], {
									   Collection<String> newValue = ((String) it.newValue).split(":")
									   row.setHour        newValue.first()
									   row.setMinute      newValue.last()
									  })
			bind pm[DESTINATION],    { row.destination  = it.newValue as String }
			bind pm[TRACK],          { row.setTrack       it.newValue as String }
			bind pm[STATUS],         { row.blinking     = it.newValue == IN_STATION_STRING }
		}
	}

	static void bind(Attribute attribute, Closure closure) {
		attribute.addPropertyChangeListener('value', closure as PropertyChangeListener)
	}

}