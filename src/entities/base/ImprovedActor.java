package entities.base;

import greenfoot.Actor;
import greenfoot.GreenfootImage;
import util.ImprovedGreenfootImage;
import world.DungeonLevel;

public class ImprovedActor extends Actor {
    private GreenfootImage currentImage;

    public boolean pausiert() {
        return getWorld() instanceof DungeonLevel && ((DungeonLevel) getWorld()).isPaused();
    }

    @Override
    public void setImage(GreenfootImage image) {
        currentImage = image;
        super.setImage(new ImprovedGreenfootImage(image));
    }

    public void draw(String text) {
        // noch kein bild gesetzt (z.b. aus dem konstruktor heraus) -> nichts zu zeichnen
        if (currentImage == null) return;

        ImprovedGreenfootImage image = new ImprovedGreenfootImage(currentImage);
        image.drawString(text, 0, 10);
        super.setImage(image);
    }

    public void draw(int value) {
        draw(String.valueOf(value));
    }
}
