package ui.worlds;

import core.GameStarter;
import greenfoot.GreenfootImage;
import greenfoot.World;
import ui.Buttons.LoadGameButton;
import ui.Buttons.SettingsButton;
import ui.Buttons.StartButton;

public class MainMenu extends World {

    private GameStarter gameStarter;


    public MainMenu(GameStarter gameStarter) {
        super(16, 9, 60);
        GreenfootImage bg = new GreenfootImage("UI/MainMenu/MainMenu.png");

        bg.scale(960, 540);
        setBackground(bg);
        this.gameStarter = gameStarter;
        int cx = getWidth()/2;
        int cy = getHeight()/2;



        addObject(new StartButton(gameStarter), cx, cy-1);
        addObject(new LoadGameButton(gameStarter), cx, cy+1);
        addObject(new SettingsButton(gameStarter), cx, cy+3);
    }
}