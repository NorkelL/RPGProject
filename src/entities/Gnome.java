package entities;

import greenfoot.Greenfoot;

public class Gnome extends BaseMonster implements Hitting{
    private int life;

    public Gnome(int life) {
        this.life = life;
    }
    public void act(){
        moveRandom();
        onDeath();
    }
    @Override
    public void moveRandom() {
        int rotation = Greenfoot.getRandomNumber(4) * 90;
        setRotation(rotation);
        if (canMove()){
            move(2);
        }else{
            move(1);
        }
    }


}
