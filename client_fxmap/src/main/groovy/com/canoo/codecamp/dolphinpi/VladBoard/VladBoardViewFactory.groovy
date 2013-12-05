package com.canoo.codecamp.dolphinpi.VladBoard

import javafx.animation.Animation
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.Group;
import javafx.animation.Interpolator
import javafx.animation.KeyFrame
import javafx.animation.KeyValue
import javafx.animation.PathTransition
import javafx.animation.PathTransitionBuilder
import javafx.animation.Timeline
import javafx.animation.TimelineBuilder
import javafx.beans.property.DoubleProperty
import javafx.beans.property.SimpleDoubleProperty
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.scene.Parent
import javafx.scene.chart.PieChart
import javafx.scene.control.SplitPane
import javafx.scene.control.SplitPaneBuilder
import javafx.scene.control.TableColumnBuilder
import javafx.scene.control.TableView
import javafx.scene.control.TableViewBuilder
import javafx.scene.effect.BlurType
import javafx.scene.effect.DropShadowBuilder
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.scene.paint.Color
import javafx.scene.shape.ArcTo
import javafx.scene.shape.Circle
import javafx.scene.shape.ClosePath
import javafx.scene.shape.MoveTo
import javafx.scene.shape.Path
import javafx.scene.shape.PathBuilder
import javafx.scene.shape.Rectangle
import javafx.scene.shape.SVGPath
import javafx.scene.shape.SVGPathBuilder
import javafx.scene.shape.Shape
import javafx.scene.shape.StrokeType
import javafx.scene.text.Text
import javafx.util.Callback
import javafx.util.Duration
import org.opendolphin.core.PresentationModel
import org.opendolphin.core.client.ClientAttributeWrapper
import org.opendolphin.core.client.ClientDolphin
import javafx.scene.image.ImageView
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.DEPARTURE_TIME
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.DESTINATION
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.PATH.*
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.CIRCLES.*
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.STATUS
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.TRACK
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.ATT.TRAIN_NUMBER
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.TYPE.BOARD_ITEM
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.SELECTED_DEPARTURE

class VladBoardViewFactory {

