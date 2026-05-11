package ui;

import core.GameStarter;
import greenfoot.*;

public class StartButton extends UI implements Clickable{

    private GameStarter gameStarter;
    private boolean starting;

    public StartButton(GameStarter gameStarter){
        super();
        this.gameStarter = gameStarter;
        GreenfootImage StartButton = new GreenfootImage("Map/StartGame.png");
        StartButton.scale(310,240);
        setImage(StartButton);
    }

    @Override
    public void act() {
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (!starting && mouse != null && Greenfoot.mousePressed(null) && isInsideButton(mouse)) {
            starting = true;
            onClick();
        }
    }

    private boolean isInsideButton(MouseInfo mouse) {
        if (getImage() == null) {
            return false;
        }
        int halfWidth = getImage().getWidth() / 2;
        int halfHeight = getImage().getHeight() / 2;
        return mouse.getX() >= getX() - halfWidth
            && mouse.getX() <= getX() + halfWidth
            && mouse.getY() >= getY() - halfHeight
            && mouse.getY() <= getY() + halfHeight;
    }



    @Override
    public UI onClick(){
        gameStarter.start();
        return null;
    }
}
