package world;

import blocks.Rock;
import entities.Player;
import greenfoot.World;

public class MainMenu extends World {
    public MainMenu() {
        super(9, 9, 60);
        setBackground("cell.jpg");
        setPaintOrder(Player.class, Rock.class);

        addObject(new Player(), 4, 4);

        addObject(new Rock(), 3, 3);
        addObject(new Rock(), 5, 5);
        addObject(new Rock(), 2, 6);
    }
}