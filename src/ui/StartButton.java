package ui;

import core.GameStarter;
import greenfoot.*;

public class StartButton extends UI implements Clickable{

    private GameStarter gameStarter;

    public StartButton(GameStarter gameStarter){
        super();
        this.gameStarter = gameStarter;
        GreenfootImage StartButton = new GreenfootImage("Map/StartGame.png");
        StartButton.scale(310,240);
        setImage(StartButton);
    }

    @Override
    public void act() {
        if (Greenfoot.mouseClicked(null)) {
            MouseInfo mouse = Greenfoot.getMouseInfo();
            if (mouse != null) {
                int dx = Math.abs(mouse.getX() - getX());
                int dy = Math.abs(mouse.getY() - getY());
                if (dx <= 2 && dy <= 2) {
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
