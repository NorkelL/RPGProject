package entities.enemies;

import entities.base.BaseMonster;
import entities.util.Direction;
import entities.util.Hitting;
import greenfoot.Greenfoot;

public class Skeleton extends BaseMonster implements Hitting {

    public Skeleton(int life) {
        super(life, 4, 2);
    }

    @Override
    public void act() {
        super.act();
    }

}