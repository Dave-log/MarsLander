package fr.davelog.main.java;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

public class Platform extends Entity {

	
	public Platform(Point2D.Float position) {

		super(position, Constants.Platform.WIDTH, Constants.Platform.HEIGHT);
		this.animSpeed = 3;

		initHitbox();
	}

	@Override
	protected void initHitbox() {
		
		hitbox = new Rectangle((int)position.x, (int)position.y, width, height);
	}

	@Override
	protected void update() {
		
		anim(8);
		updateHitbox();
	}

	@Override
	public void render(Graphics2D g2d, ImageManager img) {
		
		g2d.drawImage(img.PLATFORM, null, (int)position.x, (int)position.y);
		g2d.drawImage(img.IMG_BEACON[currentImage], null, (int)position.x, (int)position.y - img.IMG_BEACON[0].getHeight());
	}
}
