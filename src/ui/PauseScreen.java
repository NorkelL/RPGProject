package ui;

import greenfoot.Actor;
import greenfoot.GreenfootImage;
import greenfoot.World;

public class PauseScreen extends Actor {


    private boolean isPaused= false;

    public boolean isPaused() {
        return isPaused;
    }

    public void activate () {
        World myWorld = getWorld();
        myWorld.addObject();
    }
    public void deactivate () {
        World myWorld = getWorld();
        myWorld.removeObject();
    }
//hi







}
