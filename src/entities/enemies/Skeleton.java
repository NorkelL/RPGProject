package entities.enemies;

import entities.base.BaseMonster;
import entities.util.Direction;
import entities.util.Hitting;
import greenfoot.Greenfoot;
import util.SoundManager;

public class Skeleton extends BaseMonster implements Hitting {

    public Skeleton(int life) {
        super(life, 3, 5);   // leash muss groesser als agro sein, sonst laesst es nie wieder los
        setXpDrop(60);
    }

    @Override
    public void act() {
        super.act();
    }

    @Override
    protected void onDeath() {
        SoundManager.play("death_skeleton.mp3");
        super.onDeath();
    }
}