package com.canoo.codecamp.dolphinpi.admin

import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.animation.TimelineBuilder
import javafx.util.Duration
import org.opendolphin.core.PresentationModel
import org.opendolphin.core.client.ClientDolphin

import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DEPARTURE_TIME
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DESTINATION
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.POSITION
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.TRACK
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.TRAIN_NUMBER
import static com.canoo.codecamp.dolphinpi.DepartureConstants.TYPE.DEPARTURE

/**
 * @author Dieter Holz
 */
class Util {
	static void shake(javafx.scene.Node node) {
		Timeline tl = TimelineBuilder.create().cycleCount(3).autoReverse(false).keyFrames(
				new KeyFrame(Duration.millis(25), new KeyValue(node.translateXProperty(), 3)),
				new KeyFrame(Duration.millis(75), new KeyValue(node.translateXProperty(), -3)),
				new KeyFrame(Duration.millis(100), new KeyValue(node.translateXProperty(), 0)),
		).build()
		tl.play()
	}

	static  List<PresentationModel> allMatchingDepartures(ClientDolphin dolphin, String searchString){
		def attributeCandidates = [DEPARTURE_TIME, TRACK, TRAIN_NUMBER, DESTINATION]
		String lowerCaseString = searchString.toLowerCase()
		dolphin.findAllPresentationModelsByType(DEPARTURE)
			.findAll { PresentationModel departure ->
						attributeCandidates.any { departure[it].value.toLowerCase().contains(lowerCaseString) }
					 }
			.sort    { it[POSITION].value }
	}

}
