package ui;

import greenfoot.Actor;
import greenfoot.GreenfootImage;
import util.FontManager;

import java.awt.Color;

public class LevelUpMessage extends Actor {

    private static final long LEBENSDAUER_MS = 1800;
    private static final double AUSBLENDEN_AB = 0.6;

    private static final Color GOLD = new Color(255, 199, 44);

    private static final float GROESSE_TITEL = 28f;
    private static final float GROESSE_BONUS = 15f;

    private final long startZeit;
    private final long endeZeit;

    public LevelUpMessage(int bonusLeben, int bonusSchaden) {
        GreenfootImage titel   = FontManager.renderText("LevelUP!", FontManager.getMinecraftBold(GROESSE_TITEL), GOLD);
        GreenfootImage leben   = FontManager.renderText("+" + bonusLeben + " HP", FontManager.getMinecraft(GROESSE_BONUS), GOLD);
        GreenfootImage schaden = FontManager.renderText("+" + bonusSchaden + " DMG", FontManager.getMinecraft(GROESSE_BONUS), GOLD);

        boolean zeigeSchaden = bonusSchaden > 0;

        int breite = Math.max(titel.getWidth(), leben.getWidth());
        if(zeigeSchaden){breite = Math.max(breite, schaden.getWidth());}

        int hoehe = titel.getHeight() + leben.getHeight();
        if(zeigeSchaden){hoehe = hoehe + schaden.getHeight();}

        GreenfootImage img = new GreenfootImage(breite, hoehe);
        img.drawImage(titel, (breite - titel.getWidth()) / 2, 0);
        img.drawImage(leben, (breite - leben.getWidth()) / 2, titel.getHeight());
        if (zeigeSchaden) {
            img.drawImage(schaden, (breite - schaden.getWidth()) / 2, titel.getHeight() + leben.getHeight());
        }

        setImage(img);

        startZeit = System.currentTimeMillis();
        endeZeit  = startZeit + LEBENSDAUER_MS;
    }

    @Override
    public void act() {
        long jetzt = System.currentTimeMillis();

        if (jetzt >= endeZeit) {
            getWorld().removeObject(this);
            return;
        }

        long vergangen = jetzt - startZeit;
        if (vergangen > LEBENSDAUER_MS * AUSBLENDEN_AB) {
            long restMs     = endeZeit - jetzt;
            long ausblendMs = (long) (LEBENSDAUER_MS * (1 - AUSBLENDEN_AB));
            int transparenz = (int) (255 * restMs / ausblendMs);
            getImage().setTransparency(Math.max(0, Math.min(255, transparenz)));
        }
    }
}
