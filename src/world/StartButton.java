package world;

import greenfoot.*;
import core.GameStarter;

public class StartButton extends Actor {

    private GameStarter gameStarter;

    public StartButton(GameStarter gameStarter) {
        this.gameStarter = gameStarter;
        setImage("rock.gif");
    }

    public void act() {
        if (Greenfoot.mouseClicked(this)) {
            gameStarter.mainMenu();
        }
    }
}