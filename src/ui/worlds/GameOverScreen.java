package ui.worlds;

import core.GameStarter;
import greenfoot.GreenfootImage;
import greenfoot.World;
import ui.buttons.tryAgainButton;

public class GameOverScreen extends World {

    public GameOverScreen(GameStarter gameStarter) {
        super(1120, 540, 1);

        setBackground(baueHintergrund());
        addObject(new tryAgainButton(gameStarter), getWidth()/2, 450);
    }

    private GreenfootImage baueHintergrund(){
        GreenfootImage voll = new GreenfootImage("UI/GameOverScreen.png");
        voll.scale(1120, 1146);

        GreenfootImage hg = new GreenfootImage(1120, 540);
        hg.drawImage(voll, 0, 0);
        return hg;
    }
}
