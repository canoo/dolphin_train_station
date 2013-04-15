package departureboardswingbased.board;

import javax.swing.JLabel;

public class HeaderPanel extends javax.swing.JPanel {
	private JLabel labelTime;
	private JLabel labelDestination;
	private JLabel labelTrack;
//    private JLabel labelInfo;

	public HeaderPanel() {
		initComponents();
	}

	private void initComponents() {

		labelTime = new javax.swing.JLabel();
		labelDestination = new javax.swing.JLabel();
		labelTrack = new javax.swing.JLabel();
//        labelInfo = new javax.swing.JLabel();

		setBackground(new java.awt.Color(0, 0, 0));
		setLayout(new java.awt.GridBagLayout());

		labelTime.setBackground(new java.awt.Color(0, 0, 0));
		labelTime.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
		labelTime.setForeground(new java.awt.Color(255, 255, 255));
		labelTime.setText("   Zeit");
		labelTime.setPreferredSize(new java.awt.Dimension(178, 28));
		labelTime.setSize(new java.awt.Dimension(178, 28));
		add(labelTime, new java.awt.GridBagConstraints());

		labelDestination.setBackground(new java.awt.Color(0, 0, 0));
		labelDestination.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
		labelDestination.setForeground(new java.awt.Color(255, 255, 255));
		labelDestination.setText("Ziel");
		labelDestination.setPreferredSize(new java.awt.Dimension(584, 28));
		labelDestination.setSize(new java.awt.Dimension(584, 28));
		add(labelDestination, new java.awt.GridBagConstraints());

		labelTrack.setBackground(new java.awt.Color(0, 0, 0));
		labelTrack.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
		labelTrack.setForeground(new java.awt.Color(255, 255, 255));
		labelTrack.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		labelTrack.setText("Gleis");
		labelTrack.setPreferredSize(new java.awt.Dimension(101, 28));
		labelTrack.setSize(new java.awt.Dimension(101, 16));
		add(labelTrack, new java.awt.GridBagConstraints());

//        labelInfo.setBackground(new java.awt.Color(0, 0, 0));
//        labelInfo.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
//        labelInfo.setForeground(new java.awt.Color(255, 255, 255));
//        labelInfo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
//        labelInfo.setText("Hinweis ");
//        labelInfo.setPreferredSize(new java.awt.Dimension(367, 28));
//        labelInfo.setSize(new java.awt.Dimension(367, 16));
//        add(labelInfo, new java.awt.GridBagConstraints());
	}
}
