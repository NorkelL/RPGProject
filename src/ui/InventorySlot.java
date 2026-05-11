package ui;

import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import items.Item;

public class InventorySlot extends Actor {
    private static final int COMPACT_SIZE = 40;
    private static final int OPEN_SIZE = 64;
    private final InventoryVisualizer owner;
    private final int index;
    private Item item;
    private boolean openMode;
    private boolean equipped;

    public InventorySlot(InventoryVisualizer owner, int index) {
        this.owner = owner;
        this.index = index;
        redraw();
    }

    @Override
    public void act() {
        if (openMode && Greenfoot.mouseClicked(this)) {
            owner.onSlotClicked(index);
        }
    }

    public void updateState(Item item, boolean openMode, boolean equipped) {
        if (this.item != item || this.openMode != openMode || this.equipped != equipped) {
            this.item = item;
            this.openMode = openMode;
            this.equipped = equipped;
            redraw();
        }
    }

    private void redraw() {
        int size = openMode ? OPEN_SIZE : COMPACT_SIZE;
        GreenfootImage image = new GreenfootImage(size, size);
        image.setColor(new Color(23, 31, 38, 240));
        image.fillRect(0, 0, size - 1, size - 1);
        image.setColor(equipped ? new Color(224, 189, 96) : new Color(183, 145, 84));
        image.drawRect(0, 0, size - 1, size - 1);
        image.drawRect(2, 2, size - 5, size - 5);
        if (item != null) {
            GreenfootImage preview = new GreenfootImage(item.getImage());
            int previewSize = openMode ? size - 20 : size - 10;
            preview.scale(previewSize, previewSize);
            image.drawImage(preview, (size - previewSize) / 2, openMode ? 8 : 5);
        }
        image.setColor(new Color(231, 218, 191));
        image.drawString(String.valueOf(index + 1), 6, size - 6);
        setImage(image);
    }
}
