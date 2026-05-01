package ui;

import core.GameStarter;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

import java.util.List;

public class LoadGameButton extends UI {

    private GameStarter gameStarter;
    private  GreenfootImage loadGameButton = new GreenfootImage("Map/LoadGame.png");
    private GreenfootImage loadGameButtonGlowing = new GreenfootImage("LoadGameGlowing.png");
    private boolean isScaled = false;

    public LoadGameButton(GameStarter gameStarter){
        super();
        this.gameStarter = gameStarter;
        GreenfootImage LoadGameButton = new GreenfootImage("Map/LoadGame.png");
        LoadGameButton.scale(240,110);
        setImage(LoadGameButton);

    }
    public void act(){
        mouseHover();

    }
    private void mouseHover(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (!isScaled) {
            loadGameButton.scale(240, 110);
            loadGameButtonGlowing.scale(240, 110);
            isScaled = true;
        }


        if (mouse != null) {
            setImage(loadGameButton);
            List objects = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), LoadGameButton.class);
            for (Object object : objects)
            {
                if (object == this)
                {
                    setImage(loadGameButtonGlowing);
                }
            }
        }
    }




}
