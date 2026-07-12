package items;

import entities.Player;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import items.util.Useable;


public class HoneyBottle extends Item implements Useable {
    private int HealthCapacity;
    private Player owner;


    public HoneyBottle(){
        super();

    }
    @Override
    public Item onTake(Actor trigger) {
        if (trigger instanceof Player) owner = (Player) trigger;
        return super.onTake(trigger);
    }

    public void use() {
        if (owner == null) return;
        owner.setInvisible(true);
        owner.setInvisibleTimer(600);
        owner.removeItem(this);
    }

}
