package world;

import blocks.Block;
import core.GameStarter;
import entities.Player;

public class Entrance extends Block {

    private GameStarter gameStarter;

    public Entrance(GameStarter gameStarter){
        this.gameStarter = gameStarter;
        //setImage("Blocks/Entrance.png");
    }
}