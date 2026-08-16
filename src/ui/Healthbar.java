package ui;

import entities.Player;
import greenfoot.GreenfootImage;
import util.FontManager;

import java.awt.Color;

public class Healthbar extends UI{
    private final Player player;
    private static final int WIDTH = 420;
    private static final int HEIGHT = 39;

    private GreenfootImage barImage; // das skalierte bild zur aktuellen stufe
    private int lastStep = -1;
    private int lastLife = -1;

    public Healthbar(Player player) {
        this.player = player;
        update();
    }

    @Override
    public void act() {
        update();
    }

    private void update() {
        if (player.getLife() == lastLife) {
            return;
        }
        lastLife = player.getLife();

        int prozent = 100 * lastLife / player.getMaxLife();
        if(prozent<0){prozent=0;}
        if(prozent>100){prozent=100;}

        int step = prozent / 5 * 5; // die bilder gibt es nur in 5er-schritten
        if (step != lastStep) {     // das grosse png nur laden wenn sich die stufe aendert
            lastStep = step;

            String name = "h" + step;
            if(step<10){name = "h0" + step;}

            barImage = new GreenfootImage("UI/healthBar/" + name + ".png");
            barImage.scale(WIDTH, HEIGHT);
        }

        // gleiche schrift wie die item-infos beim hovern, blau wie die floor tiles
        GreenfootImage img = new GreenfootImage(barImage);
        GreenfootImage leben = FontManager.renderText(lastLife + "/" + player.getMaxLife(), FontManager.getMinecraft(14f), new Color(74, 83, 112));
        img.drawImage(leben, (WIDTH - leben.getWidth()) / 2, (HEIGHT - leben.getHeight()) / 2);

        setImage(img);
    }
}
