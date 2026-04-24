package items;

public class Carrot extends Food {
    public Carrot() {
        this(5);
    }

    public Carrot(int weight) {
        super(weight);
        draw(getWeight());
    }
}
