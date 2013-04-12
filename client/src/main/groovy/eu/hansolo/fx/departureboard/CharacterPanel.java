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
 * Date: 23.04.12
 * Time: 10:11
 */
public class CharacterPanel {
    private HBox           textPane;
    private Color          textColor;
    private boolean        soundOn;
    private int            noOfCharacters;
    private SplitFlap[]    characters;
    private StringProperty text;

    public CharacterPanel() {
        this(5, Color.WHITE);
    }

    public CharacterPanel(final int NO_OF_CHARACTERS) {
        this(NO_OF_CHARACTERS, Color.WHITE);
    }

    public CharacterPanel(final int NO_OF_CHARACTERS, final boolean SOUND_ON, final SplitFlap.Sound SOUND) {
        this(NO_OF_CHARACTERS, Color.WHITE, SOUND_ON, SOUND);
    }

    public CharacterPanel(final int NO_OF_CHARACTERS, final Color TEXT_COLOR) {
        this(NO_OF_CHARACTERS, TEXT_COLOR, false, SplitFlap.Sound.SOUND1);
    }

    public CharacterPanel(final int NO_OF_CHARACTERS, final Color TEXT_COLOR, final boolean SOUND_ON, final SplitFlap.Sound SOUND) {
        textPane = new HBox();
        textPane.setSpacing(0);
        soundOn          = SOUND_ON;
        noOfCharacters   = NO_OF_CHARACTERS;
        textColor        = TEXT_COLOR;
        characters       = new SplitFlap[noOfCharacters];
        for (int i = 0 ; i < noOfCharacters ; i++) {
            SplitFlap sf = SplitFlapBuilder.create()
                                           .selection(SplitFlap.ALPHANUMERIC)
                                           .flipTimeInMs(100)
                                           .text(" ")
                                           .textColor(textColor)
                                           .soundOn(soundOn)
                                           .sound(SOUND)
                                           .build();
            sf.setPrefSize(36, 60);
            characters[i] = sf;
        }
        text = new SimpleStringProperty();
        textPane.getChildren().addAll(characters);
    }

    public final HBox getPane() {
        return textPane;
    }

    public final String getText() {
        return text.get();
    }

    public final void setText(final String TEXT) {
        text.set(TEXT);
        int length = TEXT.length();
        if (length > noOfCharacters) {
            length = noOfCharacters;
        }
        for (int i = 0 ; i < noOfCharacters ; i++) {
            if (i < length) {
                characters[i].setText(String.valueOf(TEXT.charAt(i)));
            } else {
                characters[i].setText(" ");
            }
        }
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
}
