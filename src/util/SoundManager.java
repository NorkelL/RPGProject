package util;

import greenfoot.GreenfootSound;
import ui.Settings;

//alle sounds laufen hier durch, damit die lautstaerke und der on/off schalter an einer stelle sitzen
public class SoundManager {

    private static GreenfootSound bgMusic;

    public static void play(String filename) {
        play(filename, Settings.volume);
    }

    public static void play(String filename, int volume) {
        if (!Settings.soundOn) return;
        try {
            GreenfootSound sound = new GreenfootSound("sounds/" + filename);
            sound.setVolume(Math.max(0, Math.min(100, volume)));
            sound.play();
        } catch (Exception e) {
            // Datei fehlt noch — kein Crash
        }
    }

    public static void startMusic() {
        if (bgMusic == null) {
            try {
                bgMusic = new GreenfootSound("sounds/background.mp3");
                bgMusic.setVolume(15);
            } catch (Exception e) {
                return;
            }
        }
        if (Settings.musicOn && !bgMusic.isPlaying()) {
            bgMusic.playLoop();
        }
    }

    public static void stopMusic() {
        if (bgMusic != null && bgMusic.isPlaying()) {
            bgMusic.stop();
        }
    }

    public static void toggleMusic() {
        Settings.musicOn = !Settings.musicOn;
        if (Settings.musicOn) {
            startMusic();
        } else {
            stopMusic();
        }
    }
}
