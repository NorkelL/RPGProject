package items.waffen;

import entities.Player;
import entities.base.BaseMonster;
import greenfoot.GreenfootImage;
import items.Item;
import items.Waffen;
import items.util.Useable;
import ui.worlds.Backpack;
import entities.base.MovingActor;



public class Bow  extends Waffen implements Useable {

    private GreenfootImage normalImage;
    private GreenfootImage loadedImage;



    public Bow( int maxDistance,int damage) {
        super(damage,maxDistance);
        normalImage = new GreenfootImage("Weapons/Bow.png");
        normalImage.scale(60,60);
        loadedImage = new GreenfootImage("Weapons/LoadedBow.png");
        loadedImage.scale(60,60);
        setImage(normalImage);
    }

    public Bow(){this(5,3);}




    public boolean hasArrows(Player player) {

        for (Item item : player.getItems()) {
            if (item != null && item.getClass().getSimpleName().equals("Arrow")) return true;
        }
        return false;
    }

    private boolean consumeArrow(Player player) {
        if (player == null || player.getItems() == null) return false;

        Item[] items = player.getItems();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getClass().getSimpleName().equals("Arrow")) {
                items[i] = null; // Entfernt den ersten gefundenen Pfeil aus dem Slot
                return true;
            }
        }
        return false;
    }

    public void shoot(Player player, int rotation) {

        if (!consumeArrow(player)){
            return;
        }

        player.getWorld().addObject(new Arrow(rotation, getDamage() + player.getBonusDamage()), player.getX(), player.getY());

    }

    @Override
    public boolean hit(MovingActor angreifer) {
        return false;
    }


}
