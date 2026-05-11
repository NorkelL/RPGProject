package ui;

import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;

public class UI extends Actor {
    protected GreenfootImage createPanelImage(int width, int height, String title) {
        GreenfootImage image = new GreenfootImage(width, height);
        image.setColor(new Color(27, 36, 44, 220));
        image.fillRect(0, 0, width - 1, height - 1);
        image.setColor(new Color(183, 145, 84));
        image.drawRect(0, 0, width - 1, height - 1);
        image.drawRect(2, 2, width - 5, height - 5);
        image.setColor(new Color(231, 218, 191));
        image.drawString(title, 12, 20);
        return image;
    }
}
