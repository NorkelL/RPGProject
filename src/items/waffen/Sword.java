package items.waffen;

import greenfoot.GreenfootImage;
import items.Waffen;

public class Sword extends Waffen {

    private static final int SIZE = 32;

    public Sword(int damage, int distance) {
        super(damage, distance);
        // bild liegt in images/Weapons/, ohne den ordner findet greenfoot es nicht
        GreenfootImage img = new GreenfootImage("Weapons/Waffe.Schwert.png");
        img.scale(SIZE, SIZE);
        setImage(img);
    }

    public Sword(){this(10,2);}
}

