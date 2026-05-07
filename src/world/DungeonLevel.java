package world;

import blocks.Chest;
import blocks.Rock;
import core.GameStarter;
import entities.Player;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.util.Random;

public class DungeonLevel extends World {

    private Random rng;
    public int centerExit;
    private int centerEntrance;

    public DungeonLevel(long seed,GameStarter gameStarter) {
        super(calcWidth(seed), calcHeight(seed), 40);
        rng = new Random(seed);
        GreenfootImage tile = new GreenfootImage("cell.jpg");
        tile.scale(40, 40);
        setBackground(tile);
        setPaintOrder(Player.class, Rock.class);

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

        spawnCorridor();
        addObject(new Chest(), getWidth() / 2, getHeight() / 2);
    }

    private static int calcHeight(long rn) {return calcWidth(rn)+3;}

    private static int calcWidth(long rn){
        return new Random(rn).nextInt(16)+15;
    }

    private void spawnCorridor(){
        int[] centerCorridor = calcCorridor();
        while (centerCorridor[centerCorridor.length-1] != centerExit && centerCorridor[centerCorridor.length-1] != centerExit-1 && centerCorridor[centerCorridor.length-1] != centerExit+1){
            centerCorridor = calcCorridor();
        }
        for (int i = 0; i < getHeight()-3; i++) {
            int y = getHeight()-3 - i;
            int cx = centerCorridor[i];
            if(cx-2>=0) {
                addObject(new Rock(), centerCorridor[i] - 2, y);
            } else if (cx-1>=0) {
                addObject(new Rock(), centerCorridor[i] - 1, y);
            }
            if(cx+2<getWidth()) {
                addObject(new Rock(), centerCorridor[i] + 2, y);
            } else if (cx+1<getWidth()) {
                addObject(new Rock(), centerCorridor[i] + 1, y);
            }
        }
    }



    private int[] calcCorridor(){
        int[] centerCorridor = new int[getHeight()-3];
        int lastRow = centerEntrance;

        for (int i = 0; i < getHeight()-3; i++) {
            int rng = this.rng.nextInt(4);

            if (rng == 0 || rng == 1) {
                if (lastRow < centerExit) {
                    lastRow++;
                } else {
                    lastRow--;
                }
                centerCorridor[i] = lastRow;
            } else if (rng ==2) {
                if (centerEntrance < centerExit) {
                    lastRow--;
                } else {
                    lastRow++;
                }
                centerCorridor[i] = lastRow;
            }else {
                centerCorridor[i] = lastRow;
            }
        }
        return centerCorridor;
    }
}
