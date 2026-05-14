package blocks;

import entities.ImprovedActor;
import greenfoot.GreenfootImage;

public class Wall extends ImprovedActor {
    private static final int tilesize = 40;
    private static final int frontheight = 60;
    private static final int wallimageheight = tilesize * 2;

    public enum Design {
        top,
        front
    }

    public Wall() {
        this(Design.top);
    }

    public Wall(Design design) {
        GreenfootImage image = new GreenfootImage(tilesize, wallimageheight);
        if (design == Design.front) {
            GreenfootImage source = new GreenfootImage("Map/WallFront.png");
            source.scale(tilesize, frontheight);
            image.drawImage(source, 0, 0);
        } else {
            GreenfootImage source = new GreenfootImage("Map/WallTop.png");
            source.scale(tilesize, tilesize);
            image.drawImage(source, 0, 0);
        }
        setImage(image);
    }
}
