package entities;

import entities.base.DamageableActor;
import entities.util.Direction;
import greenfoot.Greenfoot;
import greenfoot.World;
import items.armor.Armor;
import items.Item;
import items.util.ItemData;
import items.util.Rarity;
import items.util.Useable;
import ui.InventoryVisualizer;
import ui.Settings;
import ui.worlds.Backpack;
import world.DungeonLevel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Player extends DamageableActor {
    private final Item[] items;       // Das ist deine Hotbar / visualizer (z.B. 8 Slots)
    private final Item[] backpack;    // Das große Hauptinventar (z.B. 24 Slots)
    private final int maxItems;       // Größe der Hotbar
    private final int maxBackpack;    // Größe des Rucksacks
    private Backpack BackpackWorld; // die backpack welt
    private int openCooldownE = 0;

    private final int maxLife;
    private int moveCounter;
    private InventoryVisualizer inventory;
    private int activeSlot;
    private Item headArmor = null; // z.B. "iron", "leather"
    private Item chestArmor = null;// z.B. "iron", "leather"

    private static List<String> itemPackages; //für item erstellen


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
            items[useSlot].use(this);
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

    public List<List<ItemData>> getInventorys(){
        List<List<ItemData>> inventorys = new ArrayList<>();
        List<ItemData> backpackData = new ArrayList<>();
        inventorys.add(backpackData);
        for (int i = 0; i < backpack.length; i++) {
            if (backpack[i] != null) {
                ItemData data = new ItemData();
                data.slot = i;
                data.classname = backpack[i].getClass().getSimpleName();
                data.rarity = backpack[i].rarity.name();
                backpackData.add(data);
            }
        }

        List<ItemData> hotbarData = new ArrayList<>();
        inventorys.add(hotbarData);
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null) {
                ItemData data = new ItemData();
                data.slot = i;
                data.classname = items[i].getClass().getSimpleName();
                data.rarity = items[i].rarity.name();
                hotbarData.add(data);
            }
        }


        List<ItemData> armorData = new ArrayList<>();
        inventorys.add(armorData);

        if (headArmor != null) {
            ItemData headArmorData = new ItemData();
            headArmorData.slot = 0;
            headArmorData.classname = headArmor.getClass().getSimpleName();
            headArmorData.rarity = headArmor.rarity.name();
            armorData.add(headArmorData);
        }

        if (chestArmor != null) {
            ItemData chestArmorData = new ItemData();
            chestArmorData.slot = 1;
            chestArmorData.classname = chestArmor.getClass().getSimpleName();
            chestArmorData.rarity = chestArmor.rarity.name();
            armorData.add(chestArmorData);
        }

        return inventorys;
    }

    public void setInventorys(List<List<ItemData>> inventorys) {
        for (int i = 0; i < backpack.length; i++) { backpack[i] = null; }
        for (int i = 0; i < items.length; i++) { items[i] = null; }
        headArmor = null;
        chestArmor = null;

        for (ItemData data : inventorys.get(0)) {
            backpack[data.slot] = createItem(data);
        }

        for (ItemData data : inventorys.get(1)) {
            items[data.slot] = createItem(data);
        }

        for (ItemData data : inventorys.get(2)) {
            if (data.slot == 0) { headArmor = createItem(data); }
            else if (data.slot == 1) { chestArmor = createItem(data); }
        }

        updateAppearance();
    }

    private Item createItem(ItemData data) {
        if (data == null || data.classname == null) {
            return null;
        }

        for (String pkg : getItemPackages()) {
            try {
                Item item = (Item) Class.forName(pkg + data.classname).getDeclaredConstructor().newInstance();
                if (data.rarity != null) {
                    item.rarity = Rarity.valueOf(data.rarity);
                }
                return item;
            } catch (ClassNotFoundException e) {
                // Klasse liegt in einem anderen Package, nächstes probieren
            } catch (ReflectiveOperationException e) {
                return null;
            }
        }
        return null;
    }

    private static List<String> getItemPackages() {
        if (itemPackages != null) { return itemPackages; }

        itemPackages = new ArrayList<>();
        itemPackages.add("items.");
        try {
            File codeRoot = new File(Item.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            collectSubPackages(new File(codeRoot, "items"), "items.", itemPackages);
        } catch (Exception e) {
            // Scan fehlgeschlagen (z.B. exportiertes Jar) -> nur Basis-Package "items."
        }
        return itemPackages;
    }

    private static void collectSubPackages(File dir, String prefix, List<String> result) {
        File[] children = dir.listFiles();
        if (children == null) { return; }

        for (File child : children) {
            if (child.isDirectory()) {
                String pkg = prefix + child.getName() + ".";
                result.add(pkg);
                collectSubPackages(child, pkg, result);
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
