package ui;

import entities.Player;
import greenfoot.GreenfootImage;
import util.FontManager;

import java.awt.Color;

public class XPBar extends UI {
    private final Player player;
    private static final int WIDTH = 420;
    private static final int HEIGHT = 39;

    private int lastStep = -1;  // bild nur neu laden wenn sich die stufe wirklich aendert
    private int lastLevel = -1;

    public XPBar(Player player) {
        this.player = player;
        update();
    }

    @Override
    public void act() {
        update();
    }

    private void update() {
        int prozent = 100 * player.getCurrentXP() / player.getXpToNextLevel();
        if(prozent<0){prozent=0;}
        if(prozent>100){prozent=100;}

        int step = prozent / 5 * 5; // die bilder gibt es nur in 5er-schritten
        if (step == lastStep && player.getCurrentLevel() == lastLevel) {
            return;
        }
        lastStep = step;
        lastLevel = player.getCurrentLevel();

        String name = "xp" + step;
        if(step<10){name = "xp0" + step;}

        GreenfootImage img = new GreenfootImage("UI/xpBar/" + name + ".png");
        img.scale(WIDTH, HEIGHT);

        // gleiche schrift wie die item-infos beim hovern, blau wie die floor tiles
        GreenfootImage lvl = FontManager.renderText("LVL : " + lastLevel, FontManager.getMinecraft(14f), new Color(74, 83, 112));
        img.drawImage(lvl, (WIDTH - lvl.getWidth()) / 2, (HEIGHT - lvl.getHeight()) / 2);

        setImage(img);
    }
}
