package com.canoo.codecamp.dolphinpi.swingbaseddisplay

import com.canoo.codecamp.dolphinpi.ApplicationConstants
import com.canoo.codecamp.dolphinpi.DeparturesBoardApplicationModel
import com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board.DepartureBoard
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel

import javax.swing.*
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

public class DepartureBoardUI extends JFrame {
	private final ClientDolphin clientDolphin;

	private final DeparturesBoardApplicationModel departuresModel

	private DepartureBoard board

	public DepartureBoardUI(ClientDolphin clientDolphin, DeparturesBoardApplicationModel departuresModel) {
		super("Departures of Olten")
		this.clientDolphin = clientDolphin
		this.departuresModel = departuresModel
	}


	public void start() {
		def longPoll = null
		longPoll = {
			clientDolphin.send ApplicationConstants.COMMAND_LONG_POLL, longPoll
		}

		board = new DepartureBoard()
		add(board);


		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setVisible(true);


		longPoll()

	}

	void doAllBindings() {
		(1..5).each {
			ClientPresentationModel pm = clientDolphin.modelStore.findPresentationModelById(pmId(TYPE_DEPARTURE_ON_BOARD, it))
			final row = board.getRows().get(it - 1)
			pm.addPropertyChangeListener(ATT_DEPARTURE_TIME, new PropertyChangeListener() {
				@Override
				void propertyChange(PropertyChangeEvent evt) {
					String newValue = evt.newValue
					row.setHour(newValue.split(":").first())
					row.setMinute(newValue.split(":").last())

				}
			})
		}

	}

}