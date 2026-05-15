package items;

import entities.base.ImprovedActor;
import greenfoot.Actor;
import items.util.Useable;

public abstract class Item extends ImprovedActor implements Useable {

    public Item onTake(Actor trigger) {
        getWorld().removeObject(this);
        return this;
    }

    public void onPut(int x, int y) {
        getWorld().addObject(this, x, y);
    }

    @Override
    public void use() {}
}
