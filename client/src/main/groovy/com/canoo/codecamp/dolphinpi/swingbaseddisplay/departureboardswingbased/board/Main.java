package com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class Main extends JFrame {
	DepartureBoard board;
	ControlPanel controlPanel;

	public Main() {
		board = new DepartureBoard();
		controlPanel = new ControlPanel();
		ControlPanel.ControlEventListener listener = new ControlPanel.ControlEventListener() {
			public void controlEventPerformed(ControlPanel.ControlEvent event) {
				board.getRows().get(event.getRowIndex()).setBlinking(event.isBlink());
				board.getRows().get(event.getRowIndex()).setHour(event.getHour());
				board.getRows().get(event.getRowIndex()).setMinute(event.getMinute());
				board.getRows().get(event.getRowIndex()).setDestination(event.getDestination());
				board.getRows().get(event.getRowIndex()).setTrack(event.getTrack());
				board.getRows().get(event.getRowIndex()).setInfo(event.getInfo());
			}
		};
		controlPanel.addControlEventListener(listener);

		initComponents();
	}

	private void initComponents() {
		setTitle("Swing Departure Board");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GridBagLayout layout = new GridBagLayout();
		getContentPane().setLayout(layout);

		GridBagConstraints gridBagConstraints = new GridBagConstraints();

		gridBagConstraints.gridy = 0;
		gridBagConstraints.ipady = 10;
		getContentPane().add(controlPanel, gridBagConstraints);

		gridBagConstraints.gridy = 1;
		gridBagConstraints.ipady = 10;
		getContentPane().add(board, gridBagConstraints);

		pack();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Main app = new Main();
				app.setSize(870, 500);
				app.setBackground(Color.BLACK);
				app.setLocationRelativeTo(null);
				app.setVisible(true);
			}
		});
	}
}
