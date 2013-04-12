package com.canoo.codecamp.dolphinpi

import eu.hansolo.fx.departureboard.DepartureBoard
import eu.hansolo.fx.departureboard.HeaderRow
import eu.hansolo.fx.departureboard.Row
import javafx.geometry.HPos
import javafx.geometry.Insets
import javafx.geometry.VPos
import javafx.scene.Group
import javafx.scene.control.TableColumnBuilder
import javafx.scene.control.TableView
import javafx.scene.control.TableViewBuilder
import javafx.scene.layout.GridPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.scene.text.Text
import javafx.scene.text.TextAlignment
import javafx.util.Callback
import jfxtras.labs.scene.control.gauge.Clock
import org.opendolphin.core.client.ClientAttributeWrapper

import static com.canoo.codecamp.dolphinpi.ApplicationConstants.*

class DepartureBoardViewFactory {

	static javafx.scene.Node newView(DeparturesBoardApplicationModel inModel) {

		TableViewBuilder.create()
			.items(inModel.departuresOnBoard)
			.columns(
			newTableColumn(ATT_DEPARTURE_TIME, "Uhrzeit"),
			newTableColumn(ATT_TRAIN_NUMBER, "Fahrt"),
			newTableColumn(ATT_DESTINATION, "Richtung"),
			newTableColumn(ATT_STATUS, "Status"),
			newTableColumn(ATT_TRACK, "Gleis"),
		).columnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY).build()
	}


	static newTableColumn(String inPropertyName, String inTitle) {
		TableColumnBuilder.create()
			.text(inTitle)
			.cellValueFactory({ row -> new ClientAttributeWrapper(row.value[inPropertyName]) } as Callback)
			.build()
	}


	static javafx.scene.Node newGerritView(DeparturesBoardApplicationModel inModel) {
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

//		Group airplaneImage = getDepartureImage(80, Color.WHITE);

		GridPane headerPane = new GridPane();
		headerPane.add(clock, 1, 1);
		GridPane.setMargin(clock, new Insets(20, 50, 30, 10));
//		headerPane.add(airplaneImage, 2, 1);
//		GridPane.setMargin(airplaneImage, new Insets(5, 25, 10, 5));
		headerPane.add(header, 3, 1);

		HeaderRow labels = new HeaderRow();

		grid.add(headerPane, 1, 1);

		grid.add(labels.getPane(), 1, 2);

		DepartureBoard departureBoard = new DepartureBoard();

		departureBoard.addRow(new Row("12", "05", "ZURICH", "LH1969", "A01"));
		departureBoard.addRow(new Row("12", "05", "ZURICH", "LH1969", "A01"));
		departureBoard.addRow(new Row("12", "05", "ZURICH", "LH1969", "A01"));
		departureBoard.addRow(new Row("12", "05", "ZURICH", "LH1969", "A01"));
		departureBoard.addRow(new Row("12", "05", "ZURICH", "LH1969", "A01"));

		grid.add(departureBoard.getPane(), 1, 3);
		GridPane.setHalignment(departureBoard.getPane(), HPos.CENTER);

		return grid
	}

}
