package entities;

import greenfoot.Greenfoot;

public class BaseMonster extends DamageableActor {
    private int life;
    public void act(){
        moveRandom();
        onDeath();
    }
    @Override
    protected void onDeath() {
        getWorld().removeObject(this);
        Greenfoot.stop();
    }

    public void moveRandom() {
        int rotation = Greenfoot.getRandomNumber(4) * 90;
        setRotation(rotation);
        if(canMove()){
            move(1);
        }

    }
    public void reseiveHit(int damage){
        life = life - damage;
    }
}
