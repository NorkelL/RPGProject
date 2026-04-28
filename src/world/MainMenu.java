package world;

import blocks.Rock;
import core.GameStarter;
import entities.Player;
import greenfoot.GreenfootImage;
import greenfoot.World;

public class MainMenu extends World {
   private GameStarter gameStarter;




    public MainMenu(GameStarter gameStarter) {
        super(16, 9, 60);
        GreenfootImage bg = new GreenfootImage("MainMenu.png");
        bg.scale(960, 540);
        setBackground(bg);
        this.gameStarter = gameStarter;
        addObject(new StartButton(gameStarter), 4, 5);





    }
    public MainMenu() {
        this(new GameStarter());
    }
}