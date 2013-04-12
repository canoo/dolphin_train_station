package com.canoo.codecamp.dolphinpi;

import java.util.List;

/**
 * Place for shared information among client and server. Typically identifiers for models, attributes and actions.
 */
public class ApplicationConstants {

	public static final String TYPE_DEPARTURE = unique("Departure");
	public static final String TYPE_DEPARTURE_ON_BOARD = unique("DepartureOnBoard");

	// Departure PM:
	public static final String TOP_DEPARTURE = "TOP_DEPARTURE";
	public static final String ATT_DOMAIN_ID = "ATT_DOMAIN_ID";

	// Departure PM:
	public static final String ATT_POSITION = "POSITION";
	public static final String ATT_DEPARTURE_TIME = "departureTime";
	public static final String ATT_DESTINATION = "destination";
	public static final String ATT_STOPOVERS = "stopovers";
	public static final String ATT_TRACK = "track";
	public static final String ATT_TRAIN_NUMBER = "trainNumber";
	public static final String ATT_STATUS = "status";

	public static final String STATUS_APPROACHING = "hat Einfahrt";
	public static final String STATUS_IN_STATION = "im Bahnhof";
	public static final String STATUS_HAS_LEFT = "abgefahren";



	static final List<String> ALL_ATTRIBUTES = [ATT_POSITION, ATT_DEPARTURE_TIME, ATT_DESTINATION, ATT_STOPOVERS,
		ATT_TRACK, ATT_TRAIN_NUMBER, ATT_STATUS]


	public static final String COMMAND_GET_ALL_DEPARTURES = "getAllDepartures";
	public static final String COMMAND_MOVE_TO_TOP = "COMMAND_MOVE_TO_TOP";

	public static final String COMMAND_LONG_POLL = "longPoll";

	public static final String SELECTED_DEPARTURE = "selectedDeparture";


    /**
     * Unify the identifier with the class name prefix.
     */
    private static String unique(String key) {
        return ApplicationConstants.class.getName() + "." + key;
    }

	static String pmId(String type, int index) {
		return type + "-" + index;
	}
}
