package eu.hansolo.fx.departureboard;

/**
 * Created by
 * User: hansolo
 * Date: 24.04.12
 * Time: 17:28
 */
public class RowBuilder {
    private Row row;

    public final RowBuilder create() {
        row = new Row();
        return this;
    }

    public final RowBuilder blinking(final boolean BLINKING) {
        row.setBlinking(BLINKING);
        return this;
    }

    public final RowBuilder hours(final String HOURS){
        row.setHours(HOURS);
        return this;
    }

    public final RowBuilder minutes(final String MINUTES) {
        row.setMinutes(MINUTES);
        return this;
    }

    public final RowBuilder destination(final String DESTINATION) {
        row.setDestination(DESTINATION);
        return this;
    }

    public final RowBuilder flightNo(final String FLIGHT_NUMBER) {
        row.setFlightNo(FLIGHT_NUMBER);
        return this;
    }

    public final RowBuilder gate(final String GATE) {
        row.setGate(GATE);
        return this;
    }

    public final RowBuilder soundOn(final boolean SOUND_ON) {
        row.setSoundOn(SOUND_ON);
        return this;
    }

    public final RowBuilder listOfDestinations(final String[] DESTINATIONS) {
        row.setDesinations(DESTINATIONS);
        return this;
    }

    public final Row build() {
        if (row == null) {
            row = new Row();
        }
        return row;
    }
}
