package world;

import blocks.Wall;
import core.GameStarter;
import entities.Player;
import greenfoot.World;

import java.awt.Point;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class DungeonLevel extends World {

    private Random rng;
    public int centerExit;
    private int centerEntrance;

    public DungeonLevel(long seed,GameStarter gameStarter) {
        super(calcWidth(seed), calcHeight(seed), 40);
        rng = new Random(seed);
        setPaintOrder(Player.class, Wall.class);

        if(!gameStarter.pastLevel.isEmpty()){
            centerEntrance = gameStarter.pastLevel.get(gameStarter.pastLevel.size() - 1).centerExit;
        }else{
            centerEntrance = 6;
        }

        centerExit = rng.nextInt(this.getWidth() - 6)+3;
        int[] centerCorridor = calcValidCorridor();

        generateRandomFloor(centerCorridor);

        for (int i = centerEntrance - 1; i <= centerEntrance + 1; i++) {
            addObject(new Entrance(gameStarter), i - 1, this.getHeight() - 2);
        }

        for (int i = centerExit - 1; i <= centerExit + 1; i++) {
            addObject(new Exit(gameStarter),i-1,0);
        }
        addObject(new Player(),centerEntrance - 1,this.getHeight()-2);

        spawnCorridor(centerCorridor);
    }

    private static int calcHeight(long rn) {return calcWidth(rn)+3;}

    private static int calcWidth(long rn){
        return new Random(rn).nextInt(16)+15;
    }

    private void spawnCorridor(int[] centerCorridor){
        Set<Point> wallPositions = getWallPositions(centerCorridor);

        for (Point position : wallPositions) {
            Wall.Design design = wallPositions.contains(new Point(position.x, position.y + 1))
                    ? Wall.Design.top
                    : Wall.Design.front;
            addObject(new Wall(design), position.x, position.y);
        }
    }

    private int[] calcValidCorridor() {
        int[] centerCorridor = calcCorridor();
        while (centerCorridor[centerCorridor.length-1] != centerExit && centerCorridor[centerCorridor.length-1] != centerExit-1 && centerCorridor[centerCorridor.length-1] != centerExit+1){
            centerCorridor = calcCorridor();
        }
        return centerCorridor;
    }

    private Set<Point> getWallPositions(int[] centerCorridor) {
        Set<Point> wallPositions = new HashSet<>();
        for (int i = 0; i < getHeight()-3; i++) {
            int y = getHeight()-3 - i;
            int cx = centerCorridor[i];
            if(cx-2>=0) {
                wallPositions.add(new Point(centerCorridor[i] - 2, y));
            } else if (cx-1>=0) {
                wallPositions.add(new Point(centerCorridor[i] - 1, y));
            }
            if(cx+2<getWidth()) {
                wallPositions.add(new Point(centerCorridor[i] + 2, y));
            } else if (cx+1<getWidth()) {
                wallPositions.add(new Point(centerCorridor[i] + 1, y));
            }
        }
        return wallPositions;
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
    private void generateRandomFloor(int[] centerCorridor) {

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                if (!isCorridorFloor(x, y, centerCorridor)) {
                    addObject(new FloorTile(), x, y);
                    continue;
                }

                int chance = rng.nextInt(100);


                if (chance < 60) {
                    addObject(new FloorTile("Map/FloorTile1.png"), x, y);

                } else if (chance < 85) {
                    addObject(new FloorTile("Map/FloorTile2.png"), x, y);

                } else if (chance < 95) {
                    addObject(new FloorTile("Map/FloorTile3.jpg"), x, y);

                } else {
                    addObject(new FloorTile("Map/FloorTile.jpg"), x, y);
                }
            }
        }
    }

    private boolean isCorridorFloor(int x, int y, int[] centerCorridor) {
        if (y == 0) {
            return x >= centerExit - 2 && x <= centerExit;
        }

        if (y == getHeight() - 2) {
            return x >= centerEntrance - 2 && x <= centerEntrance;
        }

        int corridorIndex = getHeight() - 3 - y;
        if (corridorIndex < 0 || corridorIndex >= centerCorridor.length) {
            return false;
        }

        int center = centerCorridor[corridorIndex];
        return x >= center - 1 && x <= center + 1;
    }
}
