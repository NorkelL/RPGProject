package entities;

import entities.base.DamageableActor;
import entities.util.Direction;
import greenfoot.Greenfoot;
import greenfoot.World;
import items.Armor;
import items.Item;
import items.LeatherArmor;
import items.util.GoldArmor;
import items.LeatherHelmet;
import items.util.Useable;
import ui.InventoryVisualizer;
import ui.Settings;
import ui.worlds.Backpack;
import world.DungeonLevel;

import java.util.List;

public class Player extends DamageableActor {
    private final Item[] items;       // Das ist deine Hotbar / visualizer (z.B. 8 Slots)
    private final Item[] backpack;    // Das große Hauptinventar (z.B. 24 Slots)
    private final Item[] armor = new Item[2];
    private final int maxItems;       // Größe der Hotbar
    private final int maxBackpack;    // Größe des Rucksacks
    private Backpack BackpackWorld; // die backpack welt
    private int openCooldownE = 0;

    private final int maxLife;
    private int moveCounter;
    private InventoryVisualizer inventory;
    private int activeSlot;
    private boolean hasArmor = false;
    private String currentArmorType = "none"; //head oder chest
    private Item headArmor = null; // z.B. "iron", "leather"
    private Item chestArmor = null;// z.B. "iron", "leather"


    public Player() {
        this(100, 8, 15,100);
    }

    public Player(int life, int maxItems, int maxBackpack, int maxLife) {
        this.maxItems = maxItems;
        this.maxBackpack = maxBackpack;
        this.maxLife = maxLife;

        this.items = new Item[maxItems];         // z.B. Size 8
        this.backpack = new Item[maxBackpack];

        setLife(life);
    }

    @Override
    public void act() {
        if(moveCounter>0){
            moveCounter--;
            return;
        }

        if(openCooldownE > 0 && !Greenfoot.isKeyDown("E")){
            openCooldownE = 0;
        } else if (openCooldownE > 0) {
            openCooldownE--;
        }

        if      (Settings.isPressed(Settings.upKey)) { turn(Direction.NORTH); move(); moveCounter=150; }
        else if (Settings.isPressed(Settings.leftKey)) { turn(Direction.WEST); move(); moveCounter=150; }
        else if (Settings.isPressed(Settings.downKey)) { turn(Direction.SOUTH); move(); moveCounter=150; }
        else if (Settings.isPressed(Settings.rightKey)) { turn(Direction.EAST); move(); moveCounter=150; }
        else if (Settings.isPressed(Settings.takeItem)) { takeItem(); }
        else if (Settings.isPressed(Settings.putItem)) { putItem(); }
        else if (Settings.isPressed(Settings.useItem)){useItem();}
        else if (Greenfoot.isKeyDown("1")){activeSlot=0;}
        else if (Greenfoot.isKeyDown("2")){activeSlot=1;}
        else if (Greenfoot.isKeyDown("3")){activeSlot=2;}
        else if (Greenfoot.isKeyDown("4")){activeSlot=3;}
        else if (Greenfoot.isKeyDown("5")){activeSlot=4;}
        else if (Greenfoot.isKeyDown("6")){activeSlot=5;}
        else if (Greenfoot.isKeyDown("7")){activeSlot=6;}
        else if (Greenfoot.isKeyDown("8")){activeSlot=7;}


        else if (Settings.isPressed(Settings.inventoryToggle) && openCooldownE == 0)
        {
            openCooldownE = 1000;
            toggleInventory();
        }
        draw(getLife() + "/" + maxLife);
    }

    public void move() {
        if (canMove()) {
            move(1);
        }
    }

    public void takeItem() {
        List<Item> onTile = getWorld().getObjectsAt(getX(), getY(), Item.class);
        if (onTile.isEmpty()) {
            return;
        }
        Item groundItem = onTile.get(0);


        //  prüfen, ob das Item eine Rüstung ist
        if (groundItem instanceof Armor) {
            Armor armor = (Armor) groundItem;

            if (armor.getSlotType().equals("head") && headArmor == null) {
                headArmor = armor;
                groundItem.onTake(this);
                updateAppearance();
                return;
            }

            // Prüfen, ob es eine Brustplatte ist und der Slot frei ist
            if (armor.getSlotType().equals("chest") && chestArmor == null) {
                chestArmor = armor;
                groundItem.onTake(this);
                updateAppearance();
                return;
            }
        }

        // Wenn keine Rüstung ist ODER die Rüstungsslots besetzt sind ->

        for (int i = 0; i < maxItems; i++) {
            if (items[i] == null) {
                items[i] = groundItem;
                groundItem.onTake(this);
                return;
            }
        }

        for (int i = 0; i < maxBackpack; i++) {
            if (backpack[i] == null) {
                backpack[i] = groundItem;
                groundItem.onTake(this);
                return;
            }
        }
    }

    public void putItem() {
        for (int i = maxItems - 1; i >= 0; i--) {
            if (items[i] != null) {
                items[i].onPut(getX(), getY());
                items[i] = null;
                return;
            }
        }
    }
    public void useItem() {
        int useSlot = getActiveSlot();

        if (useSlot != -1 && items[useSlot] != null && items[useSlot] instanceof Useable) {
            items[useSlot].use();
        }
    }

    private void toggleInventory() {
        if(getWorld()instanceof DungeonLevel) {
            if (BackpackWorld == null) {
                BackpackWorld = new Backpack(this, this.items, this.backpack, getWorld());

            }
            Greenfoot.setWorld(BackpackWorld);
        }
    }


    @Override
    protected void onDeath()
    {
        getWorld().removeObject(this);
        Greenfoot.stop();
    }

    @Override
    protected void addedToWorld(World world) {
        inventory = new InventoryVisualizer(items,60,60);
        world.addObject(inventory, 0, world.getHeight() - 1);
    }

    public void updateAppearance() {
        // Basis-Ordner ist immer "Player"
        String folder = "Player";


        if (hasChestArmor()) {
            Armor chest = (Armor) getChestArmor();
            folder += "_" + chest.getMaterial();
        }


        loadImages(folder);
    }


    public void removeItem(Item item) {
        for (int i = 0; i < maxItems; i++) {
            if (items[i] == item) {
                items[i] = null;
                return;
            }
        }
    }

    public int getMaxLife()  { return maxLife; }
    public int getMaxItems() { return maxItems; }
    public Item[] getItems() { return items; }
    private void setActiveSlot(int i ){activeSlot=i;}
    public int getActiveSlot() {return activeSlot;}
    public Item getHeadArmor() {return headArmor;}
    public Item getChestArmor() {return chestArmor;}
    public boolean hasChestArmor() {return chestArmor != null;}
    public boolean hasHeadArmor() {return headArmor != null;}
    public void setHeadArmor(Item headArmor) {
        this.headArmor = headArmor;
    }
    public void setChestArmor(Item chestArmor) {this.chestArmor = chestArmor;}


}
