package ui;

import entities.Player;
import greenfoot.Actor;
import greenfoot.World;

import java.util.List;

public class InventoryVisualizer extends Actor {
    private final InventorySlot[] slots;
    private final Actor[] inventory;
    private final int slotPixelWidth;
    private final int slotPixelHeight;


    // Modified constructor to accept slot pixel dimensions
    public InventoryVisualizer(Actor[] inventory, int slotPixelWidth, int slotPixelHeight) {
        getImage().setTransparency(0);
        slots = new InventorySlot[inventory.length];
        this.inventory = inventory;
        this.slotPixelWidth = slotPixelWidth;
        this.slotPixelHeight = slotPixelHeight;
    }
    
    @Override
    public void act() {
        update();
        checkSlot();
    }

    protected void addedToWorld(World world) {
        int numSlots = slots.length;

        int assumedCellSize = 60;
        int slotWidthInCells = slotPixelWidth / assumedCellSize;
        int slotHeightInCells = slotPixelHeight / assumedCellSize;
                        //keine ahnung was das hier ist aber ich habe jetzt einfach getCellSize() benutzt
                        //muss man nochmal schauen todo

        if (slotWidthInCells == 0) slotWidthInCells = 1;
        if (slotHeightInCells == 0) slotHeightInCells = 1;

        int totalSlotWidthInCells = numSlots * slotWidthInCells;


        int startX = (world.getWidth() - totalSlotWidthInCells) / 2 + (slotWidthInCells / 2);

        int slotY = world.getHeight() - (slotHeightInCells / 2);

        for (int i = 0; i < numSlots; i++) {
            // Pass the specified pixel dimensions to the InventorySlot constructor
            slots[i] = new InventorySlot(getWorld().getCellSize(), getWorld().getCellSize());
            world.addObject(slots[i], startX + i * slotWidthInCells, slotY);
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
    public void removeSelf() {
        // Entfernt alle Slots, die dieser Visualizer erstellt hat
        for (InventorySlot slot : slots) {
            if (slot != null && slot.getWorld() != null) {
                getWorld().removeObject(slot);
            }
        }

        getWorld().removeObject(this);
    }
    private void checkSlot() {
        List<Player> players = getWorld().getObjects(Player.class);

        if (players.isEmpty()) {
            return;
        }

        int activeSlot = players.get(0).getActiveSlot();

        for (int i = 0; i < slots.length; i++) {
            if (slots[i] != null) {
                slots[i].setSelected(i == activeSlot);
            }
        }
    }




}
