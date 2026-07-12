package ui;

import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

public class GhostItem extends Actor {
    public GhostItem(GreenfootImage itemImage) {
        setImage(new GreenfootImage(itemImage));
    }

    @Override
    public void act() {
        // Der Geist folgt in jedem Frame exakt den Pixel-Koordinaten der Maus
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null) {
            setLocation(mouse.getX(), mouse.getY());
        }
    }
}