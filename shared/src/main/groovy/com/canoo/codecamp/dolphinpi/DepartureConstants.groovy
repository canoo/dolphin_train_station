package com.canoo.codecamp.dolphinpi

/**
 * @author Dieter Holz
 */
public class DepartureConstants {

    static class TYPE {
        public static final String DEPARTURE               = DepartureConstants.unique 'type'
    }

    static class ATT {
	    public static final String POSITION                = 'position'
	    public static final String DEPARTURE_TIME          = 'departureTime';
	    public static final String DESTINATION             = 'destination'
	    public static final String STOPOVERS               = 'stopovers'
	    public static final String TRACK                   = 'track'
	    public static final String TRAIN_NUMBER            = 'trainNumber'
	    public static final String STATUS                  = 'status'

	    static final List<String> ALL                      = [POSITION, DEPARTURE_TIME, DESTINATION, STOPOVERS, TRACK, TRAIN_NUMBER, this.STATUS]
     }

    static class STATUS {
    	public static final String APPROACHING             = 'hat Einfahrt'
        public static final String IN_STATION              = 'im Bahnhof'
        public static final String HAS_LEFT                = 'abgefahren'
    }

    static class CMD {
		public static final String PULL                    = DepartureConstants.unique 'pull'
		public static final String UNDO                    = DepartureConstants.unique 'undo'
		public static final String REDO                    = DepartureConstants.unique 'redo'
		public static final String INIT_SELECTED_DEPARTURE = DepartureConstants.unique 'initSelectedDeparture'
		public static final String MOVE_TO_TOP             = DepartureConstants.unique 'moveToTop'
    }

	static class SPECIAL_ID {
		public static final String SELECTED_DEPARTURE      = DepartureConstants.unique 'selectedDeparture'
		public static final String EMPTY_DEPARTURE         = DepartureConstants.unique 'emptyDeparture'
	}

    static String unique(String s) { DepartureConstants.class.name + '.'+ s }

	static String pmId(String type, int index) { type + "-" + index}
}
