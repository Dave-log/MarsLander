package fr.davelog.main.java;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private Game game;
	//private final Color SKY_COLOR = new Color(1.0f, 0.95f, 0.1f);
	public KeyHandler keyH;
	
	public GamePanel(Game game) {
		
		this.setPreferredSize(new Dimension(Constants.Screen.SCREEN_WIDTH, Constants.Screen.SCREEN_HEIGHT));
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		keyH = new KeyHandler();
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
		this.game = game;
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		game.render(g2d);
		g2d.dispose();
	}
}
