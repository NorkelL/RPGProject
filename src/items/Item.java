package items;

import entities.ImprovedActor;
import greenfoot.Actor;

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
