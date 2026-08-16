package core;

import blocks.Chest;
import com.google.gson.*;
import entities.Player;
import greenfoot.*;
import items.util.ItemData;
import ui.worlds.MainMenu;
import util.WindowSizeManager;
import world.DungeonLevel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class GameStarter extends World {

    private long seedsseed;
    private Random seed;
    public List<DungeonLevel> pastLevels = new ArrayList<>();
    public DungeonLevel currentLevel;
    public static final Path SAVE_DIR = Path.of("saves");
    private Player player;


    public GameStarter() {
        super(1, 1, 1); //nie sichtbar
        WindowSizeManager.enforce();
        seedsseed = System.currentTimeMillis();
        seed = new Random(seedsseed);
        player = new Player();
        mainMenu();
    }

    public void mainMenu(){
        Greenfoot.setWorld(new MainMenu(this));
    }

    public void start() {
        player.gibStartwaffe();   // nur hier, resumeSave() laedt die waffen aus dem save
        currentLevel = new DungeonLevel(seed.nextLong(),this,player);
        Greenfoot.setWorld(currentLevel);

    }

    // komplett neues spiel: neuer seed, neuer spieler, keine alten level
    public void restart(){
        seedsseed = System.currentTimeMillis();
        seed = new Random(seedsseed);
        pastLevels = new ArrayList<>();
        player = new Player();
        start();
    }

    public void RenderNextWorld(){
        pastLevels.add(currentLevel);
        currentLevel = new DungeonLevel(seed.nextLong(),this,player);
        Greenfoot.setWorld(currentLevel);

    }

    public void resumeSave(Path p) throws IOException {
        String json = Files.readString(p);
        SaveData save = new Gson().fromJson(json, SaveData.class);
        if (save == null) { // leere oder kaputte datei
            return;
        }

        seedsseed = save.seed;
        seed = new Random(seedsseed);

        pastLevels = new ArrayList<>();

        for (int i = 0; i < save.currentLevel -1; i++) {
            world.DungeonLevel pl = new DungeonLevel(seed.nextLong(),this,player);
            pastLevels.add(pl);
            if(save.pastLevelLootedChests != null && i < save.pastLevelLootedChests.size() && save.pastLevelLootedChests.get(i) != null){
                for (int[] pos : save.pastLevelLootedChests.get(i)) {
                    List<Chest> chests = pl.getObjectsAt(pos[0], pos[1], Chest.class);
                    if (!chests.isEmpty()) {
                        chests.get(0).openChestWithoutDrops();
                    }
                }
            }
        }

        currentLevel = new DungeonLevel(seed.nextLong(),this,player);
        if (save.currentLevelLootedChests != null) {
            for (int[] pos : save.currentLevelLootedChests) {
                List<Chest> chests = currentLevel.getObjectsAt(pos[0], pos[1], Chest.class);
                if (!chests.isEmpty()) {
                    chests.get(0).openChestWithoutDrops();
                }
            }
        }
        currentLevel.movePlayer(save.playerX,save.playerY);
        currentLevel.player.setLife(save.health);
        currentLevel.player.ladeFortschritt(save.level, save.xp, save.maxLife, save.bonusDamage);
        currentLevel.player.setInventorys(save.inventorys);

        Greenfoot.setWorld(currentLevel);
    }

    public void saveGame() throws IOException {
        SaveData save =  new SaveData();
        save.playerX = currentLevel.player.getX();
        save.playerY = currentLevel.player.getY();
        save.health = currentLevel.player.getLife();
        save.level = currentLevel.player.getCurrentLevel();
        save.xp = currentLevel.player.getCurrentXP();
        save.maxLife = currentLevel.player.getMaxLife();
        save.bonusDamage = currentLevel.player.getBonusDamage();
        save.currentLevel = pastLevels.size()+1;
        save.seed = seedsseed;
        save.pastLevelLootedChests = new  ArrayList<>();
        for (DungeonLevel pastLevel : pastLevels) {
            save.pastLevelLootedChests.add(pastLevel.getOpenedChests());
        }
        save.currentLevelLootedChests = currentLevel.getOpenedChests();
        save.inventorys = currentLevel.player.getInventorys();

        Files.createDirectories(SAVE_DIR);   // ordner gibt es beim ersten start noch nicht

        String filename = "save_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM_HH-mm")) + ".json";
        Files.writeString(SAVE_DIR.resolve(filename), new GsonBuilder().setPrettyPrinting().create().toJson(save));
    }
}

class SaveData{
    int playerX,playerY;
    int health;
    int currentLevel;
    long seed;
    int level;
    int xp;
    int maxLife;
    int bonusDamage;
    List<List<ItemData>> inventorys;
    List<List<int[]>> pastLevelLootedChests;
    List<int[]> currentLevelLootedChests;
}