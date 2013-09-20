package com.canoo.codecamp.dolphinpi.admin

import javafx.animation.TranslateTransitionBuilder
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.EventHandler
import javafx.geometry.Side
import javafx.scene.Parent
import javafx.scene.chart.Axis
import javafx.scene.chart.BarChart
import javafx.scene.chart.BubbleChart
import javafx.scene.chart.CategoryAxis
import javafx.scene.chart.LineChart
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.PieChart
import javafx.scene.chart.ValueAxis
import javafx.scene.chart.XYChart
import javafx.scene.control.Label
import javafx.scene.control.Slider
import javafx.scene.control.SplitPane
import javafx.scene.control.SplitPaneBuilder
import javafx.scene.control.Tab
import javafx.scene.control.TabPane
import javafx.scene.control.TableColumn
import javafx.scene.control.TableColumnBuilder
import javafx.scene.control.TableView
import javafx.scene.control.TableViewBuilder
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.input.MouseEvent
import javafx.scene.layout.FlowPane
import javafx.scene.layout.Pane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.util.Callback
import javafx.util.Duration
import org.opendolphin.core.ModelStoreEvent
import org.opendolphin.core.PresentationModel
import org.opendolphin.core.Tag
import org.opendolphin.core.client.ClientAttributeWrapper
import org.opendolphin.core.client.ClientDolphin
import org.opendolphin.core.client.ClientPresentationModel

import java.text.DateFormat
import java.text.SimpleDateFormat

import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DEPARTURE_TIME
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.DESTINATION
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.STATUS
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.STOPOVERS
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.TRACK
import static com.canoo.codecamp.dolphinpi.DepartureConstants.ATT.TRAIN_NUMBER
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.EMPTY_DEPARTURE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.SPECIAL_ID.SELECTED_DEPARTURE
import static com.canoo.codecamp.dolphinpi.DepartureConstants.TYPE.DEPARTURE
import static com.canoo.codecamp.dolphinpi.BoardItemConstants.TYPE.BOARD_ITEM
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.ATT.SEARCH_STRING
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.ATT.SELECTED_DEPARTURE_ID
import static com.canoo.codecamp.dolphinpi.PresentationStateConstants.TYPE.PRESENTATION_STATE

import static com.canoo.codecamp.dolphinpi.admin.AdminApplication.bindAttribute
import static org.opendolphin.binding.JavaFxUtil.cellEdit
import static org.opendolphin.binding.JavaFxUtil.value

class MasterViewFactory {
    static Parent createMasterView(ClientDolphin clientDolphin) {
        ObservableList<ClientPresentationModel> data = FXCollections.observableArrayList()
        PresentationModel applicationState = clientDolphin[PRESENTATION_STATE]
        PresentationModel selectedDeparture = clientDolphin[SELECTED_DEPARTURE]


        TableView table = TableViewBuilder.create()
                .items(data)
                .columns(
                createColumn(selectedDeparture, DEPARTURE_TIME),
                createColumn(selectedDeparture, TRAIN_NUMBER),
                createColumn(selectedDeparture, DESTINATION),
                createColumn(selectedDeparture, STATUS, false),
                createColumn(selectedDeparture, TRACK),
        )
                .columnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY)
                .editable(true)
                .build()

        def selectedPMId = applicationState[SELECTED_DEPARTURE_ID]
        def selectedPMStops = selectedDeparture[STOPOVERS]

        boolean ignoreSelectionChange = false

        // on selection change update the selectedDepartureId
        table.selectionModel.selectedItemProperty().addListener({ o, oldVal, selectedPM ->
            if (ignoreSelectionChange) return
            selectedPMId.value = selectedPM == null ? EMPTY_DEPARTURE : selectedPM.id
        } as ChangeListener)

        // change table selection whenever the selectedDepartureId changes
        bindAttribute(selectedPMId, { evt ->
            final pmId = evt.newValue
            if (pmId == EMPTY_DEPARTURE) {
                table.getSelectionModel().clearSelection()
            } else {
                table.getSelectionModel().select(clientDolphin[pmId])
                //table.scrollTo(table.getSelectionModel().getSelectedIndex())

            }
        })


        // if the searchString changes, table needs to be filtered
        bindAttribute(applicationState[SEARCH_STRING], { evt ->
            // data.setAll will clear table selection, don't want to show this selection change
            ignoreSelectionChange = true
            data.setAll(Util.allMatchingDepartures(clientDolphin, evt.newValue))
            table.getSelectionModel().select(0)
            ignoreSelectionChange = false
        })



        clientDolphin.addModelStoreListener(DEPARTURE, { ModelStoreEvent evt ->
            if (evt.type == ModelStoreEvent.Type.ADDED) {
                data << evt.presentationModel
            } else {
                data.remove(evt.presentationModel)
            }
        })



        XYChart.Series series = new XYChart.Series()
        series.setName("Departures")

