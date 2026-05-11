package items;

public class Stock extends Waffen{

    private int damage;

    public Stock() {
        setDamage(1);
    }
    public Stock(int damage){
        setDamage(damage);
    }




    public int getDamage(){
        return damage;
    }
    public void setDamage(int newDamage){
        damage = newDamage;
    }

}
