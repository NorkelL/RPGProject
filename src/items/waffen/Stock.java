package items.waffen;

import items.Waffen;

public class Stock extends Waffen {
    private int damage;
    private int distance;

    public Stock(int damage,int distance) {
        super(5, 2);
        setImage("Waffen.Stock.png");
    }

    public Stock(){this(5,2);}
}



