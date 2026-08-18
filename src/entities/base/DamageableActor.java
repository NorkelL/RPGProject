package entities.base;

import util.SoundManager;
import greenfoot.World;
import ui.DamageNumber;

//alles was leben hat und schaden nehmen kann, also spieler und monster
public abstract class DamageableActor extends MovingActor {
    private int life;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = Math.max(0, life);
    }

   /** Normaler Treffer ohne kritischen Schaden. */
    public void takeDamage(int damage) {
        takeDamage(damage, false);
    }

    /**
     damage   bereits fertig berechneter Schaden (inkl. Kritfaktor)
     kritisch nur fuer die Darstellung - faerbt und vergroessert die Zahl*/
    public void takeDamage(int damage, boolean kritisch) {
        if (damage <= 0) return;

        // WICHTIG: die Zahl muss vor onDeath() erzeugt werden.
        // onDeath() nimmt den Actor aus der Welt, danach liefert getWorld()
        // null und getX()/getY() werfen eine Exception.
        zeigeSchadenszahl(damage, kritisch);

        setLife(life - damage);
        if (life < 1) {
            onDeath();
        } else {
            onDamageSound();
        }
    }

    /** Setzt die Schadenszahl ein Feld ueber den getroffenen Actor. */
    protected void zeigeSchadenszahl(int damage, boolean kritisch) {
        World welt = getWorld();
        if (welt == null) return;   // Actor haengt gerade nicht in einer Welt

        int y = getY() - 1;
        if (y < 0) y = 0;           // am oberen Rand nicht aus der Welt rutschen

        welt.addObject(new DamageNumber(damage, kritisch), getX(), y);
    }

    protected void onDamageSound() {
        SoundManager.play("damage.mp3");
    }

    public void hit(int damage) {
        takeDamage(damage);
    }

    protected abstract void onDeath();
}
