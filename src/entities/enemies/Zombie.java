package entities.enemies;

import entities.base.BaseMonster;
import entities.util.Hitting;
import greenfoot.Greenfoot;
import util.SoundManager;

public class Zombie extends BaseMonster implements Hitting {

    public Zombie(int life) {
        super(life, 2, 6);
        setXpDrop(40);
    }

    @Override
    public void act() {
        super.act();
    }

    @Override
    protected void onDeath() {
        SoundManager.play("death_zombie.mp3");
        super.onDeath();
    }
}
