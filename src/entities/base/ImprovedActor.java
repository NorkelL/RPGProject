package entities.base;

import greenfoot.Actor;
import greenfoot.GreenfootImage;
import util.ImprovedGreenfootImage;
import world.GridWorld;

public class ImprovedActor extends Actor {
    private GreenfootImage currentImage;

    /**
     * Tile-X dieses Actors. In einer {@link GridWorld} wird die physische
     * Zellkoordinate in das logische Tile-Raster umgerechnet; in einer
     * gewöhnlichen World entspricht das Tile schlicht der Zelle.
     */
    public int getTileX() {
        return (getWorld() instanceof GridWorld)
            ? ((GridWorld) getWorld()).cellToTile(getX())
            : getX();
    }

    /** Tile-Y dieses Actors. Siehe {@link #getTileX()}. */
    public int getTileY() {
        return (getWorld() instanceof GridWorld)
            ? ((GridWorld) getWorld()).cellToTile(getY())
            : getY();
    }

    @Override
    public void setImage(GreenfootImage image) {
        currentImage = image;
        super.setImage(new ImprovedGreenfootImage(image));
    }

    public void draw(String text) {
        ImprovedGreenfootImage image = new ImprovedGreenfootImage(currentImage);
        image.drawString(text, 0, 10);
        super.setImage(image);
    }

    public void draw(int value) {
        draw(String.valueOf(value));
    }
}
