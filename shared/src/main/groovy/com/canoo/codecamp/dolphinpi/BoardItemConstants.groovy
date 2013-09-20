package com.canoo.codecamp.dolphinpi

/**
 * @author Dieter Holz
 */
class BoardItemConstants {

    static class PATH{
        public static final String PATH_BERN =       'm442,146c0,0 -10,19 -10,19c0,0 -11,15 -11,15c0,0 -14,7 -14,7c0,0 -3,9 -3,9c0,0 -5,16 -5,16c0,0 -8,13 -8,13c0,0 -21,9 -21,9c0,0 -13,21 -13,21'
        public static final String PATH_ZURICH =     'm439.600037,147c0,0 14,0 14,0c0,0 14,-10 14,-10c0,0 20,-7 20,-7c0,0 0,-10 -0.600037,-10c-0.600037,0 8.600037,-7 8.600037,-7c0,0 8,-3 8,-3c0,0 9,4 9,4c0,0 9,12 9,12c0,0 9,7 9,7c0,0 24,7 24,7'
        public static final String PATH_INTERLAKEN = "m441,147c0,0 -10,20 -10,20c0,0 -10,12 -10,12c0,0 -14,7 -14,7c0,0 -7,25 -7,25c0,0 -9,13 -9,13c0,0 -22,10 -22,10c0,0 -13,20 -13,20c0,0 13,5 13,5c0,0 9,15 9,15c0,0 6,25 6,25c0,0 8,8 8,8c0,0 -1,11 -1,11c0,0 9,8 9,8c0,0 12,7 12,7c0,0 14,-1 14,-1c0,0 9,-8 9,-8"
        public static final String PATH_BASEL =      "m382.600037,95c0,0 19,8 19,8c0,0 8,10 8,10c0,0 14,5 14,5c0,0 7,0 7,0c0,0 8,7 8,7c0,0 2,11 2,11c0,0 1,12 1,12"
        public static final String PATH_GALLEN =     "m441.600037,147c0,0 12,0 12,0c0,0 14,-10 14,-10c0,0 19,-7 19,-7c0,0 0,-12 -0.600037,-12c-0.600037,0 9.600037,-6 9,-6c-0.600037,0 8.600037,-2 8.600037,-2c0,0 9,4 9,4c0,0 8,11 8,11c0,0 10,8 10,8c0,0 23,7 23,7c0,0 4,-20 3.399963,-20c-0.600037,0 9.600037,4 9.600037,4c0,0 15,3 15,3c0,0 6,-21 6,-21c0,0 16,0 15.399963,0c-0.600037,0 16.600037,3 16.600037,3c0,0 13,8 13,8c0,0 11,-1 11,-1c0,0 7,5 7,5c0,0 12,1 11.399963,1c-0.600037,0 19.600037,8 19.600037,8c0,0 0,0 22,-4"
        public static final String PATH_GENF =       'm117.600037,448c0,0 6,-23 6,-23c0,0 12,-23 12,-23c0,0 18,-20 17.399963,-20c-0.600037,0 22.600037,-4 22.600037,-4c0,0 8,-9 8,-9c0,0 14,-7 14,-7c0,0 -8,-8 -8.600037,-8c-0.600037,0 0.600037,-14 0.600037,-14c0,0 9,-21 9,-21c0,0 12,-20 11.399963,-20c-0.600037,0 -0.399963,-9 -0.399963,-9c0,0 22,-16 22,-16c0,0 16,-25 16,-25c0,0 16,-7 16,-7c0,0 57.399963,-36 57.399963,-36c0,0 28,-15 28,-15c0,0 25,-2 25,-3c0,-1 29,-20 29,-20c0,0 38,-20 38,-20'
        public static final String PATH_LAUSANNE =     "m440,148c0,0 -65,39 -65,39c0,0 -26,3 -26,3c0,0 -101,59 -101,59c0,0 -16,26 -16,26c0,0 -21,14 -21,14c0,0 -1,11 -1,11c0,0 -20,40 -20,40c0,0 -1,14 -1,14c0,0 18,15 18,15"
        public static final String PATH_LUGANO =    "m440,147c0,0 14,37 14,37c0,0 1,9 1,9c0,0 19,2 19,2c0,0 16,16 16,16c0,0 13,6 13,6c0,0 9,2 9,2c0,0 2,11 2,11c0,0 28,-13 28,-13c0,0 17,11 17,11c0,0 10,-1 10,-1c0,0 4,8 4,8c0,0 -3,21 -3,21c0,0 12,41 12,41c0,0 -14,20 -14,20c0,0 1,46 1,46c0,0 28,10 28,10c0,0 21,15 21,15c0,0 5,14 5,14c0,0 14,7 14,7c0,0 4,16 4,16c0,0 9,17 9,17c0,0 -4,16 -4,16c0,0 -14,9 -14,9c0,0 0,24 0,24c0,0 3,14 3,14"
    }

    static class CIRCLES{
        public static final String BERN = "Bern"
        public static final String BASEL = "Basel SBB"
        public static final String GENF = "Genf-Flughafen"
        public static final String INTERLAKEN = "Interlaken Ost"
        public static final String ZURICH = "Zürich HB"
        public static final String GALLEN =  "St. Gallen"
        public static final String LUGANO = "Lugano"
        public static final String LAUSANNE = "Lausanne"
    }

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

       static ResourceBundle bundle = ResourceBundle.getBundle("BoardResources")

		public static final String APPROACHING     = bundle.getString("APPROACHING")
        public static final String IN_STATION      = bundle.getString("IN_STATION")
        public static final String HAS_LEFT        = bundle.getString("HAS_LEFT")
	}

	static class CMD {
		public static final String LONG_POLL       = BoardItemConstants.unique 'longPoll'
	}

	static String unique(String s) { BoardItemConstants.class.name + '.'+ s }

	static String pmId(String type, int index) { type + "-" + index}

}









