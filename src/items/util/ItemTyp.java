package items.util;

import greenfoot.Greenfoot;
import items.misc.HealthPotion;
import items.Item;
import items.armor.LeatherArmor;
import items.waffen.Arrow;
import items.waffen.Bow;
import items.waffen.Messer;
import items.waffen.Stock;
import items.waffen.Sword;

import java.util.Random;


public enum ItemTyp {

    // Neue Items hier eintragen: NAME(Wahrscheinlichkeit) { public Item erstelleItem() { return new XY(); } }
    // Beispiel:
    // SWORD(70) { public Item erstelleItem() { return new Sword(); } },
    // POTION(20) { public Item erstelleItem() { return new Potion(); } };
    LeatherArmor(40){ public Item erstelleItem() { return new LeatherArmor("chest"); }},

    SWORD(25)  { public Item erstelleItem() { return new Sword();  }},
    MESSER(25) { public Item erstelleItem() { return new Messer(); }},
    STOCK(30)  { public Item erstelleItem() { return new Stock();  }},
    BOW(20)    { public Item erstelleItem() { return new Bow();    }},
    ARROW(60)  { public Item erstelleItem() { return new Arrow();  }},

    HEALTH_POTION(70) { public Item erstelleItem() { return new HealthPotion(); }};

    public final int gewicht;

    ItemTyp(int gewicht) {
        this.gewicht = gewicht;
    }

    public Item erstelleItem() {
        return null;
    }

    public static ItemTyp zufällig() {
        return waehle(Greenfoot.getRandomNumber(gesamtGewicht()));
    }

    // gleiche auswahl, aber aus einem gesetzten zufallsgenerator - damit ein level
    // mit demselben seed auch wieder dieselben items enthaelt
    public static ItemTyp zufällig(Random rng) {
        if (rng == null) return zufällig();
        return waehle(rng.nextInt(gesamtGewicht()));
    }

    private static int gesamtGewicht() {
        int gesamt = 0;
        for (ItemTyp typ : values()) gesamt += typ.gewicht;
        return gesamt;
    }

    private static ItemTyp waehle(int zufall) {
        for (ItemTyp typ : values()) {
            zufall -= typ.gewicht;
            if (zufall < 0) return typ;
        }
        return values()[0];
    }
}