package fr.davelog.main.java;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Game implements Runnable {

	// SYSTEM
	private GamePanel gamePanel;
	private GameLoop gameLoop;
	private Thread gameThread;
	private ImageManager images;
	private SoundManager sounds;
	private FontManager fonts;
	
	// ENTITIES & OBJECTS
	private Lander lander;
	private Platform platform;
	private Explosion explosion;
	private BufferedImage landscapesImage;
	private Landscape plains;
	
	// ATTRIBUTES
	private boolean victory, defeat;
	private int score;
	
	public Game() {
		
		this.victory = false;
		this.defeat = false;
		this.score = 0;
		
		gamePanel = new GamePanel(this);
		loadResources();
		initClasses();
		new GameWindow(gamePanel);
		
		startGameLoop();
	}
	
	private void loadResources() {
		
		images = new ImageManager();
		sounds = new SoundManager();
		fonts = new FontManager();
		}
	
	private void startGameLoop() {
		
		gameLoop = new GameLoop(this, gamePanel);
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	private void initClasses() {
		
		lander = new Lander(new Point2D.Float(200, 100), gamePanel.keyH, sounds);
		
		plains = new Landscape(TerrainLayer.PLAINS);
		new Landscape(TerrainLayer.HILLS);
		new Landscape(TerrainLayer.MOUNTAINS);
		landscapesImage = drawLandscapes();
		
		platform = new Platform(Landscape.getPlatformPosition());
	}
	
	private void restartGame() {
		
		if (victory) score += 1;
		victory = false;
		defeat = false;
		sounds.stop(Constants.Sounds.DEFEAT);
		sounds.stop(Constants.Sounds.VICTORY);
		landscapesImage = null;
		Landscape.lstLandscapes.clear();
		explosion = null;
		initClasses();
	}
	
	private BufferedImage drawLandscapes() {
		
		BufferedImage bufferedImage = new BufferedImage(Constants.Screen.SCREEN_WIDTH, Constants.Screen.SCREEN_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = bufferedImage.createGraphics();

		for(int i = Landscape.lstLandscapes.size() - 1; i >= 0 ; i--) {
			g.setColor(Landscape.lstLandscapes.get(i).color);
			Landscape.lstLandscapes.get(i).render(g);
		}
		
		g.dispose();
		return bufferedImage;
	}
	
	private void collisions() {
		
		if(lander.hitbox.intersects(platform.hitbox)) {
			if(lander.canLand()) {
				lander.isLanded = true;
				victory = true;
				sounds.playEffect(Constants.Sounds.VICTORY, false);
			} else {
				lander.crash();
				defeat = true;
				if(explosion == null) explosion = new Explosion(lander.position, Constants.Explosion.WIDTH, Constants.Explosion.HEIGHT);
				sounds.playEffect(Constants.Sounds.DEFEAT, false);
			}
		}
		if(lander.screenCollision()) {
			lander.crash();
			defeat = true;
			if(explosion == null) explosion = new Explosion(lander.position, Constants.Explosion.WIDTH, Constants.Explosion.HEIGHT);
		}
		for(int i = 0; i < plains.lstPoints.size() - 1; i++) {
			if(lander.hitbox.intersectsLine(
					plains.lstPoints.get(i).x, plains.lstPoints.get(i).y, 
					plains.lstPoints.get(i+1).x, plains.lstPoints.get(i+1).y)) {
				lander.crash();
				defeat = true;
				if(explosion == null) explosion = new Explosion(lander.position, Constants.Explosion.WIDTH, Constants.Explosion.HEIGHT);
				sounds.playEffect(Constants.Sounds.DEFEAT, false);
			}
		}
	}
	
	private void drawGUI(Graphics2D g2d) {
	
		lander.drawPanelInfos(g2d, fonts);
		lander.drawFuelBar(g2d);
		
		g2d.setColor(Color.WHITE);
		ToolBox.drawCenteredString(g2d, "SCORE : " + score, fonts.SPACE_GRUNGE, 30, 0, Constants.Screen.SCREEN_WIDTH, 30);
		if (victory) {
			ToolBox.drawCenteredString(g2d, "LANDING SUCCESSFULL !", fonts.NASALIZATION_RG, 30, 0, Constants.Screen.SCREEN_WIDTH, 200);
			ToolBox.drawCenteredString(g2d, "Continue ? (space)", fonts.NASALIZATION_RG, 20, 0, Constants.Screen.SCREEN_WIDTH, 300);
			ToolBox.drawCenteredString(g2d, "Back to Earth. (escape)", fonts.NASALIZATION_RG, 20, 0, Constants.Screen.SCREEN_WIDTH, 350);
		}
		if (defeat) {
			ToolBox.drawCenteredString(g2d, "YOU CRASHED THE LANDER...", fonts.NASALIZATION_RG, 30, 0, Constants.Screen.SCREEN_WIDTH, 200);
			ToolBox.drawCenteredString(g2d, "Try Again ? (space)", fonts.NASALIZATION_RG, 20, 0, Constants.Screen.SCREEN_WIDTH, 300);
			ToolBox.drawCenteredString(g2d, "Quit. (escape)", fonts.NASALIZATION_RG, 20, 0, Constants.Screen.SCREEN_WIDTH, 350);
		}
	}
	
	public void update() {
		
		lander.update();
		platform.update();
		collisions();
		if(explosion != null) explosion.update();
		
		if ((victory || defeat) && gamePanel.keyH.spacePressed) restartGame();
	}
	
	public void render(Graphics2D g2d) {
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

		//BACKGROUND
		g2d.drawImage(images.EARTH, 1000, 100, null);
		g2d.drawImage(landscapesImage, 0, 0, null);
		
		//ENTITIES
		platform.render(g2d, images);
		lander.render(g2d, images);
		if(explosion != null) explosion.render(g2d, images);
		
		//GUI
		drawGUI(g2d);
	}

	@Override
	public void run() {
		
		gameLoop.loop();
	}
}
