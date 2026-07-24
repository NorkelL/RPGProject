package core;

import blocks.Chest;
import com.google.gson.*;
import greenfoot.*;
import items.util.ItemData;
import ui.worlds.MainMenu;
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


    public GameStarter() {
        super(1, 1, 1); //nie sichtbar
        seedsseed = System.currentTimeMillis();
        seed = new Random(seedsseed);
        mainMenu();
    }

    private void mainMenu(){
        Greenfoot.setWorld(new MainMenu(this));
    }

    public void start() {
        currentLevel = new DungeonLevel(seed.nextLong(),this);
        Greenfoot.setWorld(currentLevel);

    }

    public void RenderNextWorld(){
        pastLevels.add(currentLevel);
        currentLevel = new DungeonLevel(seed.nextLong(),this);
        Greenfoot.setWorld(currentLevel);

    }

    public void resumeSave(Path p) throws IOException {
        String json = Files.readString(p);
        SaveData save = new Gson().fromJson(json, SaveData.class);

        seedsseed = save.seed;
        seed = new Random(seedsseed);

        pastLevels = new ArrayList<>();

        for (int i = 0; i < save.currentLevel -1; i++) {
            world.DungeonLevel pl = new DungeonLevel(seed.nextLong(),this);
            pastLevels.add(pl);
            if(save.pastLevelLootedChests.get(i) != null){
                for (int[] pos : save.pastLevelLootedChests.get(i)) {
                    List<Chest> chests = pl.getObjectsAt(pos[0], pos[1], Chest.class);
                    if (!chests.isEmpty()) {
                        chests.get(0).openChestWithoutDrops();
                    }
                }
            }
        }

        currentLevel = new DungeonLevel(seed.nextLong(),this);
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
        currentLevel.player.setInventorys(save.inventorys);

        Greenfoot.setWorld(currentLevel);
    }

    public void saveGame() throws IOException {
        SaveData save =  new SaveData();
        save.playerX = currentLevel.player.getX();
        save.playerY = currentLevel.player.getY();
        save.health = currentLevel.player.getLife();
        save.currentLevel = pastLevels.size()+1;
        save.seed = seedsseed;
        save.pastLevelLootedChests = new  ArrayList<>();
        for (DungeonLevel pastLevel : pastLevels) {
            save.pastLevelLootedChests.add(pastLevel.getOpenedChests());
        }
        save.currentLevelLootedChests = currentLevel.getOpenedChests();
        save.inventorys = currentLevel.player.getInventorys();

        String filename = "save_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM_HH-mm")) + ".json";
        Files.writeString(SAVE_DIR.resolve(filename), new GsonBuilder().setPrettyPrinting().create().toJson(save));
    }
}

class SaveData{
    int playerX,playerY;
    int health;
    int currentLevel;
    long seed;
    List<List<ItemData>> inventorys;
    List<List<int[]>> pastLevelLootedChests;
    List<int[]> currentLevelLootedChests;
}