        List<PresentationModel> departurelist = clientDolphin.findAllPresentationModelsByType(DEPARTURE)
        Map<String, Integer> map = new HashMap<>()
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList()
        int five = 0, ten = 0, fifteen = 0, twenty = 0, more = 0

        departurelist.each { departure ->
            if (!map.containsKey(departure[DESTINATION].value.toString())) {
                map.put(departure[DESTINATION].value.toString(), 1)
            } else {
                Integer i = map.get(departure[DESTINATION].value.toString())
                i++
                map.put(departure[DESTINATION].value.toString(), i)
            }
        }

        map.each { K, V ->
            pieChartData.add(new PieChart.Data(K, V))
            series.getData().add(new XYChart.Data(K, V))
        }

        departurelist.each {
            String[] time = it[DEPARTURE_TIME].value.toString().split(":")
            switch (time[0].toInteger()) {
                case (0..5):
                    five++; break
                case (6..10):
                    ten++; break
                case (11..15):
                    fifteen++; break
                case (16..20):
                    twenty++; break
                case (21..23):
                    more++; break

            }
        }
        XYChart.Series series2 = new XYChart.Series()
        series2.setName("Times")
        series2.getData().add(new XYChart.Data("At Night", five))
        series2.getData().add(new XYChart.Data("Morning", ten))
        series2.getData().add(new XYChart.Data("Midday", fifteen))
        series2.getData().add(new XYChart.Data("Afternoon", twenty))
        series2.getData().add(new XYChart.Data("Evening", more))

        final PieChart chart = new PieChart(pieChartData)
        chart.setTitle("Destinations")
        chart.setLabelLineLength(10)
        chart.setLegendVisible(false)

        XYChart.Series series1 = new XYChart.Series()
        series1.setName("Duration")
        XYChart.Series series3 = new XYChart.Series()
        series1.setName("Duration")

        bindAttribute(applicationState[SEARCH_STRING], { evt ->
            // data.setAll will clear table selection, don't want to show this selection change
            ignoreSelectionChange = true
            data.setAll(Util.allMatchingDepartures(clientDolphin, evt.newValue))
            table.getSelectionModel().select(0)
            ignoreSelectionChange = false
        })


        bindAttribute(selectedDeparture[STOPOVERS], { evt ->
            try{
                series1.getData().clear()
                series3.getData().clear()
            String[] infos =  evt.newValue.toString().split(" - ")
            Date startDate

            infos.eachWithIndex { String cityAndTime, int i ->
                String city = cityAndTime.substring(0,cityAndTime.lastIndexOf(' '))
                String time = cityAndTime.substring(cityAndTime.lastIndexOf(' ') + 1)
                if (i == 0) {
                    startDate = new SimpleDateFormat('HH:mm').parse(time)
                } else {
                    println ((new SimpleDateFormat('HH:mm').parse(time).time - startDate.time)/1000/60)

                    series1.getData().add(new XYChart.Data(city, (new SimpleDateFormat('HH:mm').parse(time).time - startDate.time)/1000/60))
                    series3.getData().add(new XYChart.Data(city, (new SimpleDateFormat('HH:mm').parse(time).time - startDate.time)/1000/60))
                }
            } }catch(Exception){}

        })

//        table.selectionModel.selectedItemProperty().addListener({ o, oldVal, selectedPM ->
//            if (ignoreSelectionChange) return
//            series1.getData().clear()
//            series3.getData().clear()
//            String[] infos = selectedPM[STOPOVERS].value.toString().split(" - ")
//            Date startDate
//            infos.eachWithIndex { String cityAndTime, int i ->
//                String city = cityAndTime.substring(0,cityAndTime.lastIndexOf(' '))
//                String time = cityAndTime.substring(cityAndTime.lastIndexOf(' ') + 1)
//                if (i == 0) {
//                    startDate = new SimpleDateFormat('HH:mm').parse(time)
//                } else {
//                    println ((new SimpleDateFormat('HH:mm').parse(time).time - startDate.time)/1000/60)
//
//                    series1.getData().add(new XYChart.Data(city, (new SimpleDateFormat('HH:mm').parse(time).time - startDate.time)/1000/60))
//                    series3.getData().add(new XYChart.Data(city, (new SimpleDateFormat('HH:mm').parse(time).time - startDate.time)/1000/60))
//                }
//            }
//        } as ChangeListener)

        final NumberAxis yLineAxis = new NumberAxis()
        final CategoryAxis xCLineAxis = new CategoryAxis()
        final LineChart<String, Number> lineChart =
            new LineChart<String, Number>(xCLineAxis, yLineAxis)
        xCLineAxis.setLabel("Hours")
        yLineAxis.setLabel("Value")
        lineChart.setTitle("Departure Times")
        lineChart.setHorizontalGridLinesVisible(true)
        lineChart.getData().add(series2)

