package ui;

import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import items.Item;
import items.util.OnHover;
import ui.worlds.Backpack;

public class InventorySlot extends Actor {
    private Item item;
    private final GreenfootImage baseImage;
    private final GreenfootImage glowingImage;
    private boolean isSelected = false;
    private final int slotPixelWidth;
    private final int slotPixelHeight;

    private ItemText currentHoverer;


    public InventorySlot() {
        this(60, 60);
    }


    public InventorySlot(int pixelWidth, int pixelHeight) {
        this.slotPixelWidth = pixelWidth;
        this.slotPixelHeight = pixelHeight;
        this.baseImage = new GreenfootImage("UI/Inventory/InventorySlot.png");
        this.baseImage.scale(slotPixelWidth, slotPixelHeight);

        // Leuchtendes Hintergrundbild laden und skalieren
        this.glowingImage = new GreenfootImage("UI/Inventory/InventorySlotGlowing.png");
        this.glowingImage.scale(slotPixelWidth, slotPixelHeight);

        // Erstes Zeichnen des Slots
        updateImage();
    }

    public InventorySlot(Item item) {
        this();
        setItem(item);
    }

    public void setItem(Item item) {
        this.item = item;
        updateImage(); // Bild neu zeichnen, wenn sich das Item ändert
    }

    public Item getItem() {
        return item;
    }

    public void setSelected(boolean selected) {
        if (this.isSelected != selected) {
            this.isSelected = selected;
            updateImage(); // Zeichnet den Slot neu (entweder normal oder leuchtend)
        }
    }

    public boolean isSelected() {
        return isSelected;
    }


    private void updateImage() {

        GreenfootImage currentBackground = isSelected ? new GreenfootImage(glowingImage) : new GreenfootImage(baseImage);


        if (item != null) {
            GreenfootImage itemImage = item.getImage();
            if (itemImage != null) {

                currentBackground.drawImage(itemImage,
                        (currentBackground.getWidth() - itemImage.getWidth()) / 2,
                        (currentBackground.getHeight() - itemImage.getHeight()) / 2);
            }
        }


        setImage(currentBackground);
    }

    @Override
    public void act() {
        super.act();
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null && item != null && getWorld() instanceof Backpack) {
            OnHover oh = item;
            boolean isHovering = getWorld()
                    .getObjectsAt(mouse.getX(), mouse.getY(), InventorySlot.class)
                    .contains(this);

            if (isHovering && currentHoverer == null) {
                currentHoverer = new ItemText(oh.hovering());
                getWorld().addObject(currentHoverer, getX(), getY() - 1);
            } else if (!isHovering && currentHoverer != null) {
                getWorld().removeObject(currentHoverer);
                currentHoverer = null;
            }
        }
    }
}
