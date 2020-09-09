package realmcraft.input;


/*
 * a class for detecting keyboard input from the user.
 * booleans are are made for each key and other classes such as the Player class can
 * check to see if certain keys are pressed.
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private boolean[] keys, justPressed, cantPress;
	public boolean up, down, left, right;
	public boolean aUp, aDown, aLeft, aRight;
	public boolean space;
	public boolean p;
	
	public KeyManager() {
		keys = new boolean[256];	
		justPressed = new boolean[keys.length];
		cantPress = new boolean[keys.length];
	}
	
	//method called in the game loop constantly 
	public void tick() {
		for(int i = 0; i < keys.length; i++) {
			if (cantPress[i] && !keys[i]) {
				cantPress[i] = false;
			} else if (justPressed[i]) {
				cantPress[i] = true;
				justPressed[i] = false;
			}
			if(!cantPress[i] && keys[i]) {
				justPressed[i] = true;
			}
		}
		
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		
		aUp = keys[KeyEvent.VK_UP];
		aDown = keys[KeyEvent.VK_DOWN];
		aLeft = keys[KeyEvent.VK_LEFT];
		aRight = keys[KeyEvent.VK_RIGHT];

		space = keys[KeyEvent.VK_SPACE];

		p = keys[KeyEvent.VK_P];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) return;
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length) return;
		keys[e.getKeyCode()] = false;
	}
	
	public boolean keyJustPressed(int keyCode){
		if(keyCode < 0 || keyCode >= keys.length) return false;
		return justPressed[keyCode];
		}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

}
