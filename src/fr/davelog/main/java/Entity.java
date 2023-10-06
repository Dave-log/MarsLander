package fr.davelog.main.java;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public abstract class Entity {

	protected Point2D.Float position;
	protected int width, height;
	protected Rectangle hitbox;
	protected BufferedImage[] images;
	protected int currentImage;
	protected int indexAnim;
	protected int animCounter;
	protected int animSpeed;
	protected boolean isVisible;
	
	public Entity(Point2D.Float position, int width, int height) {
	
		this.position = position;
		this.width = width;
		this.height = height;
		this.isVisible = true;
		this.indexAnim = 0;
		this.animCounter = 0;
		this.animSpeed = 10;
	}
	
	abstract protected void initHitbox();
	
	protected void updateHitbox() {
		hitbox.x = (int)position.x;
		hitbox.y = (int)position.y;
	}
	
	public Rectangle getHitbox() {
		
		return hitbox;
	}
	
	protected void anim(int imgCount) {
		
		animCounter++;
		if (animCounter > animSpeed) {
			indexAnim += 1;
			indexAnim %= imgCount;
			animCounter = 0;
		}
		
		currentImage = indexAnim;
	};
	
	abstract protected void update();
	
	abstract protected void render(Graphics2D g2d, ImageManager img);
}
