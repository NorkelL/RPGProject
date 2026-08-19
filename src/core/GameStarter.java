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


//Zentrale Steuerung das games (vergleichbar mit main())
// die Klasse regelt die Level und den primären save/load workflow
//sie kümmert sich um den levelwechsel usw.
public class GameStarter extends World {

    //seedseed ist die einzige variable die gespeichert werden muss um
    // die Levelgeneration "deterministic" zu reproduzieren

    private long seedsseed;
    private Random seed;
    //pastlevel werden gespeichert um beim zurücklaufen den state der map beibehalten zu können
    // wird auch zum speichern der einzelnen state blöcke genutzt (chest die offen sind usw.)
    public List<DungeonLevel> pastLevels = new ArrayList<>();
    public List<DungeonLevel> futureLevels = new ArrayList<>();
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
        futureLevels = new ArrayList<>();
        player = new Player();
        start();
    }

    public void RenderNextWorld(){
        pastLevels.add(currentLevel);
        if(!futureLevels.isEmpty()){
            currentLevel = futureLevels.remove(futureLevels.size()-1);
            currentLevel.movePlayer(currentLevel.centerEntrance - 1,currentLevel.getHeight()-2);
        }else{
            currentLevel = new DungeonLevel(seed.nextLong(),this,player);
        }
        currentLevel.sperreTreppen();
        Greenfoot.setWorld(currentLevel);

    }

    public void RenderPastWorld(){
        if (pastLevels.isEmpty()) return;

        futureLevels.add(currentLevel);
        currentLevel = pastLevels.remove(pastLevels.size()-1);
        currentLevel.movePlayer(currentLevel.centerExit - 1,0);
        currentLevel.sperreTreppen();
        Greenfoot.setWorld(currentLevel);
    }

    //regeneration aus dem save.json zum spielstand
    public void resumeSave(Path p) throws IOException {
        String json = Files.readString(p);
        SaveData save = new Gson().fromJson(json, SaveData.class);
        if (save == null) { // leere oder kaputte datei
            return;
        }

        seedsseed = save.seed;
        seed = new Random(seedsseed);

        pastLevels = new ArrayList<>();
        futureLevels = new ArrayList<>();

        //alte level werden gebaut damit seed.nextLong() gleich oft laeuft und man zurücklaufen kann
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
        //spielerwerte auf den frisch gebauten level uebertragen
        currentLevel.movePlayer(save.playerX,save.playerY);
        currentLevel.player.setLife(save.health);
        currentLevel.player.ladeFortschritt(save.level, save.xp, save.maxLife, save.bonusDamage);
        currentLevel.player.setInventorys(save.inventorys);

        Greenfoot.setWorld(currentLevel);
    }

    //speichern das gesamten spielstandes durch SaveGame klasse und helper in anderen klassen
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

        Files.createDirectories(SAVE_DIR);

        String filename = "save_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM_HH-mm")) + ".json";
        Files.writeString(SAVE_DIR.resolve(filename), new GsonBuilder().setPrettyPrinting().create().toJson(save));
    }
}


//klasse zum umwandeln des Spielstandes zum .json
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