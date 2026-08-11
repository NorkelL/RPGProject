package ui.buttons;

import core.GameStarter;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

import java.util.List;

public class restartButton extends PauseButtons {

    private GameStarter gameStarter;
    private GreenfootImage restartGameButton = new GreenfootImage("UI/PauseScreen/RestartButton.png");
    private GreenfootImage restartGameButtonGlowing = new GreenfootImage("UI/PauseScreen/RestartButtonGlow.png");
    private boolean isScaled = false;

    public restartButton(GameStarter gameStarter){
        super("UI/PauseScreen/RestartButton.png");
        this.gameStarter = gameStarter;
    }

    public void act(){
        if (Greenfoot.mouseClicked(this)){
            gameStarter.restart();
        }

        mouseHover();
    }

    private void mouseHover(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (!isScaled) {
            restartGameButton.scale(310, 110);
            restartGameButtonGlowing.scale(310, 110);
            isScaled = true;
        }


        if (mouse != null) {
            setImage(restartGameButton);
            List objects = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), restartButton.class);
            for (Object object : objects)
            {
                if (object == this)
                {
                    setImage(restartGameButtonGlowing);
                }
            }
        }
    }
}
