package ui;

import greenfoot.GreenfootImage;
import greenfoot.World;

public class GameOverScreen extends World {
    public GameOverScreen() {
        super(16,9,60);

        GreenfootImage thisImage= new GreenfootImage("GameOverScreen.png");
        int weltBreiteInPixeln = getWidth() * getCellSize();
        int weltHoeheInPixeln = getHeight() * getCellSize();
        thisImage.scale(weltBreiteInPixeln, weltHoeheInPixeln);
        setBackground(thisImage);

    }

    @Override
    public void act() {
        super.act();
    }
}
