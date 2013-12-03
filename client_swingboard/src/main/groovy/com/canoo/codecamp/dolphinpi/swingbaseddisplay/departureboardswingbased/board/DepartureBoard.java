package com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

public class DepartureBoard extends JComponent {
	private static final int NO_OF_ROWS = 5;
	private List<Row> listOfRows;

	public DepartureBoard() {
		listOfRows = new ArrayList<Row>(NO_OF_ROWS);
		for (int i = 0; i < NO_OF_ROWS; i++) {
			listOfRows.add(new Row());
		}
		initComponents();
	}

	private void initComponents() {
		setOpaque(true);
		setBackground(Color.BLACK);

		removeAll();

		GridBagLayout layout = new GridBagLayout();
		setLayout(layout);

		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		HeaderPanel header = new HeaderPanel();
		gridBagConstraints.gridy = 0;
		gridBagConstraints.ipady = 10;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(header, gridBagConstraints);

		gridBagConstraints.fill = GridBagConstraints.NONE;
		for (int i = 0; i < NO_OF_ROWS; i++) {
			gridBagConstraints.gridy = i + 1;
			gridBagConstraints.ipady = 5;
			listOfRows.get(i).setBackground(Color.BLACK);
			add(listOfRows.get(i), gridBagConstraints);
		}

		setSize(870, (NO_OF_ROWS * (54 + 5) + 30 + 32));
		setPreferredSize(new Dimension(870, (NO_OF_ROWS * (54 + 5) + 30 + 32)));
	}

	public List<Row> getRows() {
		return listOfRows;
	}

	public void setRows(final List<Row> LIST_OF_ROWS) {
		listOfRows.addAll(LIST_OF_ROWS);
		initComponents();
		repaint();
	}
}
