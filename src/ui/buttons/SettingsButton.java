package ui.buttons;

import core.GameStarter;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import ui.UI;
import ui.worlds.SettingsWorld;

import java.util.List;

public class SettingsButton extends UI {

    private GameStarter gameStarter;
    private  GreenfootImage settingsButton = new GreenfootImage("UI/MainMenu/Settings.png");
    private GreenfootImage settingsButtonGlowing = new GreenfootImage("UI/MainMenu/SettingsGlowing.png");
    private boolean isScaled = false;

    public SettingsButton(GameStarter gameStarter){
        super();
        this.gameStarter = gameStarter;
        GreenfootImage SettingsButton = new GreenfootImage("UI/MainMenu/Settings.png");
        SettingsButton.scale(310,110);
        setImage(SettingsButton);

    }
    public void act() {

        if (Greenfoot.mouseClicked(this)){
            Greenfoot.setWorld(new SettingsWorld());

        }


        mouseHover();
    }

    private void mouseHover(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (!isScaled) {
            settingsButton.scale(310, 110);
            settingsButtonGlowing.scale(310, 110);
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
