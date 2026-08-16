package ui.worlds;

import entities.Player;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import items.Item;
import ui.InventorySlot;
import ui.InventoryVisualizer;
import ui.Settings;

public class Backpack extends World {
    private final Item[] backpackItems;
    private final InventorySlot[] slots;
    private final World previousWorld;
    private boolean eWasDown = true;
    private boolean escWasDown = true;
    private boolean rWasDown = true;
    private final InventorySlot headSlot;
    private final InventorySlot chestSlot;
    private final InventorySlot upgradeSlot1;
    private final InventorySlot upgradeSlot2;
    private final InventorySlot upgradeSlot3;
    private final Player player;
    private final boolean tableMode;

    public Backpack(Player player, Item[] playerItems, Item[] backpack, World world, boolean tableMode) {

        super(16, 9, 80);
        this.backpackItems = backpack;
        this.slots = new InventorySlot[15];
        this.previousWorld = world;
        this.player = player;
        this.tableMode = tableMode;

        GreenfootImage bg = new GreenfootImage("UI/Inventory/BackgroundFullInventory.png");
        bg.scale(1280, 720);
        setBackground(bg);

        addObject(new InventoryVisualizer(playerItems, 80, 80), 0, getHeight() - 1);

        int startX = 2;
        int startY = 1;
        int slotCounter = 0;

        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 3; x++) {
                if (slotCounter >= 15) break;

                slots[slotCounter] = new InventorySlot(80, 80);
                slots[slotCounter].getImage().scale(80, 80);

                if (slotCounter < backpackItems.length && backpackItems[slotCounter] != null) {
                    slots[slotCounter].setItem(backpackItems[slotCounter]);
                }

                addObject(slots[slotCounter], startX + x, startY + y);
                slotCounter++;
            }
        }

        headSlot = new InventorySlot(80, 80);
        headSlot.getImage().scale(80, 80);
        addObject(headSlot, 8, 1);

        chestSlot = new InventorySlot(80, 80);
        chestSlot.getImage().scale(80, 80);
        addObject(chestSlot, 8, 3);

        upgradeSlot1 = new InventorySlot(80, 80);
        upgradeSlot2 = new InventorySlot(80, 80);
        upgradeSlot3 = new InventorySlot(80, 80);
        addObject(upgradeSlot1, 7, 5);
        addObject(upgradeSlot2, 8, 5);
        addObject(upgradeSlot3, 9, 5);

        if (!tableMode) {
            upgradeSlot1.setLocked(true);
            upgradeSlot2.setLocked(true);
            upgradeSlot3.setLocked(true);
        }
    }

    @Override
    public void act() {

        updateBackpackSlots();
        updateArmorSlots();

        boolean eIsDown  = Greenfoot.isKeyDown(Settings.inventoryToggle);
        boolean escIsDown = Greenfoot.isKeyDown("escape");
        boolean rIsDown  = tableMode && Greenfoot.isKeyDown("R");

        if ((eIsDown && !eWasDown) || (escIsDown && !escWasDown) || (rIsDown && !rWasDown)) {
            Greenfoot.setWorld(previousWorld);
        }

        eWasDown  = eIsDown;
        escWasDown = escIsDown;
        rWasDown  = rIsDown;
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

    public boolean isTableMode() { return tableMode; }

    public Player getPlayer() { return player; }
    public InventorySlot getChestSlot() { return chestSlot; }
    public InventorySlot getHeadSlot() { return headSlot; }
    public InventorySlot getUpgradeSlot1() { return upgradeSlot1; }
    public InventorySlot getUpgradeSlot2() { return upgradeSlot2; }
    public InventorySlot getUpgradeSlot3() { return upgradeSlot3; }
}
