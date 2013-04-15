package com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board;

import javax.swing.JPanel;

import com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.splitflap.GlobalTimer;
import com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.splitflap.SplitFlap;

public class MinutePanel extends JPanel {
	private SplitFlap minLeft;
	private SplitFlap minRight;
	private int minL;
	private int minR;

	public MinutePanel() {
		initComponents();
		minL = 0;
		minR = 0;
		minLeft.setSelection(SplitFlap.NUMERIC);
		minRight.setSelection(SplitFlap.NUMERIC);
		init();
	}

	private void init() {
		GlobalTimer.INSTANCE.startTimer();
	}

	public int getMinuteLeft() {
		return minL;
	}

	public void setMinuteLeft(final int MIN_LEFT) {
		minL = clamp(0, 5, minLeft.getSelectedSet().contains(Integer.toString(MIN_LEFT)) ? MIN_LEFT : 0);
		flipIt();
	}

	public int getMinuteRight() {
		return minR;
	}

	public void setMinuteRight(final int MIN_RIGHT) {
		minR = clamp(0, 9, minRight.getSelectedSet().contains(Integer.toString(MIN_RIGHT)) ? MIN_RIGHT : 0);
		flipIt();
	}

	public void setMinute(final int MIN_LEFT, final int MIN_RIGHT) {
		minL = clamp(0, 5, minLeft.getSelectedSet().contains(Integer.toString(MIN_LEFT)) ? MIN_LEFT : 0);
		minR = clamp(0, 9, minRight.getSelectedSet().contains(Integer.toString(MIN_RIGHT)) ? MIN_RIGHT : 0);
		flipIt();
	}

	public void setMinute(final int MINUTE) {
		int minute = clamp(0, 59, MINUTE);
		if (minute < 10) {
			minL = 0;
			minR = minute;
		} else {
			minL = Integer.parseInt(Integer.toString(minute).substring(0, 1));
			minR = Integer.parseInt(Integer.toString(minute).substring(1, 2));
		}
		flipIt();
	}

	public void setMinute(final String MINUTE) {
		if (MINUTE.equals("  ") || MINUTE.equals(" ") || MINUTE.isEmpty()) {
			minLeft.setText(" ");
			minRight.setText(" ");
		} else {
			try {
				setMinute(Integer.parseInt(MINUTE));
			} catch (NumberFormatException e) {
				setMinute(0);
			}
		}
	}

	public void clearPanel() {
		minLeft.setText(" ");
		minRight.setText(" ");
	}

	private void flipIt() {
		minLeft.setText(Integer.toString(minL));
		minRight.setText(Integer.toString(minR));
	}

	private int clamp(final int MIN, final int MAX, final int VALUE) {
		return VALUE < MIN ? MIN : (VALUE > MAX ? MAX : VALUE);
	}

	private void initComponents() {

		minLeft = new SplitFlap();
		minRight = new SplitFlap();

		setBackground(new java.awt.Color(51, 51, 51));
		setPreferredSize(new java.awt.Dimension(90, 66));
		setSize(new java.awt.Dimension(90, 66));

		minLeft.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout minLeftLayout = new org.jdesktop.layout.GroupLayout(minLeft);
		minLeft.setLayout(minLeftLayout);
		minLeftLayout.setHorizontalGroup(
				minLeftLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 30, Short.MAX_VALUE)
		);
		minLeftLayout.setVerticalGroup(
				minLeftLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 0, Short.MAX_VALUE)
		);

		minRight.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout minRightLayout = new org.jdesktop.layout.GroupLayout(minRight);
		minRight.setLayout(minRightLayout);
		minRightLayout.setHorizontalGroup(
				minRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 30, Short.MAX_VALUE)
		);
		minRightLayout.setVerticalGroup(
				minRightLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 40, Short.MAX_VALUE)
		);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
								.add(0, 0, 0)
								.add(minLeft, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(minRight, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 30, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
								.add(0, 0, 0)
								.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
										.add(minRight, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
										.add(minLeft, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE))
								.add(14, 14, 14))
		);
	}
}
