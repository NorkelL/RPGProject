package items.util;

import greenfoot.Greenfoot;
import items.misc.HealthPotion;
import items.Item;
import items.armor.LeatherArmor;


public enum ItemTyp {

    // Neue Items hier eintragen: NAME(Wahrscheinlichkeit) { public Item erstelleItem() { return new XY(); } }
    // Beispiel:
    // SWORD(70) { public Item erstelleItem() { return new Sword(); } },
    // POTION(20) { public Item erstelleItem() { return new Potion(); } };
    LeatherArmor(40){ public Item erstelleItem() { return new LeatherArmor("chest"); }},


    HEALTH_POTION(70) { public Item erstelleItem() { return new HealthPotion(); }};

    public final int gewicht;

    ItemTyp(int gewicht) {
        this.gewicht = gewicht;
    }

    public Item erstelleItem() {
        return null;
    }

    public static ItemTyp zufällig() {
        int gesamt = 0;
        for (ItemTyp typ : values()) gesamt += typ.gewicht;

        int zufall = Greenfoot.getRandomNumber(gesamt);
        for (ItemTyp typ : values()) {
            zufall -= typ.gewicht;
            if (zufall < 0) return typ;
        }
        return values()[0];
    }
}