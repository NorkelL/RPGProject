package ui;

import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import items.Item;

public class ItemText extends Actor {

    private final Actor parent;

    public ItemText(GreenfootImage image, Actor parent) {
        setImage(image);
        this.parent = parent;
    }

    @Override
    public void act() {
        if(parent.getWorld() == null){getWorld().removeObject(this);}
    }

}
