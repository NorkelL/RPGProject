package blocks;

import core.GameStarter;

//nur die treppe zum reinkommen, hat keine funktion - der Exit macht den levelwechsel
public class Entrance extends Block {

    private GameStarter gameStarter;

    public Entrance(GameStarter gameStarter){
        this.gameStarter = gameStarter;
        setImage("Map/StairsEntrance.png");

    }
}