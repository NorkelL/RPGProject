package items.waffen;

import entities.Player;
import entities.base.BaseMonster;
import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import java.util.List;


public class BowSprite extends Actor {

    private final Bow bow;

    private GreenfootImage normalImage;
    private GreenfootImage loadedImage;

    private int range = 300;
    private boolean isCharging = false;

    public BowSprite(Bow bow) {
        this.bow = bow;

        normalImage = new GreenfootImage("Weapons/Bow.png");
        normalImage.scale(40, 40);

        loadedImage = new GreenfootImage("Weapons/LoadedBow.png");
        loadedImage.scale(40, 40);

        setImage(normalImage);
    }

    public void update(Player player) {

        setLocation(player.getX(), player.getY());

        BaseMonster target = getClosestMonster(player);

        if (target != null) {
            turnTowards(target.getX(), target.getY());
        }

        if (Greenfoot.isKeyDown("space")) {
            if (!isCharging && bow.hasArrows(player)) {
                isCharging = true;
                setImage(loadedImage);
            }
        } else {
            if (isCharging) {
                bow.shoot(player, getRotation());
                isCharging = false;
                setImage(normalImage);
            }
        }
    }

    private BaseMonster getClosestMonster(Player player) {
        List<BaseMonster> monsters = player.getWorld().getObjects(BaseMonster.class);

        BaseMonster closest = null;
        double minDistance = range;

        for (BaseMonster m : monsters) {
            double dist = Math.hypot(
                    m.getX() - player.getX(),
                    m.getY() - player.getY());

            if (dist < minDistance) {
                minDistance = dist;
                closest = m;
            }
        }

        return closest;
    }
}
