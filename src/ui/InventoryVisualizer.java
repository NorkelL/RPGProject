package ui;

import greenfoot.Actor;
import greenfoot.World;
import world.GridWorld;

public class InventoryVisualizer extends Actor {
    private final InventorySlot[] slots;
    private final Actor[] inventory;

    public InventoryVisualizer(Actor[] inventory) {
        getImage().setTransparency(0);
        slots = new InventorySlot[inventory.length];
        this.inventory = inventory;
    }

    @Override
    public void act() {
        update();
    }

    @Override
    protected void addedToWorld(World world) {
        boolean grid = world instanceof GridWorld;
        int tileY = grid ? ((GridWorld) world).cellToTile(getY()) : getY();
        for (int i = 0; i < slots.length; i++) {
            slots[i] = new InventorySlot();
            if (grid) {
                ((GridWorld) world).addTile(slots[i], i, tileY);
            } else {
                world.addObject(slots[i], i, getY());
            }
        }
    }

    private void update() {
        int length = Math.min(inventory.length, slots.length);
        for (int i = 0; i < length; i++) {
            if (inventory[i] != slots[i].getItem()) {
                slots[i].setItem(inventory[i]);
            }
        }
    }
}
