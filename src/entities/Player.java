package entities;

import entities.base.DamageableActor;
import entities.util.Direction;
import greenfoot.Greenfoot;
import greenfoot.World;
import items.Item;
import ui.InventoryVisualizer;
import ui.InventorySlot;
import blocks.Rock;
import blocks.Wall;
import world.GridWorld;

import java.util.List;

public class Player extends DamageableActor {
    /** Act-Zyklen pro Lauf-Animationsschritt im smoothen Modus. */
    private static final int ANIM_PERIOD = 8;

    private final int maxItems;
    private final int maxLife;
    private int moveCounter;
    private int animTick;
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
        World world = getWorld();
        if (world instanceof GridWorld && ((GridWorld) world).cellsPerTile() > 1) {
            smoothAct((GridWorld) world);
        } else {
            classicAct();
        }
    }

    // ---- Klassischer Modus (1 Zelle == 1 Tile): unveraendertes Verhalten ----

    private void classicAct() {
        if (moveCounter > 0) { moveCounter--; return; }
        if      (Greenfoot.isKeyDown("W")) { turn(Direction.NORTH); classicMove(); moveCounter = 150; }
        else if (Greenfoot.isKeyDown("A")) { turn(Direction.WEST);  classicMove(); moveCounter = 150; }
        else if (Greenfoot.isKeyDown("S")) { turn(Direction.SOUTH); classicMove(); moveCounter = 150; }
        else if (Greenfoot.isKeyDown("D")) { turn(Direction.EAST);  classicMove(); moveCounter = 150; }
        else if (Greenfoot.isKeyDown("T")) { takeItem(); }
        else if (Greenfoot.isKeyDown("P")) { putItem(); }
        draw(getLife() + "/" + maxLife);
    }

    private void classicMove() {
        if (canMove(1)) move(1);
    }

    // ---- Smoother Modus (feines Raster): pixelweises, sauber kollidierendes Laufen ----

    private void smoothAct(GridWorld gw) {
        Direction dir = null;
        if      (Greenfoot.isKeyDown("W")) dir = Direction.NORTH;
        else if (Greenfoot.isKeyDown("S")) dir = Direction.SOUTH;
        else if (Greenfoot.isKeyDown("A")) dir = Direction.WEST;
        else if (Greenfoot.isKeyDown("D")) dir = Direction.EAST;

        if (dir != null) {
            turn(dir);
            walkSmooth(gw, dir);
        }

        if      (Greenfoot.isKeyDown("T")) takeItem();
        else if (Greenfoot.isKeyDown("P")) putItem();

        draw(getLife() + "/" + maxLife);
    }

    /**
     * Bewegt den Spieler pixelweise in die gegebene Richtung. Es wird so weit
     * geschritten, wie frei ist (bis zu {@code speed} Pixel pro Act), und exakt
     * vor dem Hindernis gestoppt -> fluessige Bewegung, sauberes Anliegen.
     */
    private void walkSmooth(GridWorld gw, Direction dir) {
        int ddx = 0, ddy = 0;
        switch (dir) {
            case NORTH: ddy = -1; break;
            case SOUTH: ddy =  1; break;
            case WEST:  ddx = -1; break;
            case EAST:  ddx =  1; break;
        }

        int half  = gw.cellsPerTile() / 2;                 // halbe Kachel = Spieler-Hitbox
        int speed = Math.max(1, gw.cellsPerTile() / 20);    // Pixel pro Act

        // Hindernisse einmal pro Act sammeln (statt pro Pixelschritt).
        List<Wall> walls = getWorld().getObjects(Wall.class);
        List<Rock> rocks = getWorld().getObjects(Rock.class);
        List<InventorySlot> slots = getWorld().getObjects(InventorySlot.class);

        int moved = 0;
        for (int i = 0; i < speed; i++) {
            int nx = getX() + ddx;
            int ny = getY() + ddy;
            if (blocked(nx, ny, half, walls, rocks, slots)) break;
            setLocation(nx, ny);
            moved++;
        }

        if (moved > 0 && (++animTick % ANIM_PERIOD == 0)) {
            advanceWalkAnimation();
        }
    }

    private boolean blocked(int x, int y, int half,
                            List<Wall> walls, List<Rock> rocks, List<InventorySlot> slots) {
        for (Wall w : walls) if (overlaps(x, y, half, w.getX(), w.getY(), half)) return true;
        for (Rock r : rocks) if (overlaps(x, y, half, r.getX(), r.getY(), half)) return true;
        for (InventorySlot s : slots) if (overlaps(x, y, half, s.getX(), s.getY(), half)) return true;
        return false;
    }

    private boolean overlaps(int ax, int ay, int aHalf, int bx, int by, int bHalf) {
        return Math.abs(ax - bx) < aHalf + bHalf && Math.abs(ay - by) < aHalf + bHalf;
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
