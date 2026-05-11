package blocks;

import entities.Player;
import greenfoot.GreenfootImage;
import items.ItemTyp;
import world.DungeonLevel;

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
        // Chest interaction is handled by the player via the E key.
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
        if (getWorld() instanceof DungeonLevel) {
            ((DungeonLevel) getWorld()).addWorldObject(ItemTyp.zufaellig().erstelleItem(), getTileX(), getTileY());
        } else {
            getWorld().addObject(ItemTyp.zufaellig().erstelleItem(), getX(), getY());
        }
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean canBeOpenedBy(Player player) {
        int distance = Math.abs(player.getTileX() - getTileX()) + Math.abs(player.getTileY() - getTileY());
        return !isOpen && distance <= 1;
    }
}
