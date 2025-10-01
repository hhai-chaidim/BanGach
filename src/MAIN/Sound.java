package MAIN;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/Sounds/7070672630561.wav");
        soundURL[1] = getClass().getResource("/Sounds/button-305770.wav");
        soundURL[2] = getClass().getResource("/Sounds/power-up-type-1-230548.wav");
        soundURL[3] = getClass().getResource("/Sounds/retro-select-236670.wav");
        soundURL[4] = getClass().getResource("/Sounds/beep-329314.wav");
    }

    public void setFile(int i) {
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e){

        }
    }
    public void play() {
        clip.start();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void stop() {
        clip.stop();
    }
}
