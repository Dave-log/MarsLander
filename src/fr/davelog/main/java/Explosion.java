package fr.davelog.main.java;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class Explosion extends Entity {
		
	private boolean alive;
	
	
	public Explosion(Point2D.Float position, int width, int height) {
		
		super(position, Constants.Explosion.WIDTH, Constants.Explosion.HEIGHT);
		this.alive = true;
	}
	
	@Override
	protected void initHitbox() {		
	}
	
	public void update() {

		if (alive) anim(6);
		if (indexAnim >= 5) alive = false;
	}

	protected void render(Graphics2D g2d, ImageManager img) {
		
		if (alive) {
			g2d.drawImage(img.IMG_EXPLOSION[currentImage], null, (int)position.x, (int)position.y);
		}
	}
}
