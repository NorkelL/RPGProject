package blocks;

import entities.Player;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import items.ItemTyp;

public class Chest extends Block {
    private boolean isOpen;

    private static final int SIZE = 40;

    public Chest() {
        isOpen = false;
        GreenfootImage img = new GreenfootImage("Chest/ChestClosed.png");
        img.scale(SIZE, SIZE);
        setImage(img);
    }

    public void act() {
        if (isTouching(Player.class) && !isOpen && Greenfoot.isKeyDown("E")) {
            openChest();
        }
    }

    public void openChest() {
        isOpen = true;
        GreenfootImage img = new GreenfootImage("Chest/ChestOpen.png");
        img.scale(SIZE, SIZE);
        setImage(img);
        dropRandomItem();
    }

    private void dropRandomItem() {
        if (ItemTyp.values().length == 0) return;
        getWorld().addObject(ItemTyp.zufällig().erstelleItem(), getX(), getY());
    }

    public boolean isOpen() {
        return isOpen;
    }
}
