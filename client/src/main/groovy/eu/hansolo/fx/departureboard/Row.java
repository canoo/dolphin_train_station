package eu.hansolo.fx.departureboard;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import jfxtras.labs.scene.control.gauge.SplitFlap;


/**
 * Created by
 * User: hansolo
 * Date: 23.04.12
 * Time: 10:14
 */
public class Row {
    private HBox           row;
    private LedPanel       leds;
    private TimePanel      time;
    private WordPanel      destination;
    private CharacterPanel flightNo;
    private CharacterPanel gate;
    private String[]       destinations;
    private boolean        isEmpty;
    private boolean        soundOn;


    public Row() {
        this(true, "  ", "  ", "  ", "  ", "  ", false, SplitFlap.EXTENDED);
    }

    public Row(final boolean SOUND_ON, final String[] DESTINATIONS) {
        this(false, "  ", "  ", "", "", "", SOUND_ON, DESTINATIONS);
    }

    public Row(final String HOURS, final String MINUTES, final String DESTINATION, final String FLIGHT_NO, final String GATE) {
        this(false, HOURS, MINUTES, DESTINATION, FLIGHT_NO, GATE, false, SplitFlap.ALPHANUMERIC);
    }

    public Row(final String HOURS, final String MINUTES, final String DESTINATION, final String FLIGHT_NO, final String GATE, final boolean SOUND_ON, final String[] DESTINATIONS) {
        this(false, HOURS, MINUTES, DESTINATION, FLIGHT_NO, GATE, SOUND_ON, DESTINATIONS);
    }

    public Row(final boolean BLINKING, final String HOURS, final String MINUTES, final String DESTINATION, final String FLIGHT_NO, final String GATE, final boolean SOUND_ON, final String[] DESTINATIONS) {
        row = new HBox();
        row.setSpacing(11);
        soundOn     = SOUND_ON;
        leds        = new LedPanel();
        time        = new TimePanel(soundOn, SplitFlap.Sound.SOUND2);
        destination = new WordPanel(14, soundOn, SplitFlap.Sound.SOUND2, DESTINATIONS);
        flightNo    = new CharacterPanel(8, Color.rgb(255, 240, 100), soundOn, SplitFlap.Sound.SOUND2);
        gate        = new CharacterPanel(3, soundOn, SplitFlap.Sound.SOUND2);

        leds.setBlinking(BLINKING);
        time.setTime(HOURS, MINUTES);
        destination.setText(DESTINATION);
        flightNo.setText(FLIGHT_NO);
        gate.setText(GATE);

        row.getChildren().addAll(leds.getPane(),
                                 flightNo.getPane(),
                                 destination.getPane(),
                                 time.getPane(),
                                 gate.getPane());

        isEmpty = DESTINATION.isEmpty() ? true : false;
    }

    public final HBox getPane() {
        return row;
    }

    public final String getRowString() {
        return time.getTime() + " " + destination.getText() + " " + flightNo.getText();
    }

    public final void setRow(final Row ROW) {
        setRow(ROW.isBlinking(), ROW.getHours(), ROW.getMinutes(), ROW.getDestination(), ROW.getFlightNo(), ROW.getGate(), ROW.isSoundOn());
    }

    public final void setRow(final String HOURS, final String MINUTES, final String DESTINATION, final String FLIGHT_NO, final String GATE) {
        setRow(false, HOURS, MINUTES, DESTINATION, FLIGHT_NO, GATE, false);
    }

    public final void setRow(final boolean BLINKING, final String HOURS, final String MINUTES, final String DESTINATION, final String FLIGHT_NO, final String GATE, final boolean SOUND_ON) {
        leds.setBlinking(BLINKING);
        time.setTime(HOURS, MINUTES);
        time.setSoundOn(SOUND_ON);
        destination.setText(DESTINATION);
        destination.setSoundOn(SOUND_ON);
        flightNo.setText(FLIGHT_NO);
        flightNo.setSoundOn(SOUND_ON);
        gate.setText(GATE);
        gate.setSoundOn(SOUND_ON);
        destinations = destinations;
        isEmpty = DESTINATION.isEmpty() ? true : false;
    }

    public final boolean isBlinking() {
        return leds.isBlinking();
    }

    public final void setBlinking(final boolean BLINKING) {
        leds.setBlinking(BLINKING);
    }

    public final String getTime() {
        return time.getTime();
    }

    public final void setTime(final String HOURS, final String MINUTES) {
        time.setTime(HOURS, MINUTES);
    }

    public final String getHours() {
        return time.getHours();
    }

    public final void setHours(final String HOURS) {
        time.setHours(HOURS);
    }

    public final String getMinutes() {
        return time.getMinutes();
    }

    public final void setMinutes(final String MINUTES) {
        time.setMinutes(MINUTES);
    }

    public final String getDestination() {
        return destination.getText();
    }

    public final void setDestination(final String DESTINATION) {
        destination.setText(DESTINATION);
    }

    public final String getFlightNo() {
        return flightNo.getText();
    }

    public final void setFlightNo(final String FLIGHT_NO) {
        flightNo.setText(FLIGHT_NO);
    }

    public final String getGate() {
        return gate.getText();
    }

    public final void setGate(final String GATE) {
        gate.setText(GATE);
    }

    public final boolean isSoundOn() {
        return soundOn;
    }

    public final void setSoundOn(final boolean SOUND_ON) {
        soundOn = SOUND_ON;
    }

    public final String[] getDestinations() {
        return destinations;
    }

    public final void setDesinations(final String[] DESTINATIONS) {
        destinations = DESTINATIONS;
    }

    public final void setLedColor(final Color COLOR) {
        leds.setColor(COLOR);
    }

    public final void reset() {
        setRow(false, "", "", "", "", "", false);
        isEmpty = true;
        setBlinking(false);
    }

    public final boolean isEmpty() {
        return isEmpty;
    }

    public final void toggleLeds() {
        leds.toggle();
    }

    public final void setLedsOff() {
        leds.reset();
    }

    public String toString() {
        return getRowString();
    }
}
