package items;

public class Bow  extends Waffen {
    private int damage;
    private int distance;
    private int Pfeile;



    public Bow(int damage,int distance) {
        super(10, 20);
        setImage("Waffe.Bow.png");
    }

}
