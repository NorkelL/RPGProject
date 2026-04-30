package world;

import blocks.Rock;
import core.GameStarter;
import entities.Player;
import greenfoot.World;

import java.util.Random;

public class DungeonLevel extends World {

    private Random rng;
    public int centerExit;
    private int centerEntrance;

    public DungeonLevel(long seed,GameStarter gameStarter) {
        super(12, calcHight(seed), 60);
        rng = new Random(seed);
        setBackground("cell.jpg");
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
    }

    private static int calcHight(long rn){
        return new Random(rn).nextInt(10)+5;
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
