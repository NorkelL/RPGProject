package Material;

import greenfoot.GreenfootImage;
import items.Material;
import items.util.Rarity;

public class Gold extends Material {

    private static final int SIZE = 32;

    public Gold() {
        super("Goldbarren");
        GreenfootImage img = new GreenfootImage("items/Gold.png");
        img.scale(SIZE, SIZE);
        setImage(img);


    }
}
