package ui;

import greenfoot.Actor;
import greenfoot.GreenfootImage;
import javafx.scene.image.Image;

public class InventorySlot extends Actor {
    private Actor item;


     private GreenfootImage Image = new GreenfootImage("InventorySlot.png");





    public InventorySlot() {

        GreenfootImage Image = new GreenfootImage("InventorySlot.png");
        Image.scale(60,60);
        setImage(Image);
    }

    public InventorySlot(Actor item) {
        this();
        setItem(item);
    }

    public void setItem(Actor item) {
        this.item = item;
        if (item == null) {
            Image.scale(60,60);
            setImage(new GreenfootImage(Image));
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
