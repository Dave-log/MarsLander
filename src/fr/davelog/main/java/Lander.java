package fr.davelog.main.java;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

public class Lander extends Entity {

	private KeyHandler keyH;
	private SoundManager sounds;
	private DecimalFormat df;
	private Color oldColor, textColor;
	private Rectangle emptyFuelBar, fuelBar;
	private boolean engine, crashed;
	private double vx, vy, velocity;
	private int angle, fuel;
	public boolean isLanded;
	
	public Lander(Point2D.Float position, KeyHandler keyH, SoundManager sounds) {
		
		super(position, Constants.Lander.WIDTH, Constants.Lander.HEIGHT);
		this.keyH = keyH;
		this.sounds = sounds;
		this.df = new DecimalFormat("0.0");
		
		this.vx = 0;
		this.vy = 0;
		this.angle = 0;
		this.fuel = Constants.Lander.MAX_FUEL;
		this.engine = false;
		this.crashed = false;
		this.isLanded = false;
		
		initFuelBar();
		initHitbox();
	}
	
	private void initFuelBar() {
		
		emptyFuelBar = new Rectangle(50, 34, 150, 20);
		fuelBar = new Rectangle(52, 36, 147, 17);
	}
	
	public void drawFuelBar(Graphics2D g2d) {
	
		g2d.setColor(Color.WHITE);
		g2d.drawRect(emptyFuelBar.x, emptyFuelBar.y, emptyFuelBar.width, emptyFuelBar.height);
		Color c = fuel > 200 ? Color.GREEN : Color.RED;
		g2d.setColor(c);
		g2d.fillRect(fuelBar.x, fuelBar.y, fuel*fuelBar.width/Constants.Lander.MAX_FUEL, fuelBar.height);
	}
	 
	public void drawPanelInfos(Graphics2D g2d, FontManager fonts) {
		
		// PRINT ANGLE & VELOCITY VALUES IN GREEN OR RED TO HELP LANDING
		Integer formatAngle = (int)(ToolBox.normalizedAngle(angle));
		oldColor = g2d.getColor();
		
		g2d.setColor(Color.WHITE);
		ToolBox.drawCustomString(g2d, "FUEL", fonts.SPACE_GRUNGE, 30, 60, 30);
		
		textColor = (angle >= - 5 && angle <= 5) ? Color.GREEN : Color.RED;
		g2d.setColor(textColor);
		String str = "Angle = " + formatAngle.toString();
		ToolBox.drawCustomString(g2d, str, g2d.getFont(), 16, 60, 74);
		
		textColor = (vx <= 0.5 && vy <= 0.5) ? Color.GREEN : Color.RED;
		g2d.setColor(textColor);
		g2d.drawString("Vitesse = " + df.format(velocity), 60, 94);
		
		g2d.setColor(Color.WHITE);
		g2d.drawRoundRect(40, 5, 170, 100, 8, 8);
		
		g2d.setColor(oldColor);		
		
	}
	
	@Override
	protected void initHitbox() {
		
		hitbox = new Rectangle((int)position.x, (int)position.y, width, height-4);
	}
	
	protected void keyControl() {
		
		if(keyH.upPressed && fuel > 0) {
			engine = true;			
			double angleRad = Math.toRadians(angle-90);
			vx += Math.cos(angleRad) * Constants.Lander.SPEED;
			vy += Math.sin(angleRad) * Constants.Lander.SPEED;
			fuel--;
			sounds.playEffect(Constants.Sounds.ENGINE, true);
		} else {
			engine = false;
		}
		
		if(keyH.leftPressed) angle -= Constants.Lander.ANGULAR_SPEED;
		if(keyH.rightPressed) angle += Constants.Lander.ANGULAR_SPEED;
	}
	
	protected void move() {
		
		vy += Constants.Lander.GRAVITY;
		
		if(Math.abs(vx) >= Constants.Lander.MAX_VELOCITY) {
			vx = (vx < 0) ? (0 - Constants.Lander.MAX_VELOCITY) : Constants.Lander.MAX_VELOCITY;
		}
		if(Math.abs(vy) >= Constants.Lander.MAX_VELOCITY) {
			vy = (vy < 0) ? (0 - Constants.Lander.MAX_VELOCITY) : Constants.Lander.MAX_VELOCITY;
		}
		velocity = Math.sqrt((double)(vx*vx+vy*vy)*2000);
		position.x += vx;
		position.y += vy;
	}
	
	protected boolean screenCollision() {
		
		if (position.x < 0 || position.x + Constants.Lander.WIDTH > Constants.Screen.SCREEN_WIDTH) return true;
		if (position.y + Constants.Lander.HEIGHT >= Constants.Screen.SCREEN_HEIGHT) return true;
		return false;
	}
	
	public boolean canLand() {
	
		if (angle >= - 5 && angle <= 5 && vx <= 0.5 && vy <= 0.5) {
			return true;
		}
		return false;
	}
	
	public void crash() {
	
		crashed = true;
		isVisible = false;
	}
	
	@Override
	public void update() {
		
		if (isLanded) engine = false;
		if (!isLanded && !crashed) {
			keyControl();
			move();
			if (engine)	{
				anim(2);
			} else {
				currentImage = 2;
			}
		}
		updateHitbox();
		screenCollision();
		
		if (!engine) {
			sounds.stop(Constants.Sounds.ENGINE);
		}
	}
	
	@Override
	public void render(Graphics2D g2d, ImageManager img) {
		
		if (isVisible) {
			AffineTransform backup = g2d.getTransform();
			g2d.rotate(Math.toRadians(angle), position.x + Constants.Lander.WIDTH*0.5, position.y + Constants.Lander.HEIGHT*0.5);
			g2d.drawImage(img.IMG_LANDER[currentImage], null, (int)position.x, (int)position.y);
			g2d.setTransform(backup);
		}
	}

}
