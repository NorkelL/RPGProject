package ui.buttons;

import core.GameStarter;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;

import java.io.IOException;
import java.util.List;


public class SaveGameButton extends PauseButtons{

    private GameStarter gameStarter;
    private GreenfootImage saveGameButton = new GreenfootImage("UI/PauseScreen/SaveButton.png");
    private GreenfootImage saveGameButtonGlowing = new GreenfootImage("UI/PauseScreen/SaveButtonGlow.png");
    private boolean isScaled = false;
    private int textTimer = 0;   // wie lange die meldung noch stehen bleibt

    public SaveGameButton(GameStarter gameStarter){
            super("UI/PauseScreen/SaveButton.png");
            this.gameStarter = gameStarter;
    }

    public void act(){
        if (Greenfoot.mouseClicked(this)){
            save();
        }
        if (getWorld() == null) return;

        if (textTimer > 0){
            textTimer--;
            if(textTimer==0){getWorld().showText(null, getX(), getY() - 2);}
        }

        mouseHover();
    }

    private void save(){
        try {
            gameStarter.saveGame();
        } catch (IOException e) {
            getWorld().showText("Speichern fehlgeschlagen", getX(), getY() - 2);
            textTimer = 90;
            return;
        }
        gameStarter.mainMenu();
    }

    private void mouseHover(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (!isScaled) {
            saveGameButton.scale(310, 110);
            saveGameButtonGlowing.scale(310, 110);
            isScaled = true;
        }


        if (mouse != null) {
            setImage(saveGameButton);
            List objects = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), SaveGameButton.class);
            for (Object object : objects)
            {
                if (object == this)
                {
                    setImage(saveGameButtonGlowing);
                }
            }
        }
    }
}

