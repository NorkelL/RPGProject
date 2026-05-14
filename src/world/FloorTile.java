package world;

import entities.ImprovedActor;
import greenfoot.Color;
import greenfoot.GreenfootImage;

public class FloorTile extends ImprovedActor {

    public FloorTile() {
        GreenfootImage image = new GreenfootImage(40, 40);
        image.setColor(Color.BLACK);
        image.fill();
        setImage(image);
    }

    public FloorTile(String imagePath) {
        GreenfootImage image = new GreenfootImage(imagePath);
        image.scale(40, 40);
        setImage(image);
    }
}
