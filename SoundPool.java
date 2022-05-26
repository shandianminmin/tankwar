package tankwar;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

public class SoundPool extends Thread {
	public static final String BG_MUSIC = "bgm.wav";
	public static final String SHOOT = "tank_fire.wav";
	public static final String BOOM = "explode.wav";
	public static final String TANK_MOVE = "tank_move.wav";
	AudioClip ac;
	public SoundPool(String path) {
		URL url = this.getClass().getResource("/musics/"+path);
		ac = Applet.newAudioClip(url);
	}
	
	public void play() {
		ac.play();
	}
	public void stopAc() {
		ac.stop();
	}
	public void loop() {
		ac.loop();
	}
	@Override
	public void run() {
		play();
	}
}
