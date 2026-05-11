package entities;

import greenfoot.Color;

public class Orc extends BaseMonster implements Hitting {
    public Orc() {
        super("Orc", 60, 14, 2);
    }

    @Override
    protected int getMoveDelay() {
        return 42;
    }

    @Override
    protected int getAttackDelay() {
        return 36;
    }

    @Override
    protected Color getPrimaryColor() {
        return new Color(123, 82, 57);
    }

    @Override
    protected Color getAccentColor() {
        return new Color(232, 214, 163);
    }
}
