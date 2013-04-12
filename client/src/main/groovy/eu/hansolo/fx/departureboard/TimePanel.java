package eu.hansolo.fx.departureboard;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.VPos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import jfxtras.labs.scene.control.gauge.SplitFlap;
import jfxtras.labs.scene.control.gauge.SplitFlapBuilder;


/**
 * Created by
 * User: hansolo
 * Date: 23.04.12
 * Time: 09:09
 */
public class TimePanel {
    private HBox           hourPanel;
    private SplitFlap      hourLeft;
    private SplitFlap      hourRight;
    private SplitFlap      minLeft;
    private SplitFlap      minRight;
    private StringProperty hours;
    private StringProperty minutes;
    private boolean        soundOn;

    public TimePanel(final boolean SOUND_ON, final SplitFlap.Sound SOUND) {
        soundOn = SOUND_ON;
        hourPanel = new HBox();
        hourPanel.setSpacing(0);
        hourLeft  = SplitFlapBuilder.create()
                                    .selection(SplitFlap.NUMERIC)
                                    .text(" ")
                                    .flipTimeInMs(100)
                                    .textColor(Color.rgb(255, 240, 100))
                                    .soundOn(soundOn)
                                    .sound(SOUND)
                                    .build();
        hourLeft.setPrefSize(36, 60);
        hourRight = SplitFlapBuilder.create()
                                     .selection(SplitFlap.NUMERIC)
                                     .text(" ")
                                     .flipTimeInMs(100)
                                     .textColor(Color.rgb(255, 240, 100))
                                     .soundOn(soundOn)
                                     .sound(SOUND)
                                     .build();
        hourRight.setPrefSize(36, 60);
        minLeft   = SplitFlapBuilder.create()
                                     .selection(SplitFlap.NUMERIC)
                                     .text(" ")
                                     .flipTimeInMs(100)
                                     .textColor(Color.rgb(255, 240, 100))
                                     .soundOn(soundOn)
                                     .sound(SOUND)
                                     .build();
        minLeft.setPrefSize(36, 60);
        minRight  = SplitFlapBuilder.create()
                                     .selection(SplitFlap.NUMERIC)
                                     .text(" ")
                                     .flipTimeInMs(100)
                                     .textColor(Color.rgb(255, 240, 100))
                                     .soundOn(soundOn)
                                     .sound(SOUND)
                                     .build();
        minRight.setPrefSize(36, 60);
        Text colon = new Text(":");
        colon.setFill(Color.WHITE);
        colon.setTextAlignment(TextAlignment.CENTER);
        colon.setTextOrigin(VPos.CENTER);
        colon.setFont(Font.font("sans serif", 36));
        hourPanel.getChildren().addAll(hourLeft,
                                       hourRight,
                                       colon,
                                       minLeft,
                                       minRight);
        hours = new SimpleStringProperty("  ");
        minutes = new SimpleStringProperty("  ");
    }

    public final HBox getPane() {
        return hourPanel;
    }

    public final String getTime() {
        return (hours.get() + ":" + minutes.get());
    }

    public final void setTime(final String HOURS, final String MINUTES) {
        setHours(HOURS);
        setMinutes(MINUTES);
    }

    public final String getHours() {
        return hours.get();
    }

    public final void setHours(final String HOURS) {
        hours.set(HOURS);
        if (!HOURS.isEmpty()) {
            if (hours.get().length() > 1) {
                hourLeft.setText(hours.get().substring(0, 1));
                hourRight.setText(hours.get().substring(1, 2));
            } else {
                hourLeft.setText("0");
                hourRight.setText(hours.get().substring(0, 1));
            }
        } else {
            hourLeft.setText(" ");
            hourRight.setText(" ");
        }
    }

    public final StringProperty hoursProperty() {
        return hours;
    }

    public final String getMinutes() {
        return minutes.get();
    }

    public final void setMinutes(final String MINUTES) {
        minutes.set(MINUTES);
        if (!MINUTES.isEmpty()) {
            if (minutes.get().length() > 1) {
                minLeft.setText(minutes.get().substring(0, 1));
                minRight.setText(minutes.get().substring(1, 2));
            } else {
                minLeft.setText("0");
                minRight.setText(minutes.get().substring(0, 1));
            }
        } else {
            minLeft.setText(" ");
            minRight.setText(" ");
        }
    }

    public final StringProperty minutesProperty() {
        return minutes;
    }

    public final boolean isSoundOn() {
        return soundOn;
    }

    public final void setSoundOn(final boolean SOUND_ON) {
        soundOn = SOUND_ON;
        hourLeft.setSoundOn(soundOn);
        hourRight.setSoundOn(soundOn);
        minLeft.setSoundOn(soundOn);
        minRight.setSoundOn(soundOn);
    }

}
