package items.misc;

import entities.Player;
import greenfoot.GreenfootImage;
import items.Item;
import items.util.OnHover;
import items.util.Rarity;

public class HealthPotion extends Item {

    @OnHover.ShowOnHover
    private int HEAL_AMOUNT = 30;
    private static final int SIZE = 32;

    public HealthPotion(){
        this(Rarity.setRarity());
    }

    public HealthPotion(Rarity rarity) {
        GreenfootImage img = new GreenfootImage("HealthPotion/HealthPotion.png");
        img.scale(SIZE, SIZE);
        setImage(img);

        HEAL_AMOUNT = Rarity.makeRare(HEAL_AMOUNT,rarity);
    }

    @Override
    public void use(Player trigger) {
        if (trigger == null) return;
        trigger.setLife(Math.min(trigger.getLife() + HEAL_AMOUNT, trigger.getMaxLife()));
        trigger.removeItem(this);
    }

    @Override
    public void act() {}
}
