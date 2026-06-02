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
    /** Pixel pro Act = Kachelgroesse / SPEED_DIVISOR. Groesser = langsamer. */
    private static final double SPEED_DIVISOR = 28.0;

    private final int maxItems;
    private final int maxLife;
    private int moveCounter;
    private int animTick;
    // Rest-Akkumulatoren fuer fraktionale (nicht-ganzzahlige) Geschwindigkeit.
    private double accX, accY;
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
        // Keine Priorisierung: alle gedrueckten Tasten zaehlen, Gegenrichtungen
        // heben sich auf -> WASD-Kombinationen (z.B. W+A) laufen diagonal.
        int ddx = 0, ddy = 0;
        if (Greenfoot.isKeyDown("W")) ddy -= 1;
        if (Greenfoot.isKeyDown("S")) ddy += 1;
        if (Greenfoot.isKeyDown("A")) ddx -= 1;
        if (Greenfoot.isKeyDown("D")) ddx += 1;

        if (ddx != 0 || ddy != 0) {
            turn(facingOf(ddx, ddy));
            walkSmooth(gw, ddx, ddy);
        } else {
            accX = accY = 0; // im Stand keine Restbewegung mitschleppen
        }

        if      (Greenfoot.isKeyDown("T")) takeItem();
        else if (Greenfoot.isKeyDown("P")) putItem();

        draw(getLife() + "/" + maxLife);
    }

    /** Blickrichtung fuer eine (ggf. diagonale) Eingabe; horizontale Achse hat Vorrang. */
    private Direction facingOf(int ddx, int ddy) {
        if (ddx < 0) return Direction.WEST;
        if (ddx > 0) return Direction.EAST;
        if (ddy < 0) return Direction.NORTH;
        return Direction.SOUTH;
    }

    /**
     * Bewegt den Spieler mit normalisierter Geschwindigkeit (gleich schnell in
     * alle Richtungen, auch diagonal). Die beiden Achsen werden getrennt
     * geprueft, sodass man an Waenden entlanggleitet statt haengenzubleiben.
     */
    private void walkSmooth(GridWorld gw, int ddx, int ddy) {
        double speed = gw.cellsPerTile() / SPEED_DIVISOR;   // Pixel pro Act
        double len = Math.sqrt(ddx * ddx + ddy * ddy);      // 1 (gerade) bzw. sqrt(2) (diagonal)
        accX += ddx / len * speed;
        accY += ddy / len * speed;

        int stepX = (int) accX; accX -= stepX;
        int stepY = (int) accY; accY -= stepY;

        int half = gw.cellsPerTile() / 2;                   // halbe Kachel = Spieler-Hitbox
        List<Wall> walls = getWorld().getObjects(Wall.class);
        List<Rock> rocks = getWorld().getObjects(Rock.class);
        List<InventorySlot> slots = getWorld().getObjects(InventorySlot.class);

        boolean moved = false;
        moved |= stepAxis(stepX, 0, half, walls, rocks, slots);
        moved |= stepAxis(0, stepY, half, walls, rocks, slots);

        if (moved && (++animTick % ANIM_PERIOD == 0)) {
            advanceWalkAnimation();
        }
    }

    /** Schrittweises Bewegen entlang einer Achse, stoppt exakt vor Hindernissen. */
    private boolean stepAxis(int dx, int dy, int half,
                             List<Wall> walls, List<Rock> rocks, List<InventorySlot> slots) {
        int steps = Math.abs(dx + dy);          // genau eine Komponente ist != 0
        int sx = Integer.signum(dx);
        int sy = Integer.signum(dy);
        boolean moved = false;
        for (int i = 0; i < steps; i++) {
            int nx = getX() + sx;
            int ny = getY() + sy;
            if (blocked(nx, ny, half, walls, rocks, slots)) break;
            setLocation(nx, ny);
            moved = true;
        }
        return moved;
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
