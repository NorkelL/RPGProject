package world;

import blocks.Block;
import blocks.Chest;
import blocks.Entrance;
import blocks.Exit;
import blocks.UpgradeTable;
import blocks.Wall;
import core.GameStarter;
import entities.Player;
import greenfoot.GreenfootImage;
import greenfoot.World;
import items.armor.LeatherArmor;
import ui.buttons.*;
import items.waffen.Arrow;
import entities.base.BaseMonster;
import entities.enemies.Skeleton;
import entities.enemies.Zombie;
import items.waffen.BowSprite;
import ui.DarkFilter;
import ui.Healthbar;
import ui.InventoryOverlay;
import ui.InventorySlot;
import ui.PauseScreen;
import ui.buttons.restartButton;
import ui.buttons.SaveGameButton;
import ui.buttons.settingPauseButton;
import greenfoot.Greenfoot;
import ui.Settings;
import ui.XPBar;
import items.*;
import Material.*;
import ui.LevelCounter;

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
    private boolean paused = false;
    private SaveGameButton saveGameButton;
    private settingPauseButton settingPauseButton;
    private restartButton restartButton;
    private PauseScreen pauseScreen;
    private final GameStarter gameStarter;
    public final Player player;

    private static class Room {
        int width, height, x, y;
        Room(int width, int height, int x, int y) { this.width = width; this.height = height; this.x = x; this.y = y; }
    }

    public DungeonLevel(long seed,GameStarter gameStarter,Player p) {
        super(30, 30, 40);
        this.gameStarter = gameStarter;
        rng = new Random(seed);
        generateRandomFloor();
        setPaintOrder(
                ui.buttons.PauseButtons.class,
                PauseScreen.class,
                ui.LevelCounter.class,
                DarkFilter.class,// Der dunkle Schleier
                InventoryOverlay.class, // Ganz oben
                InventorySlot.class,    // Die Slots auf dem Inventar
                Healthbar.class,        // hud muss ueber den schleier, sonst ist es abgedunkelt
                XPBar.class,
                ui.LevelUpMessage.class,
                ui.DamageNumber.class,
                DarkFilter.class,   // Der dunkle Schleier
                Wall.class,
                Arrow.class,
                BowSprite.class,           // das ist die Wall nur zur Info
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

        player = p;
        addObject(player,centerEntrance - 1,this.getHeight()-2);

        LevelCounter levelCounter= new LevelCounter(gameStarter.pastLevels.size()+1);
        int counterCells = levelCounter.getImage().getWidth() / getCellSize();
        addObject(levelCounter, getWidth() - counterCells / 2 - 1, 0);



        spawnCorridor();
        spawnRooms();
        spawnMonsters();
        spawnBloecke();
        util.SoundManager.startMusic();
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

    private void spawnMonsters(){
        if (placedRooms.isEmpty()) return;

        for (Room room : placedRooms) {
            int gewollt = rng.nextInt(3) + 1;
            int gesetzt = 0;
            int versuche = 0;

            while (gesetzt < gewollt && versuche < 100) {
                versuche++;

                int x = room.x + 1 + rng.nextInt(Math.max(1, room.width - 1));
                int y = room.y + 1 + rng.nextInt(Math.max(1, room.height - 1));
                if (!istFreiFuerMonster(x, y)) continue;

                addObject(zufaelligesMonster(), x, y);
                gesetzt++;
            }
        }
    }

    // hier neue monster eintragen und die obergrenze mit hochzaehlen:
    private BaseMonster zufaelligesMonster(){
        int typ = rng.nextInt(2);
        if      (typ == 0) return new Skeleton(50);
        else               return new Zombie(50);
        //else if (typ == 2) return new Gnome(50);      kein bild vorhanden
        //else               return new Orc(50);        komisch gescaled
    }

    // pro raum genau ein block, hier die wahrscheinlichkeiten anpassen:
    private void spawnBloecke(){
        for (Room room : placedRooms) {
            int chance = rng.nextInt(100);
            Block block;
            if      (chance < 70) block = new Chest();
            else if (chance < 90) block = new UpgradeTable();
            else                  continue;   // die restlichen 10% bleiben leer

            for (int versuche = 0; versuche < 100; versuche++) {
                int x = room.x + 1 + rng.nextInt(Math.max(1, room.width - 1));
                int y = room.y + 1 + rng.nextInt(Math.max(1, room.height - 1));
                if (!istFreiFuerMonster(x, y)) continue;

                addObject(block, x, y);
                break;
            }
        }
    }

    private boolean istFreiFuerMonster(int x, int y){
        if (x < 1 || y < 1|| x >= getWidth() - 1 || y >= getHeight() - 1) return false;

        // wie ueberall sonst: wandposition ueber getX/getY pruefen, nicht ueber getObjectsAt
        // (das wandbild ist 40x80 gross und ragt in die nachbarzelle)
        boolean wand = getObjects(Wall.class).stream().anyMatch(w -> w.getX() == x && w.getY() == y);
        if (wand) return false;

        if (!getObjectsAt(x, y, Player.class).isEmpty()) return false;
        return getObjectsAt(x, y, entities.base.BaseMonster.class).isEmpty();
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
    @Override public void act(){
        String key = Greenfoot.getKey();

        if (Settings.pauseKey.equals(key)) {
            togglePause();
        }
    }
    public void togglePause(){
        paused = !paused;
        if (paused){
            showPause();
        }else{
            hidePause();
        }
    }
    public void showPause(){
        int cx = getWidth() / 2;
        int cy = getHeight() / 2;

        pauseScreen = new PauseScreen(getWidth() * getCellSize(), getHeight() * getCellSize());
        addObject(pauseScreen, cx, cy);

        restartButton = new restartButton(gameStarter);
        addObject(restartButton, cx, cy - 3);

        settingPauseButton = new settingPauseButton();
        addObject(settingPauseButton, cx, cy);

        saveGameButton = new SaveGameButton(gameStarter);
        addObject(saveGameButton, cx, cy + 3);
    }

    public void hidePause(){
        showText(null, saveGameButton.getX(), saveGameButton.getY() - 2);   // speicher-meldung weg
        removeObject(pauseScreen);
        removeObject(restartButton);
        removeObject(settingPauseButton);
        removeObject(saveGameButton);
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

    public GameStarter getGameStarter() { return gameStarter; }
}


