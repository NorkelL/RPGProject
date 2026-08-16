package ui.buttons;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

import ui.worlds.SettingsWorld;

import java.util.List;

public class settingPauseButton extends PauseButtons{

    private GreenfootImage settingButton = new GreenfootImage("UI/PauseScreen/SettingButton.png");
    private GreenfootImage settingButtonGlowing = new GreenfootImage("UI/PauseScreen/SettingButtonGlow.png");
    private boolean isScaled = false;

    public settingPauseButton(){
        super("UI/PauseScreen/SettingButton.png");
    }

    public void act(){
        if (Greenfoot.mouseClicked(this)){
            // welt merken, damit escape zurueck ins pausierte level fuehrt
            Greenfoot.setWorld(new SettingsWorld(getWorld()));
        }

        mouseHover();
    }

    private void mouseHover(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (!isScaled) {
            settingButton.scale(310, 110);
            settingButtonGlowing.scale(310, 110);
            isScaled = true;
        }


        if (mouse != null) {
            setImage(settingButton);
            List objects = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), settingPauseButton.class);
            for (Object object : objects)
            {
                if (object == this)
                {
                    setImage(settingButtonGlowing);
                }
            }
        }
    }
}
