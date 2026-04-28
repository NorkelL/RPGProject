package world;

import blocks.Block;
import core.GameStarter;

public class Exit extends Block {


    private GameStarter gameStarter;

    public Exit(GameStarter gameStarter){
        this.gameStarter = gameStarter;
        //setImage("Blocks/Exit.png");
    }
}
