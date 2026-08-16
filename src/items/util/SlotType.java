package items.util;

public enum SlotType {
    GENERIC("UI/Inventory/InventorySlot.png"),
    HELMET("UI/Inventory/HelmetSlot.png"),
    CHESTPLATE("UI/Inventory/ChestUpgradeSlot.png"),
    MATERIAL("UI/Inventory/MaterialUpgradeSlot.png");

    private final String imagePath;

    SlotType(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
