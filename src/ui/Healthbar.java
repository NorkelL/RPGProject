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

}
