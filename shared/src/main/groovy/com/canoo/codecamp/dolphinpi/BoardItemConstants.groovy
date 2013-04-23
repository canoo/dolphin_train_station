package com.canoo.codecamp.dolphinpi

/**
 * @author Dieter Holz
 */
class BoardItemConstants {

	static class TYPE {
        public static final String BOARD_ITEM      = BoardItemConstants.unique 'type'
	}

	static class ATT {
		public static final String POSITION        = 'position'
        public static final String DEPARTURE_TIME  = 'departureTime';
        public static final String DESTINATION     = 'destination'
        public static final String STOPOVERS       = 'stopovers'
        public static final String TRACK           = 'track'
        public static final String TRAIN_NUMBER    = 'trainNumber'
        public static final String STATUS          = 'status'

        public static final List<String> ALL       = [POSITION, DEPARTURE_TIME, DESTINATION, STOPOVERS, TRACK, TRAIN_NUMBER, this.STATUS]
    }

	static class STATUS {
		public static final String APPROACHING     = 'hat Einfahrt'
        public static final String IN_STATION      = 'im Bahnhof'
        public static final String HAS_LEFT        = 'abgefahren'
	}

	static class CMD {
		public static final String LONG_POLL       = DepartureConstants.unique 'longPull'
	}

	static String unique(String s) { DepartureConstants.class.name + '.'+ s }

	static String pmId(String type, int index) { type + "-" + index}

}
