package blocks;

import core.GameStarter;
import entities.Player;

public class Entrance extends Block {

    private GameStarter gameStarter;
    private boolean schonBenutzt = true;

    public Entrance(GameStarter gameStarter){
        this.gameStarter = gameStarter;
        setImage("Map/StairsEntrance.png");

    }

    @Override
    public void act(){
        if (pausiert()) return;

        if(!isTouching(Player.class)){schonBenutzt=false; return;}
        if (schonBenutzt) return;

        schonBenutzt = true;
        gameStarter.RenderPastWorld();
    }

    public void sperren(){schonBenutzt = true;}
}
