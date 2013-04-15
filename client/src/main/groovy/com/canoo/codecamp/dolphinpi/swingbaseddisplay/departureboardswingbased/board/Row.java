package com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class Row extends JPanel {
	private LedPanel ledPanel;
	private JPanel ledSpacer;
	private HourPanel hourPanel;
	private JPanel hourSpacer;
	private MinutePanel minutePanel;
	private JPanel minuteSpacer;
	private DestinationPanel destinationPanel;
	private JPanel destinationSpacer;
	private TrackPanel trackPanel;
	private JPanel trackSpacer;
	//private InfoPanel        infoPanel;

	public Row() {
		initComponents();
	}

	public void setBlinking(final boolean BLINKING) {
		ledPanel.setBlinking(BLINKING);
	}

	public void setHour(final int HOUR_LEFT, final int HOUR_RIGHT) {
		hourPanel.setHour(HOUR_LEFT, HOUR_RIGHT);
	}

	public void setHour(final int HOUR) {
		hourPanel.setHour(HOUR);
	}

	public void setHour(final String HOUR) {
		hourPanel.setHour(HOUR);
	}

	public void setMinute(final int MINUTE_LEFT, final int MINUTE_RIGHT) {
		minutePanel.setMinute(MINUTE_LEFT, MINUTE_RIGHT);
	}

	public void setMinute(final int MINUTE) {
		minutePanel.setMinute(MINUTE);
	}

	public void setMinute(final String MINUTE) {
		minutePanel.setMinute(MINUTE);
	}

	public void setDestination(final String DESTINATION) {
		destinationPanel.setDestination(DESTINATION);
	}

	public void setTrack(final int TRACK_LEFT, final int TRACK_RIGHT) {
		trackPanel.setTrack(TRACK_LEFT, TRACK_RIGHT);
	}

	public void setTrack(final int TRACK) {
		trackPanel.setTrack(TRACK);
	}

	public void setTrack(final String TRACK) {
		trackPanel.setTrack(TRACK);
	}

	public void setInfo(final String INFO) {
		//  infoPanel.setInfo(INFO);
	}

	private void initComponents() {
		ledPanel = new LedPanel();
		ledSpacer = new JPanel();
		hourPanel = new HourPanel();
		hourSpacer = new JPanel();
		minutePanel = new MinutePanel();
		minuteSpacer = new JPanel();
		destinationPanel = new DestinationPanel();
		destinationSpacer = new JPanel();
		trackPanel = new TrackPanel();
		trackSpacer = new JPanel();
		//infoPanel         = new InfoPanel();

		setLayout(new GridBagLayout());

		ledPanel.setBackground(new Color(0, 255, 0));
		ledPanel.setMinimumSize(new Dimension(20, 54));
		ledPanel.setPreferredSize(new Dimension(20, 54));
		add(ledPanel, new GridBagConstraints());

		ledSpacer.setBackground(Color.BLACK);
		ledSpacer.setMinimumSize(new Dimension(5, 54));
		ledSpacer.setPreferredSize(new Dimension(5, 54));
		add(ledSpacer, new GridBagConstraints());

		hourPanel.setBackground(Color.BLACK);
		hourPanel.setMinimumSize(new Dimension(72, 54));
		hourPanel.setPreferredSize(new Dimension(72, 54));
		add(hourPanel, new GridBagConstraints());

		hourSpacer.setBackground(Color.BLACK);
		hourSpacer.setMinimumSize(new Dimension(5, 54));
		hourSpacer.setPreferredSize(new Dimension(5, 54));
		add(hourSpacer, new GridBagConstraints());

		minutePanel.setBackground(Color.BLACK);
		minutePanel.setMinimumSize(new Dimension(60, 54));
		minutePanel.setPreferredSize(new Dimension(60, 54));
		add(minutePanel, new GridBagConstraints());

		minuteSpacer.setBackground(Color.BLACK);
		minuteSpacer.setMinimumSize(new Dimension(20, 54));
		minuteSpacer.setPreferredSize(new Dimension(20, 54));
		add(minuteSpacer, new GridBagConstraints());

		destinationPanel.setBackground(new Color(255, 153, 0));
		destinationPanel.setMinimumSize(new Dimension(576, 54));
		destinationPanel.setPreferredSize(new Dimension(576, 54));
		add(destinationPanel, new GridBagConstraints());

		destinationSpacer.setBackground(Color.BLACK);
		destinationSpacer.setMinimumSize(new Dimension(20, 54));
		destinationSpacer.setPreferredSize(new Dimension(20, 54));
		add(destinationSpacer, new GridBagConstraints());

		trackPanel.setBackground(Color.BLACK);
		trackPanel.setMinimumSize(new Dimension(72, 54));
		trackPanel.setPreferredSize(new Dimension(72, 54));
		add(trackPanel, new GridBagConstraints());

		trackSpacer.setBackground(Color.BLACK);
		trackSpacer.setMinimumSize(new Dimension(20, 54));
		trackSpacer.setPreferredSize(new Dimension(20, 54));
		add(trackSpacer, new GridBagConstraints());

//        infoPanel.setBackground(Color.BLACK);
//        infoPanel.setMinimumSize(new Dimension(360, 54));
//        infoPanel.setPreferredSize(new Dimension(360, 54));
//        add(infoPanel, new GridBagConstraints());
	}
}
