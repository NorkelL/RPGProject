package items.waffen;

import blocks.Wall;
import entities.base.BaseMonster;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import items.Item;
import ui.Explosion;

//der abgeschossene pfeil, fliegt von selbst weiter bis er wand, rand oder monster trifft
// moveDelay waechst dabei, der pfeil wird also mit der flugstrecke langsamer
public class Arrow extends Item {
    private int speed = 1;
    private int damage;
    private int moveDelay = 40;
    private int delayCounter = 0;

    private boolean isFlying =false;

    public Arrow(int rotation,int damage) {
        this();
        GreenfootImage img = new GreenfootImage("Weapons/arrow.png");
        img.scale(45, 45);
        setImage(img);
        setRotation(rotation);
        this.isFlying = true;
        this.damage=damage;
    }

    public Arrow() {
        GreenfootImage img = new GreenfootImage("Weapons/arrow.png");
        img.scale(50, 50);
        setImage(img);
    }

    @Override
    public void act() {
        if (pausiert()) return;
        if (!isFlying) return;


        delayCounter++;
        if (delayCounter < moveDelay) {
            return;
        }
        delayCounter = 0;


        move(speed);
        moveDelay+=5;


        if (isAtEdge()) {
            getWorld().removeObject(this);
            return;
        }


        if (isTouching(Wall.class)) {
            getWorld().removeObject(this);
            return;
        }


        BaseMonster hitMonster = (BaseMonster) getOneIntersectingObject(BaseMonster.class);
        if (hitMonster != null) {
            hitMonster.takeDamage(damage);
            getWorld().addObject(new Explosion(30,20),getX(),getY());
            getWorld().removeObject(this);
            return;
        }
    }
}
