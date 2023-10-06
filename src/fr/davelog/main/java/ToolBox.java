package fr.davelog.main.java;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.Random;

public final class ToolBox {

	private static Random random = new Random();
	
	public static int getRandomNumber(int start, int end) {

		return start + random.nextInt(end - start + 1);
	}
	
	public static float getRandomNumber(float start, float end) {
		
		return start + random.nextFloat(end - start + 1);
	}

	public static double getRandomNumber(double start, double end) {
		
		return start + random.nextDouble(end - start + 1);
	}
	
	public static long getRandomNumber(long start, long end) {
		
		return start + random.nextLong(end - start + 1);
	}
	
	public static double normalizedAngle(double angle) {
		
		return angle -= Math.floor(angle / 360 + 0.5) * 360;
	}
	
	public static void drawCustomString(Graphics2D g2d, String str, Font font, float fontSize, int x, int y) {
		
		g2d.setFont(font.deriveFont(Font.PLAIN, fontSize));
		g2d.drawString(str, x, y);
	}
	public static void drawCenteredString(Graphics2D g2d, String str, Font font, float fontSize, int x1, int x2, int y) {
		
		g2d.setFont(font.deriveFont(Font.PLAIN, fontSize));
		FontMetrics metrics = g2d.getFontMetrics();
		g2d.drawString(str, x1 + (x2 - metrics.stringWidth(str)) / 2, y);
	}
}

