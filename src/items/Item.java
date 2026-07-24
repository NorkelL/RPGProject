package items;

import entities.Player;
import entities.base.ImprovedActor;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import items.util.OnHover;
import items.util.Rarity;
import items.util.Useable;
import ui.ItemText;

public abstract class Item extends ImprovedActor implements Useable, OnHover {

    @OnHover.ShowOnHover
    public Rarity rarity;

    protected Item(){
        this(Rarity.setRarity());
    }

    protected Item(Rarity rarity) {
        this.rarity = rarity;
    }

    protected ItemText currentHoverer;

    public Item onTake(Actor trigger) {
        getWorld().removeObject(this);
        return this;
    }

    public void onPut(int x, int y) {
        getWorld().addObject(this, x, y);
    }

    @Override
    public void use(Player trigger) {}

    public void checkHover() {
        MouseInfo mouse =  Greenfoot.getMouseInfo();
        if(mouse != null) {
            boolean isHovering = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), this.getClass()).contains(this);
            if (isHovering && currentHoverer == null) {
                currentHoverer = new ItemText(hovering(),this);

                int x = getX() +2;
                if(x>getWorld().getWidth()-1){x=getWorld().getWidth()-1;}
                int y = getY() -1;
                if(y<0){y=0;}

                getWorld().addObject(currentHoverer, x, y);
            } else if (!isHovering && currentHoverer != null) {
                getWorld().removeObject(currentHoverer);
                currentHoverer = null;
            }
        }
    }
}

