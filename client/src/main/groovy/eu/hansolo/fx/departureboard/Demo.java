package eu.hansolo.fx.departureboard;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.gauge.Clock;

import java.util.Calendar;


/**
 * Created by
 * User: hansolo
 * Date: 23.04.12
 * Time: 11:53
 */
public class Demo extends Application {
    private DepartureBoard        departureBoard;

    public Demo() {
        departureBoard = new DepartureBoard();
    }

    @Override
    public void start(Stage stage) throws Exception {
        GridPane grid = new GridPane();

        Clock clock = new Clock();
        //clock.setSecondPointerVisible(false);
        clock.setAutoDimEnabled(false);
        clock.setTheme(Clock.Theme.DARK);
        clock.setRunning(true);
        clock.setPrefSize(120, 120);

        Text header = new Text("Departures");
        header.setFill(Color.WHITE);
        header.setTextAlignment(TextAlignment.LEFT);
        header.setTextOrigin(VPos.BOTTOM);
        header.setFont(Font.font("sans serif", 36));

        Group airplaneImage = getDepartureImage(80, Color.WHITE);

        GridPane headerPane = new GridPane();
        headerPane.add(clock, 1, 1);
        GridPane.setMargin(clock, new Insets(20, 50, 30, 10));
        headerPane.add(airplaneImage, 2, 1);
        GridPane.setMargin(airplaneImage, new Insets(5, 25, 10, 5));
        headerPane.add(header, 3, 1);

        HeaderRow labels = new HeaderRow();

        grid.add(headerPane, 1, 1);

        grid.add(labels.getPane(), 1, 2);

        grid.add(departureBoard.getPane(), 1, 3);
        GridPane.setHalignment(departureBoard.getPane(), HPos.CENTER);

        LinearGradient gradient = new LinearGradient(0, 0, 0, 600, false, CycleMethod.NO_CYCLE,
                                                     new Stop(0.0, Color.rgb(28, 27, 22)),
                                                     new Stop(0.25, Color.rgb(38, 37, 32)),
                                                     new Stop(1.0, Color.rgb(28, 27, 22)));

        //final Scene scene = new Scene(grid, 1024, 840, gradient);
        final Scene scene = new Scene(grid, 1024, 768, gradient);
        //scene.setCamera(new PerspectiveCamera());

        stage.setTitle("DepartureBoard");
        stage.setScene(scene);
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        initialSetup();
    }

    private void initialSetup() {
        final Calendar CAL = Calendar.getInstance();
        final long NOW = System.currentTimeMillis();
        CAL.setTimeInMillis(NOW + 60000);
        departureBoard.addRow(new Row(Integer.toString(CAL.get(Calendar.HOUR_OF_DAY)), Integer.toString(CAL.get(Calendar.MINUTE)), "BERLIN", "LH1969", "A01"));
        CAL.setTimeInMillis(NOW + 120000);
        departureBoard.addRow(new Row(Integer.toString(CAL.get(Calendar.HOUR_OF_DAY)), Integer.toString(CAL.get(Calendar.MINUTE)), "BASEL", "LH1203", "A03"));
        CAL.setTimeInMillis(NOW + 180000);
        departureBoard.addRow(new Row(Integer.toString(CAL.get(Calendar.HOUR_OF_DAY)), Integer.toString(CAL.get(Calendar.MINUTE)), "HELSINKI", "LH0502", "A08"));
        CAL.setTimeInMillis(NOW + 240000);
        departureBoard.addRow(new Row(Integer.toString(CAL.get(Calendar.HOUR_OF_DAY)), Integer.toString(CAL.get(Calendar.MINUTE)), "MOSCOW", "LH0610", "C13"));
        CAL.setTimeInMillis(NOW + 600000);
        departureBoard.addRow(new Row(Integer.toString(CAL.get(Calendar.HOUR_OF_DAY)), Integer.toString(CAL.get(Calendar.MINUTE)), "LONDON", "LH0610", "B02"));
        CAL.setTimeInMillis(NOW + 900000);
        departureBoard.addRow(new Row(Integer.toString(CAL.get(Calendar.HOUR_OF_DAY)), Integer.toString(CAL.get(Calendar.MINUTE)), "FRANKFURT", "LH1972", "A02"));
        CAL.setTimeInMillis(NOW + 1200000);
        departureBoard.addRow(new Row(Integer.toString(CAL.get(Calendar.HOUR_OF_DAY)), Integer.toString(CAL.get(Calendar.MINUTE)), "PARIS", "LH0502", "B01"));
        CAL.setTimeInMillis(NOW + 1500000);
        departureBoard.addRow(new Row(Integer.toString(CAL.get(Calendar.HOUR_OF_DAY)), Integer.toString(CAL.get(Calendar.MINUTE)), "SYDNEY", "LH0310", "A01"));
        CAL.setTimeInMillis(NOW + 1800000);
        departureBoard.addRow(new Row(Integer.toString(CAL.get(Calendar.HOUR_OF_DAY)), Integer.toString(CAL.get(Calendar.MINUTE)), "SAN FRANCISCO", "LH2310", "A12"));
        CAL.setTimeInMillis(NOW + 2100000);
        departureBoard.addRow(new Row(Integer.toString(CAL.get(Calendar.HOUR_OF_DAY)), Integer.toString(CAL.get(Calendar.MINUTE)), "BOSTON", "LH01942", "B05"));
        CAL.setTimeInMillis(NOW + 2400000);
        departureBoard.addRow(new Row(Integer.toString(CAL.get(Calendar.HOUR_OF_DAY)), Integer.toString(CAL.get(Calendar.MINUTE)), "NEW YORK", "LH4212", "C28"));
        CAL.setTimeInMillis(NOW + 2700000);
        departureBoard.addRow(new Row(Integer.toString(CAL.get(Calendar.HOUR_OF_DAY)), Integer.toString(CAL.get(Calendar.MINUTE)), "MUNICH", "LH2205", "B13"));

    }

