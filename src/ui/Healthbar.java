package ui;

import greenfoot.GreenfootImage;

public class Healthbar extends UI {

    private static final int SIZE_WIDTH = 205;
    private static final int SIZE_HEIGHT = 45;
    private static GreenfootImage Healthbar_IMG;

    public Healthbar() {
        Healthbar_IMG = new GreenfootImage("Healthbarneu.png");
        Healthbar_IMG.scale(SIZE_WIDTH, SIZE_HEIGHT);
        setImage(Healthbar_IMG);
    }

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
