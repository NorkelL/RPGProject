package entities;

import entities.base.DamageableActor;
import entities.util.Direction;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import items.armor.Armor;
import items.Item;
import items.util.ItemData;
import items.util.Rarity;
import items.util.Useable;
import items.Waffen;
import items.waffen.Bow;
import items.waffen.BowSprite;
import items.waffen.Stock;
import ui.InventoryVisualizer;
import ui.Settings;
import util.SoundManager;
import ui.Healthbar;
import ui.XPBar;
import ui.worlds.Backpack;
import world.DungeonLevel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Player extends DamageableActor {
    private final Item[] items;       // Das ist die Hotbar / visualizer (z.B. 8 Slots)
    private final Item[] backpack;    // Das große Hauptinventar (z.B. 24 Slots)
    private final int maxItems;       // Größe der Hotbar
    private final int maxBackpack;    // Größe des Rucksacks
    private boolean eWasDown = false;


    //Item Variablen für Effekte
    private boolean invisible = false;
    private int invisibleTimer;
    private double DamageMultiplier = 1.0;
    private int multiplierTimer = 0;


    private final int maxLife;
    private int moveCounter;
    private InventoryVisualizer inventory;
    private int activeSlot;
    private int currentXP = 0;
    private int currentLevel = 1;
    private int xpToNextLevel = 100;
    private int debugXPCooldown = 0; // nur zum testen der xp-bar, kann spaeter wieder raus
    private Item headArmor = null; // z.B. "iron", "leather"
    private Item chestArmor = null;// z.B. "iron", "leather"
    private BowSprite activeBowSprite;

    private static final long ANGRIFF_PAUSE_MS = 400;
    private long naechsterAngriff = 0;

    private static List<String> itemPackages; //für item erstellen


    public Player() {
        this(100, 8, 15,100);
    }

    public Player(int life, int maxItems, int maxBackpack, int maxLife) {
        this.maxItems = maxItems;
        this.maxBackpack = maxBackpack;
        this.maxLife = maxLife;

        this.items = new Item[maxItems];
        this.backpack = new Item[maxBackpack];

        setLife(life);
    }

    @Override
    public void act() {
        super.act();

        if (Settings.isPressed(Settings.attack)) {
            angreifen();
        }

        // das inventar haengt an der tastenflanke und steht vor dem moveCounter -
        // sonst laesst es sich waehrend der laufpause nicht oeffnen
        boolean eIsDown = Settings.isPressed(Settings.inventoryToggle);
        if (eIsDown && !eWasDown) {
            eWasDown = true;
            toggleInventory();
            return;
        }
        eWasDown = eIsDown;

        if(moveCounter>0){
            moveCounter--;
            return;
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

        if (stepSoundCooldown > 0) stepSoundCooldown--;

        if (invisibleTimer > 0) {
            invisibleTimer--;

            if (invisibleTimer == 0) {
                setInvisible(false);
            }
        }
        updateActiveWeapon();
    }

    private int stepSoundCooldown = 0;

    public void move() {
        if (canMove()) {
            move(1);
            if (stepSoundCooldown <= 0) {
                SoundManager.play("steps.mp3", 20);
                stepSoundCooldown = 8;
            }
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
    // startwaffe fuer ein neues spiel. beim laden nicht aufrufen, da holt
    // setInventorys() sowieso alles aus dem save
    public void gibStartwaffe() {
        if (items[0] == null) {
            items[0] = new Stock();
        }
    }

    public void useItem() {
        int useSlot = getActiveSlot();

        if (useSlot != -1 && items[useSlot] != null && items[useSlot] instanceof Useable) {
            items[useSlot].use(this);
        }
    }

    private void toggleInventory() {
        if (getWorld() instanceof DungeonLevel) {
            Greenfoot.setWorld(new Backpack(this, this.items, this.backpack, getWorld(), false));
        }
    }

    // aufruf vom UpgradeTable: gleiches inventar, aber mit offenen upgrade slots
    public void openInventoryFromTable(World previousWorld) {
        Greenfoot.setWorld(new Backpack(this, this.items, this.backpack, previousWorld, true));
    }

    public void angreifen() {
        if (System.currentTimeMillis() < naechsterAngriff) return;   // noch in der Abklingzeit

        Item aktiv = (activeSlot >= 0 && activeSlot < items.length) ? items[activeSlot] : null;
        if (!(aktiv instanceof Waffen)) return;                      // blosse Faeuste machen keinen Schaden

        naechsterAngriff = System.currentTimeMillis() + ANGRIFF_PAUSE_MS;

        // Sound immer, auch beim Schlag ins Leere - sonst fuehlt es sich an,
        // als haette die Taste nicht reagiert
        SoundManager.play("attack.mp3");
        ((Waffen) aktiv).hit(this);
    }


    @Override
    protected void onDeath()
    {
        SoundManager.play("death_player.mp3");
        getWorld().removeObject(this);
        Greenfoot.stop();
    }

    @Override
    protected void onDamageSound() {
        SoundManager.play("damage.mp3");
    }

    @Override
    protected void addedToWorld(World world) {
        // movePlayer() nimmt den spieler raus und setzt ihn neu rein -> sonst haengt die ui doppelt drin
        if (!world.getObjects(XPBar.class).isEmpty()) {
            return;
        }

        inventory = new InventoryVisualizer(items,60,60);
        world.addObject(inventory, 0, world.getHeight() - 1);

        XPBar xpBar = new XPBar(this);
        int barCells = xpBar.getImage().getWidth() / world.getCellSize();
        world.addObject(xpBar, world.getWidth() - barCells / 2 - 1, world.getHeight() - 1);

        Healthbar healthbar = new Healthbar(this);
        int lebenCells = healthbar.getImage().getWidth() / world.getCellSize();
        world.addObject(healthbar, lebenCells / 2, world.getHeight() - 1);
    }

    public void updateAppearance() {
        // Basis-Ordner ist immer "Player"
        String folder = "Player";


        // instanceof statt hartem cast: in den slot kann per drag and drop auch was
        // anderes als ruestung landen, das darf hier nicht knallen
        if (getChestArmor() instanceof Armor chest) {
            folder += "_" + chest.getMaterial();
        }


        loadImages(folder,null);
    }


    public void removeItem(Item item) {
        for (int i = 0; i < maxItems; i++) {
            if (items[i] == item) {
                items[i] = null;
                return;
            }
        }
    }

    public void setInvisible(boolean invisible) {
        this.invisible = invisible;
        super.setInvisible(invisible);
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
        // kaputter save -> lieber gar nichts laden als mittendrin abbrechen
        if (inventorys == null || inventorys.size() < 3) {
            return;
        }

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
    private void updateActiveWeapon() {

        Item currentItem = (activeSlot >= 0 && activeSlot < items.length) ? items[activeSlot] : null;


        if (currentItem instanceof Bow) {
            Bow bow = (Bow) currentItem;


            if (activeBowSprite == null) {
                activeBowSprite = new BowSprite(bow);
                getWorld().addObject(activeBowSprite, getX(), getY());
            }

            activeBowSprite.update(this);

        } else {

            if (activeBowSprite != null) {
                getWorld().removeObject(activeBowSprite);
                activeBowSprite = null;
            }
        }
    }

    public void gainXP(int xp) {
        currentXP += xp;
        while (currentXP >= xpToNextLevel) {
            currentXP -= xpToNextLevel;
            currentLevel++;
            xpToNextLevel = (int)(100 * Math.pow(currentLevel, 1.5));
            SoundManager.play("levelup.mp3", 20);
            say("LEVEL UP!");
        }
    }

    public int getCurrentXP()     { return currentXP; }
    public int getCurrentLevel()   { return currentLevel; }
    public int getXpToNextLevel()  { return xpToNextLevel; }

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
        updateAppearance();
    }
    public void setChestArmor(Item chestArmor) {this.chestArmor = chestArmor; updateAppearance();}
    public void setInvisibleTimer(int invisibleTimer) {this.invisibleTimer = invisibleTimer;}
    public boolean isInvisible() {return invisible;}


}
