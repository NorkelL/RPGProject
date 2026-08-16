package blocks;

import entities.Player;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.Color;
import world.DungeonLevel;

public class UpgradeTable extends Block {
    private static final int SIZE = 42;

    public UpgradeTable() {
        GreenfootImage img;
        try {
            img = new GreenfootImage("Blocks/UpgradeTable/UpgradeTable.png");
            img.scale(SIZE, SIZE);
        } catch (Exception e) {
            img = new GreenfootImage(SIZE, SIZE);
            img.setColor(new Color(139, 90, 43));
            img.fill();
            img.setColor(new Color(80, 50, 20));
            img.drawRect(0, 0, SIZE - 1, SIZE - 1);
        }
        setImage(img);
    }

    private boolean rWasDown = false;

    @Override
    public void act() {
        boolean rIsDown = Greenfoot.isKeyDown("R");
        if (isTouching(Player.class) && rIsDown && !rWasDown) {
            rWasDown = true;
            ((DungeonLevel) getWorld()).player.openInventoryFromTable(getWorld());
        } else {
            rWasDown = rIsDown;
        }
    }

}
