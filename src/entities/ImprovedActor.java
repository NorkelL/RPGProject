package entities;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class ImprovedActor extends Actor {
    private GreenfootImage currentImage;

    @Override
    public void setImage(GreenfootImage image) {
        currentImage = image;
        super.setImage(new ImprovedGreenfootImage(image));
    }

    public void draw(String text) {
        ImprovedGreenfootImage image = new ImprovedGreenfootImage(currentImage);
        image.drawString(text, 0, 10);
        super.setImage(image);
    }

    public void draw(int value) {
        draw(String.valueOf(value));
    }
}
