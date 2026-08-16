package ui.buttons;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import ui.UI;
import util.SoundManager;

public abstract class PauseButtons extends UI{

    public PauseButtons (String imagePath){
        GreenfootImage img = new GreenfootImage(imagePath);
        img.scale(310, 110);
        setImage(img);
    }

    @Override
    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            SoundManager.play("button_click.mp3");
            onPauseClick();
        }
    }

    protected void onPauseClick() {}
}
