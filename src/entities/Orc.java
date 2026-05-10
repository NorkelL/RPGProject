package entities;

import greenfoot.Greenfoot;

public class Orc extends BaseMonster implements Hitting {
    private int life;

    public Orc(int life) {
        this.life = life;
    }

    public void act() {
        moveRandom();
        onDeath();
    }

    @Override
    public void moveRandom() {
        int rotation = Greenfoot.getRandomNumber(4) * 90;
        setRotation(rotation);
        if (canMove()) {
            move(3);
        } else {
            move(1);
        }
    }
}