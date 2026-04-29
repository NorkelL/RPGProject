package ui;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class InventorySlot extends Actor {
    private Actor item;

    private static final GreenfootImage EMPTY_SLOT_IMG = new GreenfootImage("EmptySlot.png");

    public InventorySlot() {
        setImage(new GreenfootImage(EMPTY_SLOT_IMG));
    }

    public InventorySlot(Actor item) {
        this();
        setItem(item);
    }

    public void setItem(Actor item) {
        this.item = item;
        if (item == null) {
            setImage(new GreenfootImage(EMPTY_SLOT_IMG));
        } else {
            getImage().drawImage(new GreenfootImage(item.getImage()), 5, 5);
        }
    }

    public Actor getItem() {
        return item;
    }

    @Override
    public void act() {
        super.act();
    }
}
