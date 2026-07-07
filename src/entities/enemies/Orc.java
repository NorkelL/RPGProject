package entities.enemies;

import entities.base.BaseMonster;
import entities.util.Hitting;

public class Orc extends BaseMonster implements Hitting {

    public Orc(int life) {
        super(100,5,7);
    }
}