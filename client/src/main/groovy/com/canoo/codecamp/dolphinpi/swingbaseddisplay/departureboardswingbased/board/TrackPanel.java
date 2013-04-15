package com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board;

import javax.swing.JPanel;

import com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.splitflap.GlobalTimer;
import com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.splitflap.SplitFlap;

public class TrackPanel extends JPanel {
	private SplitFlap trackLeft;
	private SplitFlap trackRight;
	private int trackL;
	private int trackR;

	public TrackPanel() {
		initComponents();
		trackL = 0;
		trackR = 0;
		trackLeft.setSelection(SplitFlap.NUMERIC);
		trackRight.setSelection(SplitFlap.NUMERIC);
		init();
	}

	private void init() {
		GlobalTimer.INSTANCE.startTimer();
	}

	public int getHourLeft() {
		return trackL;
	}

	public void setTrackLeft(final int TRACK_LEFT) {
		trackL = clamp(0, 9, trackLeft.getSelectedSet().contains(Integer.toString(TRACK_LEFT)) ? TRACK_LEFT : 0);
		flipIt();
	}

	public int getTrackRight() {
		return trackR;
	}

	public void setTrackRight(final int TRACK_RIGHT) {
		trackR = clamp(0, 0, trackRight.getSelectedSet().contains(Integer.toString(TRACK_RIGHT)) ? TRACK_RIGHT : 0);
		flipIt();
	}

	public void setTrack(final int TRACK_LEFT, final int TRACK_RIGHT) {
		trackL = clamp(0, 9, trackLeft.getSelectedSet().contains(Integer.toString(TRACK_LEFT)) ? TRACK_LEFT : 0);
		trackR = clamp(0, 9, trackRight.getSelectedSet().contains(Integer.toString(TRACK_RIGHT)) ? TRACK_RIGHT : 0);
		flipIt();
	}

	public void setTrack(final int TRACK) {
		int track = clamp(0, 99, TRACK);
		if (track < 10) {
			trackL = 0;
			trackR = track;
		} else {
			trackL = Integer.parseInt(Integer.toString(track).substring(0, 1));
			trackR = Integer.parseInt(Integer.toString(track).substring(1, 2));
		}
		flipIt();
	}

	public void setTrack(final String TRACK) {
		if (TRACK.equals("  ") || TRACK.equals(" ") || TRACK.isEmpty()) {
			trackLeft.setText(" ");
			trackRight.setText(" ");
		} else {
			try {
				setTrack(Integer.parseInt(TRACK));
			} catch (NumberFormatException e) {
				setTrack(0);
			}
		}
	}

	public void clearPanel() {
		trackLeft.setText(" ");
		trackRight.setText(" ");
	}

	private void flipIt() {
		trackLeft.setText(Integer.toString(trackL));
		trackRight.setText(Integer.toString(trackR));
	}

	private int clamp(final int MIN, final int MAX, final int VALUE) {
		return VALUE < MIN ? MIN : (VALUE > MAX ? MAX : VALUE);
	}

	private void initComponents() {

		trackLeft = new SplitFlap();
		trackRight = new SplitFlap();

		setBackground(new java.awt.Color(51, 51, 51));
		setPreferredSize(new java.awt.Dimension(90, 66));
		setSize(new java.awt.Dimension(90, 66));

		trackLeft.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout trackLeftLayout = new org.jdesktop.layout.GroupLayout(trackLeft);
		trackLeft.setLayout(trackLeftLayout);
		trackLeftLayout.setHorizontalGroup(
				trackLeftLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		trackLeftLayout.setVerticalGroup(
				trackLeftLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 0, Short.MAX_VALUE)
		);

		trackRight.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout trackRightLayout = new org.jdesktop.layout.GroupLayout(trackRight);
		trackRight.setLayout(trackRightLayout);
		trackRightLayout.setHorizontalGroup(
				trackRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		trackRightLayout.setVerticalGroup(
				trackRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 54, Short.MAX_VALUE)
		);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(layout.createSequentialGroup()
								.add(0, 0, 0)
								.add(trackLeft, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(trackRight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(layout.createSequentialGroup()
								.add(0, 0, 0)
								.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
										.add(trackRight, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.add(trackLeft, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
		);
	}
}
