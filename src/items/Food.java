package items;

public abstract class Food extends Item {
    private int weight;

    public Food() {
        this(5);
    }

    public Food(int weight) {
        setWeight(weight);
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
