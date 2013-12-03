package com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board;

import com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.splitflap.GlobalTimer;
import com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.splitflap.SplitFlap;

public class DestinationPanel extends javax.swing.JPanel {
	private SplitFlap splitFlap1;
	private SplitFlap splitFlap2;
	private SplitFlap splitFlap3;
	private SplitFlap splitFlap4;
	private SplitFlap splitFlap5;
	private SplitFlap splitFlap6;
	private SplitFlap splitFlap7;
	private SplitFlap splitFlap8;
	private SplitFlap splitFlap9;
	private SplitFlap splitFlap10;
	private SplitFlap splitFlap11;
	private SplitFlap splitFlap12;
	private SplitFlap splitFlap13;
	private SplitFlap splitFlap14;
	private SplitFlap splitFlap15;
	private SplitFlap splitFlap16;
	private String destination = "                ";
	private SplitFlap[] splitFlapArray = new SplitFlap[16];

	public DestinationPanel() {
		initComponents();
		init();
	}

	private void init() {
		splitFlapArray[0] = splitFlap1;
		splitFlapArray[1] = splitFlap2;
		splitFlapArray[2] = splitFlap3;
		splitFlapArray[3] = splitFlap4;
		splitFlapArray[4] = splitFlap5;
		splitFlapArray[5] = splitFlap6;
		splitFlapArray[6] = splitFlap7;
		splitFlapArray[7] = splitFlap8;
		splitFlapArray[8] = splitFlap9;
		splitFlapArray[9] = splitFlap10;
		splitFlapArray[10] = splitFlap11;
		splitFlapArray[11] = splitFlap12;
		splitFlapArray[12] = splitFlap13;
		splitFlapArray[13] = splitFlap14;
		splitFlapArray[14] = splitFlap15;
		splitFlapArray[15] = splitFlap16;

		for (SplitFlap splitFlap : splitFlapArray) {
			splitFlap.setSelection(SplitFlap.EXTENDED);
		}

		GlobalTimer.INSTANCE.startTimer();
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(final String DESTINATION) {
		destination = DESTINATION;
		flipIt();
	}

	public void clearPanel() {
		setDestination("                ");
		flipIt();
	}

	private void flipIt() {
		for (int i = 0; i < 16; i++) {
			if (i >= destination.length()) {
				splitFlapArray[i].setText(" ");
			} else {
				splitFlapArray[i].setText(this.destination.toUpperCase().substring(i, i + 1));
			}
		}
	}

	private void initComponents() {

		splitFlap1 = new SplitFlap();
		splitFlap2 = new SplitFlap();
		splitFlap3 = new SplitFlap();
		splitFlap4 = new SplitFlap();
		splitFlap5 = new SplitFlap();
		splitFlap6 = new SplitFlap();
		splitFlap7 = new SplitFlap();
		splitFlap8 = new SplitFlap();
		splitFlap9 = new SplitFlap();
		splitFlap10 = new SplitFlap();
		splitFlap11 = new SplitFlap();
		splitFlap12 = new SplitFlap();
		splitFlap13 = new SplitFlap();
		splitFlap14 = new SplitFlap();
		splitFlap15 = new SplitFlap();
		splitFlap16 = new SplitFlap();

		setBackground(new java.awt.Color(51, 51, 51));
		setPreferredSize(new java.awt.Dimension(678, 66));
		setSize(new java.awt.Dimension(678, 66));

		splitFlap1.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap1Layout = new org.jdesktop.layout.GroupLayout(splitFlap1);
		splitFlap1.setLayout(splitFlap1Layout);
		splitFlap1Layout.setHorizontalGroup(
				splitFlap1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap1Layout.setVerticalGroup(
				splitFlap1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 0, Short.MAX_VALUE)
		);

		splitFlap2.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap2Layout = new org.jdesktop.layout.GroupLayout(splitFlap2);
		splitFlap2.setLayout(splitFlap2Layout);
		splitFlap2Layout.setHorizontalGroup(
				splitFlap2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap2Layout.setVerticalGroup(
				splitFlap2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 0, Short.MAX_VALUE)
		);

		splitFlap3.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap3Layout = new org.jdesktop.layout.GroupLayout(splitFlap3);
		splitFlap3.setLayout(splitFlap3Layout);
		splitFlap3Layout.setHorizontalGroup(
				splitFlap3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap3Layout.setVerticalGroup(
				splitFlap3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 0, Short.MAX_VALUE)
		);

		splitFlap4.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap4Layout = new org.jdesktop.layout.GroupLayout(splitFlap4);
		splitFlap4.setLayout(splitFlap4Layout);
		splitFlap4Layout.setHorizontalGroup(
				splitFlap4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap4Layout.setVerticalGroup(
				splitFlap4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 0, Short.MAX_VALUE)
		);

		splitFlap5.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap5Layout = new org.jdesktop.layout.GroupLayout(splitFlap5);
		splitFlap5.setLayout(splitFlap5Layout);
		splitFlap5Layout.setHorizontalGroup(
				splitFlap5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap5Layout.setVerticalGroup(
				splitFlap5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 0, Short.MAX_VALUE)
		);

		splitFlap6.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap6Layout = new org.jdesktop.layout.GroupLayout(splitFlap6);
		splitFlap6.setLayout(splitFlap6Layout);
		splitFlap6Layout.setHorizontalGroup(
				splitFlap6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap6Layout.setVerticalGroup(
				splitFlap6Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 0, Short.MAX_VALUE)
		);

		splitFlap7.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap7Layout = new org.jdesktop.layout.GroupLayout(splitFlap7);
		splitFlap7.setLayout(splitFlap7Layout);
		splitFlap7Layout.setHorizontalGroup(
				splitFlap7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap7Layout.setVerticalGroup(
				splitFlap7Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 54, Short.MAX_VALUE)
		);

		splitFlap8.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap8Layout = new org.jdesktop.layout.GroupLayout(splitFlap8);
		splitFlap8.setLayout(splitFlap8Layout);
		splitFlap8Layout.setHorizontalGroup(
				splitFlap8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap8Layout.setVerticalGroup(
				splitFlap8Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 0, Short.MAX_VALUE)
		);

		splitFlap9.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap9Layout = new org.jdesktop.layout.GroupLayout(splitFlap9);
		splitFlap9.setLayout(splitFlap9Layout);
		splitFlap9Layout.setHorizontalGroup(
				splitFlap9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap9Layout.setVerticalGroup(
				splitFlap9Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 0, Short.MAX_VALUE)
		);

		splitFlap10.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap10Layout = new org.jdesktop.layout.GroupLayout(splitFlap10);
		splitFlap10.setLayout(splitFlap10Layout);
		splitFlap10Layout.setHorizontalGroup(
				splitFlap10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap10Layout.setVerticalGroup(
				splitFlap10Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 54, Short.MAX_VALUE)
		);

		splitFlap11.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap11Layout = new org.jdesktop.layout.GroupLayout(splitFlap11);
		splitFlap11.setLayout(splitFlap11Layout);
		splitFlap11Layout.setHorizontalGroup(
				splitFlap11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap11Layout.setVerticalGroup(
				splitFlap11Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 0, Short.MAX_VALUE)
		);

		splitFlap12.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap12Layout = new org.jdesktop.layout.GroupLayout(splitFlap12);
		splitFlap12.setLayout(splitFlap12Layout);
		splitFlap12Layout.setHorizontalGroup(
				splitFlap12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap12Layout.setVerticalGroup(
				splitFlap12Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 54, Short.MAX_VALUE)
		);

		splitFlap13.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap13Layout = new org.jdesktop.layout.GroupLayout(splitFlap13);
		splitFlap13.setLayout(splitFlap13Layout);
		splitFlap13Layout.setHorizontalGroup(
				splitFlap13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap13Layout.setVerticalGroup(
				splitFlap13Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 54, Short.MAX_VALUE)
		);

		splitFlap14.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap14Layout = new org.jdesktop.layout.GroupLayout(splitFlap14);
		splitFlap14.setLayout(splitFlap14Layout);
		splitFlap14Layout.setHorizontalGroup(
				splitFlap14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap14Layout.setVerticalGroup(
				splitFlap14Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 54, Short.MAX_VALUE)
		);

		splitFlap15.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap15Layout = new org.jdesktop.layout.GroupLayout(splitFlap15);
		splitFlap15.setLayout(splitFlap15Layout);
		splitFlap15Layout.setHorizontalGroup(
				splitFlap15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap15Layout.setVerticalGroup(
				splitFlap15Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 54, Short.MAX_VALUE)
		);

		splitFlap16.setFontColor(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout splitFlap16Layout = new org.jdesktop.layout.GroupLayout(splitFlap16);
		splitFlap16.setLayout(splitFlap16Layout);
		splitFlap16Layout.setHorizontalGroup(
				splitFlap16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 36, Short.MAX_VALUE)
		);
		splitFlap16Layout.setVerticalGroup(
				splitFlap16Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(0, 54, Short.MAX_VALUE)
		);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(layout.createSequentialGroup()
								.add(0, 0, 0)
								.add(splitFlap1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap6, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap8, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap9, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap12, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap15, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0)
								.add(splitFlap16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.add(0, 0, 0))
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(layout.createSequentialGroup()
								.add(0, 0, 0)
								.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
										.add(splitFlap14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
												.add(splitFlap9, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(splitFlap8, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(splitFlap15, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(splitFlap16, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(splitFlap7, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(splitFlap6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(splitFlap5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(splitFlap4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(splitFlap3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(splitFlap2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(splitFlap1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(splitFlap11, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.add(splitFlap12, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.add(splitFlap10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(splitFlap13, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(0, 0, 0))
		);
	}
}
