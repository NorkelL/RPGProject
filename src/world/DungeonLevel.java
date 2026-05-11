package world;

import blocks.Chest;
import blocks.Rock;
import core.GameStarter;
import entities.BaseMonster;
import entities.Gnome;
import entities.ImprovedActor;
import entities.MovingActor;
import entities.Orc;
import entities.Player;
import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import items.Item;
import items.ItemTyp;
import ui.Healthbar;
import ui.InfoPanel;
import ui.InventorySlot;
import ui.UI;
import ui.WeaponBar;
import ui.XPBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DungeonLevel extends World {
    private static final int VIEW_WIDTH = 16;
    private static final int VIEW_HEIGHT = 9;
    private static final int CELL_SIZE = 60;

    private final GameStarter gameStarter;
    private final Random rng;
    private final int mapWidth;
    private final int mapHeight;
    public int centerExit;
    private int centerEntrance;
    private int[] centerCorridor;
    private int cameraLeft;
    private int cameraTop;
    private final List<Room> placedRooms = new ArrayList<>();
    private Player player;
    private InfoPanel tutorialPanel;

    private static class Room {
        int width, height, x, y;

        Room(int width, int height, int x, int y) {
            this.width = width;
            this.height = height;
            this.x = x;
            this.y = y;
        }
    }

    public DungeonLevel(long seed, GameStarter gameStarter) {
        super(VIEW_WIDTH, VIEW_HEIGHT, CELL_SIZE);
        this.gameStarter = gameStarter;
        mapWidth = calcWidth(seed);
        mapHeight = calcHeight(seed);
        rng = new Random(seed);
        setBackground(createBackground());
        setPaintOrder(UI.class, InventorySlot.class, Player.class, BaseMonster.class, Item.class, Exit.class, Entrance.class, Chest.class, Rock.class);

        if (!gameStarter.pastLevel.isEmpty()) {
            centerEntrance = gameStarter.pastLevel.get(gameStarter.pastLevel.size() - 1).centerExit;
        } else {
            centerEntrance = mapWidth / 2;
        }
        centerEntrance = clampToCorridor(centerEntrance);
        placeEntrance(centerEntrance, mapHeight - 2);
        placeGateWalls(centerEntrance, mapHeight - 2);

        centerExit = clampToCorridor(rng.nextInt(mapWidth - 6) + 3);
        placeExit(centerExit, 1);
        placeGateWalls(centerExit, 1);

        player = new Player();
        addWorldObject(player, centerEntrance, mapHeight - 2);

        spawnCorridor();
        spawnRooms();
        populateRooms();
        addHud();
        addStoryPanels();
        refreshCamera();
    }

    private GreenfootImage createBackground() {
        GreenfootImage background = new GreenfootImage(VIEW_WIDTH * CELL_SIZE, VIEW_HEIGHT * CELL_SIZE);
        GreenfootImage tile = new GreenfootImage("cell.jpg");
        tile.scale(CELL_SIZE, CELL_SIZE);
        for (int x = 0; x < VIEW_WIDTH; x++) {
            for (int y = 0; y < VIEW_HEIGHT; y++) {
                background.drawImage(tile, x * CELL_SIZE, y * CELL_SIZE);
            }
        }
        background.setColor(new Color(8, 15, 21, 125));
        background.fillRect(0, 0, background.getWidth(), background.getHeight());
        background.setColor(new Color(24, 33, 41, 225));
        background.fillRect(8, 8, background.getWidth() - 16, 48);
        background.setColor(new Color(191, 158, 95));
        background.drawRect(8, 8, background.getWidth() - 16, 48);
        background.drawString("Dungeon Run", 28, 38);
        return background;
    }

    private void addHud() {
        addObject(new Healthbar(player), 2, 0);
        addObject(new XPBar(player, getDepth()), VIEW_WIDTH - 3, 0);
        addObject(new WeaponBar(player), VIEW_WIDTH - 3, 1);
    }

    private void addStoryPanels() {
        if (getDepth() == 1) {
            tutorialPanel = new InfoPanel(
                "Tutorial",
                420,
                150,
                -1,
                "W/A/S/D move through the dungeon.",
                "SPACE attacks the tile in front of you.",
                "T picks up loot, E drinks a potion, P drops an item.",
                "Weapons gain XP when you hit monsters.",
                "Floor 1 is safe: learn first, descend after.",
                "Press ENTER or H to hide this panel."
            );
            addObject(tutorialPanel, 4, VIEW_HEIGHT - 2);
        }

        addObject(new InfoPanel(
            "Depth " + getDepth(),
            360,
            92,
            360,
            getStoryLineOne(),
            getStoryLineTwo(),
            "Clear rooms, loot chests, and push deeper."
        ), VIEW_WIDTH / 2, VIEW_HEIGHT - 1);
    }

    private static int calcHeight(long rn) {
        return calcWidth(rn) + 3;
    }

    private static int calcWidth(long rn) {
        return new Random(rn).nextInt(16) + 15;
    }

    private void spawnCorridor() {
        do {
            centerCorridor = calcCorridor();
        } while (centerCorridor[centerCorridor.length - 1] != centerExit
            && centerCorridor[centerCorridor.length - 1] != centerExit - 1
            && centerCorridor[centerCorridor.length - 1] != centerExit + 1);

        for (int i = 0; i < centerCorridor.length; i++) {
            int y = mapHeight - 3 - i;
            int cx = centerCorridor[i];
            if (cx - 2 >= 0) {
                addWorldObject(new Rock(), cx - 2, y);
            } else if (cx - 1 >= 0) {
                addWorldObject(new Rock(), cx - 1, y);
            }
            if (cx + 2 < mapWidth) {
                addWorldObject(new Rock(), cx + 2, y);
            } else if (cx + 1 < mapWidth) {
                addWorldObject(new Rock(), cx + 1, y);
            }
        }
    }

    private int[] calcCorridor() {
        int[] corridor = new int[mapHeight - 4];
        int pos = centerEntrance;
        int delta = 0;
        int runLeft = 0;

        for (int i = 0; i < corridor.length; i++) {
            int stepsLeft = corridor.length - i;
            int distToExit = Math.abs(centerExit - pos);

            if (distToExit >= stepsLeft) {
                delta = Integer.compare(centerExit, pos);
                runLeft = 1;
            } else if (runLeft == 0) {
                int toward = Integer.compare(centerExit, pos);
                int r = rng.nextInt(10);
                delta = (r < 4) ? toward : (r < 7) ? 0 : -toward;
                runLeft = rng.nextInt(3) + 3;
            }

            pos = Math.max(1, Math.min(mapWidth - 2, pos + delta));
            corridor[i] = pos;
            runLeft--;
        }
        return corridor;
    }

    private void spawnRooms() {
        int placedCount = 0;
        int tries = 0;
        while (placedCount < 3) {
            if (tryPlaceRoom(genRandomRoom())) {
                placedCount++;
            } else {
                tries++;
                if (tries > 10) {
                    return;
                }
            }
        }
    }

    private boolean tryPlaceRoom(Room room) {
        for (Room placed : placedRooms) {
            if (room.x <= placed.x + placed.width && room.x + room.width >= placed.x
                && room.y <= placed.y + placed.height && room.y + room.height >= placed.y) {
                return false;
            }
        }
        boolean touchesCorridor = false;
        for (int i = room.x; i < room.x + room.width; i++) {
            for (int j = room.y; j < room.y + room.height; j++) {
                if (!getTileObjects(i, j, Rock.class).isEmpty()) {
                    removeRockAt(i, j);
                    touchesCorridor = true;
                }
            }
        }
        if (touchesCorridor) {
            for (int i = room.x; i < room.x + room.width + 1; i++) {
                addWorldObject(new Rock(), i, room.y);
                addWorldObject(new Rock(), i, room.y + room.height);
            }
            for (int i = room.y; i < room.y + room.height + 1; i++) {
                addWorldObject(new Rock(), room.x, i);
                addWorldObject(new Rock(), room.x + room.width, i);
            }
            placedRooms.add(room);
        }
        clearCorridorFloor();
        return touchesCorridor;
    }

    private void clearCorridorFloor() {
        for (int i = 0; i < centerCorridor.length; i++) {
            int worldY = mapHeight - 3 - i;
            for (int j = centerCorridor[i] - 1; j < centerCorridor[i] + 2; j++) {
                removeRockAt(j, worldY);
            }
        }
    }

    private void populateRooms() {
        for (int i = 0; i < placedRooms.size(); i++) {
            Room room = placedRooms.get(i);
            if (i == placedRooms.size() - 1 || rng.nextBoolean()) {
                int[] chestTile = findFreeTileInRoom(room);
                if (chestTile != null) {
                    addWorldObject(new Chest(), chestTile[0], chestTile[1]);
                }
            }

            if (getDepth() == 1) {
                continue;
            }

            int monsterCount = 1 + rng.nextInt(2 + gameStarter.pastLevel.size() / 2);
            for (int n = 0; n < monsterCount; n++) {
                int[] monsterTile = findFreeTileInRoom(room);
                if (monsterTile == null) {
                    break;
                }
                BaseMonster monster = rng.nextInt(100) < 60 ? new Gnome() : new Orc();
                addWorldObject(monster, monsterTile[0], monsterTile[1]);
            }
        }
    }

    private int[] findFreeTileInRoom(Room room) {
        for (int tries = 0; tries < 20; tries++) {
            int x = room.x + 1 + rng.nextInt(Math.max(1, room.width - 1));
            int y = room.y + 1 + rng.nextInt(Math.max(1, room.height - 1));
            if (isFreeFloorTile(x, y)) {
                return new int[] { x, y };
            }
        }
        return null;
    }

    private boolean isFreeFloorTile(int tileX, int tileY) {
        if (tileX <= 0 || tileY <= 0 || tileX >= mapWidth - 1 || tileY >= mapHeight - 1) {
            return false;
        }
        return getTileObjects(tileX, tileY, Rock.class).isEmpty()
            && getTileObjects(tileX, tileY, Chest.class).isEmpty()
            && getTileObjects(tileX, tileY, BaseMonster.class).isEmpty()
            && getTileObjects(tileX, tileY, Player.class).isEmpty()
            && getTileObjects(tileX, tileY, Exit.class).isEmpty()
            && getTileObjects(tileX, tileY, Entrance.class).isEmpty();
    }

    private void removeRockAt(int x, int y) {
        List<Rock> rocks = getTileObjects(x, y, Rock.class);
        for (Rock rock : rocks) {
            removeObject(rock);
        }
    }

    private Room genRandomRoom() {
        int w = rng.nextInt(10) + 4;
        int h = rng.nextInt(10) + 4;
        int x = rng.nextInt(Math.max(1, mapWidth - w - 2)) + 1;
        int y = rng.nextInt(Math.max(1, mapHeight - h - 5)) + 2;
        return new Room(w, h, x, y);
    }

    private void placeEntrance(int centerX, int y) {
        for (int x = centerX - 1; x <= centerX + 1; x++) {
            addWorldObject(new Entrance(gameStarter), x, y);
        }
    }

    private void placeExit(int centerX, int y) {
        for (int x = centerX - 1; x <= centerX + 1; x++) {
            addWorldObject(new Exit(gameStarter), x, y);
        }
    }

    private void placeGateWalls(int centerX, int y) {
        addWorldObject(new Rock(), centerX - 2, y);
        addWorldObject(new Rock(), centerX + 2, y);
    }

    private int clampToCorridor(int x) {
        return Math.max(2, Math.min(mapWidth - 3, x));
    }

    public void addWorldObject(Actor actor, int tileX, int tileY) {
        if (actor instanceof ImprovedActor) {
            super.addObject(actor, 0, 0);
            ImprovedActor improvedActor = (ImprovedActor) actor;
            improvedActor.setTileLocation(tileX, tileY);
            updateActorScreenPosition(improvedActor);
        } else {
            super.addObject(actor, tileX, tileY);
        }
    }

    public boolean canMoveTo(ImprovedActor actor, int tileX, int tileY) {
        if (tileX < 0 || tileY < 0 || tileX >= mapWidth || tileY >= mapHeight) {
            return false;
        }
        if (!getTileObjects(tileX, tileY, Rock.class).isEmpty()) {
            return false;
        }
        if (actor instanceof Player) {
            return getTileObjects(tileX, tileY, BaseMonster.class).isEmpty();
        }
        if (actor instanceof BaseMonster) {
            return getTileObjects(tileX, tileY, Player.class).isEmpty()
                && getTileObjects(tileX, tileY, BaseMonster.class).isEmpty();
        }
        return !hasBlockingGate(tileX, tileY, actor);
    }

    private boolean hasBlockingGate(int tileX, int tileY, ImprovedActor actor) {
        if (actor instanceof Player) {
            return false;
        }
        return !getTileObjects(tileX, tileY, Entrance.class).isEmpty()
            || !getTileObjects(tileX, tileY, Exit.class).isEmpty();
    }

    public void moveActor(ImprovedActor actor, int steps) {
        int stepCount = Math.max(0, steps);
        for (int i = 0; i < stepCount; i++) {
            int nextX;
            int nextY;
            if (actor instanceof MovingActor) {
                MovingActor movingActor = (MovingActor) actor;
                nextX = movingActor.getNextX();
                nextY = movingActor.getNextY();
            } else {
                nextX = actor.getTileX();
                nextY = actor.getTileY();
            }
            if (!canMoveTo(actor, nextX, nextY)) {
                break;
            }
            actor.setTileLocation(nextX, nextY);
        }
        updateActorScreenPosition(actor);
        if (actor == player) {
            refreshCamera();
        }
    }

    public <T> List<T> getTileObjects(int tileX, int tileY, Class<T> cls) {
        List<T> matches = new ArrayList<>();
        for (T object : getObjects(cls)) {
            if (object instanceof ImprovedActor) {
                ImprovedActor actor = (ImprovedActor) object;
                if (actor.getTileX() == tileX && actor.getTileY() == tileY) {
                    matches.add(object);
                }
            } else if (object instanceof Actor) {
                Actor actor = (Actor) object;
                if (actor.getX() == tileX && actor.getY() == tileY) {
                    matches.add(object);
                }
            }
        }
        return matches;
    }

    public Player getPlayer() {
        return player;
    }

    public void onMonsterDefeated(BaseMonster monster) {
        player.gainXp(6 + monster.getLevelReward() * 3);
        if (rng.nextInt(100) < 35) {
            addWorldObject(ItemTyp.zufaellig().erstelleItem(), monster.getTileX(), monster.getTileY());
        }
    }

    @Override
    public void act() {
        if (getDepth() != 1 && (Greenfoot.isKeyDown("enter") || Greenfoot.isKeyDown("h")) && tutorialPanel != null) {
            if (tutorialPanel.getWorld() != null) {
                removeObject(tutorialPanel);
            }
            tutorialPanel = null;
        }
    }

    public int getDepth() {
        return gameStarter.pastLevel.size() + 1;
    }

    public boolean isGameplayLocked() {
        return false;
    }

    private String getStoryLineOne() {
        if (getDepth() == 1) {
            return "The outer vault still remembers your oath.";
        }
        if (getDepth() == 2) {
            return "The stones whisper of a fire locked below.";
        }
        if (getDepth() == 3) {
            return "You find scars from the last failed expedition.";
        }
        return "The Heart stirs harder with every layer you breach.";
    }

    private String getStoryLineTwo() {
        if (getDepth() == 1) {
            return "Learn the ruins before the deeper halls wake up.";
        }
        if (getDepth() == 2) {
            return "Your weapons begin to resonate with ember-light.";
        }
        if (getDepth() == 3) {
            return "The monsters now hunt with purpose, not instinct.";
        }
        return "Only a leveled hunter and forged weapons can endure.";
    }

    private void refreshCamera() {
        cameraLeft = clampCamera(player.getTileX() - VIEW_WIDTH / 2, mapWidth - VIEW_WIDTH);
        cameraTop = clampCamera(player.getTileY() - VIEW_HEIGHT / 2, mapHeight - VIEW_HEIGHT);
        for (ImprovedActor actor : getObjects(ImprovedActor.class)) {
            updateActorScreenPosition(actor);
        }
    }

    private int clampCamera(int value, int maxValue) {
        return Math.max(0, Math.min(Math.max(0, maxValue), value));
    }

    private void updateActorScreenPosition(ImprovedActor actor) {
        int screenX = actor.getTileX() - cameraLeft;
        int screenY = actor.getTileY() - cameraTop;
        boolean visible = screenX >= 0 && screenX < VIEW_WIDTH && screenY >= 0 && screenY < VIEW_HEIGHT;
        actor.setCameraVisible(visible);
        if (visible) {
            actor.setLocation(screenX, screenY);
        } else {
            actor.setLocation(0, 0);
        }
    }
}
