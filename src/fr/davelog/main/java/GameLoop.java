package fr.davelog.main.java;

public class GameLoop {

	private Game game;
	private GamePanel gamePanel;
	
	public GameLoop(Game game, GamePanel gamePanel) {
		
		this.game = game;
		this.gamePanel = gamePanel;
		//loop();
	}
	
	public void loop() {
		
		double timePerFrame = 1000000000.0 / Constants.Game.FPS;
		double timePerUpdate = 1000000000.0 / Constants.Game.UPS;
		
		long previousTime = System.nanoTime();
		double deltaU = 0;
		double deltaF = 0;
		
		// GameLoop
		while(true) {
						
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if (deltaU >= 1) {
				game.update();
				deltaU--;
			}
			
			if (deltaF >= 1) {
				gamePanel.repaint(); // call paintComponent method
				deltaF--;
			}
		}	
	}
}
