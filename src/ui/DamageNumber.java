package ui;

import greenfoot.Actor;
import util.FontManager;

import java.awt.Color;

/**

 Kurz aufblitzende Schadenszahl ueber einem getroffenen Actor (Issue #10).
 Aufgebaut nach dem Vorbild von Explosion.*/
public class DamageNumber extends Actor {

    private static final long LEBENSDAUER_MS = 1000;
    private static final double AUSBLENDEN_AB = 0.6;

    private static final Color FARBE_NORMAL   = Color.WHITE;
    private static final Color FARBE_KRITISCH = new Color(255, 140, 0);   // orange

    private static final float GROESSE_NORMAL   = 16f;
    private static final float GROESSE_KRITISCH = 22f;

    private final long startZeit;
    private final long endeZeit;

    public DamageNumber(int schaden, boolean kritisch) {
        String text = kritisch ? schaden + "!" : String.valueOf(schaden);

        setImage(FontManager.renderText(
                text,
                FontManager.getMinecraftBold(kritisch ? GROESSE_KRITISCH : GROESSE_NORMAL),
                kritisch ? FARBE_KRITISCH : FARBE_NORMAL));

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

        // im letzten Teil der Lebensdauer sanft ausblenden
        long vergangen = jetzt - startZeit;
        if (vergangen > LEBENSDAUER_MS * AUSBLENDEN_AB) {
            long restMs     = endeZeit - jetzt;
            long ausblendMs = (long) (LEBENSDAUER_MS * (1 - AUSBLENDEN_AB));
            int transparenz = (int) (255 * restMs / ausblendMs);
            getImage().setTransparency(Math.max(0, Math.min(255, transparenz)));
        }
    }
}