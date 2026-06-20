package items.util;

import java.awt.*;
import java.util.*;

import static java.awt.Color.*;

public enum Rarity {
    COMMON(1,1,WHITE),
    UNCOMMON(0.35,1.15,GREEN),
    RARE(0.25,1.5,BLUE),
    EPIC(0.04,2,MAGENTA),
    LEGENDARY(0.01,5,YELLOW);

    public final double Chance;
    public final double Multiplier;
    public final Color Color;
    private static final Random rng = new Random();

    Rarity(double Chance, double Multiplier, Color Color) {
        this.Chance = Chance;
        this.Multiplier = Multiplier;
        this.Color = Color;
    }

    public static Rarity setRarity() {
        int randomNum = rng.nextInt(100) + 1;
        double vergleich = LEGENDARY.Chance * 100;
        if(randomNum <= vergleich) {
            return LEGENDARY;
        }
        vergleich = vergleich + EPIC.Chance * 100;
        if(randomNum <= vergleich) {
            return EPIC;
        }
        vergleich = vergleich + RARE.Chance * 100;
        if(randomNum <= vergleich) {
            return RARE;
        }
        vergleich = vergleich + UNCOMMON.Chance * 100;
        if(randomNum <=vergleich) {
            return UNCOMMON;
        }
        return COMMON;

    }
}
