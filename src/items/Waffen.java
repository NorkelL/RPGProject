package items;

import entities.base.BaseMonster;
import greenfoot.World;

import java.util.List;

public abstract class Waffen extends Item {

    private int damage;
    private int distance;


    public Waffen(int damage, int distance){
        setDamage(damage);
        setDistance(distance);
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
        List<BaseMonster> monsters = myWorld.getObjectsAt(getNextX(distance), getNextY(distance), BaseMonster.class);
       if (!monsters.isEmpty()) {
            BaseMonster monster = monsters.get(0);
            monster.reciveHit(damage);
       }


   }




    public int getDamage(){
        return damage;
    }
    public int getDistance(){return distance;}
    public void setDamage(int newDamage){
        damage = newDamage;
        draw(damage);
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }


}

