package world;

import core.GameStarter;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import items.Item;
import ui.InventorySlot;
import ui.InventoryVisualizer;

public class Backpack extends World {
    // Wir merken uns das Rucksack-Array, um es wie beim Visualizer upzudaten
    private final Item[] backpackItems;
    private final InventorySlot[] slots;
    private final World previousWorld;

    public Backpack(Item[] playerItems, Item[] backpack,World world) {

        super(16, 9, 80);
        this.backpackItems = backpack;
        this.slots = new InventorySlot[15];
        this.previousWorld = world;

        GreenfootImage bg = new GreenfootImage("BackgroundFullInventory.png");
        bg.scale(1280, 720);
        setBackground(bg);

        // Deine Hotbar (Visualizer) wird ganz unten angezeigt
        addObject(new InventoryVisualizer(playerItems), 0, getHeight() - 1);

        // Die Slots im Raster anordnen
        int startX = 2; // Start-Spalte im Raster
        int startY = 1; // Start-Zeile im Raster
        int slotCounter = 0;

        // Äußere Schleife für die ZEILEN (Y-Achse)
        for (int y = 0; y < 5; y++) {
            // Innere Schleife für die SPALTEN (X-Achse)
            for (int x = 0; x < 3; x++) {
                // Sobald wir 15 Slots gebaut haben, hören wir auf
                if (slotCounter >= 15) break;

                // Neuen Slot erstellen
                slots[slotCounter] = new InventorySlot();

                // Da deine Kacheln 80x80 groß sind (siehe super(16,9,80)),
                // skaliere den Slot auch auf 80x80, damit er die Kachel perfekt ausfüllt!
                slots[slotCounter].getImage().scale(80, 80);

                // Prüfen, ob im Rucksack-Array an dieser Stelle schon ein Item liegt
                if (slotCounter < backpackItems.length && backpackItems[slotCounter] != null) {
                    slots[slotCounter].setItem(backpackItems[slotCounter]);
                }

                // Slot an der  Raster-Position hinzufügen
                addObject(slots[slotCounter], startX + x, startY + y);

                slotCounter++;
            }
        }
    }

    @Override
    public void act() {
        // Genau wie beim InventoryVisualizer: Wir spiegeln Änderungen live!
        updateBackpackSlots();

        // Möglichkeit bieten, die Inventarwelt wieder zu schließen
        if (Greenfoot.isKeyDown("escape") || Greenfoot.isKeyDown("e")) {
            Greenfoot.setWorld(previousWorld);
        }
    }

    private void updateBackpackSlots() {
        int length = Math.min(backpackItems.length, slots.length);
        for (int i = 0; i < length; i++) {
            if (slots[i] != null && backpackItems[i] != slots[i].getItem()) {
                slots[i].setItem(backpackItems[i]);
            }
        }
    }
}