        final CategoryAxis xAxis = new CategoryAxis()
        final NumberAxis yAxis = new NumberAxis()
        final BarChart<String, Number> bc =
            new BarChart<String, Number>(xAxis, yAxis)
        bc.setTitle("Time to Destination")
        bc.setBarGap(10.0)
        xAxis.setLabel("City")
        yAxis.setLabel("Minutes")
        bc.getData().add(series3)

        final NumberAxis xBubbleAxis = new NumberAxis()
        final NumberAxis yBubbleAxis = new NumberAxis()
        final BubbleChart<Number, Number> blc = new
        BubbleChart<Number,Number>(xBubbleAxis,yBubbleAxis)
        xBubbleAxis.setLabel("Time")
        xBubbleAxis.setTickUnit(1.0)
        xBubbleAxis.setMaxSize(24,24)
        xBubbleAxis.setTickLength(10)
        yBubbleAxis.setLabel("Departures")
        blc.setTitle("Departures in a day")

        //blc.getData().add(series2)

        TabPane tabPane = new TabPane()
        Tab tab = new Tab()
        tab.setText("Time to Destination")
        tab.setContent(bc)
        Tab tab2 = new Tab()
        tab2.setText("Destinations")
        tab2.setContent(chart)
        Tab tab4 = new Tab()
        tab4.setText("Departues")
        tab4.setContent(blc)
        Tab tab3 = new Tab()
        tab3.setText("Departure Times")
        tab3.setContent(lineChart)
        tabPane.getTabs().addAll(tab2, tab3, tab)




        addMouseListeners(chart)

        StackPane test = new StackPane()
        test.getChildren().addAll(bc)

        StackPane test2 = new StackPane()
        test2.getChildren().addAll(chart)

        final SplitPane splitPane = SplitPaneBuilder.create()
                .dividerPositions([0.66] as double[])
                .items(
                tabPane,
                table
        ).build()

        return splitPane
    }

    static TableColumn createColumn(ClientPresentationModel selectedDeparture, String inPropertyName, boolean editable = true) {
        TableColumn col = TableColumnBuilder.create()
                .cellFactory(TextFieldTableCell.forTableColumn())
                .cellValueFactory({ row -> new ClientAttributeWrapper(row.value[inPropertyName]) } as Callback)
                .onEditCommit(cellEdit(inPropertyName, { it }) as EventHandler)
                .editable(editable)
                .build()

        bindAttribute selectedDeparture.getAt(inPropertyName, Tag.LABEL), { evt -> col.setText(evt.newValue) }

        col
    }
    static void addMouseListeners(PieChart pieChart) {
        for( PieChart.Data data : pieChart.getData()) {
            data.getNode().setOnMouseEntered(new MouseHoverAnimation(data, pieChart))
            data.getNode().setOnMouseExited(new MouseExitAnimation())
        }
    }
    private static class MouseHoverAnimation implements EventHandler<MouseEvent> {
        private static final Duration ANIMATION_DURATION = new Duration(500)
        private static final double   ANIMATION_DISTANCE = 0.15
        private double   cos
        private double   sin
        private PieChart chart

        public MouseHoverAnimation(final PieChart.Data DATA, final PieChart CHART) {
            chart        = CHART
            double start = 0
            double angle = calcAngle(DATA)
            for( PieChart.Data data : CHART.getData() ) {
                if( data.equals(DATA) ) {
                    break
                }
                start += calcAngle(data)
            }
            cos = Math.cos(Math.toRadians(-start - angle / 2))
            sin = Math.sin(Math.toRadians(-start - angle / 2))
        }

        @Override public void handle(MouseEvent event) {
            javafx.scene.Node node = (javafx.scene.Node) event.getSource()

            double minX = Double.MAX_VALUE
            double maxX = -1 * Double.MAX_VALUE

            for( PieChart.Data data : chart.getData() ) {
                minX = Math.min(minX, data.getNode().getBoundsInParent().getMinX())
                maxX = Math.max(maxX, data.getNode().getBoundsInParent().getMaxX())
            }

            double radius = maxX - minX
            TranslateTransitionBuilder.create()
                    .toX((radius * ANIMATION_DISTANCE) * cos)
                    .toY(-(radius * ANIMATION_DISTANCE) * sin)
                    .duration(ANIMATION_DURATION)
                    .rate(2)
                    .node(node)
                    .build().play()

        }

        private static double calcAngle(PieChart.Data d) {
            double total = 0
            for( PieChart.Data tmp : d.getChart().getData() ) {
                total += tmp.getPieValue()
            }

            return 360 * (d.getPieValue() / total)
        }
    }

    private static class MouseExitAnimation implements EventHandler<MouseEvent> {
        @Override public void handle(MouseEvent event) {
            TranslateTransitionBuilder.create()
                    .toX(0)
                    .toY(0)
                    .duration(new Duration(500))
                    .node((javafx.scene.Node) event.getSource())
                    .build().play()
        }
    }
}
