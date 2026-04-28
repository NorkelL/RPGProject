package world;

import blocks.Rock;
import core.GameStarter;
import entities.Player;
import greenfoot.GreenfootImage;
import greenfoot.World;

public class MainMenu extends World {
   private GameStarter gameStarter;




    public MainMenu(GameStarter gameStarter) {
        super(9, 9, 60);
        setBackground("cell.jpg");
        this.gameStarter = gameStarter;
        addObject(new StartButton(gameStarter), 4, 5);





    }
}