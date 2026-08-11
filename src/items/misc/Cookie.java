package items.misc;

import entities.Player;
import greenfoot.GreenfootImage;
import items.Item;
import items.util.OnHover;
import items.util.Rarity;

public class Cookie extends Item {

    @OnHover.ShowOnHover
    private int healAmount = 15;
    private static final int SIZE = 32;

    public Cookie() {
        this(Rarity.setRarity());
    }

    public Cookie(Rarity rarity) {
        super(rarity);
        GreenfootImage img = new GreenfootImage("items/Cookie.png");
        img.scale(SIZE, SIZE);
        setImage(img);

        healAmount = Rarity.makeRare(healAmount, rarity);
    }

    @Override
    public void use(Player trigger) {
        if (trigger == null) return;
        trigger.setLife(Math.min(trigger.getLife() + healAmount, trigger.getMaxLife()));
        trigger.removeItem(this);
    }

    @Override
    public void act() {}
}
