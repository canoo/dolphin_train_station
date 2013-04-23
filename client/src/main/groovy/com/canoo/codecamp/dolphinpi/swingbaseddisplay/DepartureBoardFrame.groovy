package com.canoo.codecamp.dolphinpi.swingbaseddisplay

import com.canoo.codecamp.dolphinpi.BoardItemConstants
import com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board.DepartureBoard
import org.opendolphin.core.Attribute
import org.opendolphin.core.PresentationModel
import org.opendolphin.core.client.ClientDolphin

import javax.swing.*
import java.awt.*
import java.beans.PropertyChangeListener
import java.util.List

public class DepartureBoardFrame extends JFrame {
	private final ClientDolphin clientDolphin;
	private final List departuresOnBoard

	private DepartureBoard board

	public DepartureBoardFrame(ClientDolphin clientDolphin) {
		super("Swing - Abfahrten ab Olten")
		departuresOnBoard = new ArrayList<>(clientDolphin.findAllPresentationModelsByType(BoardItemConstants.TYPE.BOARD_ITEM))

		this.clientDolphin = clientDolphin
	}

	public void createAndShow() {
		def longPoll = null
		longPoll = {
			clientDolphin.send BoardItemConstants.CMD.LONG_POLL, longPoll
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
			bind(pm[BoardItemConstants.ATT.DEPARTURE_TIME], {
										   Collection<String> newValue = ((String) it.newValue).split(":")
										   row.setHour        newValue.first()
										   row.setMinute      newValue.last()
										 })
			bind pm[BoardItemConstants.ATT.DESTINATION],    { row.destination  = it.newValue as String }
			bind pm[BoardItemConstants.ATT.TRACK],          { row.setTrack       it.newValue as String }
			bind pm[BoardItemConstants.ATT.STATUS],         { row.blinking     = it.newValue == BoardItemConstants.STATUS.IN_STATION }
		}
	}

	static void bind(Attribute attribute, Closure closure) {
		attribute.addPropertyChangeListener('value', closure as PropertyChangeListener)
	}

}