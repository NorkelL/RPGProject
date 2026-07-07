package entities.enemies;

import entities.base.BaseMonster;
import entities.util.Hitting;
import greenfoot.Greenfoot;

public class Zombie extends BaseMonster implements Hitting {

    public Zombie(int life) {
        super(life, 2, 2);
    }

    @Override
    public void act() {
        super.act();
    }
}
