package items;

import entities.base.ImprovedActor;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import items.util.OnHover;
import items.util.Useable;
import ui.ItemText;

import java.util.List;

public abstract class Item extends ImprovedActor implements Useable, OnHover {

    protected ItemText currentHoverer;

    public Item onTake(Actor trigger) {
        getWorld().removeObject(this);
        return this;
    }
    public Item(){
        GreenfootImage img = new GreenfootImage("items/" +this.getClass().getSimpleName() + ".png");
        img.scale(32,32);
        setImage(img);
    }

    public void onPut(int x, int y) {
        getWorld().addObject(this, x, y);
    }

    @Override
    public void use() {}

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
