package items;

import entities.Player;
import greenfoot.Actor;
import items.util.Useable;

public class Apple extends Item implements Useable {

    private int healing;
    private Player owner;


    public Apple (int Healing){
        super();
        this.healing = Healing;
    }
    @Override
    public Item onTake(Actor trigger) {
        if (trigger instanceof Player) owner = (Player) trigger;
        return super.onTake(trigger);
    }

    @Override
    public void use() {
        if (owner == null) return;
        owner.setLife(owner.getLife()+ healing);
        owner.removeItem(this);
    }
}
