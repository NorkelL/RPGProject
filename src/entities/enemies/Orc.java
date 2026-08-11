package entities.enemies;

import entities.base.BaseMonster;
import entities.util.Hitting;
import util.SoundManager;

public class Orc extends BaseMonster implements Hitting {

    public Orc(int life) {
        super(100,5,7);
    }

    @Override
    protected void onDeath() {
        SoundManager.play("death_orc.mp3");
        super.onDeath();
    }
}