package blocks;

import core.GameStarter;
import entities.Player;

public class Exit extends Block {


    private GameStarter gameStarter;
    private boolean schonBenutzt = true;

    public Exit(GameStarter gameStarter){
        this.gameStarter = gameStarter;
        setImage("Map/StairsExit.png");
    }

    @Override
    public void act(){
        if (pausiert()) return;

        if(!isTouching(Player.class)){schonBenutzt=false; return;}
        if (schonBenutzt) return;

        schonBenutzt = true;
        gameStarter.RenderNextWorld();
    }

    public void sperren(){schonBenutzt = true;}
}
