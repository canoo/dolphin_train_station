package com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.Timer;

public class LedPanel extends JComponent implements ActionListener {
	private final Timer TIMER = new Timer(500, this);
	private final BufferedImage LED_OFF_OFF = createLedImage(0);
	private final BufferedImage LED_ON_OFF = createLedImage(1);
	private final BufferedImage LED_OFF_ON = createLedImage(2);
	private BufferedImage currentImage;
	private boolean blinking = false;
	private boolean toggle;

	public LedPanel() {
		super();
		this.currentImage = LED_OFF_OFF;
		toggle = false;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		final Graphics2D G2 = (Graphics2D) g;
		G2.drawImage(currentImage, 0, 0, this);

		G2.dispose();
	}

	public boolean isBlinking() {
		return blinking;
	}

	public void setBlinking(final boolean BLINKING) {
		if (BLINKING) {
			blinking = BLINKING;
			TIMER.start();
		} else {
			blinking = false;
			currentImage = LED_OFF_OFF;
			repaint(0, 0, 20, 35);
			TIMER.stop();
		}
	}

	private BufferedImage createLedImage(final int STATE) {
		final GraphicsConfiguration GFX_CONF = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		final BufferedImage IMAGE = GFX_CONF.createCompatibleImage(20, 35, Transparency.TRANSLUCENT);
		final java.awt.Graphics2D G2 = IMAGE.createGraphics();

		G2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		G2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

		// Define led data
		final Ellipse2D UPPER_LED = new Ellipse2D.Float(5, 5, 10, 10);
		final Ellipse2D UPPER_LED_CORONA = new Ellipse2D.Float(0, 0, 20, 20);
		final Ellipse2D LOWER_LED = new Ellipse2D.Float(5, 20, 10, 10);
		final Ellipse2D LOWER_LED_CORONA = new Ellipse2D.Float(0, 15, 20, 20);

		final Point2D UPPER_LED_CENTER = new Point2D.Double(UPPER_LED.getCenterX(), UPPER_LED.getCenterY());
		final Point2D LOWER_LED_CENTER = new Point2D.Double(LOWER_LED.getCenterX(), LOWER_LED.getCenterY());

		final float[] LED_FRACTIONS = {
				0.0f,
				0.2f,
				1.0f
		};

		final Color[] LED_OFF_COLORS = {
				new Color(0x1C7E00),
				new Color(0x1C7E00),
				new Color(0x1B6400)
		};

		final Color[] LED_ON_COLORS = {
				new Color(0x9AFF89),
				new Color(0x9AFF89),
				new Color(0x59FF2A)
		};

		final float[] LED_INNER_SHADOW_FRACTIONS = {
				0.0f,
				0.8f,
				1.0f
		};

		final Color[] LED_INNER_SHADOW_COLORS = {
				new Color(0.0f, 0.0f, 0.0f, 0.0f),
				new Color(0.0f, 0.0f, 0.0f, 0.0f),
				new Color(0.0f, 0.0f, 0.0f, 0.4f),};

		final float[] LED_ON_CORONA_FRACTIONS = {
				0.0f,
				0.6f,
				0.7f,
				0.8f,
				0.85f,
				1.0f
		};

		final Color[] LED_ON_CORONA_COLORS = {
				new Color(0.6470f, 1.0f, 0.0f, 0.0f),
				new Color(0.6470f, 1.0f, 0.0f, 0.4f),
				new Color(0.6470f, 1.0f, 0.0f, 0.25f),
				new Color(0.6470f, 1.0f, 0.0f, 0.15f),
				new Color(0.6470f, 1.0f, 0.0f, 0.05f),
				new Color(0.6470f, 1.0f, 0.0f, 0.0f)
		};

		// Define gradients for the upper led
		final RadialGradientPaint UPPER_LED_OFF_GRADIENT = new RadialGradientPaint(UPPER_LED_CENTER, 5,
				LED_FRACTIONS, LED_OFF_COLORS);
		final RadialGradientPaint UPPER_LED_ON_GRADIENT = new RadialGradientPaint(UPPER_LED_CENTER, 5,
				LED_FRACTIONS, LED_ON_COLORS);
		final RadialGradientPaint UPPER_LED_INNER_SHADOW_GRADIENT = new RadialGradientPaint(UPPER_LED_CENTER, 5,
				LED_INNER_SHADOW_FRACTIONS, LED_INNER_SHADOW_COLORS);
		final RadialGradientPaint UPPER_LED_ON_CORONA_GRADIENT = new RadialGradientPaint(UPPER_LED_CENTER, 10,
				LED_ON_CORONA_FRACTIONS, LED_ON_CORONA_COLORS);

		// Define gradients for the lower led
		final RadialGradientPaint LOWER_LED_OFF_GRADIENT = new RadialGradientPaint(LOWER_LED_CENTER, 5,
				LED_FRACTIONS, LED_OFF_COLORS);
		final RadialGradientPaint LOWER_LED_ON_GRADIENT = new RadialGradientPaint(LOWER_LED_CENTER, 5,
				LED_FRACTIONS, LED_ON_COLORS);
		final RadialGradientPaint LOWER_LED_INNER_SHADOW_GRADIENT = new RadialGradientPaint(LOWER_LED_CENTER, 5,
				LED_INNER_SHADOW_FRACTIONS, LED_INNER_SHADOW_COLORS);
		final RadialGradientPaint LOWER_LED_ON_CORONA_GRADIENT = new RadialGradientPaint(LOWER_LED_CENTER, 10,
				LED_ON_CORONA_FRACTIONS, LED_ON_CORONA_COLORS);

		// Define light reflex data
		final Ellipse2D UPPER_LED_LIGHTREFLEX1 = new Ellipse2D.Float(8, 7, 4, 3);
		final Ellipse2D UPPER_LED_LIGHTREFLEX2 = new Ellipse2D.Float(8, 12, 4, 3);
		final Ellipse2D LOWER_LED_LIGHTREFLEX = new Ellipse2D.Float(8, 22, 4, 3);

		final Point2D UPPER_LED_LIGHTREFLEX1_START = new Point2D.Float(0, 7);
		final Point2D UPPER_LED_LIGHTREFLEX1_STOP = new Point2D.Float(0, 10);

		final Point2D UPPER_LED_LIGHTREFLEX2_START = new Point2D.Float(0, 12);
		final Point2D UPPER_LED_LIGHTREFLEX2_STOP = new Point2D.Float(0, 15);

		final Point2D LOWER_LED_LIGHTREFLEX_START = new Point2D.Float(0, 22);
		final Point2D LOWER_LED_LIGHTREFLEX_STOP = new Point2D.Float(0, 25);

		final float[] LIGHT_REFLEX_FRACTIONS = {
				0.0f,
				1.0f
		};

		final Color[] LIGHTREFLEX_COLORS = {
				new Color(1.0f, 1.0f, 1.0f, 0.4f),
				new Color(1.0f, 1.0f, 1.0f, 0.0f)
		};

		final Color[] LIGHTREFLEX_COLORS2 = {
				new Color(1.0f, 1.0f, 1.0f, 0.0f),
				new Color(1.0f, 1.0f, 1.0f, 0.4f)
		};

		// Define light reflex gradients
		final LinearGradientPaint UPPER_LED_LIGHTREFLEX1_GRADIENT = new LinearGradientPaint(UPPER_LED_LIGHTREFLEX1_START, UPPER_LED_LIGHTREFLEX1_STOP,
				LIGHT_REFLEX_FRACTIONS, LIGHTREFLEX_COLORS);
		final LinearGradientPaint UPPER_LED_LIGHTREFLEX2_GRADIENT = new LinearGradientPaint(UPPER_LED_LIGHTREFLEX2_START, UPPER_LED_LIGHTREFLEX2_STOP,
				LIGHT_REFLEX_FRACTIONS, LIGHTREFLEX_COLORS2);
		final LinearGradientPaint LOWER_LED_LIGHTREFLEX_GRADIENT = new LinearGradientPaint(LOWER_LED_LIGHTREFLEX_START, LOWER_LED_LIGHTREFLEX_STOP,
				LIGHT_REFLEX_FRACTIONS, LIGHTREFLEX_COLORS);

		switch (STATE) {
			case 0:
				// Draw upper LED OFF
				G2.setPaint(UPPER_LED_OFF_GRADIENT);
				G2.fill(UPPER_LED);
				G2.setPaint(UPPER_LED_INNER_SHADOW_GRADIENT);
				G2.fill(UPPER_LED);
				G2.setPaint(UPPER_LED_LIGHTREFLEX1_GRADIENT);
				G2.fill(UPPER_LED_LIGHTREFLEX1);

				// Draw lower LED OFF
				G2.setPaint(LOWER_LED_OFF_GRADIENT);
				G2.fill(LOWER_LED);
				G2.setPaint(LOWER_LED_INNER_SHADOW_GRADIENT);
				G2.fill(LOWER_LED);
				G2.setPaint(LOWER_LED_LIGHTREFLEX_GRADIENT);
				G2.fill(LOWER_LED_LIGHTREFLEX);
				break;
			case 1:
				// Draw uper LED ON
				G2.setPaint(UPPER_LED_ON_CORONA_GRADIENT);
				G2.fill(UPPER_LED_CORONA);
				G2.setPaint(UPPER_LED_ON_GRADIENT);
				G2.fill(UPPER_LED);
				G2.setPaint(UPPER_LED_INNER_SHADOW_GRADIENT);
				G2.fill(UPPER_LED);
				G2.setPaint(UPPER_LED_LIGHTREFLEX1_GRADIENT);
				G2.fill(UPPER_LED_LIGHTREFLEX1);

				// Draw lower LED OFF
				G2.setPaint(LOWER_LED_OFF_GRADIENT);
				G2.fill(LOWER_LED);
				G2.setPaint(LOWER_LED_INNER_SHADOW_GRADIENT);
				G2.fill(LOWER_LED);
				G2.setPaint(LOWER_LED_LIGHTREFLEX_GRADIENT);
				G2.fill(LOWER_LED_LIGHTREFLEX);
				break;
			case 2:
				// Draw upper LED OFF
				G2.setPaint(UPPER_LED_OFF_GRADIENT);
				G2.fill(UPPER_LED);
				G2.setPaint(UPPER_LED_INNER_SHADOW_GRADIENT);
				G2.fill(UPPER_LED);
				G2.setPaint(UPPER_LED_LIGHTREFLEX1_GRADIENT);
				G2.fill(UPPER_LED_LIGHTREFLEX1);
				G2.setPaint(UPPER_LED_LIGHTREFLEX2_GRADIENT);
				G2.fill(UPPER_LED_LIGHTREFLEX2);

				// Draw lower LED ON
				G2.setPaint(LOWER_LED_ON_CORONA_GRADIENT);
				G2.fill(LOWER_LED_CORONA);
				G2.setPaint(LOWER_LED_ON_GRADIENT);
				G2.fill(LOWER_LED);
				G2.setPaint(LOWER_LED_INNER_SHADOW_GRADIENT);
				G2.fill(LOWER_LED);
				G2.setPaint(LOWER_LED_LIGHTREFLEX_GRADIENT);
				G2.fill(LOWER_LED_LIGHTREFLEX);
				break;
			default:
				// Draw upper LED OFF
				G2.setPaint(UPPER_LED_OFF_GRADIENT);
				G2.fill(UPPER_LED);
				G2.setPaint(UPPER_LED_INNER_SHADOW_GRADIENT);
				G2.fill(UPPER_LED);
				G2.setPaint(UPPER_LED_LIGHTREFLEX1_GRADIENT);
				G2.fill(UPPER_LED_LIGHTREFLEX1);

				// Draw lower LED OFF
				G2.setPaint(LOWER_LED_OFF_GRADIENT);
				G2.fill(LOWER_LED);
				G2.setPaint(LOWER_LED_INNER_SHADOW_GRADIENT);
				G2.fill(LOWER_LED);
				G2.setPaint(LOWER_LED_LIGHTREFLEX_GRADIENT);
				G2.fill(LOWER_LED_LIGHTREFLEX);
				break;
		}

		G2.dispose();

		return IMAGE;
	}

	@Override
	public Dimension getSize() {
		return new Dimension(20, 35);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(20, 35);
	}

	@Override
	public Dimension getSize(final Dimension DIM) {
		return new Dimension(20, 35);
	}

	public void actionPerformed(ActionEvent event) {
		toggle ^= true;

		if (toggle) {
			currentImage = LED_ON_OFF;
		} else {
			currentImage = LED_OFF_ON;
		}

		repaint(0, 0, 20, 35);
	}
}
