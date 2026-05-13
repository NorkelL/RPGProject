package items;

import entities.Player;
import greenfoot.GreenfootImage;

import java.util.List;

public class HealthPotion extends Item {
    private static final int HEAL_AMOUNT = 30;
    private static final int SIZE = 20;

    public HealthPotion() {
        GreenfootImage img = new GreenfootImage("HealthPotion/HealthPotion.png");
        img.scale(SIZE, SIZE);
        setImage(img);
    }

    @Override
    public void use() {
        List<Player> players = getWorld().getObjects(Player.class);
        if (players.isEmpty()) return;
        Player player = players.get(0);
        player.setLife(Math.min(player.getLife() + HEAL_AMOUNT, player.getMaxLife()));
    }

    @Override
    public void act() {}
}
