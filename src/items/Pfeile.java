package items;

public class Pfeile extends Waffen{
        private int damage;
        private int distance;

    public Pfeile(int damage,int distance) {
            super(10, 1);
            setImage("Waffen.Pfeil.png");
    }
}