    static Parent createView(ClientDolphin clientDolphin) {
        def departuresOnBoard = FXCollections.observableArrayList()
        departuresOnBoard.addAll(clientDolphin.findAllPresentationModelsByType(BOARD_ITEM))
        PresentationModel selectedDeparture = clientDolphin[SELECTED_DEPARTURE]

        //create shapes for train paths
        Shape pathBern = createPath(PATH_BERN)
        Shape pathZurich = createPath(PATH_ZURICH)
        Shape pathGenf = createPath(PATH_GENF)
        Shape pathBasel = createPath(PATH_BASEL)
        Shape pathGallen = createPath(PATH_GALLEN)
        Shape pathInterlaken = createPath(PATH_INTERLAKEN)
        Shape pathLausanne = createPath(PATH_LAUSANNE)
        Shape pathLugano = createPath(PATH_LUGANO)

        //create Clock elements
        Text twelve = createText("12", 94, 155)
        Text three = createText("3", 137, 193)
        Text nine = createText("9", 59, 191)
        Text six = createText("6", 97, 230)
        Shape ArrowSec = createArrow(0.3,0.3,Color.RED)
        Shape Arrow = createArrow(0.4,0.4,Color.BLACK)
        Shape ArrowHour = createArrow(0.6,0.6,Color.BLACK)

        //create train as rectangles
        Rectangle rect = createRect(20,10,7,7)
        Rectangle rect2 = createRect(20,10,7,7)
        Rectangle rect3 = createRect(20,10,7,7)

        //create node from image of swiss map

        Image image = new Image(this.getClass().getResourceAsStream("/map.jpg"))
        ImageView iv1 = new ImageView(image)
        iv1.setImage(image)

        //create circles for all the major cities and their corresponding pings
        Circle circleBern = new Circle();        circleBern.setId(BERN)
        Circle circleBasel = new Circle();       circleBasel.setId(BASEL)
        Circle circleZurich = new Circle();      circleZurich.setId(ZURICH)
        Circle circleGenf = new Circle();        circleGenf.setId(GENF)
        Circle circleGallen = new Circle();      circleGallen.setId(GALLEN)
        Circle circleInterlaken = new Circle();  circleInterlaken.setId(INTERLAKEN)
        Circle circleLugano = new Circle();      circleLugano.setId(LUGANO)
        Circle circleLausanne = new Circle();      circleLausanne.setId(LAUSANNE)

        Circle circleOlten = new Circle()
        circleOlten.setRadius(7.0f)
        circleOlten.setFill(Color.BLUE)
        circleOlten.setStroke(Color.BLACK)

        Circle circleBernPing = new Circle()
        Circle circleBaselPing = new Circle()
        Circle circleZurichPing = new Circle()
        Circle circleGenfPing = new Circle()
        Circle circleGallenPing = new Circle()
        Circle circleInterlakenPing = new Circle()
        Circle circleLuganoPing = new Circle()
        Circle circleLausannePing = new Circle()

        //build clock nodes
        Circle circleClock = new Circle()
        circleClock.setCenterX(100)
        circleClock.setCenterY(200)
        circleClock.setFill(null)
        circleClock.setStroke(Color.BLACK)
        circleClock.setStrokeWidth(3)
        circleClock.setRadius(53)
        circleClock.setVisible(false)

        Circle circleDetail = new Circle()
        circleDetail.setCenterX(100)
        circleDetail.setCenterY(200)
        circleDetail.setFill(null)
        circleDetail.setStroke(Color.BLACK)
        circleDetail.setStrokeWidth(5)
        circleDetail.getStrokeDashArray().addAll(2d, 25d)
        circleDetail.setRadius(51)
        circleDetail.setRotate(28)
        circleDetail.setFill(Color.ANTIQUEWHITE)
        circleDetail.setOpacity(0.3)
        circleDetail.setVisible(false)
        circleDetail.setEffect(DropShadowBuilder.create().radius(10).blurType(BlurType.GAUSSIAN).build())

        //create node table
        TableView table = TableViewBuilder.create()
                .items(departuresOnBoard)
                .columns(
                createColumn(DEPARTURE_TIME, "Time"),
                createColumn(TRAIN_NUMBER,   "Train Number"),
                createColumn(DESTINATION,    "Destination"),
                createColumn(STATUS,         "Status"),
                createColumn(TRACK,          "Track"),
        )
                .columnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY)
                .build()

        Pane canvas = new Pane()
        canvas.getChildren().addAll(iv1,
                pathBasel,pathGallen,pathGenf,pathInterlaken,pathBern,pathZurich,pathLugano, pathLausanne,                                                      //paths
                circleBern,circleInterlaken,circleZurich,circleGenf,circleBasel,circleGallen, circleOlten,circleLugano, circleLausanne,                          //cities
                circleBernPing, circleInterlakenPing, circleZurichPing, circleGenfPing, circleGallenPing, circleBaselPing,circleLuganoPing, circleLausannePing, //cityPings
                rect, rect2, rect3,                                                                                                                             //trains
                circleDetail,ArrowHour,Arrow,ArrowSec, circleClock, twelve, nine, six, three)                                                                   //clock

        //initialize the circles and path with a specified color
        putDefault(circleBern,pathBern,circleBasel,pathBasel,circleGallen,pathGallen,circleInterlaken,pathInterlaken,circleGenf,pathGenf,circleZurich, pathZurich,circleLugano,pathLugano,circleLausanne, pathLausanne, circleBernPing, circleBaselPing, circleGallenPing, circleGenfPing, circleInterlakenPing, circleZurichPing, circleLuganoPing, circleLausannePing)

        //relocate circles in the correct position
        circleBernPing.relocate(350,250)
        circleBern.relocate(350, 250)
        circleBasel.relocate(380, 90)
        circleBaselPing.relocate(380, 90)
        circleGallen.relocate(700, 120)
        circleGallenPing.relocate(700, 120)
        circleGenf.relocate(110, 445)
        circleGenfPing.relocate(110, 445)
        circleZurich.relocate(550, 135)
        circleZurichPing.relocate(550, 135)
        circleInterlaken.relocate(430, 315)
        circleInterlakenPing.relocate(430, 315)
        circleLugano.relocate(630, 500)
        circleLuganoPing.relocate(630, 500)
        circleLausanne.relocate(200, 365)
        circleLausannePing.relocate(200, 365)
        circleOlten.relocate(433, 140)