    public final Group getDepartureImage(final double SIZE, final Color COLOR) {
        final double WIDTH = SIZE;
        final double HEIGHT = SIZE;

        final Group DEPARTURE_GROUP = new Group();

        DEPARTURE_GROUP.getChildren().clear();
        final Shape IBOUNDS = new Rectangle(0, 0, WIDTH, HEIGHT);
        IBOUNDS.setOpacity(0.0);
        DEPARTURE_GROUP.getChildren().add(IBOUNDS);

        final Path FRAME = new Path();
        FRAME.setFillRule(FillRule.EVEN_ODD);
        FRAME.getElements().add(new MoveTo(0.8218181818181818 * WIDTH, 0.43272727272727274 * HEIGHT));
        FRAME.getElements().add(new CubicCurveTo(0.8109090909090909 * WIDTH, 0.41454545454545455 * HEIGHT,
                                                0.7745454545454545 * WIDTH, 0.41454545454545455 * HEIGHT,
                                                0.7745454545454545 * WIDTH, 0.41454545454545455 * HEIGHT));
        FRAME.getElements().add(new LineTo(0.6218181818181818 * WIDTH, 0.46545454545454545 * HEIGHT));
        FRAME.getElements().add(new LineTo(0.3418181818181818 * WIDTH, 0.36727272727272725 * HEIGHT));
        FRAME.getElements().add(new LineTo(0.3054545454545455 * WIDTH, 0.38181818181818183 * HEIGHT));
        FRAME.getElements().add(new LineTo(0.49454545454545457 * WIDTH, 0.5127272727272727 * HEIGHT));
        FRAME.getElements().add(new CubicCurveTo(0.49454545454545457 * WIDTH, 0.5127272727272727 * HEIGHT,
                                                0.32 * WIDTH, 0.5745454545454546 * HEIGHT,
                                                0.32 * WIDTH, 0.5745454545454546 * HEIGHT));
        FRAME.getElements().add(new CubicCurveTo(0.31272727272727274 * WIDTH, 0.5781818181818181 * HEIGHT,
                                                0.29818181818181816 * WIDTH, 0.5709090909090909 * HEIGHT,
                                                0.29818181818181816 * WIDTH, 0.5709090909090909 * HEIGHT));
        FRAME.getElements().add(new LineTo(0.19272727272727272 * WIDTH, 0.5272727272727272 * HEIGHT));
        FRAME.getElements().add(new LineTo(0.18181818181818182 * WIDTH, 0.5345454545454545 * HEIGHT));
        FRAME.getElements().add(new LineTo(0.2581818181818182 * WIDTH, 0.610909090909091 * HEIGHT));
        FRAME.getElements().add(new CubicCurveTo(0.2581818181818182 * WIDTH, 0.610909090909091 * HEIGHT,
                                                0.2509090909090909 * WIDTH, 0.6290909090909091 * HEIGHT,
                                                0.2509090909090909 * WIDTH, 0.6290909090909091 * HEIGHT));
        FRAME.getElements().add(new CubicCurveTo(0.2690909090909091 * WIDTH, 0.6472727272727272 * HEIGHT,
                                                0.3381818181818182 * WIDTH, 0.6327272727272727 * HEIGHT,
                                                0.3381818181818182 * WIDTH, 0.6327272727272727 * HEIGHT));
        FRAME.getElements().add(new LineTo(0.7963636363636364 * WIDTH, 0.4763636363636364 * HEIGHT));
        FRAME.getElements().add(new CubicCurveTo(0.7963636363636364 * WIDTH, 0.4763636363636364 * HEIGHT,
            0.8254545454545454 * WIDTH, 0.4581818181818182 * HEIGHT,
            0.8218181818181818 * WIDTH, 0.43272727272727274 * HEIGHT));
        FRAME.getElements().add(new ClosePath());
        FRAME.setFill(COLOR);
        FRAME.setStroke(null);

        final Path PLANE = new Path();
        PLANE.setFillRule(FillRule.EVEN_ODD);
        PLANE.getElements().add(new MoveTo(0.08727272727272728 * WIDTH, 0.14545454545454545 * HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(0.08727272727272728 * WIDTH, 0.09454545454545454 * HEIGHT,
                                                  0.09454545454545454 * WIDTH, 0.08727272727272728 * HEIGHT,
                                                  0.14545454545454545 * WIDTH, 0.08727272727272728 * HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(0.14545454545454545 * WIDTH, 0.08727272727272728 * HEIGHT,
                                                  0.8581818181818182 * WIDTH, 0.08727272727272728 * HEIGHT,
                                                  0.8581818181818182 * WIDTH, 0.08727272727272728 * HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(0.9054545454545454 * WIDTH, 0.08727272727272728 * HEIGHT,
                                                  0.9163636363636364 * WIDTH, 0.09454545454545454 * HEIGHT,
                                                  0.9163636363636364 * WIDTH, 0.14545454545454545 * HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(0.9163636363636364 * WIDTH, 0.14545454545454545 * HEIGHT,
                                                  0.9163636363636364 * WIDTH, 0.8581818181818182 * HEIGHT,
                                                  0.9163636363636364 * WIDTH, 0.8581818181818182 * HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(0.9163636363636364 * WIDTH, 0.9054545454545454 * HEIGHT,
                                                  0.9054545454545454 * WIDTH, 0.9163636363636364 * HEIGHT,
                                                  0.8581818181818182 * WIDTH, 0.9163636363636364 * HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(0.8581818181818182 * WIDTH, 0.9163636363636364 * HEIGHT,
                                                  0.14545454545454545 * WIDTH, 0.9163636363636364 * HEIGHT,
                                                  0.14545454545454545 * WIDTH, 0.9163636363636364 * HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(0.09454545454545454 * WIDTH, 0.9163636363636364 * HEIGHT,
                                                  0.08727272727272728 * WIDTH, 0.9054545454545454 * HEIGHT,
                                                  0.08727272727272728 * WIDTH, 0.8581818181818182 * HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(0.08727272727272728 * WIDTH, 0.8581818181818182 * HEIGHT,
                                                  0.08727272727272728 * WIDTH, 0.14545454545454545 * HEIGHT,
                                                  0.08727272727272728 * WIDTH, 0.14545454545454545 * HEIGHT));
        PLANE.getElements().add(new ClosePath());
        PLANE.getElements().add(new MoveTo(0.0, 0.06909090909090909 * HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(0.0, 0.06909090909090909 * HEIGHT,
                                                  0.0, 0.9309090909090909 * HEIGHT,
                                                  0.0, 0.9309090909090909 * HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(0.0, 0.9890909090909091 * HEIGHT,
                                                  0.01090909090909091 * WIDTH, HEIGHT,
                                                  0.06909090909090909 * WIDTH, HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(0.06909090909090909 * WIDTH, HEIGHT,
                                                  0.9309090909090909 * WIDTH, HEIGHT,
                                                  0.9309090909090909 * WIDTH, HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(0.9890909090909091 * WIDTH, HEIGHT,
                                                  WIDTH, 0.9890909090909091 * HEIGHT,
                                                  WIDTH, 0.9309090909090909 * HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(WIDTH, 0.9309090909090909 * HEIGHT,
                                                  WIDTH, 0.06909090909090909 * HEIGHT,
                                                  WIDTH, 0.06909090909090909 * HEIGHT));
        PLANE.getElements().add(new CubicCurveTo(WIDTH, 0.01090909090909091 * HEIGHT,
                                                  0.9890909090909091 * WIDTH, 0.0,
                                                  0.9309090909090909 * WIDTH, 0.0));
        PLANE.getElements().add(new CubicCurveTo(0.9309090909090909 * WIDTH, 0.0,
                                                  0.06909090909090909 * WIDTH, 0.0,
                                                  0.06909090909090909 * WIDTH, 0.0));
        PLANE.getElements().add(new CubicCurveTo(0.01090909090909091 * WIDTH, 0.0,
            0.0, 0.01090909090909091 * HEIGHT,
            0.0, 0.06909090909090909 * HEIGHT));
        PLANE.getElements().add(new ClosePath());
        PLANE.setFill(COLOR);
        PLANE.setStroke(null);

        DEPARTURE_GROUP.getChildren().addAll(FRAME,
                                            PLANE);

        return DEPARTURE_GROUP;
    }

    public static void main(final String[] args) {
        Application.launch(args);
    }
}
