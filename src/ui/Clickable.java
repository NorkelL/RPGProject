package ui;

import util.SoundManager;

public interface Clickable {

    public UI onClick();

    default void playClickSound() {
        SoundManager.play("button_click.mp3");
    }
}
