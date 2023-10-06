package fr.davelog.main.java;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManager {

	public BufferedImage[] IMG_LANDER;
	public BufferedImage[] IMG_BEACON;
	public BufferedImage[] IMG_EXPLOSION;
	public BufferedImage EARTH;
	public BufferedImage PLATFORM;
	
	public ImageManager() {
		
		this.EARTH = loadImage("/background/earth");
		this.PLATFORM = loadImage("/platform/platform");
		this.IMG_LANDER = loadImage("/lander/lander", 3);
		this.IMG_BEACON = loadImage("/platform/beacon", 8);
		this.IMG_EXPLOSION = loadImage("/explosion/explosion", 6);
	}
	
	private BufferedImage loadImage(String path) {
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResourceAsStream(path + ".png"));
		}catch(IOException e) {
			e.printStackTrace();
		}
		return img;
		
	}
	
	private BufferedImage[] loadImage(String path, int size) {
		
		BufferedImage[] imgTab = new BufferedImage[size];
		try {
			for (int i = 0; i < size; i++) {
				imgTab[i] = ImageIO.read(getClass().getResourceAsStream(path + i + ".png"));
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return imgTab;
	}
}
