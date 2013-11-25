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
	    public static final String DRIVE_IN                = 'driveIn'
	    public static final String DRIVE_OUT               = 'driveOut'
	    public static final String FIRST_ONE               = 'firstOne'
        public static final String APPROACHING             = 'approaching'
        public static final String IN_STATION              = 'inStation'
        public static final String HAS_LEFT                = 'hasLeft'

	    static final List<String> ALL                      = [POSITION, DEPARTURE_TIME, DESTINATION, STOPOVERS, TRACK, TRAIN_NUMBER, this.STATUS]
	   public static final List<String> ALLBUTTONS               = [DRIVE_IN, DRIVE_OUT, FIRST_ONE, APPROACHING, IN_STATION, HAS_LEFT]
     }

    static class STATUS {


    }

    static class CMD {
		public static final String PULL                    = DepartureConstants.unique 'pull'
		public static final String UNDO                    = DepartureConstants.unique 'undo'
		public static final String REDO                    = DepartureConstants.unique 'redo'
		public static final String CHANGE                    = DepartureConstants.unique 'change'
		public static final String SAVE                    = DepartureConstants.unique 'save'
		public static final String INIT_SELECTED_DEPARTURE = DepartureConstants.unique 'initSelectedDeparture'
		public static final String MOVE_TO_TOP             = DepartureConstants.unique 'moveToTop'
    }

	static class SPECIAL_ID {
		public static final String SELECTED_DEPARTURE      = DepartureConstants.unique 'selectedDeparture'
		public static final String EMPTY_DEPARTURE         = DepartureConstants.unique 'emptyDeparture'
		public static final String BUTTONS         = DepartureConstants.unique 'buttonsPM'
        public static final String DEPARTURES              = 'Departures.txt'
        public static final String IN_STATION_STRING              = 'In Station'
	}

    static String unique(String s) { DepartureConstants.class.name + '.'+ s }

	static String pmId(String type, int index) { type + "-" + index}
}
