package entities.util;

import blocks.Rock;
import blocks.Wall;
import greenfoot.World;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.WeakHashMap;

public interface ASharpPathfinding {

    WeakHashMap<ASharpPathfinding, int[]> RANDOM_TARGETS = new WeakHashMap<>();

    // falls keine Actor subclass
    int getX();
    int getY();
    World getWorld();
    void setRotation(int rotation);
    void move(int steps);

    default void aSharpPathfindTakeStep(int targetX, int targetY) {
        int startX = getX();
        int startY = getY();
        if (startX == targetX && startY == targetY) return;

        World world = getWorld();
        int breite = world.getWidth();
        int hoehe = world.getHeight();

        // breitensuche vom ziel aus: dist ist der abstand jeder kachel zum ziel
        int[][] dist = new int[breite][hoehe];
        for (int[] row : dist) Arrays.fill(row, -1);

        dist[targetX][targetY] = 0;

        List<int[]> queue = new ArrayList<>();
        queue.add(new int[]{targetX, targetY});

        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int gelesen = 0;

        while (gelesen < queue.size()) {
            int[] current = queue.get(gelesen);
            gelesen++;
            if (current[0] == startX && current[1] == startY) break;

            for (int[] d : dirs) {
                int nx = current[0] + d[0];
                int ny = current[1] + d[1];
                if (nx < 0 || nx >= breite || ny < 0 || ny >= hoehe) continue;
                if (dist[nx][ny] != -1 || isBlocked(nx, ny)) continue;

                dist[nx][ny] = dist[current[0]][current[1]] + 1;
                queue.add(new int[]{nx, ny});
            }
        }

        if (dist[startX][startY] == -1) return; // kein weg

        // der nachbar mit dem kleinsten abstand ist der naechste schritt
        int bestX = -1;
        int bestY = -1;
        int best = dist[startX][startY];
        for (int[] d : dirs) {
            int nx = startX + d[0];
            int ny = startY + d[1];
            if (nx < 0 || nx >= breite || ny < 0 || ny >= hoehe) continue;
            if (dist[nx][ny] == -1 || dist[nx][ny] >= best) continue;

            best = dist[nx][ny];
            bestX = nx;
            bestY = ny;
        }
        if (bestX == -1) return;

        if      (bestX > startX) setRotation(0);
        else if (bestX < startX) setRotation(180);
        else if (bestY > startY) setRotation(90);
        else                     setRotation(270);
        move(1);
    }

    default void aSharpRandomStep() {
        int[] target = RANDOM_TARGETS.get(this);
        if (target == null || (getX() == target[0] && getY() == target[1])) {
            target = pickRandomTarget();
            RANDOM_TARGETS.put(this, target);
        }
        aSharpPathfindTakeStep(target[0], target[1]);
    }

    default int[] pickRandomTarget() {
        World world = getWorld();
        int W = world.getWidth();
        int H = world.getHeight();
        int x, y;
        int tries = 0;
        do {
            x = (int) (Math.random() * W);
            y = (int) (Math.random() * H);
            tries++;
        } while (isBlocked(x, y) && tries < 50);
        return new int[]{x, y};
    }

    // hier neue blöcke, durch die man nicht durchgehen kann hinzufügen:
    default boolean isBlocked(int x, int y) {
        World world = getWorld();
        if (!world.getObjectsAt(x, y, Rock.class).isEmpty()) return true;
        return world.getObjects(Wall.class).stream()
            .anyMatch(w -> w.getX() == x && w.getY() == y);
    }
}
