package items;

public class Sword extends Waffen{

    private int damage;
    private int distance;

    public Sword(int damage,int distance) {
        super(10, 2);
        setImage("Waffe.Schwert.png");
    }
}

