package items;

import entities.Player;
import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class HealthPotion extends Item {
    private static final int HEAL_AMOUNT = 30;
    private static final int SIZE = 32;
    private Player owner;

    public HealthPotion() {
        GreenfootImage img = new GreenfootImage("HealthPotion/HealthPotion.png");
        img.scale(SIZE, SIZE);
        setImage(img);
    }

    @Override
    public Item onTake(Actor trigger) {
        if (trigger instanceof Player) owner = (Player) trigger;
        return super.onTake(trigger);
    }

    @Override
    public void use() {
        if (owner == null) return;
        owner.setLife(Math.min(owner.getLife() + HEAL_AMOUNT, owner.getMaxLife()));
        owner.removeItem(this);
    }

    @Override
    public void act() {}
}
