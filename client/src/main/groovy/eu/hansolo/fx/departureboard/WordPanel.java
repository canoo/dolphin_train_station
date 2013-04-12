package eu.hansolo.fx.departureboard;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import jfxtras.labs.scene.control.gauge.SplitFlap;
import jfxtras.labs.scene.control.gauge.SplitFlapBuilder;


/**
 * Created by
 * User: hansolo
 * Date: 27.04.12
 * Time: 11:10
 */
public class WordPanel {
    private HBox           textPane;
    private Color          textColor;
    private boolean        soundOn;
    private SplitFlap      splitFlap;
    private StringProperty text;
    private String[]       selection;

    public WordPanel() {
        this(5, Color.WHITE);
    }

    public WordPanel(final int NO_OF_CHARACTERS) {
        this(NO_OF_CHARACTERS, Color.WHITE);
    }

    public WordPanel(final int NO_OF_CHARACTERS, final boolean SOUND_ON, final SplitFlap.Sound SOUND, final String[] SELECTION) {
        this(NO_OF_CHARACTERS, Color.WHITE, SOUND_ON, SOUND, SELECTION);
    }

    public WordPanel(final int NO_OF_CHARACTERS, final Color TEXT_COLOR) {
        this(NO_OF_CHARACTERS, TEXT_COLOR, false, SplitFlap.Sound.SOUND1, SplitFlap.ALPHANUMERIC);
    }

    public WordPanel(final int NO_OF_CHARACTERS, final Color TEXT_COLOR, final boolean SOUND_ON, final SplitFlap.Sound SOUND, final String[] SELECTION) {
        textPane = new HBox();
        textPane.setSpacing(0);
        soundOn        = SOUND_ON;
        selection      = SELECTION;
        textColor      = TEXT_COLOR;
        splitFlap      = SplitFlapBuilder.create()
                                         .selection(selection)
                                         .flipTimeInMs(100)
                                         .text(" ")
                                         .textColor(textColor)
                                         .soundOn(soundOn)
                                         .sound(SOUND)
                                         .build();
        splitFlap.setPrefSize(NO_OF_CHARACTERS * 25, 60);
        text = new SimpleStringProperty();
        textPane.getChildren().addAll(splitFlap);
    }

    public final HBox getPane() {
        return textPane;
    }

    public final String getText() {
        return text.get();
    }

    public final void setText(final String TEXT) {
        text.set(TEXT);
        splitFlap.setText(TEXT);
    }

    public final StringProperty textProperty() {
        return text;
    }

    public final boolean isSoundOn() {
        return soundOn;
    }

    public final void setSoundOn(final boolean SOUND_ON) {
        soundOn = SOUND_ON;
    }

    public final String[] getSelection() {
        return selection;
    }

    public final void setSelection(final String[] SELECTION) {
        selection = SELECTION;
    }
}
