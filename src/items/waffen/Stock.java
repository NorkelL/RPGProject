package items.waffen;

import greenfoot.GreenfootImage;
import items.Waffen;

public class Stock extends Waffen {

    private static final int SIZE = 32;

    public Stock(int damage, int distance) {
        super(damage, distance);
        GreenfootImage img = new GreenfootImage("Weapons/Waffen.Stock.png");
        img.scale(SIZE, SIZE);
        setImage(img);
    }

    public Stock(){this(5,5);}
}