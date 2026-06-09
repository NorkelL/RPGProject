package entities;

import entities.base.DamageableActor;
import entities.util.Direction;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import items.Item;
import ui.GameOverScreen;
import ui.InventoryVisualizer;
import ui.worlds.Backpack;
import world.DungeonLevel;

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

    public Player() {
        this(100, 8, 15,100);
    }

    public Player(int life, int maxItems, int maxBackpack, int maxLife) {
        this.maxItems = maxItems;
        this.maxBackpack = maxBackpack;
        this.maxLife = maxLife;

        this.items = new Item[maxItems];         // z.B. Size 8
        this.backpack = new Item[maxBackpack];   // z.B. Size 24

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

        if      (Greenfoot.isKeyDown("W")) { turn(Direction.NORTH); move(); moveCounter=150;}
        else if (Greenfoot.isKeyDown("A")) { turn(Direction.WEST);  move(); moveCounter=150;}
        else if (Greenfoot.isKeyDown("S")) { turn(Direction.SOUTH); move(); moveCounter=150;}
        else if (Greenfoot.isKeyDown("D")) { turn(Direction.EAST);  move(); moveCounter=150;}
        else if (Greenfoot.isKeyDown("T")) { takeItem(); }
        else if (Greenfoot.isKeyDown("P")) { putItem(); }
        else if (Greenfoot.isKeyDown("E") && openCooldownE == 0){openCooldownE=1000; toggleInventory();}
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
        for (int i = 0; i < maxItems; i++) {
            if (items[i] == null) {
                items[i] = groundItem;
                groundItem.onTake(this);

                return;
            }
        }
        //Wenn inventory voll ist in backpack
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

    private void toggleInventory() {
        if(getWorld()instanceof DungeonLevel) {
            if (BackpackWorld == null) {
                BackpackWorld = new Backpack(this.items, this.backpack, getWorld());

            }
            Greenfoot.setWorld(BackpackWorld);
        }
    }


    @Override
    protected void onDeath()
    {
        GreenfootImage bg = new GreenfootImage("GameOverScreen.png");
        World gameOverWelt = new GameOverScreen();
        Greenfoot.setWorld(gameOverWelt);
        getWorld().removeObject(this);
    }

    @Override
    protected void addedToWorld(World world) {
        inventory = new InventoryVisualizer(items,60,60);
        world.addObject(inventory, 0, world.getHeight() - 1);
    }

    public int getMaxLife()  { return maxLife; }
    public int getMaxItems() { return maxItems; }
    public Item[] getItems() { return items; }

   // private void hit(Waffen dieseWaffe){
     //   int damage = dieseWaffe.getDamage;
       // if(getCurrentItem instanceof Waffe){
         //   getCurrentItem.hit
        //}

    //}
}
