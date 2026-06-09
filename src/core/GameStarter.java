package core;

import greenfoot.*;
import ui.worlds.MainMenu;

import java.util.*;
import world.DungeonLevel;

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

    public void start() {
        currentLevel = new DungeonLevel(seed.nextLong(),this);
        Greenfoot.setWorld(currentLevel);
    }

    public void setSeed(long savedseed,int currentLevel){
        seed = new Random(savedseed);
        for(int i = 1;i<currentLevel;i++){
            seed.nextLong();
        }
        start();
    }

    public void RenderNextWorld(){
        pastLevel.add(currentLevel);
        currentLevel = new DungeonLevel(seed.nextLong(),this);
        Greenfoot.setWorld(currentLevel);

    }
}