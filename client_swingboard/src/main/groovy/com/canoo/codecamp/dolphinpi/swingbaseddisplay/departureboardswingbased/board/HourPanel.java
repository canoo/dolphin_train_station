package com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board;

import javax.swing.JPanel;

import com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.splitflap.GlobalTimer;
import com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.splitflap.SplitFlap;

public class HourPanel extends JPanel {
	private SplitFlap hourLeft;
	private SplitFlap hourRight;
	private int hourL;
	private int hourR;

	public HourPanel() {
		initComponents();
		hourL = 0;
		hourR = 0;
		hourLeft.setSelection(SplitFlap.NUMERIC);
		hourRight.setSelection(SplitFlap.NUMERIC);
		init();
	}

	private void init() {
		GlobalTimer.INSTANCE.startTimer();
	}

	public int getHourLeft() {
		return hourL;
	}

	public void setHourLeft(final int HOUR_LEFT) {
		hourL = clamp(0, 2, hourLeft.getSelectedSet().contains(Integer.toString(HOUR_LEFT)) ? HOUR_LEFT : 0);
		flipIt();
	}

	public int getHourRight() {
		return hourR;
	}

	public void setHourRight(final int HOUR_RIGHT) {
		hourR = clamp(0, 9, hourRight.getSelectedSet().contains(Integer.toString(HOUR_RIGHT)) ? HOUR_RIGHT : 0);
		flipIt();
	}

	public void setHour(final int HOUR_LEFT, final int HOUR_RIGHT) {
		hourL = clamp(0, 2, hourLeft.getSelectedSet().contains(Integer.toString(HOUR_LEFT)) ? HOUR_LEFT : 0);
		hourR = clamp(0, 9, hourRight.getSelectedSet().contains(Integer.toString(HOUR_RIGHT)) ? HOUR_RIGHT : 0);
		flipIt();
	}

	public void setHour(final int HOUR) {
		int hour = clamp(0, 23, HOUR);
		if (hour < 10) {
			hourL = 0;
			hourR = hour;
		} else {
			hourL = Integer.parseInt(Integer.toString(hour).substring(0, 1));
			hourR = Integer.parseInt(Integer.toString(hour).substring(1, 2));
		}
		flipIt();
	}

	public void setHour(final String HOUR) {
		if (HOUR.equals("  ") || HOUR.equals(" ") || HOUR.isEmpty()) {
			hourLeft.setText(" ");
			hourRight.setText(" ");
		} else {
			try {
				setHour(Integer.parseInt(HOUR));
			} catch (NumberFormatException e) {
				setHour(0);
			}
		}
	}

	public void clearPanel() {
		hourLeft.setText(" ");
		hourRight.setText(" ");
	}

	private void flipIt() {
		hourLeft.setText(Integer.toString(hourL));
		hourRight.setText(Integer.toString(hourR));
	}

	private int clamp(final int MIN, final int MAX, final int VALUE) {
		return VALUE < MIN ? MIN : (VALUE > MAX ? MAX : VALUE);
	}

	private void initComponents() {

		hourLeft = new SplitFlap();
		hourRight = new SplitFlap();

		setBackground(new java.awt.Color(51, 51, 51));
		setPreferredSize(new java.awt.Dimension(90, 66));
		setSize(new java.awt.Dimension(90, 66));

		hourLeft.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout hourLeftLayout = new org.jdesktop.layout.GroupLayout(hourLeft);
		hourLeft.setLayout(hourLeftLayout);
		hourLeftLayout.setHorizontalGroup(
				hourLeftLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		hourLeftLayout.setVerticalGroup(
				hourLeftLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 0, Short.MAX_VALUE)
		);

		hourRight.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout hourRightLayout = new org.jdesktop.layout.GroupLayout(hourRight);
		hourRight.setLayout(hourRightLayout);
		hourRightLayout.setHorizontalGroup(
				hourRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		hourRightLayout.setVerticalGroup(
				hourRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 54, Short.MAX_VALUE)
		);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(layout.createSequentialGroup()
								.add(0, 0, 0)
								.add(hourLeft, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(hourRight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(layout.createSequentialGroup()
								.add(0, 0, 0)
								.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
										.add(hourRight, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.add(hourLeft, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
		);
	}
}
