package entities;

import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import blocks.Chest;
import items.Item;
import items.PotionItem;
import items.WeaponItem;
import ui.InventoryVisualizer;
import world.DungeonLevel;

import java.util.List;

public class Player extends DamageableActor {
    private static final int MOVE_DELAY = 72;
    private static final int ATTACK_DELAY = 22;
    private static final int WALL_DAMAGE_DELAY = 60;
    private final int maxItems;
    private int maxLife;
    private int moveCounter;
    private int attackCounter;
    private int wallDamageCounter;
    private int playerLevel = 1;
    private int xp;
    private int equippedWeaponIndex = -1;
    private boolean inventoryOpen;
    private boolean inventoryToggleHeld;
    private boolean interactHeld;
    private final Item[] items;
    private InventoryVisualizer inventory;

    public Player() {
        this(100, 8, 100);
    }

    public Player(int life, int maxItems, int maxLife) {
        this.maxItems = maxItems;
        this.maxLife = maxLife;
        items = new Item[maxItems];
        setImage(createStatusImage());
        setLife(life);
    }

    @Override
    public void act() {
        handleInventoryToggle();
        handleInteraction();

        if (moveCounter > 0) {
            moveCounter--;
        }
        if (attackCounter > 0) {
            attackCounter--;
        }
        if (wallDamageCounter > 0) {
            wallDamageCounter--;
        }

        if (moveCounter == 0 && !inventoryOpen) {
            boolean moved = false;

            if      (Greenfoot.isKeyDown("w")) { turn(Direction.NORTH); moved = tryMove(); }
            else if (Greenfoot.isKeyDown("a")) { turn(Direction.WEST);  moved = tryMove(); }
            else if (Greenfoot.isKeyDown("s")) { turn(Direction.SOUTH); moved = tryMove(); }
            else if (Greenfoot.isKeyDown("d")) { turn(Direction.EAST);  moved = tryMove(); }

            if (moved) {
                moveCounter = MOVE_DELAY;
            }
        }

        if (!inventoryOpen && Greenfoot.isKeyDown("space")) { attack(); }
        if (!inventoryOpen && Greenfoot.isKeyDown("t")) { takeItem(); }
        if (!inventoryOpen && Greenfoot.isKeyDown("p")) { putItem(); }
        draw(getLife() + "/" + maxLife);
    }

    private void handleInventoryToggle() {
        boolean pressed = Greenfoot.isKeyDown("i");
        if (pressed && !inventoryToggleHeld) {
            inventoryOpen = !inventoryOpen;
        }
        inventoryToggleHeld = pressed;
    }

    private void handleInteraction() {
        boolean pressed = Greenfoot.isKeyDown("e");
        if (pressed && !interactHeld && !inventoryOpen) {
            if (!openNearbyChest()) {
                useFirstPotion();
            }
        }
        interactHeld = pressed;
    }

    private boolean tryMove() {
        if (canMove()) {
            move(1);
            return true;
        }
        if (wallDamageCounter == 0) {
            takeDamage(3);
            wallDamageCounter = WALL_DAMAGE_DELAY;
        }
        return false;
    }

    public void takeItem() {
        for (int i = 0; i < maxItems; i++) {
            if (items[i] == null) {
                List<Item> onTile;
                if (getWorld() instanceof DungeonLevel) {
                    onTile = ((DungeonLevel) getWorld()).getTileObjects(getTileX(), getTileY(), Item.class);
                } else {
                    onTile = getWorld().getObjectsAt(getX(), getY(), Item.class);
                }
                if (!onTile.isEmpty()) {
                    items[i] = onTile.get(0);
                    onTile.get(0).onTake(this);
                    if (equippedWeaponIndex < 0 && items[i] instanceof WeaponItem) {
                        equippedWeaponIndex = i;
                    }
                    return;
                }
            }
        }
    }

    public void putItem() {
        for (int i = maxItems - 1; i >= 0; i--) {
            if (items[i] != null) {
                dropItemAt(i);
                return;
            }
        }
    }

    public void dropItemAt(int index) {
        if (index < 0 || index >= items.length || items[index] == null) {
            return;
        }
        if (getWorld() instanceof DungeonLevel) {
            ((DungeonLevel) getWorld()).addWorldObject(items[index], getTileX(), getTileY());
        } else {
            items[index].onPut(getX(), getY());
        }
        items[index] = null;
        if (equippedWeaponIndex == index) {
            equippedWeaponIndex = findFirstWeaponIndex();
        }
    }

