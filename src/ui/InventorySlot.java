package ui;

import greenfoot.Actor;
import greenfoot.GreenfootImage;

public class InventorySlot extends Actor {
    private Actor item;

    private static final int SIZE = 40;
    private static final GreenfootImage baseImage;
    static {
        baseImage = new GreenfootImage("InventorySlot.png");
        baseImage.scale(SIZE, SIZE);
    }

    public InventorySlot() {
        this(200, 200); // Default size, assuming 200x200 fills a 2x2 cell area
    }

    // New constructor to specify slot pixel dimensions
    public InventorySlot(int pixelWidth, int pixelHeight) {
        baseImage = new GreenfootImage("InventorySlot.png");
        baseImage.scale(pixelWidth, pixelHeight);
        setImage(new GreenfootImage(baseImage));
    }

    public InventorySlot(Actor item) {
        this();
        setItem(item);
    }

    public void setItem(Actor item) {
        this.item = item;
        GreenfootImage currentImage = new GreenfootImage(baseImage); // Start with the scaled base image

        if (item != null) {
            // Draw the item's image onto the scaled base image
            // Assuming item's image might be smaller, center it
            GreenfootImage itemImage = item.getImage();
            // Ensure itemImage is not null before drawing
            if (itemImage != null) {
                currentImage.drawImage(itemImage,
                                       (currentImage.getWidth() - itemImage.getWidth()) / 2,
                                       (currentImage.getHeight() - itemImage.getHeight()) / 2);
            }
        }
        setImage(currentImage);
    }

    public Actor getItem() {
        return item;
    }

    @Override
    public void act() {
        super.act();
    }
}
