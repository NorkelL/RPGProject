package items;

public class Sword extends Waffen{
    private int damage;

    public Sword() {
        setDamage(5);
    }
    public Sword(int damage){
        setDamage(damage);
    }




    public int getDamage(){
        return damage;
    }
    public void setDamage(int newDamage){
        damage = newDamage;
    }
}
