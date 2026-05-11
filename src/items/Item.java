package items;

import entities.ImprovedActor;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import world.DungeonLevel;

public abstract class Item extends ImprovedActor implements Useable {
    public Item() {
        GreenfootImage image = new GreenfootImage("Items/TestItem.png");
        setImage(image);
    }

    public Item onTake(Actor trigger) {
        getWorld().removeObject(this);
        return this;
    }

    public void onPut(int x, int y) {
        if (getWorld() instanceof DungeonLevel) {
            ((DungeonLevel) getWorld()).addWorldObject(this, x, y);
        } else {
            getWorld().addObject(this, x, y);
        }
    }

    public String getDisplayName() {
        return getClass().getSimpleName();
    }

    @Override
    public void use() {}
}
