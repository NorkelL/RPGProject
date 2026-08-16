package ui;

import greenfoot.Actor;
import greenfoot.GreenfootImage;
import items.Item;


public class UpgradeSlot extends InventorySlot {

    private String slotType;

    public UpgradeSlot(String slotType) {

        super(80, 80);
        this.slotType = slotType;

        loadImage();
    }

    private void loadImage() {
        String imagePath;

        if ("Armor".equals(getSlotType())) {
            imagePath = "UI/Inventory/ChestUpgradeSlot.png";
        }
        else if ("Material".equals(getSlotType())) {
            imagePath = "UI/Inventory/MaterialUpgradeSlot.png";
        }
        else {
            imagePath = "UI/Inventory/InventorySlot.png";
        }

        // 1. Neues Basis-Bild laden und auf 80x80 skalieren
        this.baseImage = new GreenfootImage(imagePath);
        this.baseImage.scale(80, 80);

        // 2. WICHTIG: Das Bild sofort neu zeichnen lassen!
        updateImage();
    }

    public String getSlotType() {
        return slotType;
    }
}