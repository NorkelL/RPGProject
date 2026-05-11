package items;

import greenfoot.Greenfoot;

public enum ItemTyp {
    SWORD(35) { public Item erstelleItem() { return new SwordItem(); } },
    AXE(20) { public Item erstelleItem() { return new AxeItem(); } },
    POTION(35) { public Item erstelleItem() { return new PotionItem(); } },
    TEST(10) { public Item erstelleItem() { return new TestItem(); } };

    public final int gewicht;

    ItemTyp(int gewicht) {
        this.gewicht = gewicht;
    }

    public Item erstelleItem() { return null; }

    public static ItemTyp zufaellig() {
        int gesamt = 0;
        for (ItemTyp typ : values()) {
            gesamt += typ.gewicht;
        }

        int zufall = Greenfoot.getRandomNumber(gesamt);
        for (ItemTyp typ : values()) {
            zufall -= typ.gewicht;
            if (zufall < 0) {
                return typ;
            }
        }
        return values()[0];
    }
}
