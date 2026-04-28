package entities;

import greenfoot.Greenfoot;

public class BaseMonster extends DamageableActor {
    public void act(){
        moveRandom();
    }
    @Override
    protected void onDeath() {
        getWorld().removeObject(this);
        Greenfoot.stop();
    }

    public void moveRandom() {
        int rotation = Greenfoot.getRandomNumber(4) * 90; // 0, 90, 180, 270
        setRotation(rotation);
        move(1);
    }
}
