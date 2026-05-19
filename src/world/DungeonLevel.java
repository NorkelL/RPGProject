package world;

import blocks.Rock;
import core.GameStarter;
import entities.Monster;
import entities.Player;
import greenfoot.World;
import ui.DarkFilter;
import ui.InventoryOverlay;
import ui.InventorySlot;

import java.util.Random;

public class DungeonLevel extends World {

    private Random rng;
    public int centerExit;
    private int centerEntrance;

    public DungeonLevel(long seed,GameStarter gameStarter) {
        super(12, calcHight(seed), 60);
        rng = new Random(seed);
        setBackground("cell.jpg");
        setPaintOrder(
                InventoryOverlay.class, // Ganz oben
                InventorySlot.class,    // Die Slots auf dem Inventar
                DarkFilter.class,       // Der dunkle Schleier
                Player.class,           // Darunter der Rest
                Monster.class,
                Rock.class
        );

        if(!gameStarter.pastLevel.isEmpty()){
            centerEntrance = gameStarter.pastLevel.get(gameStarter.pastLevel.size() - 1).centerExit;
        }else{
            centerEntrance = 6;
        }
        for (int i = centerEntrance - 1; i <= centerEntrance + 1; i++) {
            addObject(new Entrance(gameStarter), i - 1, this.getHeight() - 2);
        }

        centerExit = rng.nextInt(this.getWidth() - 6)+3;
        for (int i = centerExit - 1; i <= centerExit + 1; i++) {
            addObject(new Exit(gameStarter),i-1,0);
        }
        addObject(new Player(),centerEntrance - 1,this.getHeight()-2);
    }

    public DungeonLevel() {
        this(System.currentTimeMillis(), new GameStarter());
    }

    private static int calcHight(long rn){
        return new Random(rn).nextInt(10)+5;
    }
}
