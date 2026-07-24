package items;

import entities.Player;

public class Messer extends Waffen {
    private int damage;
    private int distance;

    public Messer(int damage,int distance) {
        super(15, 1);
        setImage("Waffe.Messer.png");
    }
}

