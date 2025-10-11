package MAIN;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];
    FloatControl volumeControl;

    public Sound() {
        soundURL[0] = getClass().getResource("/Sounds/7070672630561.wav");
        soundURL[1] = getClass().getResource("/Sounds/button-305770.wav");
        soundURL[2] = getClass().getResource("/Sounds/power-up-type-1-230548.wav");
        soundURL[3] = getClass().getResource("/Sounds/retro-select-236670.wav");
        soundURL[4] = getClass().getResource("/Sounds/beep-329314.wav");
        soundURL[5] = getClass().getResource("/Sounds/explosion-9-340460_1.wav");
    }

    public void setFile(int i) {
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            }
        } catch (Exception e){

        }
    }

    public void setVolume(float volume) {
        if (volumeControl != null) {

            volume = Math.max(0.0f, Math.min(1.0f, volume));

            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();

            float dB;
            if (volume == 0.0f) {
                dB = min;
            } else {
                float range = max - min;
                dB = min + range * (float)Math.pow(volume, 0.5);
            }

            volumeControl.setValue(dB);
        }
    }

    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }
}
