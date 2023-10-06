package fr.davelog.main.java;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public boolean upPressed, rightPressed, leftPressed, spacePressed;
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_Z || key == KeyEvent.VK_UP) upPressed = true;
		if(key == KeyEvent.VK_Q || key == KeyEvent.VK_LEFT) leftPressed = true;
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) rightPressed = true;		
		if(key == KeyEvent.VK_SPACE) spacePressed = true;
		
		if(key == KeyEvent.VK_ESCAPE) System.exit(0);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_Z || key == KeyEvent.VK_UP) upPressed = false;
		if(key == KeyEvent.VK_Q || key == KeyEvent.VK_LEFT) leftPressed = false;
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) rightPressed = false;		
		if(key == KeyEvent.VK_SPACE) spacePressed = false;
	}

}
