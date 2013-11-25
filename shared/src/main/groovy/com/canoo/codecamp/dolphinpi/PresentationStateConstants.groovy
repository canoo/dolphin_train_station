package com.canoo.codecamp.dolphinpi

/**
 * @author Dieter Holz
 */
class PresentationStateConstants {

    static class TYPE {
		public static final String PRESENTATION_STATE     = PresentationStateConstants.unique 'presentationState'
    }

    static class ATT {
		public static final String SELECTED_DEPARTURE_ID  = 'selectedDepartureId'
		public static final String TOP_DEPARTURE_ON_BOARD = 'topDepartureOnBoard'
		public static final String SEARCH_STRING          = 'searchString'
		public static final String LANGUAGE          = 'language'
		public static final String UNDO_DISABLED          = 'undoDisabled'
		public static final String REDO_DISABLED          = 'redoDisabled'
		public static final String SAVE_DISABLED          = 'saveDisabled'
	}

	static String unique(String s) { PresentationStateConstants.class.name + '.'+ s }
}
