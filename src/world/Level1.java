package world;

import entities.Monster;
import entities.Player;
import greenfoot.World;
import items.Carrot;

public class Level1 extends World {
    public Level1() {
        super(9, 9, 60);
        setBackground("cell.jpg");
        setPaintOrder(Star.class, Player.class, Carrot.class, Rock.class);

        Player player = new Player(100, 5, 8, 100, 5);
        addObject(player, 0, 0);

        Rock rock = new Rock();
        addObject(rock, 5, 5);

        addObject(new Carrot(),     1, 5);
        addObject(new Carrot(21),   1, 4);
        addObject(new Carrot(7),    1, 3);
        addObject(new Carrot(15),   1, 2);

        addObject(new Monster(), 4, 4);
        addObject(new Monster(), 2, 6);

        for (int i = 0; i < 8; i++) {
            addObject(new Rock(), 8, i);
        }

        addObject(new Castle(1, 5), 3, 3);
    }
}
