package com.canoo.codecamp.dolphinpi

/**
 * @author Dieter Holz
 */
class PresentationStateConstants {

    static class TYPE {
		public static final String PRESENTATION_STATE     = unique 'type'
    }

    static class ATT {
		public static final String SELECTED_DEPARTURE_ID  = 'selectedDepartureId'
		public static final String TOP_DEPARTURE_ON_BOARD = 'topDepartureOnBoard'
		public static final String SEARCH_STRING          = 'searchString'
	}

	static String unique(String s) { PresentationStateConstants.class.name + '.'+ s }
}
