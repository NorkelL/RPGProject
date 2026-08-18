package ui;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import items.Item;

//das item das beim ziehen am mauszeiger klebt, der echte slot ist solange leer gezeichnet
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