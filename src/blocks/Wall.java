package blocks;

import entities.ImprovedActor;
import greenfoot.GreenfootImage;

public class Wall extends ImprovedActor {

    public Wall() {
        GreenfootImage wall = new GreenfootImage("Map/WallTile.png");
        wall.scale(40, 60);

        GreenfootImage image = new GreenfootImage(40, 80);
        image.drawImage(wall, 0, 0);
        setImage(image);
    }
}
