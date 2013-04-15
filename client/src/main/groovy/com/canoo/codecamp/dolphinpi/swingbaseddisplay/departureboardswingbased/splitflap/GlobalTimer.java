package com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.splitflap;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashSet;
import java.util.Set;

import javax.swing.Timer;

public enum GlobalTimer implements ActionListener {

	INSTANCE;

	// <editor-fold defaultstate="collapsed" desc="Variable declaration">
	private static final ActionEvent FLIP_EVENT = new ActionEvent("flip", ActionEvent.ACTION_PERFORMED, "flip");
	private static final ActionEvent FLIP_SEQUENCE_EVENT = new ActionEvent("flipSequence", ActionEvent.ACTION_PERFORMED, "flipSequence");
	private Timer flipTimer = new Timer(120, this);
	private Timer flipSequenceTimer = new Timer(10, this);
	private int currentSequence = 0;
	private Set<SplitFlap> componentSet = new HashSet<SplitFlap>(32);
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Misc">
	public void startTimer() {
		flipTimer.start();
	}

	public void stopTimer() {
		flipTimer.stop();
	}

	public void addComponent(SplitFlap component) {
		componentSet.add(component);
	}

	public void removeComponent(SplitFlap component) {
		componentSet.remove(component);
	}
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Action Listener">
	public void actionPerformed(final ActionEvent EVENT) {
		if (EVENT.getSource().equals(flipTimer)) {
			for (SplitFlap component : componentSet) {
				component.actionPerformed(FLIP_EVENT);
			}

			flipSequenceTimer.start();
		}

		if (EVENT.getSource().equals(flipSequenceTimer)) {
			if (currentSequence == 10) {
				currentSequence = 0;
				flipSequenceTimer.stop();
			}

			for (SplitFlap component : componentSet) {
				component.actionPerformed(FLIP_SEQUENCE_EVENT);
			}
			currentSequence++;
		}
	}
	// </editor-fold>
}
