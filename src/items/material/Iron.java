package items.material;

import greenfoot.GreenfootImage;
import items.Material;

public class Iron extends Material {

    private static final int SIZE = 32;

    public Iron(){
        super("Eisenbarren");
        GreenfootImage img = new GreenfootImage("items/Iron.png");
        img.scale(SIZE, SIZE);
        setImage(img);
    }

}
