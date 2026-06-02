package entities.util;

import blocks.Rock;
import blocks.Wall;
import greenfoot.World;
import world.GridWorld;
import java.util.Arrays;
import java.util.WeakHashMap;

/**
 * A*-Pathfinding auf dem logischen Tile-Raster. Es arbeitet immer in
 * Tile-Koordinaten (nicht in physischen Zellen), damit das Suchgitter klein
 * bleibt – unabhaengig davon, wie fein die {@link GridWorld#getUnit() unit}
 * gewaehlt ist. Ein Schritt bewegt den Actor um genau ein Tile.
 */
public interface ASharpPathfinding {

    WeakHashMap<ASharpPathfinding, int[]> RANDOM_TARGETS = new WeakHashMap<>();

    // falls keine Actor subclass
    int getX();
    int getY();
    World getWorld();
    void setRotation(int rotation);
    void move(int steps);

    default GridWorld gridWorld() {
        World w = getWorld();
        return (w instanceof GridWorld) ? (GridWorld) w : null;
    }

    /** physische Zelle -> Tile (Identitaet ausserhalb einer GridWorld). */
    default int toTile(int cell) {
        GridWorld g = gridWorld();
        return g != null ? g.cellToTile(cell) : cell;
    }

    /** Anzahl physischer Zellen, die ein Tile-Schritt entspricht. */
    default int tileStepCells() {
        GridWorld g = gridWorld();
        return g != null ? g.cellsPerTile() : 1;
    }

    default int tilesWide() {
        GridWorld g = gridWorld();
        return g != null ? g.getTilesX() : getWorld().getWidth();
    }

    default int tilesHigh() {
        GridWorld g = gridWorld();
        return g != null ? g.getTilesY() : getWorld().getHeight();
    }

    default void aSharpPathfindTakeStep(int targetTileX, int targetTileY) {
        int startX = toTile(getX());
        int startY = toTile(getY());
        if (startX == targetTileX && startY == targetTileY) return;

        int W = tilesWide();
        int H = tilesHigh();
        if (targetTileX < 0 || targetTileX >= W || targetTileY < 0 || targetTileY >= H) return;

        int[][] g        = new int[W][H];
        int[][] parentX  = new int[W][H];
        int[][] parentY  = new int[W][H];
        boolean[][] closed = new boolean[W][H];
        boolean[][] open   = new boolean[W][H];

        for (int[] row : g)       Arrays.fill(row, Integer.MAX_VALUE);
        for (int[] row : parentX) Arrays.fill(row, -1);
        for (int[] row : parentY) Arrays.fill(row, -1);

        g[startX][startY] = 0;
        open[startX][startY] = true;

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (true) {
            int cx = -1, cy = -1, bestF = Integer.MAX_VALUE;
            for (int x = 0; x < W; x++) {
                for (int y = 0; y < H; y++) {
                    if (open[x][y]) {
                        int f = g[x][y] + Math.abs(x - targetTileX) + Math.abs(y - targetTileY);
                        if (f < bestF) { bestF = f; cx = x; cy = y; }
                    }
                }
            }
            if (cx == -1) return; // no path

            if (cx == targetTileX && cy == targetTileY) {
                // Trace path back to find the first step after start
                int nx = cx, ny = cy;
                while (parentX[nx][ny] != startX || parentY[nx][ny] != startY) {
                    int tmp = parentX[nx][ny];
                    ny = parentY[nx][ny];
                    nx = tmp;
                }
                int dx = nx - startX;
                int dy = ny - startY;
                if      (dx > 0) setRotation(0);
                else if (dx < 0) setRotation(180);
                else if (dy > 0) setRotation(90);
                else             setRotation(270);
                move(tileStepCells());
                return;
            }

            open[cx][cy] = false;
            closed[cx][cy] = true;

            for (int[] d : dirs) {
                int nx = cx + d[0];
                int ny = cy + d[1];
                if (nx < 0 || nx >= W || ny < 0 || ny >= H) continue;
                if (closed[nx][ny] || isBlocked(nx, ny)) continue;
                int ng = g[cx][cy] + 1;
                if (ng < g[nx][ny]) {
                    g[nx][ny] = ng;
                    parentX[nx][ny] = cx;
                    parentY[nx][ny] = cy;
                    open[nx][ny] = true;
                }
            }
        }
    }

    default void aSharpRandomStep() {
        int[] target = RANDOM_TARGETS.get(this);
        if (target == null || (toTile(getX()) == target[0] && toTile(getY()) == target[1])) {
            target = pickRandomTarget();
            RANDOM_TARGETS.put(this, target);
        }
        aSharpPathfindTakeStep(target[0], target[1]);
    }

    default int[] pickRandomTarget() {
        int W = tilesWide();
        int H = tilesHigh();
        int x, y;
        int tries = 0;
        do {
            x = (int) (Math.random() * W);
            y = (int) (Math.random() * H);
            tries++;
        } while (isBlocked(x, y) && tries < 50);
        return new int[]{x, y};
    }

    // hier neue blöcke, durch die man nicht durchgehen kann hinzufügen (Tile-Koordinaten):
    default boolean isBlocked(int tileX, int tileY) {
        World world = getWorld();
        if (world.getObjects(Rock.class).stream()
                .anyMatch(r -> toTile(r.getX()) == tileX && toTile(r.getY()) == tileY)) return true;
        return world.getObjects(Wall.class).stream()
            .anyMatch(w -> toTile(w.getX()) == tileX && toTile(w.getY()) == tileY);
    }
}
