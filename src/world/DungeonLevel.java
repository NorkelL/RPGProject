package world;

import blocks.Chest;
import blocks.Rock;
import core.GameStarter;
import entities.Player;
import greenfoot.GreenfootImage;
import greenfoot.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonLevel extends World {

    private final Random rng;
    public int centerExit;
    private int centerEntrance;
    private int[] centerCorridor;
    private List<Room> placedRooms = new ArrayList<>();

    private static class Room {
        int width, height, x, y;
        Room(int width, int height, int x, int y) { this.width = width; this.height = height; this.x = x; this.y = y; }
    }

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
            centerEntrance = this.getWidth()/2;
        }
        for (int i = centerEntrance - 1; i <= centerEntrance +1; i++) {
            addObject(new Entrance(gameStarter), i - 1, this.getHeight() - 2);
        }
        addObject(new Rock(), centerEntrance - 3, this.getHeight() - 2);
        addObject(new Rock(), centerEntrance + 1, this.getHeight() - 2);

        centerExit = rng.nextInt(this.getWidth() - 6)+3;
        for (int i = centerExit - 1; i <= centerExit + 1; i++) {
            addObject(new Exit(gameStarter),i-1,0);
        }
        addObject(new Rock(), centerExit - 3, 0);
        addObject(new Rock(), centerExit + 1, 0);

        addObject(new Player(),centerEntrance - 1,this.getHeight()-2);

        spawnCorridor();
        spawnRooms();
    }

    private static int calcHeight(long rn) {return calcWidth(rn)+3;}

    private static int calcWidth(long rn){
        return new Random(rn).nextInt(16)+15;
    }

    private void spawnCorridor(){
        do {
            centerCorridor = calcCorridor();
        } while (centerCorridor[centerCorridor.length - 1] != centerExit && centerCorridor[centerCorridor.length - 1] != centerExit - 1 && centerCorridor[centerCorridor.length - 1] != centerExit + 1);
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
        int pos = centerEntrance;
        int delta = 0;
        int runLeft = 0;
        int length = getHeight() - 3;

        for (int i = 0; i < length; i++) {
            int stepsLeft = length - i;
            int distToExit = Math.abs(centerExit - pos);

            if (distToExit >= stepsLeft) {
                delta = Integer.compare(centerExit, pos);
                runLeft = 1;
            } else if (runLeft == 0) {
                int toward = Integer.compare(centerExit, pos);
                int r = rng.nextInt(10);
                delta = (r < 4) ? toward : (r < 7) ? 0 : -toward;
                runLeft = rng.nextInt(3) + 3;
            }

            pos = Math.max(1, Math.min(getWidth() - 2, pos + delta));
            centerCorridor[i] = pos;
            runLeft--;
        }
        return centerCorridor;
    }

    private void spawnRooms(){
        int placedCount = 0;
        int tries = 0;
        while(placedCount < 3){
            if(tryPlaceRoom(genRandomRoom())){
                placedCount++;
            }else{
                tries++;
                if(tries > 10) return;
            }
        }
    }

    private boolean tryPlaceRoom(Room room){
        for (Room placed : placedRooms) {
            if (room.x <= placed.x + placed.width && room.x + room.width >= placed.x &&
                room.y <= placed.y + placed.height && room.y + room.height >= placed.y) {
                return false;
            }
        }
        boolean touchesCorridor = false;
        for(int i = room.x+1; i < room.x + room.width-1; i++) {
            for (int j = room.y+1; j < room.y+room.height-1; j++) {
                if(!getObjectsAt(i,j, Rock.class).isEmpty()){
                    removeRockAt(i,j);
                    touchesCorridor = true;
                }
            }
        }
        if(touchesCorridor){
            for (int i = room.x; i < room.x + room.width+1; i++) {
                addObject(new Rock(), i, room.y);
                addObject(new Rock(), i, room.y+room.height);
            }
            for (int i = room.y; i < room.y + room.height+1; i++) {
                addObject(new Rock(), room.x, i);
                addObject(new Rock(), room.x + room.width, i);
            }
        }
        for (int i = 0; i < centerCorridor.length; i++) {
            int worldY = getHeight()-3-i;
            for (int j = centerCorridor[i]-1; j < centerCorridor[i]+2; j++) {
                removeRockAt(j, worldY);
            }
        }
        if (touchesCorridor) placedRooms.add(room);
        return touchesCorridor;
    }

    private void removeRockAt(int x, int y) {
        List<Rock> rocks = getObjectsAt(x,y, Rock.class);
        for (Rock rock : rocks) {
            removeObject(rock);
        }
    }

    private Room genRandomRoom(){
        int w = rng.nextInt(10)+4;
        int h = rng.nextInt(10)+4;
        int x = rng.nextInt(Math.max(1, getWidth()-w-2))+1;
        int y = rng.nextInt(Math.max(1, getHeight()-h-4))+1;
        return new Room(w, h, x, y);
    }
}
