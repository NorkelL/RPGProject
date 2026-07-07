package entities.base;

import entities.util.Direction;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import util.ImprovedGreenfootImage;
import ui.InventorySlot;
import blocks.Rock;
import blocks.Wall;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MovingActor extends ImprovedActor {
    private  GreenfootImage[][] movingActorImages = new ImprovedGreenfootImage[4][4];
    private int animationStep = 0;

    public MovingActor() {
        loadImages(this.getClass().getSimpleName());
    }

    public boolean canMove() {
        return canMove(1);
    }

    public boolean canMove(int distance) {
        World myWorld = getWorld();
        int x = getNextX(distance);
        int y = getNextY(distance);
        List<Rock> rocks = myWorld.getObjectsAt(x, y, Rock.class);
        boolean hasWall = myWorld.getObjects(Wall.class).stream()
            .anyMatch(w -> w.getX() == x && w.getY() == y);
        List<InventorySlot> slots = myWorld.getObjectsAt(x, y, InventorySlot.class);
        return rocks.isEmpty() && !hasWall && slots.isEmpty();
    }

    public int getNextX(int distance) {
        double radians = Math.toRadians(getRotation());
        int dx = (int) Math.round(Math.cos(radians) * distance);
        return getX() + dx;
    }

    public int getNextY(int distance) {
        double radians = Math.toRadians(getRotation());
        int dy = (int) Math.round(Math.sin(radians) * distance);
        return getY() + dy;
    }

    public int getNextX() { return getNextX(1); }
    public int getNextY() { return getNextY(1); }

    @Override
    public void setRotation(int rotation) {
        Direction direction = Direction.getDirectionByRotation(rotation);
        setImageRotation(direction);
    }

    public void turnLeft() {
        Direction dir = Direction.getDirectionByRotation(getRotation());
        setImageRotation(Direction.byValue(dir.getValue() - 1));
    }

    public void turnRight() {
        Direction dir = Direction.getDirectionByRotation(getRotation());
        setImageRotation(Direction.byValue(dir.getValue() + 1));
    }

    public void turn(Direction direction) {
        setImageRotation(direction);
    }

    public void turn(int rotation) {
        turn(Direction.getDirectionByRotation(rotation));
    }

    public void setImageRotation(Direction direction) {
        super.setRotation(direction.getRotation());
        setImage(movingActorImages[direction.getValue()][animationStep]);
    }

    @Override
    public void act() {
        super.act();
    }

    public void say(String text) {
        int y = getY() - 1;
        if (y < 0) y = 1;
        getWorld().showText(text, getX(), y);
        System.out.println("a " + this.getClass().getName() + " says: " + text);
        Greenfoot.delay(4);
        getWorld().showText("", getX(), y);
    }

    public void say(boolean text) { say(String.valueOf(text)); }
    public void say(int text)     { say(String.valueOf(text)); }
    public void say(double text)  { say(String.valueOf(text)); }

    @Override
    public void move(int steps) {
        animationStep = (animationStep + 1) % 4;
        setImage(movingActorImages[Direction.getDirectionByRotation(getRotation()).getValue()][animationStep]);
        if (canMove(steps)) {
            super.move(steps);
        }
    }
    public void loadImages(String folderName) {
        String imgFolder = "." + File.separator + "images" + File.separator + folderName + File.separator;
        for (int i = 0; i < Direction.values().length; i++) {
            try {
                for (int j = 0; j < 4; j++) {
                    String imgName = Direction.values()[i].name() + j + ".png";
                    File img = new File(imgFolder, imgName);
                    if (img.exists()) {
                        movingActorImages[i][j] = new ImprovedGreenfootImage(img.getCanonicalPath());
                        movingActorImages[i][j].scale(40, 40);
                        int rotationAmount = i % 2 == 1 ? -i : i;
                        movingActorImages[i][j].rotate(rotationAmount * 90);
                    } else {
                        if (j == 0) {
                            movingActorImages[i][j] = new ImprovedGreenfootImage(getImage());
                        } else {
                            movingActorImages[i][j] = new ImprovedGreenfootImage(movingActorImages[i][j - 1]);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Direction currentDir = Direction.getDirectionByRotation(getRotation());
        setImage(movingActorImages[currentDir.getValue()][animationStep]);
    }
}
