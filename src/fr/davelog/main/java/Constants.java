package fr.davelog.main.java;

public class Constants {

	public static class Screen {
	
		public static final int SCREEN_WIDTH = 1280;
		public static final int SCREEN_HEIGHT = 720;
	}
	
	public static class Game {
	
		public static final int UPS = 120;
		public static final int FPS = 60;
	}
	
	public static class Lander {
		public static final int WIDTH = 40;
		public static final int HEIGHT = 44;
		public static final double MAX_VELOCITY = 2.0;
		public static final double GRAVITY = 0.005;
		public static final int ANGULAR_SPEED = 1;
		public static final double SPEED = 0.02;
		public static final int MAX_FUEL = 1000;
	}
	
	public static class Platform {
		public static final int WIDTH = 120;
		public static final int HEIGHT = 24;
	}
	
	public static class Explosion {
		public static final int WIDTH = 42;
		public static final int HEIGHT = 42;
	}
	
	public static class Sounds {
		public static final int DEFEAT = 0;
		public static final int ENGINE = 1;
		public static final int VICTORY = 2;
	}
}
