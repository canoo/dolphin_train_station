package com.canoo.codecamp.dolphinpi.admin

import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.Timeline
import javafx.animation.TimelineBuilder
import javafx.util.Duration

/**
 * @author Dieter Holz
 */
class Util {
	static void shake(javafx.scene.Node node) {
		Timeline tl = TimelineBuilder.create().cycleCount(2).autoReverse(false).keyFrames(
				new KeyFrame(Duration.millis(25), new KeyValue(node.translateXProperty(), 3)),
				new KeyFrame(Duration.millis(75), new KeyValue(node.translateXProperty(), -3)),
				new KeyFrame(Duration.millis(100), new KeyValue(node.translateXProperty(), 0)),
		).build()
		tl.play()
	}

}