        //create timeline to show selected city more clearly and animate the clock
        DoubleProperty radius = new SimpleDoubleProperty(5.0)
        Timeline pulse = TimelineBuilder.create()
                .autoReverse(true)
                .keyFrames(
                new KeyFrame(
                        new Duration(0.0),
                        new KeyValue(radius, 5.0f)
                    ),
                new KeyFrame(
                        new Duration(1000.0),
                        new KeyValue(radius, 8.0f)
                    )
                ).cycleCount(Timeline.INDEFINITE)
                 .build()

        PathTransition animMin = PathTransitionBuilder.create()
                .duration(Duration.minutes(60))
                .node(Arrow)
                .path(createEllipsePath(145, 290, 45, 45, 0))
                .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                .interpolator(Interpolator.LINEAR)
                .autoReverse(false)
                .cycleCount(Timeline.INDEFINITE)
                .build()

        PathTransition animSec = PathTransitionBuilder.create()
                .duration(Duration.seconds(60))
                .node(ArrowSec)
                .path(createEllipsePath(145, 290, 45, 45, 0))
                .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                .interpolator(Interpolator.LINEAR)
                .autoReverse(false)
                .cycleCount(Timeline.INDEFINITE)
                .build()

        PathTransition animHour = PathTransitionBuilder.create()
                .duration(Duration.hours(12))
                .node(ArrowHour)
                .path(createEllipsePath(145, 290, 45, 45, 0))
                .orientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT)
                .interpolator(Interpolator.LINEAR)
                .autoReverse(false)
                .cycleCount(Timeline.INDEFINITE)
                .build()

        //add listener to table selection and depending on which city is selected, do all animation to that city including sending trains
        table.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            void changed(ObservableValue observableValue, Object t, Object t1) {
                Arrow.setVisible(true)
                ArrowHour.setVisible(true)
                ArrowSec.setVisible(true)
                circleClock.setVisible(true)
                circleDetail.setVisible(true)
                nine.setVisible(true)
                twelve.setVisible(true)
                three.setVisible(true)
                six.setVisible(true)

               //activate clock based on departure time
               def time = t1[DEPARTURE_TIME].value.toString()
               String[] parts = time.split(":")
               animHour.playFrom(Duration.hours(parts[0].toInteger() + (parts[1].toInteger()/60)))
               animMin.playFrom(Duration.minutes(parts[1].toInteger()))
               animSec.playFromStart()

               //Show cities based on the Destination
               switch (t1[DESTINATION].value.toString()) {
                    case circleBern.getId():
                        putDefault(circleBern,pathBern,circleBasel,pathBasel,circleGallen,pathGallen,circleInterlaken,pathInterlaken,circleGenf,pathGenf,circleZurich, pathZurich,circleLugano,pathLugano,circleLausanne, pathLausanne, circleBernPing, circleBaselPing, circleGallenPing, circleGenfPing, circleInterlakenPing, circleZurichPing, circleLuganoPing, circleLausannePing)
                        createPair(circleBern,Color.GREEN,Color.DARKBLUE,pathBern,Color.GREEN, circleBernPing)
                        bindAll(circleBern, pathBern, radius, circleBernPing)
                        sendTrain(rect, rect2, rect3, pathBern, 5, 0.3)
                        pulse.playFromStart()

                        break

                    case circleZurich.getId():
                        putDefault(circleBern,pathBern,circleBasel,pathBasel,circleGallen,pathGallen,circleInterlaken,pathInterlaken,circleGenf,pathGenf,circleZurich, pathZurich,circleLugano,pathLugano,circleLausanne, pathLausanne, circleBernPing, circleBaselPing, circleGallenPing, circleGenfPing, circleInterlakenPing, circleZurichPing, circleLuganoPing, circleLausannePing)
                        createPair(circleZurich,Color.GREEN,Color.DARKBLUE,pathZurich,Color.GREEN, circleZurichPing)
                        bindAll(circleZurich, pathZurich, radius, circleZurichPing)
                        sendTrain(rect, rect2, rect3, pathZurich, 5, 0.3)
                        pulse.playFromStart()
                        break

                    case circleBasel.getId():
                        putDefault(circleBern,pathBern,circleBasel,pathBasel,circleGallen,pathGallen,circleInterlaken,pathInterlaken,circleGenf,pathGenf,circleZurich, pathZurich,circleLugano,pathLugano,circleLausanne, pathLausanne, circleBernPing, circleBaselPing, circleGallenPing, circleGenfPing, circleInterlakenPing, circleZurichPing, circleLuganoPing, circleLausannePing)
                        createPair(circleBasel,Color.GREEN,Color.DARKBLUE,pathBasel,Color.GREEN, circleBaselPing)
                        bindAll(circleBasel, pathBasel, radius, circleBaselPing)
                        sendTrain(rect, rect2, rect3, pathBasel, 4.4, 0.3,-1)
                        pulse.playFromStart()
                        break

                    case circleGallen.getId():
                        putDefault(circleBern,pathBern,circleBasel,pathBasel,circleGallen,pathGallen,circleInterlaken,pathInterlaken,circleGenf,pathGenf,circleZurich, pathZurich,circleLugano,pathLugano,circleLausanne, pathLausanne, circleBernPing, circleBaselPing, circleGallenPing, circleGenfPing, circleInterlakenPing, circleZurichPing, circleLuganoPing, circleLausannePing)
                        createPair(circleGallen,Color.GREEN,Color.DARKBLUE,pathGallen,Color.GREEN, circleGallenPing)
                        pathZurich.setStroke(Color.GREEN)
                        bindAll(circleGallen, pathGallen, radius, circleGallenPing)
                        sendTrain(rect, rect2, rect3, pathGallen, 10, 0.2)
                        pulse.playFromStart()
                        break

                    case circleInterlaken.getId():
                        putDefault(circleBern,pathBern,circleBasel,pathBasel,circleGallen,pathGallen,circleInterlaken,pathInterlaken,circleGenf,pathGenf,circleZurich, pathZurich,circleLugano,pathLugano,circleLausanne, pathLausanne, circleBernPing, circleBaselPing, circleGallenPing, circleGenfPing, circleInterlakenPing, circleZurichPing, circleLuganoPing, circleLausannePing)
                        createPair(circleInterlaken,Color.GREEN,Color.DARKBLUE,pathInterlaken,Color.GREEN, circleInterlakenPing)
                        pathBern.setStroke(Color.GREEN)
                        bindAll(circleInterlaken, pathInterlaken, radius, circleInterlakenPing)
                        sendTrain(rect, rect2, rect3, pathInterlaken, 6.2, 0.2)
                        pulse.playFromStart()
                        break

                    case circleGenf.getId():
                        putDefault(circleBern,pathBern,circleBasel,pathBasel,circleGallen,pathGallen,circleInterlaken,pathInterlaken,circleGenf,pathGenf,circleZurich, pathZurich,circleLugano,pathLugano,circleLausanne, pathLausanne, circleBernPing, circleBaselPing, circleGallenPing, circleGenfPing, circleInterlakenPing, circleZurichPing, circleLuganoPing, circleLausannePing)
                        createPair(circleGenf,Color.GREEN,Color.DARKBLUE,pathGenf,Color.GREEN, circleGenfPing)
                        pathLausanne.setStroke(Color.GREEN)
                        bindAll(circleGenf, pathGenf, radius, circleGenfPing)
                        sendTrain(rect, rect2, rect3, pathGenf, 10, 0.35, -1)
                        pulse.playFromStart()
                        break
                    case circleLugano.getId():
                        putDefault(circleBern,pathBern,circleBasel,pathBasel,circleGallen,pathGallen,circleInterlaken,pathInterlaken,circleGenf,pathGenf,circleZurich, pathZurich,circleLugano,pathLugano,circleLausanne, pathLausanne, circleBernPing, circleBaselPing, circleGallenPing, circleGenfPing, circleInterlakenPing, circleZurichPing, circleLuganoPing, circleLausannePing)
                        createPair(circleLugano,Color.GREEN,Color.DARKBLUE,pathLugano,Color.GREEN, circleLuganoPing)
                        bindAll(circleLugano, pathLugano, radius, circleLuganoPing)
                        sendTrain(rect, rect2, rect3, pathLugano, 10, 0.35)
                        pulse.playFromStart()
                        break
                    case circleLausanne.getId():
                        putDefault(circleBern,pathBern,circleBasel,pathBasel,circleGallen,pathGallen,circleInterlaken,pathInterlaken,circleGenf,pathGenf,circleZurich, pathZurich,circleLugano,pathLugano,circleLausanne, pathLausanne, circleBernPing, circleBaselPing, circleGallenPing, circleGenfPing, circleInterlakenPing, circleZurichPing, circleLuganoPing, circleLausannePing)
                        createPair(circleLausanne,Color.GREEN,Color.DARKBLUE,pathLausanne,Color.GREEN, circleLausannePing)
                        bindAll(circleLausanne, pathLausanne, radius, circleLausannePing)
                        sendTrain(rect, rect2, rect3, pathLausanne, 10, 0.35)
                        pulse.playFromStart()
                        break
               }

            }
        })
    ObservableList<PieChart.Data> pieChartData =
            FXCollections.observableArrayList(
                    new PieChart.Data("Grapefruit",Math.random()),
                    new PieChart.Data("Oranges", Math.random() ),
                    new PieChart.Data("Plums", Math.random()),
                    new PieChart.Data("Pears", Math.random()),
                    new PieChart.Data("Apples", Math.random()))
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Imported Fruits");
        chart.setLabelLineLength(10);
        chart.setLegendSide(Side.LEFT);




        final String zurich = "Zurich";
        final  String Bern = "Bern";
        final  String Genf = "Genf";
        final  String interlaken = "Interlaken";
        final  String Basel = "Basel";

        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc =
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("Country Summary");
        xAxis.setLabel("City");
        yAxis.setLabel("Value");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Trains");
        series1.getData().add(new XYChart.Data(zurich, 7));
        series1.getData().add(new XYChart.Data(Bern, 20148));
        series1.getData().add(new XYChart.Data(Genf, 10000));
        series1.getData().add(new XYChart.Data(interlaken, 35407));
        series1.getData().add(new XYChart.Data(Basel, 12000));


        bc.getData().addAll(series1);

       Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1500),
                new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent event) {
                        for (XYChart.Series<String, Number> serie : bc.getData()) {
                            for (XYChart.Data<String, Number> data : serie.getData()) {
                                data.setYValue(Math.random() * 100);
                            }
                        }
                    }
                }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

     Pane test = new Pane()
     test.getChildren().addAll(chart)
     Pane test2 = new Pane()
     test2.getChildren().addAll(bc)


    //create splitpane and add table to one side and map to the other
    final SplitPane splitPane = SplitPaneBuilder.create()
        .dividerPositions([0.4] as double[])
        .items(
               table,
               canvas,
               //test,
               //test2
        ).build()

        splitPane
    }
    //Unbind and give properties to the circles and path
    static createPair(Circle circle,colorFill, colorStroke,Shape path, strokePath,Circle circlePing) {
        circlePing.setVisible(false)
        path.strokeWidthProperty().unbind()
        path.setStroke(strokePath)
        circle.radiusProperty().unbind()
        circlePing.radiusProperty().unbind()
        circle.setRadius(5.0f)
        circle.setFill(colorFill)
        circle.setStroke(colorStroke)
        circlePing.setRadius(5.0f)
        circlePing.setFill(null)
        circlePing.setStroke(Color.GREEN)


    }

    static SVGPath createPath(String path) {
        return SVGPathBuilder.create()
                .content(path)
                .fill(null)
                .stroke(Color.rgb(191, 0, 47))
                .strokeWidth(5.0)
                .scaleX(1)
                .scaleY(1)
                .build();
    }

    //Animate path animation
    static PathTransition createPathtransition(path, shape, duration, delay, rate = 1.0) {
        final PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(duration));
        pathTransition.setPath(path);
        pathTransition.setNode(shape);
        pathTransition.setRate(rate)
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(Timeline.INDEFINITE);
        pathTransition.setDelay(Duration.seconds(delay))
        pathTransition.setInterpolator(Interpolator.EASE_BOTH)
        pathTransition.setAutoReverse(false);


        pathTransition
    }

    //set default properties to all circles
    static putDefault(circleBern, pathBern, circleBasel, pathBasel, circleGallen, pathGallen, circleInterlaken, pathInterlaken, circleGenf, pathGenf, circleZurich, pathZurich,circleLugano,pathLugano,circleLausanne,pathLausanne, circleBernPing, circleBaselPing, circleGallenPing, circleGenfPing, circleInterlakenPing, circleZurichPing, circleLuganoPing, circleLausannePing){
        createPair(circleBern,Color.RED, Color.BLACK, pathBern, Color.rgb(191, 0, 47), circleBernPing)
        createPair(circleBasel,Color.RED, Color.BLACK, pathBasel, Color.rgb(191, 0, 47), circleBaselPing)
        createPair(circleGallen,Color.RED, Color.BLACK, pathGallen, Color.rgb(191, 0, 47),circleGallenPing)
        createPair(circleInterlaken,Color.RED, Color.BLACK, pathInterlaken, Color.rgb(191, 0, 47), circleInterlakenPing)
        createPair(circleGenf,Color.RED, Color.BLACK, pathGenf, Color.rgb(191, 0, 47), circleGenfPing)
        createPair(circleZurich,Color.RED, Color.BLACK, pathZurich, Color.rgb(191, 0, 47), circleZurichPing)
        createPair(circleLugano,Color.RED, Color.BLACK, pathLugano, Color.rgb(191, 0, 47), circleLuganoPing)
        createPair(circleLausanne,Color.RED, Color.BLACK, pathLausanne, Color.rgb(191, 0, 47), circleLausannePing)
    }

    static createColumn(String inPropertyName, String inTitle) {
        TableColumnBuilder.create()
                .text(inTitle)
                .cellValueFactory({ row -> new ClientAttributeWrapper(row.value[inPropertyName]) } as Callback)
                .build()
    }

    static Rectangle createRect(length, width, h, w){
        Rectangle r = new Rectangle(length,width)
        r.setArcHeight(h)
        r.setArcWidth(w)
        r.setVisible(false)
        r.setStrokeWidth(2.5)
        r.setStroke(Color.RED)
        r.setFill(Color.GHOSTWHITE)
        r.setSmooth(true)
        r.getStrokeDashArray().addAll(20d, 40d)
        r.setStrokeType(StrokeType.INSIDE)
        r
    }

    static Shape createArrow(x, y,Color color){
        Shape Arrow = createPath("m140,80c0,0 9,-15 9,-15c0,0 10,15 10,15c0,0 -19,0 -19,0z")
        Arrow.setFill(color)
        Arrow.setStroke(color)
        Arrow.setScaleX(x)
        Arrow.setScaleY(y)
        Arrow.setVisible(false)
        Arrow
    }

    static Text createText(string, x, y){
        Text text = new Text(string)
        text.setVisible(false)
        text.relocate(x, y)
        text
    }

    static bindAll(circle, path, radius, circlePing){
        circle.radiusProperty().bind(radius)
        path.strokeWidthProperty().bind(radius)
        circlePing.radiusProperty().bind((radius)*4)
        circlePing.setVisible(true)
    }

    static sendTrain(rect, rect2, rect3, path, duration, delay, rate = 1.0){
        rect.setVisible(true)
        rect2.setVisible(true)
        rect3.setVisible(true)
        createPathtransition(path, rect, duration, 0, rate).play()
        createPathtransition(path, rect2, duration, delay, rate).play()
        createPathtransition(path, rect3, duration, delay*2, rate).play()

    }

    static Path createEllipsePath(double centerX, double centerY, double radiusX, double radiusY, double rotate) {
        ArcTo arcTo = new ArcTo();
        arcTo.setX(centerX - radiusX + 1); // to simulate a full 360 degree celcius circle.
        arcTo.setY(centerY - radiusY);
        arcTo.setSweepFlag(true);
        arcTo.setLargeArcFlag(true);
        arcTo.setRadiusX(radiusX);
        arcTo.setRadiusY(radiusY);
        arcTo.setXAxisRotation(rotate);

        Path path = PathBuilder.create()
                .elements(
                new MoveTo(centerX - radiusX, centerY - radiusY),
                arcTo,
                new ClosePath()) // close 1 px gap.
                .build();
        path.setStroke(Color.DODGERBLUE);
        path.setRotate(180)
        return path;
    }
}
