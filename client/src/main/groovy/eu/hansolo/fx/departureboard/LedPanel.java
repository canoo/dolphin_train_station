package eu.hansolo.fx.departureboard;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import jfxtras.labs.scene.control.gauge.Led;
import jfxtras.labs.scene.control.gauge.LedBuilder;



/**
 * Created by
 * User: hansolo
 * Date: 23.04.12
 * Time: 08:47
 */
public class LedPanel {
    private HBox            ledPane;
    private Led             leftLed;
    private Led             rightLed;
    private Color           color;
    private boolean         toggle;
    private BooleanProperty blinking;
    private AnimationTimer  timer;
    private long            lastCall;

    public LedPanel() {
        ledPane  = new HBox();
        ledPane.setSpacing(0);
        color    = Color.LIME;
        leftLed  = LedBuilder.create()
                             .color(color)
                             .build();
        leftLed.setPrefSize(25, 25);
        HBox.setMargin(leftLed, new Insets(0, 0, 0, 5));
        rightLed = LedBuilder.create()
                             .color(color)
                             .build();
        rightLed.setPrefSize(25, 25);
        ledPane.getChildren().addAll(leftLed, rightLed);
        toggle   = false;
        blinking = new SimpleBooleanProperty(false);
        timer    = new AnimationTimer() {
            @Override public void handle(final long l) {
                if ((l - lastCall) >= 750000000l) {
                    toggle ^= true;
                    leftLed.setOn(toggle);
                    rightLed.setOn(!toggle);
                    lastCall = l;
                }
            }
        };
    }

    public final HBox getPane() {
        return ledPane;
    }

    public final boolean isBlinking() {
        return blinking.get();
    }

    public final void setBlinking(final boolean BLINKING) {
        blinking.set(BLINKING);
        if(BLINKING) {
            timer.start();
        } else {
            timer.stop();
            leftLed.setOn(false);
            rightLed.setOn(false);
        }
    }

    public final BooleanProperty blinkingProperty() {
        return blinking;
    }

    public final Color getColor() {
        return color;
    }

    public final void setColor(final Color COLOR) {
        color = COLOR;
        leftLed.setColor(COLOR);
        rightLed.setColor(COLOR);
    }

    public final void reset() {
        leftLed.setOn(false);
        rightLed.setOn(false);
    }

    public final void toggle() {
        toggle ^= true;
        leftLed.setOn(toggle);
        rightLed.setOn(!toggle);
    }
}
