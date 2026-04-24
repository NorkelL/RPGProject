package entities;

import greenfoot.Greenfoot;
import world.Rock;

import java.util.List;

public class Monster extends DamageableActor {
    private int nextX;
    private int nextY;
    private boolean arrived = true;
    private int tick = 0;

    public Monster() {
        setLife(50);
    }

    public Monster(int life) {
        setLife(life);
    }

    @Override
    public void act() {
        tryHit();
        tick++;
        if (tick == 4) {
            tick = 0;
            if (arrived) {
                nextX = Greenfoot.getRandomNumber(getWorld().getWidth());
                nextY = Greenfoot.getRandomNumber(getWorld().getHeight());
                arrived = false;
            }
            moveToTarget();
        }
        draw(getLife());
    }

    private void tryHit() {
        List<Player> playersOnXY = getWorld().getObjectsAt(getX(), getY(), Player.class);
        for (Player p : playersOnXY) {
            p.takeDamage(10);
        }
    }

    public void moveToTarget() {
        boolean notMoved = false;
        int distanceX = nextX - getX();
        int distanceY = nextY - getY();
        if (Math.abs(distanceX) > Math.abs(distanceY)) {
            if (distanceX > 0) { setRotation(0);   setImage("Monster/EAST0.png"); }
            else               { setRotation(180);  setImage("Monster/WEST0.png"); }
            if (getWorld().getObjectsAt(getNextX(), getY(), Rock.class).isEmpty()) {
                move(1);
            } else {
                notMoved = true;
            }
        } else {
            if (distanceY > 0) { setRotation(90);  setImage("Monster/SOUTH0.png"); }
            else               { setRotation(270); setImage("Monster/NORTH0.png"); }
            if (getWorld().getObjectsAt(getX(), getNextY(), Rock.class).isEmpty()) {
                move(1);
            } else {
                notMoved = true;
            }
        }
        if (distanceX == 0 && distanceY == 0) arrived = true;
        if (notMoved) arrived = true;
    }

    @Override
    protected void onDeath() {
        if (getWorld() != null) {
            getWorld().removeObject(this);
        }
    }

    public int getNextXTarget() { return nextX; }
    public int getNextYTarget() { return nextY; }
}
