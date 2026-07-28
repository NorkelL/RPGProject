package ui;

import entities.Player;
import greenfoot.Color;
import greenfoot.GreenfootImage;

public class XPBar extends UI {
    private final Player player;
    private static final int WIDTH = 420;
    private static final int HEIGHT = 36;

    public XPBar(Player player) {
        this.player = player;
        update();
    }

    @Override
    public void act() {
        update();
    }

    private void update() {
        GreenfootImage img = new GreenfootImage(WIDTH, HEIGHT);

        String lvlText = "LVL " + player.getCurrentLevel();
        GreenfootImage lvl = new GreenfootImage(lvlText, 13, new Color(255, 210, 0), new Color(0, 0, 0, 0));
        img.drawImage(lvl, 4, 2);

        int barX = 60;
        int barY = 6;
        int barW = WIDTH - barX - 6;
        int barH = HEIGHT - 14;

        img.setColor(new Color(140, 100, 20));
        img.fillRect(barX - 2, barY - 2, barW + 4, barH + 4);

        img.setColor(new Color(10, 30, 25));
        img.fillRect(barX, barY, barW, barH);

        double ratio = (double) player.getCurrentXP() / player.getXpToNextLevel();
        int fillW = (int)(ratio * (barW - 2));
        if (fillW > 0) {
            img.setColor(new Color(0, 195, 170));
            img.fillRect(barX + 1, barY + 1, fillW, barH - 2);
            img.setColor(new Color(140, 255, 240));
            img.fillRect(barX + 1, barY + 1, fillW, 3);
        }

        String xpText = player.getCurrentXP() + " / " + player.getXpToNextLevel();
        GreenfootImage xp = new GreenfootImage(xpText, 11, Color.WHITE, new Color(0, 0, 0, 0));
        img.drawImage(xp, barX + (barW - xp.getWidth()) / 2, barY + (barH - xp.getHeight()) / 2);

        setImage(img);
    }
}
