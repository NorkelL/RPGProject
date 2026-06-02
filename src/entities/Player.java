package entities;

import entities.base.DamageableActor;
import entities.util.Direction;
import greenfoot.Greenfoot;
import greenfoot.World;
import items.Item;
import ui.InventoryVisualizer;
import world.GridWorld;

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
        if      (Greenfoot.isKeyDown("W")) { turn(Direction.NORTH); move(); moveCounter=moveCooldown();}
        else if (Greenfoot.isKeyDown("A")) { turn(Direction.WEST);  move(); moveCounter=moveCooldown();}
        else if (Greenfoot.isKeyDown("S")) { turn(Direction.SOUTH); move(); moveCounter=moveCooldown();}
        else if (Greenfoot.isKeyDown("D")) { turn(Direction.EAST);  move(); moveCounter=moveCooldown();}
        else if (Greenfoot.isKeyDown("T")) { takeItem(); }
        else if (Greenfoot.isKeyDown("P")) { putItem(); }
        draw(getLife() + "/" + maxLife);
    }

    public void move() {
        int step = moveStep();
        if (canMove(step)) {
            move(step);
        }
    }

    /**
     * Schrittweite in physischen Zellen. Auf dem klassischen Raster
     * (1 Zelle == 1 Tile) ist das ein Tile; auf einem feinen Raster ein
     * kleiner Sub-Tile-Schritt fuer weichere Bewegung.
     */
    private int moveStep() {
        if (getWorld() instanceof GridWorld) {
            int cpt = ((GridWorld) getWorld()).cellsPerTile();
            return cpt == 1 ? 1 : Math.max(1, cpt / 8);
        }
        return 1;
    }

    /**
     * Bewegungs-Cooldown in Act-Zyklen. Auf dem klassischen Raster die
     * urspruenglichen 150 (ruckartige Tile-Spruenge), auf einem feinen Raster
     * 0, damit die kleinen Schritte fluessig aneinander anschliessen.
     */
    private int moveCooldown() {
        if (getWorld() instanceof GridWorld && ((GridWorld) getWorld()).cellsPerTile() > 1) {
            return 0;
        }
        return 150;
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
        getWorld().removeObject(this);
        Greenfoot.stop();
    }

    @Override
    protected void addedToWorld(World world) {
        inventory = new InventoryVisualizer(items);
        if (world instanceof GridWorld) {
            GridWorld gw = (GridWorld) world;
            gw.addTile(inventory, 0, gw.getTilesY() - 1);
        } else {
            world.addObject(inventory, 0, world.getHeight() - 1);
        }
    }

    public int getMaxLife()  { return maxLife; }
    public int getMaxItems() { return maxItems; }
    public Item[] getItems() { return items; }
}
