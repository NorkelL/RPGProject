package blocks;

import entities.Player;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import items.util.ItemTyp;
import world.GridWorld;

public class Chest extends Block {
    private boolean isOpen;

    private static final int SIZE = 40;

    public Chest() {
        isOpen = false;
        GreenfootImage img = new GreenfootImage("Blocks/Chest/ChestClosed.png");
        img.scale(SIZE, SIZE);
        setImage(img);
    }

    public void act() {
        if (isTouching(Player.class) && !isOpen && angeklickt()) {
            openChest();
        }
    }

    private boolean angeklickt() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse == null || mouse.getButton() != 1) return false;
        if (!Greenfoot.mouseClicked(null)) return false;
        // Klick-Toleranz: eine halbe Kachel in physischen Zellen.
        int tol = (getWorld() instanceof GridWorld)
            ? Math.max(1, ((GridWorld) getWorld()).cellsPerTile() / 2)
            : 1;
        return Math.abs(mouse.getX() - getX()) <= tol && Math.abs(mouse.getY() - getY()) <= tol;
    }

    public void openChest() {
        isOpen = true;
        GreenfootImage img = new GreenfootImage("Blocks/Chest/ChestOpen.png");
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
