package ui;

import entities.Player;
import greenfoot.Actor;
import greenfoot.World;
import items.Item;

import java.util.List;

public class InventoryVisualizer extends Actor {
    private final InventorySlot[] slots;
    private final Item[] inventory;
    private final int slotPixelWidth;
    private final int slotPixelHeight;



    public InventoryVisualizer(Item[] inventory, int slotPixelWidth, int slotPixelHeight) {
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
            slots[i] = new InventorySlot(getWorld().getCellSize(), getWorld().getCellSize());
            world.addObject(slots[i], startX + i * slotWidthInCells, slotY);
        }
    }

    private void update() {
        if (slots == null || inventory == null) return;

        int length = Math.min(inventory.length, slots.length);
        for (int i = 0; i < length; i++) {
            if (slots[i] != null) {
                // Wenn das UI-Item nicht mit dem Array-Item übereinstimmt, UI updaten!
                if (slots[i].getItem() != inventory[i]) {
                    slots[i].setItem(inventory[i]);
                }
            }
        }
    }

    //  zwingt das Array nach einem Tausch zum Reset
    public void forceSyncArray() {
        if (slots == null || inventory == null) return;

        int length = Math.min(inventory.length, slots.length);
        for (int i = 0; i < length; i++) {
            if (slots[i] != null) {
                // Wenn das Item im UI-Slot durch Drag-and-Drop weggezogen (null) wurde,
                // wird es hier jetzt auch im echten Spieler-Array gelöscht!
                inventory[i] = slots[i].getItem();
            }
        }
    }

    // Hilfsmethode, um zu prüfen, ob dieser Visualizer einen bestimmten Slot besitzt
    public boolean containsSlot(InventorySlot slot) {
        for (InventorySlot s : slots) {
            if (s == slot) return true;
        }
        return false;
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

                if (slots[i].isSelected() != (i == activeSlot)) {
                    slots[i].setSelected(i == activeSlot);
                }
            }
        }
    }




}
