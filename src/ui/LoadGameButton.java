package ui;

import core.GameStarter;
import greenfoot.GreenfootImage;

public class LoadGameButton extends UI {

    private GameStarter gameStarter;

    public LoadGameButton(GameStarter gameStarter){
        super();
        this.gameStarter = gameStarter;
        GreenfootImage LoadGameButton = new GreenfootImage("Map/LoadGame.png");
        LoadGameButton.scale(310,110);
        setImage(LoadGameButton);

    }



}
