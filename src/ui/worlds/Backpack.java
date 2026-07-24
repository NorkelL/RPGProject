package ui.worlds;

import entities.Player;
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
    private int openCooldownE = 0;
    private int openCooldownEsc = 0;
    private final InventorySlot headSlot;
    private final InventorySlot chestSlot;
    private final Player player;

    public Backpack(Player player, Item[] playerItems, Item[] backpack, World world) {

        super(16, 9, 80);
        this.backpackItems = backpack;
        this.slots = new InventorySlot[15];
        this.previousWorld = world;
        this.player = player;

        GreenfootImage bg = new GreenfootImage("UI/Inventory/BackgroundFullInventory.png");
        bg.scale(1280, 720);
        setBackground(bg);


        addObject(new InventoryVisualizer(playerItems,80,80), 0, getHeight() - 1);


        int startX = 2;
        int startY = 1;
        int slotCounter = 0;

        // Äußere Schleife für die ZEILEN (Y-Achse)
        for (int y = 0; y < 5; y++) {
            // Innere Schleife für die SPALTEN (X-Achse)
            for (int x = 0; x < 3; x++) {
                // Sobald wir 15 Slots gebaut haben, hören wir auf
                if (slotCounter >= 15) break;

                // Neuen Slot erstellen
                slots[slotCounter] = new InventorySlot(80,80);


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
        // --- 2. RÜSTUNGS-SLOTS INSTANZIIEREN & PLATZIEREN ---

        headSlot = new InventorySlot(80,80);
        headSlot.getImage().scale(80, 80);
        addObject(headSlot, 8, 1); // Reihe 1 für den Helm

        chestSlot = new InventorySlot(80,80);
        chestSlot.getImage().scale(80, 80);
        addObject(chestSlot, 8, 3); // Reihe 2 für die Brust
    }

    @Override
    public void act() {

        updateBackpackSlots();
        updateArmorSlots();

        if(openCooldownE > 0 && !Greenfoot.isKeyDown("E")){
            openCooldownE = 0;
        } else if (openCooldownE > 0) {
            openCooldownE--;
        }

        if(openCooldownEsc > 0 && !Greenfoot.isKeyDown("escape")){
            openCooldownEsc = 0;
        } else if (openCooldownEsc > 0) {
            openCooldownEsc--;
        }

        // Backpack schließen
        if (Greenfoot.isKeyDown("escape") && openCooldownEsc == 0 || Greenfoot.isKeyDown("e") && openCooldownE ==0) {
            openCooldownE = openCooldownEsc = 1000;
            Greenfoot.setWorld(previousWorld);
        }
    }

    private void updateBackpackSlots() {
        int length = Math.min(backpackItems.length, slots.length);
        for (int i = 0; i < length; i++) {
            if (slots[i] != null) {
                if (backpackItems[i] != slots[i].getItem()) {
                    backpackItems[i] = (items.Item) slots[i].getItem();
                }
            }
        }
    }
    private void updateArmorSlots() {
        if (headSlot.getItem() != player.getHeadArmor()) {
            headSlot.setItem(player.getHeadArmor());
        }
        if (chestSlot.getItem() != player.getChestArmor()) {
            chestSlot.setItem(player.getChestArmor());
        }
    }

    public Player getPlayer() {
        return player;
    }
    public InventorySlot getChestSlot() {
        return chestSlot;
    }
    public InventorySlot getHeadSlot() {
        return headSlot;
    }

}