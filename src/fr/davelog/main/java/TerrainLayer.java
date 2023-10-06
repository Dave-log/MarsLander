package fr.davelog.main.java;

import java.awt.Color;

public enum TerrainLayer {

	PLAINS(450, 650, 6, 0.7, 100, new Color(0.68f, 0.17f, 0.02f)),
	HILLS(400, 500, 7, 0.6, 80, new Color(0.78f, 0.28f, 0.05f)),
	MOUNTAINS(350, 450, 8, 0.6, 80, new Color(0.96f, 0.49f, 0.14f));
	
	public int minY;
	public int maxY;
	public int iterations;
	public double smoothness;
	public int delta;
	public Color color;
	
	private TerrainLayer(int minY, int maxY, int iterations, double smoothness, int delta, Color color) {
		
		this.minY = minY;
		this.maxY = maxY;
		this.iterations = iterations;
		this.smoothness = smoothness;
		this.delta = delta;
		this.color = color;
	}
}
