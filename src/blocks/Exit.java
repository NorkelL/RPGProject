package blocks;

import core.GameStarter;
import entities.Player;

public class Exit extends Block {


    private GameStarter gameStarter;

    public Exit(GameStarter gameStarter){
        this.gameStarter = gameStarter;
        setImage("Map/StairsExit.png");
    }

    @Override
    public void act(){
        if(isTouching(Player.class)){
            gameStarter.RenderNextWorld();
        }
    }
}
