package items.waffen;

import greenfoot.GreenfootImage;
import items.Waffen;

public class Messer extends Waffen {

    private static final int SIZE = 32;

    public Messer(int damage, int distance) {
        super(damage, distance);
        GreenfootImage img = new GreenfootImage("Weapons/Waffe.Messer.png");
        img.scale(SIZE, SIZE);
        setImage(img);
    }

    public Messer(){this(15,1);}
}

