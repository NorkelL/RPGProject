package ui;

import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import items.Item;

public class GhostItem extends Item {
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