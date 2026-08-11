package ui;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class Explosion extends Actor {

    private int moveDelay;
    private int delayCounter = 0;

    public Explosion(int Scale,int Countdown){
        GreenfootImage img = new GreenfootImage("UI/Explosion.png");
        img.scale(Scale,Scale);
        setImage(img);
        this.moveDelay=Countdown;
    }


    @Override
    public void act() {
        super.act();
        delayCounter++;
        if (delayCounter < moveDelay) {
            return;
        }
        getWorld().removeObject(this);
    }
}
