package items;

import entities.BaseMonster;
import entities.Monster;
import greenfoot.World;

import java.util.List;

public class Waffen extends Item {

    private int damage;

    public Waffen() {
        setDamage(3);
    }
    public Waffen(int damage){
        setDamage(damage);
    }

    public int getNextX(int distance){
        double radians = Math.toRadians(getRotation());
        int dx = (int) Math.round(Math.cos(radians) * distance);

        return getX()+dx;
    }
    public int getNextY(int distance){
        double radians = Math.toRadians(getRotation());
        int dy = (int) Math.round(Math.cos(radians) * distance);

        return getX()+dy;
    }

    public void hit() {
       World myWorld = getWorld();
        List<Monster> monsters = myWorld.getObjectsAt(getNextX(1), getNextY(1), Monster.class);
       if (!monsters.isEmpty()) {
            Monster monster = monsters.get(0);
           baseMonster.receiveHit;
       }


   }




    public int getDamage(){
        return damage;
    }
    public void setDamage(int newDamage){
        damage = newDamage;
        draw(damage);
    }

}

