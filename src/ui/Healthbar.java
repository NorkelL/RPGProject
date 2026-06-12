package ui;

import greenfoot.GreenfootImage;

public class Healthbar extends UI {

    private static final int SIZE_WITH = 600;
    private static final int SIZE_HEIGHT = 600;
    private static GreenfootImage Healthbar_IMG;

    {
        Healthbar_IMG = new GreenfootImage("Healthbar.png");
        Healthbar_IMG.scale(SIZE_WITH, SIZE_HEIGHT);
    }

    public Healthbar() {setImage(new GreenfootImage(Healthbar_IMG));
    }

}
