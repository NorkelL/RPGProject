package ui;

import core.GameStarter;
import entities.Player;
import greenfoot.GreenfootImage;

import java.util.List;

public class LoadGameButton extends UI {

    private GameStarter gameStarter;

    public LoadGameButton(GameStarter gameStarter){
        super();
        this.gameStarter = gameStarter;
        GreenfootImage LoadGameButton = new GreenfootImage("Map/LoadGame.png");
        LoadGameButton.scale(310,240);
        setImage(LoadGameButton);

    }



}
