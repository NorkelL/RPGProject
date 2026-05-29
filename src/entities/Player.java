package entities;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import items.Item;
import ui.GameOverScreen;
import ui.InventoryVisualizer;

import java.util.List;

public class Player extends DamageableActor {
    private final int maxItems;
    private final int maxLife;
    private int moveCounter;
    private final Item[] items;
    private InventoryVisualizer inventory;

    public Player() {
        this(100, 8, 100);
    }

    public Player(int life, int maxItems, int maxLife) {
        this.maxItems = maxItems;
        this.maxLife = maxLife;
        items = new Item[maxItems];
        setLife(life);
    }

    @Override
    public void act() {
        if(moveCounter>0){
            moveCounter--;
            return;
        }
        if      (Greenfoot.isKeyDown("W")) { turn(Direction.NORTH); move(); moveCounter=300;}
        else if (Greenfoot.isKeyDown("A")) { turn(Direction.WEST);  move(); moveCounter=300;}
        else if (Greenfoot.isKeyDown("S")) { turn(Direction.SOUTH); move(); moveCounter=300;}
        else if (Greenfoot.isKeyDown("D")) { turn(Direction.EAST);  move(); moveCounter=300;}
        else if (Greenfoot.isKeyDown("T")) { takeItem(); }
        else if (Greenfoot.isKeyDown("P")) { putItem(); }
        draw(getLife() + "/" + maxLife);
    }

    public void move() {
        if (canMove()) {
            move(1);
        } else {
            takeDamage(10);
        }
    }

    public void takeItem() {
        for (int i = 0; i < maxItems; i++) {
            if (items[i] == null) {
                List<Item> onTile = getWorld().getObjectsAt(getX(), getY(), Item.class);
                if (!onTile.isEmpty()) {
                    items[i] = onTile.get(0);
                    onTile.get(0).onTake(this);
                    return;
                }
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
        inventory = new InventoryVisualizer(items);
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
