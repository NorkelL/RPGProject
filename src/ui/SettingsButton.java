package ui;

import core.GameStarter;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

import java.util.List;

public class SettingsButton extends UI {

    private GameStarter gameStarter;
    private  GreenfootImage settingsButton = new GreenfootImage("Map/Settings.png");
    private GreenfootImage settingsButtonGlowing = new GreenfootImage("SettingsGlowing.png");
    private boolean isScaled = false;

    public SettingsButton(GameStarter gameStarter){
        super();
        this.gameStarter = gameStarter;
        GreenfootImage SettingsButton = new GreenfootImage("Map/Settings.png");
        SettingsButton.scale(310,110);
        setImage(SettingsButton);

    }
    public void act() {
        mouseHover();
    }

    private void mouseHover(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (!isScaled) {
            settingsButton.scale(240, 110);
            settingsButtonGlowing.scale(240, 110);
            isScaled = true;
        }


        if (mouse != null) {
            setImage(settingsButton);
            List objects = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), SettingsButton.class);
            for (Object object : objects)
            {
                if (object == this)
                {
                    setImage(settingsButtonGlowing);
                }
            }
        }
    }
}
