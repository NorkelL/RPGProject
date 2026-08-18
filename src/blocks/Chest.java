package blocks;

import entities.Player;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import items.util.ItemTyp;
import util.SoundManager;

//truhe, gibt beim oeffnen mit R ein zufaelliges item aus
public class Chest extends Block {
    private boolean isOpen;

    private static final int SIZE = 32;

    public Chest() {
        isOpen = false;
        GreenfootImage img = new GreenfootImage("Blocks/Chest/ChestClosed.png");
        img.scale(SIZE, SIZE);
        setImage(img);
    }

    public void act() {
        if (pausiert()) return;

        if (isTouching(Player.class) && !isOpen && Greenfoot.isKeyDown("R")) {
            openChest();
        }
    }

    private boolean angeklickt() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse == null || mouse.getButton() != 1) return false;
        if (!Greenfoot.mouseClicked(null)) return false;
        return Math.abs(mouse.getX() - getX()) <= 1 && Math.abs(mouse.getY() - getY()) <= 1;
    }

    public void openChest() {
        isOpen = true;
        SoundManager.play("chest_open.mp3");
        GreenfootImage img = new GreenfootImage("Blocks/Chest/ChestOpen.png");
        img.scale(SIZE, SIZE);
        setImage(img);
        dropRandomItem();
    }

    //beim laden eines saves: truhe steht offen da, der loot kam ja schon beim ersten mal
    public void openChestWithoutDrops() {
        isOpen = true;
        GreenfootImage img = new GreenfootImage("Blocks/Chest/ChestOpen.png");
        img.scale(SIZE, SIZE);
        setImage(img);
    }

    private void dropRandomItem() {
        if (ItemTyp.values().length == 0) return;
        getWorld().addObject(ItemTyp.zufällig().erstelleItem(), getX(), getY());
    }

    public boolean isOpen() {
        return isOpen;
    }
}
