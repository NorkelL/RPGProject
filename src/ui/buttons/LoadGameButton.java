package ui.buttons;

import core.GameStarter;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import ui.UI;

import java.util.List;

public class LoadGameButton extends UI {

    private GameStarter gameStarter;
    private  GreenfootImage loadGameButton = new GreenfootImage("UI/MainMenu/LoadGame.png");
    private GreenfootImage loadGameButtonGlowing = new GreenfootImage("UI/MainMenu/LoadGameGlowing.png");
    private boolean isScaled = false;

    public LoadGameButton(GameStarter gameStarter){
        super();
        this.gameStarter = gameStarter;
        GreenfootImage LoadGameButton = new GreenfootImage("UI/MainMenu/LoadGame.png");
        LoadGameButton.scale(310,110);
        setImage(LoadGameButton);

    }
    public void act(){
        mouseHover();

    }
    private void mouseHover(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (!isScaled) {
            loadGameButton.scale(310, 110);
            loadGameButtonGlowing.scale(310, 110);
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
