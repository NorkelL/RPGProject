package ui;

import core.GameStarter;
import entities.Player;
import greenfoot.GreenfootImage;

import java.util.List;

public class StartButton extends UI implements Clickable{

    public StartButton(){
        super();
        GreenfootImage StartButton = new GreenfootImage("StartGame.png");
        StartButton.scale(310,240);
        setImage(StartButton);

    }




    @Override
    public UI onClick(Player player) {
        List<GameStarter> gameStarters= getWorld().getObjects(GameStarter.class);
        gameStarters.get(0).start();
        return null;
    }
}
