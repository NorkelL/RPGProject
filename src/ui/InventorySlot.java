package ui;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class InventorySlot extends Actor {
    private Actor item;

    private static final int SIZE = 40;
    private static final GreenfootImage EMPTY_SLOT_IMG;
    static {
        EMPTY_SLOT_IMG = new GreenfootImage("EmptySlot.png");
        EMPTY_SLOT_IMG.scale(SIZE, SIZE);
    }

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
            GreenfootImage preview = new GreenfootImage(item.getImage());
            preview.scale(SIZE - 10, SIZE - 10);
            getImage().drawImage(preview, 5, 5);
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
