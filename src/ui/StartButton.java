package ui;

import core.GameStarter;
import greenfoot.*;

import java.util.List;

public class StartButton extends UI implements Clickable{

    private GameStarter gameStarter;
    private GreenfootImage StartButton = new GreenfootImage("Map/StartGame.png");
    private GreenfootImage StartButtonGlowing = new GreenfootImage("StartGameGlowing.png");
    private boolean isScaled = false;


    public StartButton(GameStarter gameStarter){
        super();
        this.gameStarter = gameStarter;
        GreenfootImage StartButton = new GreenfootImage("Map/StartGame.png");
        GreenfootImage StartButtonGlowing = new GreenfootImage("StartGameGlowing.png");
        StartButtonGlowing.scale(240,110);
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
        mouseHover();
    }



    @Override
    public UI onClick(){
        gameStarter.start();
        return null;
    }


    private void mouseHover(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (!isScaled) {
            StartButton.scale(240, 110);
            StartButtonGlowing.scale(240, 110);
            isScaled = true;
        }


        if (mouse != null) {
            setImage(StartButton);
            List objects = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), StartButton.class);
            for (Object object : objects)
            {
                if (object == this)
                {
                    setImage(StartButtonGlowing);
                }
            }
        }
    }
}
