package world;

import blocks.Block;
import core.GameStarter;
import greenfoot.GreenfootImage;

public class Entrance extends Block {

    private GameStarter gameStarter;

    public Entrance(GameStarter gameStarter){
        this.gameStarter = gameStarter;
        GreenfootImage image = new GreenfootImage("World/Entrance.png");
        setImage(image);
    }
}
