package items;

import blocks.Wall;
import entities.Player;
import entities.base.BaseMonster;
import entities.base.MovingActor;
import greenfoot.Greenfoot;
import greenfoot.World;
import items.util.OnHover;
import items.util.Rarity;

import java.util.List;

//basis fuer alle waffen, regelt schaden, krit und reichweite
// die einzelnen waffen (Sword, Bow usw.) geben nur noch ihre werte an den konstruktor
public abstract class Waffen extends Item {

    //die annotation sorgt dafuer dass der wert im tooltip beim druebergehen auftaucht
    @OnHover.ShowOnHover
    private int damage;

    @OnHover.ShowOnHover
    private int kritChance;      // in Prozent, 0-100

    //reichweite in feldern, ab hier wird beim schlagen nicht weiter gesucht
    private int maxDistance;

    private static final int KRIT_FAKTOR = 2;
    private static final int STANDARD_KRIT_CHANCE = 15;

    public Waffen(int damage, int distance) {
        this(damage, distance, STANDARD_KRIT_CHANCE);
    }

    public Waffen(int damage, int distance, int kritChance) {
        this.kritChance = kritChance;

        // rarity wird schon im Konstruktor von Item gesetzt (laeuft vor diesem hier),
        // deshalb koennen wir den Schaden direkt danach hochskalieren
        setDamage(Rarity.makeRare(damage, rarity));
        setDistance(distance);
    }

    /**
     * Schlaegt in Blickrichtung des Angreifers zu.
     *
     * @param angreifer wer zuschlaegt - liefert Position und Blickrichtung
     * @return true, wenn wirklich etwas getroffen wurde
     */
    public boolean hit(MovingActor angreifer) {
        if (angreifer == null || angreifer.getWorld() == null) return false;

        World welt = angreifer.getWorld();

        // Start bei 0, nicht bei 1: das eigene Feld muss mitgeprueft werden.
        // canMove() blockt Monster nicht am Spieler, sie laufen also bis auf
        // dessen Feld. Wer erst ab Schritt 1 sucht, kann ein Monster, das in
        // einem drinsteht, nicht mehr treffen - waehrend es selbst zuschlaegt.
        for (int schritt = 0; schritt <= maxDistance; schritt++) {
            final int x = angreifer.getNextX(schritt);
            final int y = angreifer.getNextY(schritt);

            if (x < 0 || y < 0 || x >= welt.getWidth() || y >= welt.getHeight()) {
                return false;   // aus der Welt gelaufen
            }

            if (schritt > 0) {
                boolean wand = welt.getObjects(Wall.class).stream()
                        .anyMatch(w -> w.getX() == x && w.getY() == y);
                if (wand) return false;   // durch Waende schlaegt niemand
            }

            List<BaseMonster> getroffen = welt.getObjectsAt(x, y, BaseMonster.class);
            if (!getroffen.isEmpty()) {
                boolean kritisch = Greenfoot.getRandomNumber(100) < kritChance;
                int schaden = kritisch ? damage * KRIT_FAKTOR : damage;

                if (angreifer instanceof Player spieler) { schaden += spieler.getBonusDamage(); }

                getroffen.get(0).takeDamage(schaden, kritisch);
                return true;   // nur das erste Monster in der Reihe wird getroffen
            }
        }
        return false;
    }

    //gleiche rechnung wie im MovingActor, die waffe braucht sie fuer ihre reichweite
    public int getNextX(int distance) {
        double radians = Math.toRadians(getRotation());
        int dx = (int) Math.round(Math.cos(radians) * distance);
        return getX()+dx;
    }

    public int getNextY(int distance) {
        double radians = Math.toRadians(getRotation());
        int dy = (int) Math.round(Math.sin(radians) * distance);
        return getY()+dy;
    }

    public int getDamage(){ return damage; }
    public int getKritChance(){ return kritChance; }
    public int getMaxDistance(){ return maxDistance; }

    public void setDamage(int newDamage){
        damage = newDamage;
        draw(damage);
    }

    public void setDistance(int maxDistance) {
        this.maxDistance = maxDistance;
    }
}

