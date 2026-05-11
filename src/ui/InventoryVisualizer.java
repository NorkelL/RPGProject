package ui;

import entities.Player;
import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import greenfoot.World;
import items.Item;

public class InventoryVisualizer extends Actor {
    private final InventorySlot[] slots;
    private final Player player;
    private boolean openMode;

    public InventoryVisualizer(Player player) {
        this.player = player;
        setImage(new GreenfootImage(1, 1));
        getImage().setTransparency(0);
        slots = new InventorySlot[player.getItems().length];
    }

    @Override
    public void act() {
        updateLayout();
        updateSlots();
        updateLabel();
    }

    @Override
    protected void addedToWorld(World world) {
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new InventorySlot(this, i);
            world.addObject(slots[i], 0, 0);
        }
        updateLayout();
        updateSlots();
    }

    private void updateLayout() {
        openMode = player.isInventoryOpen();
        int size = openMode ? 64 : 40;
        int gap = openMode ? 1 : 0;
        int totalWidth = slots.length * (1 + gap);
        int startX = Math.max(0, (getWorld().getWidth() - totalWidth) / 2);
        int y = openMode ? getWorld().getHeight() - 2 : getWorld().getHeight() - 1;
        for (int i = 0; i < slots.length; i++) {
            slots[i].setLocation(startX + i * (1 + gap), y);
        }
    }

    private void updateSlots() {
        for (int i = 0; i < slots.length; i++) {
            Item item = player.getInventoryItem(i);
            slots[i].updateState(item, openMode, player.isEquippedSlot(i));
        }
    }

    private void updateLabel() {
        if (!openMode) {
            getWorld().showText("I: inventory", getWorld().getWidth() / 2, getWorld().getHeight() - 2);
            return;
        }
        String text = "Inventory: click weapon to equip, click potion to use";
        WeaponLabel.draw(getWorld(), text);
    }

    public void onSlotClicked(int index) {
        player.useInventorySlot(index);
        Item item = player.getInventoryItem(index);
        if (item == null) {
            return;
        }
    }

    private static class WeaponLabel {
        static void draw(World world, String text) {
            world.showText(text, world.getWidth() / 2, world.getHeight() - 3);
        }
    }
}