    public void heal(int amount) {
        setLife(Math.min(maxLife, getLife() + Math.max(0, amount)));
    }

    public void gainXp(int amount) {
        xp += Math.max(0, amount);
        while (xp >= getXpForNextLevel()) {
            xp -= getXpForNextLevel();
            playerLevel++;
            maxLife += 12;
            heal(18);
        }
    }

    public int getAttackDamage() {
        WeaponItem weapon = getEquippedWeapon();
        return weapon != null ? weapon.getDamage() : 12 + (playerLevel - 1) * 2;
    }

    public WeaponItem getBestWeapon() {
        WeaponItem best = null;
        for (Item item : items) {
            if (item instanceof WeaponItem) {
                WeaponItem weapon = (WeaponItem) item;
                if (best == null || weapon.getDamage() > best.getDamage()) {
                    best = weapon;
                }
            }
        }
        return best;
    }

    public WeaponItem getEquippedWeapon() {
        if (equippedWeaponIndex >= 0 && equippedWeaponIndex < items.length && items[equippedWeaponIndex] instanceof WeaponItem) {
            return (WeaponItem) items[equippedWeaponIndex];
        }
        return null;
    }

    private void attack() {
        if (attackCounter > 0 || !(getWorld() instanceof DungeonLevel)) {
            return;
        }
        attackCounter = ATTACK_DELAY;
        DungeonLevel level = (DungeonLevel) getWorld();
        int targetX = getNextX();
        int targetY = getNextY();
        List<BaseMonster> monsters = level.getTileObjects(targetX, targetY, BaseMonster.class);
        if (!monsters.isEmpty()) {
            BaseMonster monster = monsters.get(0);
            monster.takeDamage(getAttackDamage());
            gainXp(2);
            WeaponItem weapon = getEquippedWeapon();
            if (weapon != null) {
                weapon.gainWeaponXp(3);
            }
        }
    }

    private void useFirstPotion() {
        for (int i = 0; i < items.length; i++) {
            if (items[i] instanceof PotionItem) {
                useInventorySlot(i);
                return;
            }
        }
    }

    private boolean openNearbyChest() {
        if (!(getWorld() instanceof DungeonLevel)) {
            return false;
        }
        DungeonLevel level = (DungeonLevel) getWorld();
        for (Chest chest : level.getObjects(Chest.class)) {
            if (chest.canBeOpenedBy(this)) {
                chest.openChest();
                return true;
            }
        }
        return false;
    }

    public void useInventorySlot(int index) {
        if (index < 0 || index >= items.length || items[index] == null) {
            return;
        }
        Item item = items[index];
        if (item instanceof PotionItem) {
            heal(((PotionItem) item).getHealAmount());
            items[index] = null;
            if (equippedWeaponIndex == index) {
                equippedWeaponIndex = -1;
            }
        } else if (item instanceof WeaponItem) {
            equippedWeaponIndex = index;
        }
    }

    public String getSlotActionText(int index) {
        if (index < 0 || index >= items.length || items[index] == null) {
            return "Empty";
        }
        if (items[index] instanceof PotionItem) {
            return "Click: use";
        }
        if (items[index] instanceof WeaponItem) {
            return isEquippedSlot(index) ? "Equipped" : "Click: equip";
        }
        return "Stored";
    }

    public boolean isEquippedSlot(int index) {
        return equippedWeaponIndex == index;
    }

    public boolean isInventoryOpen() {
        return inventoryOpen;
    }

    public Item getInventoryItem(int index) {
        return index >= 0 && index < items.length ? items[index] : null;
    }

    private int findFirstWeaponIndex() {
        for (int i = 0; i < items.length; i++) {
            if (items[i] instanceof WeaponItem) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onDeath()
    {
        getWorld().removeObject(this);
        Greenfoot.stop();
    }

    @Override
    protected void addedToWorld(World world) {
        inventory = new InventoryVisualizer(this);
        world.addObject(inventory, 0, world.getHeight() - 1);
    }

    private GreenfootImage createStatusImage() {
        GreenfootImage image = new GreenfootImage(52, 52);
        image.setColor(new Color(71, 102, 137));
        image.fillOval(9, 6, 32, 32);
        image.setColor(new Color(228, 214, 183));
        image.fillRect(21, 34, 8, 10);
        return image;
    }

    public int getPlayerLevel() { return playerLevel; }
    public int getXp() { return xp; }
    public int getXpForNextLevel() { return 18 + playerLevel * 14; }
    public int getMaxLife()  { return maxLife; }
    public int getMaxItems() { return maxItems; }
    public Item[] getItems() { return items; }
}
