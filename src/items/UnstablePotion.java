package items;

import entities.Player;
import greenfoot.Actor;
import items.util.Useable;

public class UnstablePotion extends Item implements Useable {
    private int HealthCapacity;
    private Player owner;


    public UnstablePotion(){
        super();

    }
    @Override
    public Item onTake(Actor trigger) {
        if (trigger instanceof Player) owner = (Player) trigger;
        return super.onTake(trigger);
    }

    public void use() {

    }
}
