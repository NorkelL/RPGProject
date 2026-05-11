package entities;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class ImprovedActor extends Actor {
    private GreenfootImage currentImage;
    private int tileX;
    private int tileY;
    private boolean cameraVisible = true;

    @Override
    public void setImage(GreenfootImage image) {
        currentImage = image;
        GreenfootImage copy = new ImprovedGreenfootImage(image);
        applyVisibility(copy);
        super.setImage(copy);
    }

    public void draw(String text) {
        ImprovedGreenfootImage image = new ImprovedGreenfootImage(currentImage);
        image.drawString(text, 0, 10);
        applyVisibility(image);
        super.setImage(image);
    }

    public void draw(int value) {
        draw(String.valueOf(value));
    }

    public void setTileLocation(int tileX, int tileY) {
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setCameraVisible(boolean cameraVisible) {
        this.cameraVisible = cameraVisible;
        GreenfootImage image = getImage();
        if (image != null) {
            applyVisibility(image);
        }
    }

    private void applyVisibility(GreenfootImage image) {
        image.setTransparency(cameraVisible ? 255 : 0);
    }
}
