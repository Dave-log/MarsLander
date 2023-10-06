package fr.davelog.main.java;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundManager {

	private Clip[] effects;
	public int currentSoundId;
	
	public SoundManager() {
	
		loadEffects();
	}
	
	private void loadEffects() {
		
		String[] effectNames = {"defeat", "engine", "victory"};
		effects = new Clip[effectNames.length];
		for (int i = 0; i < effects.length; i++)
			effects[i] = getClip(effectNames[i]);
	}
	
	private Clip getClip(String name) {
		
		URL url = getClass().getResource("/sounds/" + name + ".wav");
		AudioInputStream audio;
		
		try {
			audio = AudioSystem.getAudioInputStream(url);
			Clip clip = AudioSystem.getClip();
			clip.open(audio);
			return clip;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public void playEffect(int effect, boolean loop) {
		
		if (!effects[effect].isRunning()) {
			effects[effect].start();
			currentSoundId = effect;
			if(loop) effects[effect].loop(Clip.LOOP_CONTINUOUSLY);
		}
	}
	
	public void stop(int effect) {
		
		effects[effect].stop();
		effects[effect].setMicrosecondPosition(0);
	}
}
