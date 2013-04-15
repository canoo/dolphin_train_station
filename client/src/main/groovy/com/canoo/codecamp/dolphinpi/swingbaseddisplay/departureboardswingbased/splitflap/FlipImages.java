package com.canoo.codecamp.dolphinpi.swingbaseddisplay.departureboardswingbased.splitflap;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import java.io.IOException;

public enum FlipImages {
	INSTANCE;

	private int formerWidth = 0;
	private int formerHeight = 0;
	private Font font;

	private FlipImages() {
		try {
			font = Font.createFont(0, this.getClass().getResourceAsStream("/swingdepartureboard/splitflap/ArialNarrow.ttf"));
		} catch (FontFormatException ex) {
			font = new Font("sans-serif", 0, 40);
		} catch (IOException ex) {
			font = new Font("sans-serif", 0, 40);
		}
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
	}

	// <editor-fold defaultstate="collapsed" desc="Variable declaration">
	private BufferedImage[] flipImageArray = createFlipImages(45, 61);
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Getter and Setter">
	public BufferedImage[] getFlipImageArray() {
		return flipImageArray.clone();
	}

	public Font getFont() {
		return font;
	}
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Misc">
	public void recreateImages(final int WIDTH, final int HEIGHT) {
		if (WIDTH == formerWidth && HEIGHT == formerHeight) {
			return;
		}
		flipImageArray = createFlipImages(WIDTH, (int) (0.9838709677 * HEIGHT));
		formerWidth = WIDTH;
		formerHeight = HEIGHT;
	}
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="Image related">
	private BufferedImage[] createFlipImages(final int WIDTH, final int HEIGHT) {
		final BufferedImage[] IMAGE_ARRAY = new BufferedImage[9];
		final GraphicsConfiguration GFX_CONF = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();

		// Flip Image 1
		final BufferedImage FLIP_IMG1 = GFX_CONF.createCompatibleImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);
		Graphics2D g2 = FLIP_IMG1.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		final GeneralPath FLIP1 = new GeneralPath();
		FLIP1.moveTo(WIDTH * 0.08888888888888889, HEIGHT * 0.16923076923076924);
		FLIP1.quadTo(WIDTH * 0.08888888888888889, HEIGHT * 0.13846153846153847, WIDTH * 0.15555555555555556, HEIGHT * 0.13846153846153847);
		FLIP1.lineTo(WIDTH * 0.8444444444444444, HEIGHT * 0.13846153846153847);
		FLIP1.quadTo(WIDTH * 0.9111111111111111, HEIGHT * 0.13846153846153847, WIDTH * 0.9111111111111111, HEIGHT * 0.16923076923076924);
		FLIP1.lineTo(WIDTH * 0.8888888888888888, HEIGHT * 0.43548387096774194);
		FLIP1.lineTo(WIDTH * 0.8666666666666667, HEIGHT * 0.46774193548387094);
		FLIP1.lineTo(WIDTH * 0.13333333333333333, HEIGHT * 0.46774193548387094);
		FLIP1.lineTo(WIDTH * 0.1111111111111111, HEIGHT * 0.43548387096774194);
		FLIP1.closePath();

		g2.setColor(new Color(0x565551).darker());
		g2.fill(FLIP1);

		g2.dispose();
		IMAGE_ARRAY[0] = FLIP_IMG1;

