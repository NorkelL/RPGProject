package items.misc;

import entities.Player;
import greenfoot.GreenfootImage;
import items.Item;
import items.util.OnHover;
import items.util.Rarity;

public class HoneyBottle extends Item {

    @OnHover.ShowOnHover
    private int invisibleDuration = 600;
    private static final int SIZE = 32;

    public HoneyBottle() {
        this(Rarity.setRarity());
    }

    public HoneyBottle(Rarity rarity) {
        super(rarity);
        GreenfootImage img = new GreenfootImage("items/HoneyBottle.png");
        img.scale(SIZE, SIZE);
        setImage(img);

        invisibleDuration = Rarity.makeRare(invisibleDuration, rarity);
    }

    @Override
    public void use(Player trigger) {
        if (trigger == null) return;
        trigger.setInvisible(true);
        trigger.setInvisibleTimer(invisibleDuration);
        trigger.removeItem(this);
    }

    @Override
    public void act() {}
}
