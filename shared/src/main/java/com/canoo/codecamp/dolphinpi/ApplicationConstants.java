package com.canoo.codecamp.dolphinpi;

/**
 * Place for shared information among client and server. Typically identifiers for models, attributes and actions.
 */
public class ApplicationConstants {

	public static final String TYPE_DEPARTURE = unique("Departure");
	public static final String ATT_DEPARTURE_TIME = "departureTime";
	public static final String ATT_DESTINATION = "destination";
	public static final String ATT_STOPOVERS = "stopovers";
	public static final String ATT_TRACK = "track";
	public static final String ATT_TRAIN_NUMBER = "trainNumber";
	public static final String ATT_STATUS = "status";

	public static final String COMMAND_GET_ALL_DEPARTURES = "getAllDepartures";


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
