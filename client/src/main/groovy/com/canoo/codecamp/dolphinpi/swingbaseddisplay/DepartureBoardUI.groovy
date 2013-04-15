package com.canoo.codecamp.dolphinpi.swingbaseddisplay

import com.canoo.codecamp.dolphinpi.DeparturesBoardApplicationModel
import com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board.DepartureBoard
import org.opendolphin.core.Attribute
import org.opendolphin.core.PresentationModel
import org.opendolphin.core.client.ClientDolphin

import javax.swing.*
import java.awt.*
import java.beans.PropertyChangeListener

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

public class DepartureBoardUI extends JFrame {
	private final ClientDolphin clientDolphin;

	private final DeparturesBoardApplicationModel departuresModel

	private DepartureBoard board

	public DepartureBoardUI(ClientDolphin clientDolphin, DeparturesBoardApplicationModel departuresModel) {
		super("Abfahrten ab Olten")
		this.clientDolphin = clientDolphin
		this.departuresModel = departuresModel
	}


	public void start() {
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
		for (it in (1..5)) {
			PresentationModel pm = clientDolphin.modelStore.findPresentationModelById(pmId(TYPE_DEPARTURE_ON_BOARD, it))
			final row = board.getRows().get(it - 1)
			bind(pm[ATT_DEPARTURE_TIME],  { Collection<String> newValue = ((String)it.newValue).split(":")
									 	    row.setHour newValue.first()
										    row.setMinute newValue.last()
			                              })
			bind pm[ATT_DESTINATION],     { row.destination = it.newValue as String }
			bind pm[ATT_TRACK],           { row.setTrack      it.newValue as String}
			bind pm[ATT_STATUS],          { row.blinking    = it.newValue == STATUS_IN_STATION }
		}

	}

	static void bind(Attribute attribute, Closure closure) {
		attribute.addPropertyChangeListener(closure as PropertyChangeListener)
	}

}