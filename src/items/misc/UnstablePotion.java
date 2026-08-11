package items.misc;

import entities.Player;
import greenfoot.GreenfootImage;
import items.Item;
import items.util.Rarity;

public class UnstablePotion extends Item {

    private static final int SIZE = 32;

    public UnstablePotion() {
        this(Rarity.setRarity());
    }

    public UnstablePotion(Rarity rarity) {
        super(rarity);
        GreenfootImage img = new GreenfootImage("items/UnstablePotion.png");
        img.scale(SIZE, SIZE);
        setImage(img);
    }

    @Override
    public void use(Player trigger) {
        if (trigger == null) return;
        trigger.removeItem(this);
    }

    @Override
    public void act() {}
}
