package entities.enemies;

import entities.base.BaseMonster;
import entities.util.Hitting;

public class Gnome extends BaseMonster implements Hitting {

    public Gnome(int life) {
        super(100,3,5);
    }

    @Override
    public void act() {
        super.move();
        super.act();
    }
}
