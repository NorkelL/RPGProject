package world;

import blocks.Block;
import core.GameStarter;
import entities.Player;
import greenfoot.GreenfootImage;

public class Exit extends Block {


    private GameStarter gameStarter;

    public Exit(GameStarter gameStarter){
        this.gameStarter = gameStarter;
        GreenfootImage image = new GreenfootImage("World/Exit.png");
        setImage(image);
    }

    @Override
    public void act(){
        if (touchesPlayer()) {
            gameStarter.RenderNextWorld();
        }
    }

    private boolean touchesPlayer() {
        if (getWorld() instanceof DungeonLevel) {
            return !((DungeonLevel) getWorld()).getTileObjects(getTileX(), getTileY(), Player.class).isEmpty();
        }
        return isTouching(Player.class);
    }
}
