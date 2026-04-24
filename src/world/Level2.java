package world;

import entities.Monster;
import entities.Player;
import greenfoot.World;
import items.Carrot;

public class Level2 extends World {
    public Level2() {
        super(8, 8, 60);
        setBackground("cell.jpg");
        setPaintOrder(Star.class, Player.class, Carrot.class, Rock.class);

        addObject(new Player(100, 3, 8, 100, 5), 6, 3);

        addObject(new Monster(), 2, 2);
        addObject(new Monster(), 4, 5);

        addObject(new Carrot(10), 1, 1);
        addObject(new Carrot(15), 3, 6);

        addObject(new Rock(), 3, 3);
        for (int i = 0; i < 7; i++) {
            addObject(new Rock(), 0, i);
        }
    }
}
