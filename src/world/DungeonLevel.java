package world;

import blocks.Wall;
import core.GameStarter;
import entities.Player;
import greenfoot.GreenfootImage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DungeonLevel extends GridWorld {

    /** Logische Kachelgröße in Pixeln (Texturgröße). */
    private static final int TILE = 40;
    /**
     * Physische Greenfoot-Zellgröße.
     *   - {@code 40}: klassisches Verhalten, 1 Zelle == 1 Tile.
     *   - {@code 1}:  pixelgenaue Platzierung / weichere Bewegung.
     * Muss {@link #TILE} teilen.
     */
    private static final int UNIT = 1;

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
        super(30, 30, TILE, UNIT);
        rng = new Random(seed);
        generateRandomFloor();
        setPaintOrder(Wall.class, Player.class);

        if(!gameStarter.pastLevel.isEmpty()){
            centerEntrance = gameStarter.pastLevel.get(gameStarter.pastLevel.size() - 1).centerExit;
        }else{
            centerEntrance = getTilesX()/2;
        }
        for (int i = centerEntrance - 1; i <= centerEntrance +1; i++) {
            addTile(new Entrance(gameStarter), i - 1, getTilesY() - 2);
        }
        savePlaceWall(centerEntrance - 3, getTilesY() - 2);
        savePlaceWall(centerEntrance + 1, getTilesY() - 2);

        centerExit = rng.nextInt(getTilesX() - 6)+3;
        for (int i = centerExit - 1; i <= centerExit + 1; i++) {
            addTile(new Exit(gameStarter), i-1, 0);
        }
        savePlaceWall(centerExit - 3,0);
        savePlaceWall(centerExit + 1,0);

        addTile(new Player(), centerEntrance - 1, getTilesY()-2);



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
        for (int i = centerCorridor.length - 1; i >= 0; i--) {
            int y = getTilesY()-3 - i;
            int cx = centerCorridor[i];
            if(cx-2>=0) {
                addTile(new Wall(), centerCorridor[i] - 2, y);
            } else if (cx-1>=0) {
                addTile(new Wall(), centerCorridor[i] - 1, y);
            }
            if(cx+2<getTilesX()) {
                addTile(new Wall(), centerCorridor[i] + 2, y);
            } else if (cx+1<getTilesX()) {
                addTile(new Wall(), centerCorridor[i] + 1, y);
            }
        }
    }

    private int[] calcCorridor(){
        int[] centerCorridor = new int[getTilesY()-3];
        int pos = centerEntrance;
        int delta = 0;
        int runLeft = 0;
        int length = getTilesY() - 3;

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

            pos = Math.max(1, Math.min(getTilesX() - 2, pos + delta));
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
                int finalI = i;
                int finalJ = j;
                if(getObjects(Wall.class).stream().anyMatch(w -> cellToTile(w.getX()) == finalI && cellToTile(w.getY()) == finalJ)){
                    removeWallAt(i,j);
                    touchesCorridor = true;
                }
            }
        }
        if(touchesCorridor){
            for (int i = room.x; i < room.x + room.width+1; i++) {
                savePlaceWall(i, room.y);
                savePlaceWall(i, room.y+room.height);
            }
            for (int i = room.y; i < room.y + room.height+1; i++) {
                savePlaceWall(room.x, i);
                savePlaceWall(room.x + room.width, i);
            }
        }
        for (int i = 0; i < centerCorridor.length; i++) {
            int worldY = getTilesY()-3-i;
            for (int j = centerCorridor[i]-1; j < centerCorridor[i]+2; j++) {
                removeWallAt(j, worldY);
            }
        }
        if (touchesCorridor) placedRooms.add(room);
        return touchesCorridor;
    }

    private void savePlaceWall(int x, int y) {
        removeWallAt(x, y);
        addTile(new Wall(), x, y);

        getObjects(Wall.class).stream()
            .filter(w -> cellToTile(w.getX()) == x && cellToTile(w.getY()) > y)
            .sorted(Comparator.comparingInt(w -> cellToTile(w.getY())))
            .collect(Collectors.toList())
            .forEach(w -> { int wy = cellToTile(w.getY()); removeObject(w); addTile(new Wall(), x, wy); });
    }

    private void removeWallAt(int x, int y) {
        getObjects(Wall.class).stream()
            .filter(w -> cellToTile(w.getX()) == x && cellToTile(w.getY()) == y)
            .forEach(this::removeObject);
    }

    private Room genRandomRoom(){
        int w = rng.nextInt(10)+4;
        int h = rng.nextInt(10)+4;
        int x = rng.nextInt(Math.max(1, getTilesX()-w-2))+1;
        int y = rng.nextInt(Math.max(1, getTilesY()-h-4))+1;
        return new Room(w, h, x, y);
    }
    private void generateRandomFloor() {
        int w = getTilesX();
        int h = getTilesY();
        int ts = getTileSize();

        GreenfootImage[] tiles = {
            new GreenfootImage("Map/FloorTile1.png"),
            new GreenfootImage("Map/FloorTile2.png"),
            new GreenfootImage("Map/FloorTile3.jpg"),
            new GreenfootImage("Map/FloorTile.jpg"),
        };
        for (GreenfootImage t : tiles) t.scale(ts, ts);

        GreenfootImage bg = new GreenfootImage(w * ts, h * ts);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int chance = rng.nextInt(100);
                GreenfootImage tile;
                if      (chance < 60) tile = tiles[0];
                else if (chance < 85) tile = tiles[1];
                else if (chance < 95) tile = tiles[2];
                else                  tile = tiles[3];
                bg.drawImage(tile, x * ts, y * ts);
            }
        }
        setBackground(bg);
    }
}
