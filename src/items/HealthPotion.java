package items;

import entities.Player;
import greenfoot.Actor;
import greenfoot.GreenfootImage;
import items.util.OnHover;
import items.util.Rarity;

public class HealthPotion extends Item {
    private static int HEAL_AMOUNT = 30;
    private static final int SIZE = 32;
    private Player owner;

    @OnHover.ShowOnHover
    public Rarity rarity;

    public HealthPotion(){
        this(Rarity.setRarity());
    }

    public HealthPotion(Rarity rarity) {
        GreenfootImage img = new GreenfootImage("HealthPotion/HealthPotion.png");
        img.scale(SIZE, SIZE);
        setImage(img);

        this.rarity = rarity;
        HEAL_AMOUNT = Rarity.makeRare(HEAL_AMOUNT,rarity);
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