		// Flip Image 2
		final BufferedImage FLIP_IMG2 = GFX_CONF.createCompatibleImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);
		g2 = FLIP_IMG2.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		final GeneralPath FLIP2 = new GeneralPath();
		FLIP2.moveTo(WIDTH * 0.06666666666666667, HEIGHT * 0.25806451612903225);
		FLIP2.quadTo(WIDTH * 0.06666666666666667, HEIGHT * 0.22580645161290322, WIDTH * 0.13333333333333333, HEIGHT * 0.22580645161290322);
		FLIP2.lineTo(WIDTH * 0.8666666666666667, HEIGHT * 0.22580645161290322);
		FLIP2.quadTo(WIDTH * 0.9333333333333333, HEIGHT * 0.22580645161290322, WIDTH * 0.9333333333333333, HEIGHT * 0.25806451612903225);
		FLIP2.lineTo(WIDTH * 0.8888888888888888, HEIGHT * 0.43548387096774194);
		FLIP2.lineTo(WIDTH * 0.8666666666666667, HEIGHT * 0.46774193548387094);
		FLIP2.lineTo(WIDTH * 0.13333333333333333, HEIGHT * 0.46774193548387094);
		FLIP2.lineTo(WIDTH * 0.1111111111111111, HEIGHT * 0.43548387096774194);
		FLIP2.closePath();

		g2.setColor(new Color(0x565551).darker().darker());
		g2.fill(FLIP2);

		g2.dispose();
		IMAGE_ARRAY[1] = FLIP_IMG2;

		// Flip Image 3
		final BufferedImage FLIP_IMG3 = GFX_CONF.createCompatibleImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);
		g2 = FLIP_IMG3.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		final GeneralPath FLIP3 = new GeneralPath();
		FLIP3.moveTo(WIDTH * 0.044444444444444446, HEIGHT * 0.3225806451612903);
		FLIP3.quadTo(WIDTH * 0.044444444444444446, HEIGHT * 0.3064516129032258, WIDTH * 0.1111111111111111, HEIGHT * 0.3064516129032258);
		FLIP3.lineTo(WIDTH * 0.8888888888888888, HEIGHT * 0.3064516129032258);
		FLIP3.quadTo(WIDTH * 0.9555555555555556, HEIGHT * 0.3064516129032258, WIDTH * 0.9555555555555556, HEIGHT * 0.3225806451612903);
		FLIP3.lineTo(WIDTH * 0.8888888888888888, HEIGHT * 0.45161290322580644);
		FLIP3.lineTo(WIDTH * 0.8666666666666667, HEIGHT * 0.46774193548387094);
		FLIP3.lineTo(WIDTH * 0.13333333333333333, HEIGHT * 0.46774193548387094);
		FLIP3.lineTo(WIDTH * 0.1111111111111111, HEIGHT * 0.45161290322580644);
		FLIP3.closePath();

		g2.setColor(new Color(0x565551).darker().darker());
		g2.fill(FLIP3);

		g2.dispose();
		IMAGE_ARRAY[2] = FLIP_IMG3;

		// Flip Image 4
		final BufferedImage FLIP_IMG4 = GFX_CONF.createCompatibleImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);
		g2 = FLIP_IMG4.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		final GeneralPath FLIP4 = new GeneralPath();
		FLIP4.moveTo(WIDTH * 0.044444444444444446, HEIGHT * 0.4032258064516129);
		FLIP4.quadTo(WIDTH * 0.022222222222222223, HEIGHT * 0.3870967741935484, WIDTH * 0.08888888888888889, HEIGHT * 0.3870967741935484);
		FLIP4.lineTo(WIDTH * 0.9111111111111111, HEIGHT * 0.3870967741935484);
		FLIP4.quadTo(WIDTH * 0.9555555555555556, HEIGHT * 0.3870967741935484, WIDTH * 0.9777777777777777, HEIGHT * 0.4032258064516129);
		FLIP4.lineTo(WIDTH * 0.8888888888888888, HEIGHT * 0.46774193548387094);
		FLIP4.lineTo(WIDTH * 0.8666666666666667, HEIGHT * 0.46774193548387094);
		FLIP4.lineTo(WIDTH * 0.13333333333333333, HEIGHT * 0.46774193548387094);
		FLIP4.lineTo(WIDTH * 0.1111111111111111, HEIGHT * 0.46774193548387094);
		FLIP4.closePath();

		g2.setColor(new Color(0x565551).darker().darker());
		g2.fill(FLIP4);

		g2.dispose();
		IMAGE_ARRAY[3] = FLIP_IMG4;

		// Flip Image 5
		final BufferedImage FLIP_IMG5 = GFX_CONF.createCompatibleImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);
		g2 = FLIP_IMG5.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		final GeneralPath FLIP5 = new GeneralPath();
		FLIP5.moveTo(WIDTH * 0.044444444444444446, HEIGHT * 0.46774193548387094);
		FLIP5.lineTo(WIDTH * 0.9555555555555556, HEIGHT * 0.46774193548387094);
		FLIP5.lineTo(WIDTH * 0.9555555555555556, HEIGHT * 0.4838709677419355);
		FLIP5.lineTo(WIDTH * 0.044444444444444446, HEIGHT * 0.4838709677419355);
		FLIP5.closePath();

		g2.setColor(new Color(0x565551).darker().darker().darker());
		g2.fill(FLIP5);

		g2.dispose();
		IMAGE_ARRAY[4] = FLIP_IMG5;

		// Flip Image 6
		final BufferedImage FLIP_IMG6 = GFX_CONF.createCompatibleImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);
		g2 = FLIP_IMG6.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		final GeneralPath FLIP6 = new GeneralPath();
		FLIP6.moveTo(WIDTH * 0.13333333333333333, HEIGHT * 0.5);
		FLIP6.lineTo(WIDTH * 0.8666666666666667, HEIGHT * 0.5);
		FLIP6.lineTo(WIDTH * 0.9111111111111111, HEIGHT * 0.5161290322580645);
		FLIP6.lineTo(WIDTH * 0.9777777777777777, HEIGHT * 0.5645161290322581);
		FLIP6.quadTo(WIDTH * 0.9777777777777777, HEIGHT * 0.5806451612903226, WIDTH * 0.9111111111111111, HEIGHT * 0.5806451612903226);
		FLIP6.lineTo(WIDTH * 0.08888888888888889, HEIGHT * 0.5806451612903226);
		FLIP6.quadTo(WIDTH * 0.022222222222222223, HEIGHT * 0.5806451612903226, WIDTH * 0.044444444444444446, HEIGHT * 0.5645161290322581);
		FLIP6.lineTo(WIDTH * 0.1111111111111111, HEIGHT * 0.5161290322580645);
		FLIP6.closePath();

		g2.setColor(new Color(0x565551).brighter().brighter());
		g2.fill(FLIP6);

		g2.dispose();
		IMAGE_ARRAY[5] = FLIP_IMG6;

		// Flip Image 7
		final BufferedImage FLIP_IMG7 = GFX_CONF.createCompatibleImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);
		g2 = FLIP_IMG7.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		final GeneralPath FLIP7 = new GeneralPath();
		FLIP7.moveTo(WIDTH * 0.13333333333333333, HEIGHT * 0.5);
		FLIP7.lineTo(WIDTH * 0.8666666666666667, HEIGHT * 0.5);
		FLIP7.lineTo(WIDTH * 0.8888888888888888, HEIGHT * 0.5161290322580645);
		FLIP7.lineTo(WIDTH * 0.9555555555555556, HEIGHT * 0.6451612903225806);
		FLIP7.quadTo(WIDTH * 0.9555555555555556, HEIGHT * 0.6612903225806451, WIDTH * 0.8888888888888888, HEIGHT * 0.6612903225806451);
		FLIP7.lineTo(WIDTH * 0.1111111111111111, HEIGHT * 0.6612903225806451);
		FLIP7.quadTo(WIDTH * 0.044444444444444446, HEIGHT * 0.6612903225806451, WIDTH * 0.044444444444444446, HEIGHT * 0.6451612903225806);
		FLIP7.lineTo(WIDTH * 0.1111111111111111, HEIGHT * 0.5161290322580645);
		FLIP7.closePath();

		g2.setColor(new Color(0x565551).brighter());
		g2.fill(FLIP7);

		g2.dispose();
		IMAGE_ARRAY[6] = FLIP_IMG7;

		// Flip Image 8
		final BufferedImage FLIP_IMG8 = GFX_CONF.createCompatibleImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);
		g2 = FLIP_IMG8.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		final GeneralPath FLIP8 = new GeneralPath();
		FLIP8.moveTo(WIDTH * 0.13333333333333333, HEIGHT * 0.5);
		FLIP8.lineTo(WIDTH * 0.8666666666666667, HEIGHT * 0.5);
		FLIP8.lineTo(WIDTH * 0.8888888888888888, HEIGHT * 0.532258064516129);
		FLIP8.lineTo(WIDTH * 0.9333333333333333, HEIGHT * 0.7096774193548387);
		FLIP8.quadTo(WIDTH * 0.9333333333333333, HEIGHT * 0.7419354838709677, WIDTH * 0.8666666666666667, HEIGHT * 0.7419354838709677);
		FLIP8.lineTo(WIDTH * 0.13333333333333333, HEIGHT * 0.7419354838709677);
		FLIP8.quadTo(WIDTH * 0.06666666666666667, HEIGHT * 0.7419354838709677, WIDTH * 0.06666666666666667, HEIGHT * 0.7096774193548387);
		FLIP8.lineTo(WIDTH * 0.1111111111111111, HEIGHT * 0.532258064516129);
		FLIP8.closePath();

		g2.setColor(new Color(0x565551).brighter());
		g2.fill(FLIP8);

		g2.dispose();
		IMAGE_ARRAY[7] = FLIP_IMG8;

		// Flip Image 9
		final BufferedImage FLIP_IMG9 = GFX_CONF.createCompatibleImage(WIDTH, HEIGHT, Transparency.TRANSLUCENT);
		g2 = FLIP_IMG9.createGraphics();

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		final GeneralPath FLIP9 = new GeneralPath();
		FLIP9.moveTo(WIDTH * 0.13333333333333333, HEIGHT * 0.5);
		FLIP9.lineTo(WIDTH * 0.8666666666666667, HEIGHT * 0.5);
		FLIP9.lineTo(WIDTH * 0.8888888888888888, HEIGHT * 0.532258064516129);
		FLIP9.lineTo(WIDTH * 0.9111111111111111, HEIGHT * 0.7903225806451613);
		FLIP9.quadTo(WIDTH * 0.9111111111111111, HEIGHT * 0.8225806451612904, WIDTH * 0.8444444444444444, HEIGHT * 0.8225806451612904);
		FLIP9.lineTo(WIDTH * 0.15555555555555556, HEIGHT * 0.8225806451612904);
		FLIP9.quadTo(WIDTH * 0.08888888888888889, HEIGHT * 0.8225806451612904, WIDTH * 0.08888888888888889, HEIGHT * 0.7903225806451613);
		FLIP9.lineTo(WIDTH * 0.1111111111111111, HEIGHT * 0.532258064516129);
		FLIP9.closePath();

		g2.setColor(new Color(0x565551));
		g2.fill(FLIP9);

		g2.dispose();
		IMAGE_ARRAY[8] = FLIP_IMG9;

		return IMAGE_ARRAY;
	}
	// </editor-fold>
}
