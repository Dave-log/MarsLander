package fr.davelog.main.java;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Landscape {

	public static ArrayList<Landscape> lstLandscapes = new ArrayList<Landscape>(5);
	
	private Point2D.Float startPos;
	private Point2D.Float endPos;
	private int iterations, deltaY;
	private double smoothness;
	private TerrainLayer type;
	public ArrayList<Point2D.Float> lstPoints;
	public Color color;
	public static Point2D.Float platformPos;
	
	public Landscape(TerrainLayer type) {
		
		this.type = type;
		this.startPos = new Point2D.Float(0, ToolBox.getRandomNumber(type.minY, type.maxY));
		this.endPos = new Point2D.Float(Constants.Screen.SCREEN_WIDTH, ToolBox.getRandomNumber(type.minY, type.maxY));
		this.iterations = type.iterations;
		this.smoothness = type.smoothness;
		this.lstPoints = new ArrayList<Point2D.Float>();
		this.deltaY = type.delta;
		this.color = type.color;
		
		this.create();
		
		if (type == TerrainLayer.PLAINS) {
			replacePlatformPoints();
		}
		
		lstLandscapes.add(this);
	}
	
	private void initSegment() {
		
		lstPoints.add(startPos);
		lstPoints.add(endPos);
	}
	
	private void computePoints() {
		
		/*
		 * Here is the implementation of Midpoint Displacement Algorithm (a sort of noise). The idea is to start from a large horizontal segment (2 points at screen borders).
		 * Next we take the middle on x-axis and add a random delta to y-value. That's one iteration.
		 * Then we repeat the operation but now we have 2 segments. The more we iterates, more the landscape is detailed and smooth.
		 */
		for(int i = 0; i < iterations; i++) {
			ArrayList<Point2D.Float> lstNewPoints = new ArrayList<Point2D.Float>();
			for(int n = 0; n < lstPoints.size() - 1; n++) {
				Point2D.Float newPoint = new Point2D.Float(
						(float)((lstPoints.get(n).x + lstPoints.get(n+1).x) * 0.5),
						(float)(((lstPoints.get(n+1).y + lstPoints.get(n).y) * 0.5) + ToolBox.getRandomNumber(-deltaY, deltaY))
				);
				lstNewPoints.add(newPoint);
			}
			
			for(int n = 0; n < lstNewPoints.size(); n++) {
				lstPoints.add(n*2+1, lstNewPoints.get(n)); // insert newPoints into lstPoints at odd index to keep logic order.
			}
			
			deltaY *= smoothness; // it makes the landscape more realistic.
		}
	}
	
	private void replacePlatformPoints() {
		
		/* 
		 * This method sets the platform position on landscape "plains". Problem is the landscape is not flat. 
		 * The trick is to force x and y values for points under the platform (which has width of 120pixels) to be equals to the point at randomIndex.
		 * Adding a 10 pixels security at y-value to avoid lander collides with platform AND terrain at same time.
		 */ 
		platformPos = new Point2D.Float();
		int randomIndex = ToolBox.getRandomNumber(5, 50);
		platformPos = lstPoints.get(randomIndex);
		for (int i = 0; i < 6; i ++) {
			lstPoints.set(randomIndex + i, new Point2D.Float(platformPos.x + 20*i, platformPos.y + 10));
		}
	}
	
	private void create() {
		
		initSegment();
		computePoints();
	}

	private int getHeight(ArrayList<Point2D.Float> list, int i) {
		
		double step = list.get(1).x - list.get(0).x;
		double w = (double)i / step;
		double lambda = w - Math.floor(w);
		int A = (int)list.get( (int)w ).y;
		int B = (int)list.get( (int)w + 1 ).y;
		return (int)((1-lambda) * A + lambda * B);
	}
	
	public void render(Graphics2D g2d) {
		
		for(int i = 0; i < endPos.x; i++) {
			int h = getHeight(lstPoints, i);
			g2d.fillRect(i, h, 1, Constants.Screen.SCREEN_HEIGHT - h);
		}
		
		if (type == TerrainLayer.PLAINS) {
			g2d.setColor(Color.BLACK);
			for (int i = 0; i < lstPoints.size() - 1; i++) {
				g2d.drawLine((int)lstPoints.get(i).x, (int)lstPoints.get(i).y, (int)lstPoints.get(i+1).x, (int)lstPoints.get(i+1).y);
			}
		}
	}
	
	public static Point2D.Float getPlatformPosition() {
		
		return platformPos;
	}
}
