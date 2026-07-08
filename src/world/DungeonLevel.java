package world;

import blocks.Chest;
import blocks.Entrance;
import blocks.Exit;
import blocks.Wall;
import core.GameStarter;
import entities.Player;
import greenfoot.GreenfootImage;
import greenfoot.World;
import items.*;
import ui.DarkFilter;
import ui.InventoryOverlay;
import ui.InventorySlot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DungeonLevel extends World {

    private final Random rng;
    public int centerExit;
    private int centerEntrance;
    private int[] centerCorridor;
    private List<Room> placedRooms = new ArrayList<>();
    public final Player player;

    private static class Room {
        int width, height, x, y;
        Room(int width, int height, int x, int y) { this.width = width; this.height = height; this.x = x; this.y = y; }
    }

    public DungeonLevel(long seed,GameStarter gameStarter) {
        super(30, 30, 40);
        rng = new Random(seed);
        generateRandomFloor();
        setPaintOrder(
                InventoryOverlay.class, // Ganz oben
                InventorySlot.class,    // Die Slots auf dem Inventar
                DarkFilter.class,   // Der dunkle Schleier
                Wall.class,         // das ist die Wall nur zur Info
                Player.class         // Darunter der Rest
        );

        if(!gameStarter.pastLevels.isEmpty()){
            centerEntrance = gameStarter.pastLevels.get(gameStarter.pastLevels.size() - 1).centerExit;
        }else{
            centerEntrance = this.getWidth()/2;
        }
        for (int i = centerEntrance - 1; i <= centerEntrance +1; i++) {
            addObject(new Entrance(gameStarter), i - 1, this.getHeight() - 2);
        }
        savePlaceWall(centerEntrance - 3,this.getHeight() - 2);
        savePlaceWall(centerEntrance + 1,this.getHeight() - 2);

        centerExit = rng.nextInt(this.getWidth() - 6)+3;
        for (int i = centerExit - 1; i <= centerExit + 1; i++) {
            addObject(new Exit(gameStarter),i-1,0);
        }
        savePlaceWall(centerExit - 3,0);
        savePlaceWall(centerExit + 1,0);

        player = new Player();
        addObject(player,centerEntrance - 1,this.getHeight()-2);



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
            int y = getHeight()-3 - i;
            int cx = centerCorridor[i];
            if(cx-2>=0) {
                addObject(new Wall(), centerCorridor[i] - 2, y);
            } else if (cx-1>=0) {
                addObject(new Wall(), centerCorridor[i] - 1, y);
            }
            if(cx+2<getWidth()) {
                addObject(new Wall(), centerCorridor[i] + 2, y);
            } else if (cx+1<getWidth()) {
                addObject(new Wall(), centerCorridor[i] + 1, y);
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
                int finalI = i;
                int finalJ = j;
                if(getObjects(Wall.class).stream().anyMatch(w -> w.getX() == finalI && w.getY() == finalJ)){
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
            int worldY = getHeight()-3-i;
            for (int j = centerCorridor[i]-1; j < centerCorridor[i]+2; j++) {
                removeWallAt(j, worldY);
            }
        }
        if (touchesCorridor) placedRooms.add(room);
        return touchesCorridor;
    }

    private void savePlaceWall(int x, int y) {
        removeWallAt(x, y);
        addObject(new Wall(), x, y);

        getObjects(Wall.class).stream()
            .filter(w -> w.getX() == x && w.getY() > y)
            .sorted(Comparator.comparingInt(Wall::getY))
            .collect(Collectors.toList())
            .forEach(w -> { int wy = w.getY(); removeObject(w); addObject(new Wall(), x, wy); });
    }

    private void removeWallAt(int x, int y) {
        getObjects(Wall.class).stream()
            .filter(w -> w.getX() == x && w.getY() == y)
            .forEach(this::removeObject);
    }

    private Room genRandomRoom(){
        int w = rng.nextInt(10)+4;
        int h = rng.nextInt(10)+4;
        int x = rng.nextInt(Math.max(1, getWidth()-w-2))+1;
        int y = rng.nextInt(Math.max(1, getHeight()-h-4))+1;
        return new Room(w, h, x, y);
    }
    private void generateRandomFloor() {
        int cellSize = 40;
        int w = getWidth();
        int h = getHeight();

        GreenfootImage[] tiles = {
            new GreenfootImage("Map/FloorTile1.png"),
            new GreenfootImage("Map/FloorTile2.png"),
            new GreenfootImage("Map/FloorTile3.jpg"),
            new GreenfootImage("Map/FloorTile.jpg"),
        };
        for (GreenfootImage t : tiles) t.scale(cellSize, cellSize);

        GreenfootImage bg = new GreenfootImage(w * cellSize, h * cellSize);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int chance = rng.nextInt(100);
                GreenfootImage tile;
                if      (chance < 60) tile = tiles[0];
                else if (chance < 85) tile = tiles[1];
                else if (chance < 95) tile = tiles[2];
                else                  tile = tiles[3];
                bg.drawImage(tile, x * cellSize, y * cellSize);
            }
        }
        setBackground(bg);
    }

    public List<int[]> getOpenedChests() {
        List<int[]> opened = new ArrayList<>();
        for (Chest chest : getObjects(Chest.class)) {
            if (chest.isOpen()) opened.add(new int[]{chest.getX(), chest.getY()});
        }
        return opened;
    }

    public void movePlayer(int x, int y) {
        removeObject(player);
        addObject(player, x, y);
    }
}
