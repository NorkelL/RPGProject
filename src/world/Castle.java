package world;

import entities.Monster;
import greenfoot.Actor;

import java.util.ArrayList;

public class Castle extends Actor {
    private final int maxMonster;
    private int countdown;
    private final int spawnDelay;
    private final ArrayList<Monster> spawnedMonsters = new ArrayList<>();

    public Castle(int maxMonster, int spawnDelay) {
        this.maxMonster = maxMonster;
        this.spawnDelay = spawnDelay;
        this.countdown = spawnDelay;
    }

    @Override
    public void act() {
        spawnedMonsters.removeIf(m -> m.getWorld() == null);
        if (countdown <= 0 && spawnedMonsters.size() < maxMonster) {
            Monster m = new Monster();
            getWorld().addObject(m, getX(), getY());
            spawnedMonsters.add(m);
            countdown = spawnDelay;
        }
        countdown--;
    }
}
