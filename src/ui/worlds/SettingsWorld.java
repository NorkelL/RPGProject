package ui.worlds;

import core.GameStarter;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import ui.Buttons.KeyButton;
import ui.Settings;
import ui.Buttons.StandardButton;

public class SettingsWorld extends World {

    public static boolean waitingForKey = false;
    public static String switchKey = "";
    public boolean blinkActivated = false;



    public boolean isBlinkActivated() {
        return blinkActivated;
    }

    public void setBlinkActivated(boolean blinkActivated) {
        this.blinkActivated = blinkActivated;
    }



    public SettingsWorld() {
        super(1376, 1200, 1);
        showText("Press ESC to go back", 150, 50);
        GreenfootImage bg = new GreenfootImage("UI/Inventory/BackgroundFullInventory.png");
        bg.scale(getWidth() * getCellSize(), getHeight() * getCellSize());
        setBackground(bg);
        addObject(new StandardButton("forward"), 220, 180);
        addObject(new StandardButton("backward"), 220, 300);
        addObject(new StandardButton("left"), 220, 420);
        addObject(new StandardButton("right"), 220, 540);
        addObject(new StandardButton("attack"), 220, 660);
        addObject(new StandardButton("take Item"),220,780);
        addObject(new StandardButton("Put Item"),220,900);
        addObject(new StandardButton("Inventory"), 220, 1020);
        addObject(new StandardButton("Sound"), 220, 1140);


        addObject(new KeyButton("W","up"), 700, 180);
        addObject(new KeyButton("S","down"), 700, 300);
        addObject(new KeyButton("A","left"), 700, 420);
        addObject(new KeyButton("D","right"), 700, 540);
        addObject(new KeyButton("LeftClick","attack"), 700, 660);
        addObject(new KeyButton("t","takeItem"), 700, 780);
        addObject(new KeyButton("p","putItem"), 700, 900);
        addObject(new KeyButton("e","toggleInventory"), 700, 1020);
        addObject(new KeyButton("on/off","Sound"), 700, 1140);


    }

    public void act() {


        if (Greenfoot.isKeyDown("escape")) {
            Greenfoot.setWorld(new MainMenu(new GameStarter()));
        }

    }




}
