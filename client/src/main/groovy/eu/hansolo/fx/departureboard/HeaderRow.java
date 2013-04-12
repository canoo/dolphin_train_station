package eu.hansolo.fx.departureboard;

import javafx.geometry.VPos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


/**
 * Created by
 * User: hansolo
 * Date: 23.04.12
 * Time: 13:00
 */
public class HeaderRow {

    private GridPane pane;

    public HeaderRow() {
        pane = new GridPane();

        Font font = Font.font("sans-serif", 20);

        Rectangle spacer1 = new Rectangle(0, 0, 68, 10);
        spacer1.setOpacity(0.0);

        Text flightLabel = new Text("Flight");
        flightLabel.setFill(Color.WHITE);
        flightLabel.setTextAlignment(TextAlignment.LEFT);
        flightLabel.setTextOrigin(VPos.BOTTOM);
        flightLabel.setFont(font);

        Rectangle spacer2 = new Rectangle(0, 0, 210, 10);
        spacer2.setOpacity(0.0);

        Text destinationLabel = new Text("Destination");
        destinationLabel.setFill(Color.WHITE);
        destinationLabel.setTextAlignment(TextAlignment.LEFT);
        destinationLabel.setTextOrigin(VPos.BOTTOM);
        destinationLabel.setFont(font);

        Rectangle spacer3 = new Rectangle(0, 0, 345, 10);
        spacer3.setOpacity(0.0);

        Text timeLabel = new Text("Time");
        timeLabel.setFill(Color.WHITE);
        timeLabel.setTextAlignment(TextAlignment.LEFT);
        timeLabel.setTextOrigin(VPos.BOTTOM);
        timeLabel.setFont(font);

        Rectangle spacer4 = new Rectangle(0, 0, 102, 10);
        spacer4.setOpacity(0.0);

        Text gateLabel = new Text("Gate");
        gateLabel.setFill(Color.WHITE);
        gateLabel.setTextAlignment(TextAlignment.LEFT);
        gateLabel.setTextOrigin(VPos.BOTTOM);
        gateLabel.setFont(font);

        pane.add(spacer1, 1, 1);
        pane.add(flightLabel, 2, 1);
        pane.add(spacer2, 3, 1);
        pane.add(destinationLabel, 4, 1);
        pane.add(spacer3, 5, 1);
        pane.add(timeLabel, 6, 1);
        pane.add(spacer4, 7, 1);
        pane.add(gateLabel, 8, 1);
    }

    public final GridPane getPane() {
        return pane;
    }
}
