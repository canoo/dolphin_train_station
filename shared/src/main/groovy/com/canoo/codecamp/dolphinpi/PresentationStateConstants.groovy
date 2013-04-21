package com.canoo.codecamp.dolphinpi

/**
 * @author Dieter Holz
 */
class PresentationStateConstants {

    static class TYPE {
		public static final String PRESENTATION_STATE     = PresentationStateConstants.unique 'type'
    }

    static class ATT {
		public static final String SELECTED_DEPARTURE_ID  = 'selectedDepartureId'
		public static final String TOP_DEPARTURE_ON_BOARD = 'topDepartureOnBoard'
	}

	static String unique(String s) { DepartureConstants.class.name + '.'+ s }
}
