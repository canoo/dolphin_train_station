package com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board;

import java.awt.event.ActionEvent;

import java.util.EventListener;
import java.util.EventObject;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ControlPanel extends JPanel {
	private JButton applyButton;
	private JCheckBox blinkCheckBox;
	private JTextField destinationTextField;
	private JTextField hourTextField;
	private JTextField infoTextField;
	private JComboBox jComboBox1;
	private JLabel jLabel1;
	private JLabel jLabel2;
	private JLabel jLabel3;
	private JLabel jLabel4;
	private JLabel jLabel5;
	private JPanel jPanel1;
	private JTextField minuteTextField;
	private JTextField trackTextField;

	public ControlPanel() {
		initComponents();
	}

	private void initComponents() {

		jPanel1 = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		hourTextField = new javax.swing.JTextField();
		minuteTextField = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		destinationTextField = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		trackTextField = new javax.swing.JTextField();
		jLabel4 = new javax.swing.JLabel();
		infoTextField = new javax.swing.JTextField();
		jComboBox1 = new javax.swing.JComboBox();
		jLabel5 = new javax.swing.JLabel();
		applyButton = new javax.swing.JButton();
		blinkCheckBox = new javax.swing.JCheckBox();

		jLabel1.setText("Zeit");

		hourTextField.setText("12");

		minuteTextField.setText("00");

		jLabel2.setText("Ziel");

		destinationTextField.setText("Deine Stadt");

		jLabel3.setText("Gleis");

		trackTextField.setText("10");

		jLabel4.setText("Hinweis");

		jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"1", "2", "3", "4", "5"}));

		jLabel5.setText("Reihe");

		applyButton.setText("Anwenden");
		applyButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent event) {
				actionHandler(event);
			}
		});

		blinkCheckBox.setText("Blinken");

		org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(
				jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanel1Layout.createSequentialGroup()
								.addContainerGap()
								.add(blinkCheckBox)
								.add(18, 18, 18)
								.add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
										.add(org.jdesktop.layout.GroupLayout.LEADING, jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.add(org.jdesktop.layout.GroupLayout.LEADING, jPanel1Layout.createSequentialGroup()
												.add(hourTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
												.add(minuteTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
								.add(18, 18, 18)
								.add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
										.add(destinationTextField)
										.add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 120, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(18, 18, 18)
								.add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
										.add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.add(trackTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 31, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(18, 18, 18)
								.add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
										.add(infoTextField)
										.add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 94, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(18, 18, 18)
								.add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
										.add(jComboBox1, 0, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(18, 18, 18)
								.add(applyButton)
								.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		jPanel1Layout.setVerticalGroup(
				jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
								.add(0, 12, Short.MAX_VALUE)
								.add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
										.add(jLabel1)
										.add(jLabel2)
										.add(jLabel3)
										.add(jLabel4)
										.add(jLabel5))
								.addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
								.add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
										.add(hourTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(minuteTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(destinationTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(trackTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(infoTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(jComboBox1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(applyButton)
										.add(blinkCheckBox)))
		);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(layout.createSequentialGroup()
								.addContainerGap()
								.add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
		);
		layout.setVerticalGroup(
				layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
						.add(layout.createSequentialGroup()
								.addContainerGap()
								.add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
	}

	private void actionHandler(ActionEvent event) {//GEN-FIRST:event_actionHandler
		if (event.getSource().equals(applyButton)) {
			boolean blink = blinkCheckBox.isSelected();
			String hour = hourTextField.getText().isEmpty() ? " " : hourTextField.getText();
			String minute = minuteTextField.getText().isEmpty() ? " " : minuteTextField.getText();
			String destination = destinationTextField.getText();
			String track = trackTextField.getText().isEmpty() ? " " : trackTextField.getText();
			String info = infoTextField.getText();
			int rowIndex = jComboBox1.getSelectedIndex();
			fireControlEvent(new ControlEvent(this, blink, hour, minute, destination, track, info, rowIndex));
		}
	}

	public void addControlEventListener(ControlEventListener listener) {
		listenerList.add(ControlEventListener.class, listener);
	}

	public void removeControlEventListener(ControlEventListener listener) {
		listenerList.remove(ControlEventListener.class, listener);
	}

	private void fireControlEvent(ControlEvent event) {
		Object[] listeners = listenerList.getListenerList();
		int max = listeners.length;
		for (int i = 0; i < max; i++) {
			if (listeners[i] == ControlEventListener.class) {
				((ControlEventListener) listeners[i + 1]).controlEventPerformed(event);
			}
		}
	}

	// ******************** Internal Classes **********************************
	public class ControlEvent extends EventObject {
		private final boolean BLINK;
		private final String HOUR;
		private final String MINUTE;
		private final String DESTINATION;
		private final String TRACK;
		private final String INFO;
		private final int ROW_INDEX;

		public ControlEvent(Object source, boolean blink, String hour, String minute, String destination, String track, String info, int rowIndex) {
			super(source);
			BLINK = blink;
			HOUR = hour;
			MINUTE = minute;
			DESTINATION = destination;
			TRACK = track;
			INFO = info;
			ROW_INDEX = rowIndex;
		}

		public boolean isBlink() {
			return BLINK;
		}

		public String getHour() {
			return HOUR;
		}

		public String getMinute() {
			return MINUTE;
		}

		public String getDestination() {
			return DESTINATION;
		}

		public String getTrack() {
			return TRACK;
		}

		public String getInfo() {
			return INFO;
		}

		public int getRowIndex() {
			return ROW_INDEX;
		}
	}

	public interface ControlEventListener extends EventListener {
		public void controlEventPerformed(ControlEvent event);
	}
}
