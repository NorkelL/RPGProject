package ui.buttons;

import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import ui.UI;

public abstract class PauseButtons extends UI{

    public PauseButtons (String imagePath){
        GreenfootImage img = new GreenfootImage(imagePath);
        img.scale(310, 110);   // Größe der Knöpfe - hier anpassen!
        setImage(img);
    }
}
