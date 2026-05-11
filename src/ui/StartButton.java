package ui;

import core.GameStarter;
import greenfoot.*;

public class StartButton extends UI implements Clickable{

    private GameStarter gameStarter;

    public StartButton(GameStarter gameStarter){
        super();
        this.gameStarter = gameStarter;
        GreenfootImage StartButton = new GreenfootImage("Map/StartGame.png");
        StartButton.scale(310,110);
        setImage(StartButton);
    }

    @Override
    public void act() {
        if (Greenfoot.mouseClicked(null)) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                int cellSize = getWorld().getCellSize();
                int halfW = getImage().getWidth() / (2 * cellSize);
                int halfH = getImage().getHeight() / (2 * cellSize);
                if (Math.abs(mouse.getX() - getX()) <= halfW &&
                    Math.abs(mouse.getY() - getY()) <= halfH) {
                    onClick();
                }
            }
        }
    }



    @Override
    public UI onClick(){
        gameStarter.start();
        return null;
    }
}
