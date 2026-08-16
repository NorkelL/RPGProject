package ui.worlds;

import core.GameStarter;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import ui.buttons.KeyButton;
import ui.buttons.StandardButton;

public class SettingsWorld extends World {

    public static boolean waitingForKey = false;
    public static String switchKey = "";
    public boolean blinkActivated = false;
    private World lastWorld;   // null heisst: kam aus dem hauptmenue

    public boolean isBlinkActivated() {
        return blinkActivated;
    }

    public void setBlinkActivated(boolean blinkActivated) {
        this.blinkActivated = blinkActivated;
    }

    public SettingsWorld() {
        this(null);
    }

    public SettingsWorld(World lastWorld) {
        super(1376, 1300, 1);
        this.lastWorld = lastWorld;
        showText("Press ESC to go back", 150, 50);
        GreenfootImage bg = new GreenfootImage("UI/Inventory/BackgroundFullInventory.png");
        bg.scale(getWidth() * getCellSize(), getHeight() * getCellSize());
        setBackground(bg);


        addObject(new StandardButton("forward"), 200, 200);
        addObject(new KeyButton("W","up"), 480, 200);

        addObject(new StandardButton("backward"), 200, 340);
        addObject(new KeyButton("S","down"), 480, 340);

        addObject(new StandardButton("left"), 200, 480);
        addObject(new KeyButton("A","left"), 480, 480);

        addObject(new StandardButton("right"), 200, 620);
        addObject(new KeyButton("D","right"), 480, 620);

        addObject(new StandardButton("attack"), 200, 760);
        addObject(new KeyButton("LeftClick","attack"), 480, 760);


        addObject(new StandardButton("take Item"), 800, 200);
        addObject(new KeyButton("t","takeItem"), 1080, 200);

        addObject(new StandardButton("Put Item"), 800, 340);
        addObject(new KeyButton("p","putItem"), 1080, 340);

        addObject(new StandardButton("use Item"), 800, 480);
        addObject(new KeyButton("f","useItem"), 1080, 480);

        addObject(new StandardButton("Inventory"), 800, 620);
        addObject(new KeyButton("e","toggleInventory"), 1080, 620);

        addObject(new StandardButton("Sound"), 800, 760);
        addObject(new KeyButton("on/off","Sound"), 1080, 760);

        addObject(new StandardButton("Music"), 800, 900);
        addObject(new KeyButton("on/off","Music"), 1080, 900);
    }

    public void act() {
        if (Greenfoot.isKeyDown("escape")) {
            if(lastWorld != null){
                Greenfoot.setWorld(lastWorld);
            }else{
                Greenfoot.setWorld(new MainMenu(new GameStarter()));
            }
        }
    }
}