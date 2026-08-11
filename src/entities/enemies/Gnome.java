package entities.enemies;

import entities.base.BaseMonster;
import entities.util.Hitting;
import util.SoundManager;

public class Gnome extends BaseMonster implements Hitting {

    public Gnome(int life) {
        super(100,3,5);
    }

    @Override
    public void act() {
        super.move();
        super.act();
    }

    @Override
    protected void onDeath() {
        SoundManager.play("death_gnome.mp3");
        super.onDeath();
    }
}
