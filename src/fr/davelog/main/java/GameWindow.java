package fr.davelog.main.java;

import javax.swing.JFrame;

public class GameWindow {

	private JFrame window;
	
	public GameWindow(GamePanel gamePanel) {
		
		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("OC Mars Lander");
		window.add(gamePanel);
		window.pack(); // sets the window size wraps properly the gamePanel size (1280*720)
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}
}
