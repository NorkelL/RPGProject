package core;

import greenfoot.*;
import world.*;
import java.util.*;

public class GameStarter extends World {

    private Random seed;
    public List<DungeonLevel> pastLevel = new ArrayList<>();
    public DungeonLevel currentLevel;


    public GameStarter() {
        super(1, 1, 1); // minimale Welt, nie sichtbar
        seed = new Random(System.currentTimeMillis());
        mainMenu();
    }

    private void mainMenu(){
        Greenfoot.setWorld(new MainMenu(this));
    }

    private void start() {
        currentLevel = new DungeonLevel(seed.nextLong(),this);
        Greenfoot.setWorld(currentLevel);
    }

    public void setSeed(int savedseed){
        seed = new Random(savedseed);
        start();
    }

    public void RenderNextWorld(){
        pastLevel.add(currentLevel);
        currentLevel = new DungeonLevel(seed.nextLong(),this);
        Greenfoot.setWorld(currentLevel);
    }
}