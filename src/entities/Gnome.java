package entities;

import greenfoot.Color;

public class Gnome extends BaseMonster implements Hitting {
    public Gnome() {
        super("Gnome", 35, 8, 1);
    }

    @Override
    protected int getMoveDelay() {
        return 34;
    }

    @Override
    protected int getAttackDelay() {
        return 30;
    }

    @Override
    protected Color getPrimaryColor() {
        return new Color(78, 128, 92);
    }

    @Override
    protected Color getAccentColor() {
        return new Color(222, 231, 192);
    }
}
