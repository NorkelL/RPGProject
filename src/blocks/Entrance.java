package blocks;

import core.GameStarter;

public class Entrance extends Block {

    private GameStarter gameStarter;

    public Entrance(GameStarter gameStarter){
        this.gameStarter = gameStarter;
        setImage("Map/StairsEntrance.png");

    }
}