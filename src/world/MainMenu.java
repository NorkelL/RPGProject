package world;

import core.GameStarter;
import greenfoot.GreenfootImage;
import greenfoot.World;
import ui.LoadGameButton;
import ui.SettingsButton;
import ui.StartButton;

public class MainMenu extends World {
   private GameStarter gameStarter;




    public MainMenu(GameStarter gameStarter) {
        super(16, 9, 60);
        GreenfootImage bg = new GreenfootImage("Map/MainMenu.png");
        bg.scale(960, 540);
        setBackground(bg);
        this.gameStarter = gameStarter;
        int cx = getWidth()/2;
        int cy = getHeight()/2;

        addObject(new StartButton(), cx, cy-1);
        addObject(new LoadGameButton(), cx, cy+1);
        addObject(new SettingsButton(), cx, cy+3);






    }
    public MainMenu() {
        this(new GameStarter());
    }
}