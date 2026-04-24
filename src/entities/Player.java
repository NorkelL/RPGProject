package entities;

import greenfoot.Greenfoot;
import greenfoot.World;
import items.Carrot;
import items.Food;
import items.Item;
import ui.InventoryVisualizer;
import world.Level1;
import world.Level2;
import world.Rock;

import java.util.List;

public class Player extends DamageableActor {
    private int startCarrots;
    private int maxItems;
    private int maxLife;
    private Item[] items;
    private int weightStartCarrot;
    private InventoryVisualizer inventory;

    public Player() {
        this(100, 20, 20, 100, 5);
    }

    public Player(int life, int startCarrots, int maxItems, int maxLife, int weightStartCarrot) {
        setLife(life);
        this.startCarrots = startCarrots;
        this.maxItems = maxItems;
        this.maxLife = maxLife;
        this.weightStartCarrot = weightStartCarrot;
        setCarrots();
    }

    private void setCarrots() {
        items = new Carrot[maxItems];
        for (int i = 0; i < startCarrots; i++) {
            items[i] = new Carrot(weightStartCarrot);
        }
    }

    @Override
    public void act() {
        performTick();
    }

    private void performTick() {
        if      (Greenfoot.isKeyDown("W")) { turn(Direction.NORTH); move(); }
        else if (Greenfoot.isKeyDown("A")) { turn(Direction.WEST);  move(); }
        else if (Greenfoot.isKeyDown("S")) { turn(Direction.SOUTH); move(); }
        else if (Greenfoot.isKeyDown("D")) { turn(Direction.EAST);  move(); }
        else if (Greenfoot.isKeyDown("P")) { putItem(); }
        else if (Greenfoot.isKeyDown("T")) { takeItem(); }
        else if (Greenfoot.isKeyDown("Q")) { eatFood(); }
        else if (Greenfoot.isKeyDown("M")) { mine(); }
        else if (Greenfoot.isKeyDown("H")) { hit(); }
        draw(getLife() + "/" + maxLife);
    }

    public void move() {
        if (getX() == 0 && getRotation() == 180) {
            Greenfoot.setWorld(new Level1());
        } else if (getX() == getWorld().getWidth() - 1 && getRotation() == 0) {
            Greenfoot.setWorld(new Level2());
        } else {
            if (canMove()) {
                move(1);
            } else {
                takeDamage(10);
            }
        }
    }

    public void putItem() {
        for (int i = maxItems - 1; i > -1; i--) {
            if (items[i] != null) {
                items[i].onPut(getX(), getY());
                items[i] = null;
                return;
            }
        }
    }

    public void takeItem() {
        for (int i = 0; i < maxItems; i++) {
            if (items[i] == null) {
                List<Item> itemsOnXY = getWorld().getObjectsAt(getX(), getY(), Item.class);
                if (!itemsOnXY.isEmpty()) {
                    items[i] = itemsOnXY.get(0);
                    itemsOnXY.get(0).onTake(this);
                    return;
                }
            }
        }
    }

    public void eatFood() {
        if (getLife() >= maxLife) return;
        int lifeToReg = maxLife - getLife();
        List<Food> foodOnXY = getWorld().getObjectsAt(getX(), getY(), Food.class);
        if (foodOnXY.isEmpty()) return;
        for (int needed = lifeToReg; needed > 0; needed--) {
            for (Food food : foodOnXY) {
                if (food.getWeight() == needed) {
                    setLife(getLife() + food.getWeight());
                    getWorld().removeObject(food);
                    return;
                }
            }
        }
        Food best = foodOnXY.get(0);
        setLife(Math.min(maxLife, getLife() + best.getWeight()));
        getWorld().removeObject(best);
    }

    public void mine() {
        List<Rock> rocks = getWorld().getObjectsAt(getNextX(1), getNextY(1), Rock.class);
        if (!rocks.isEmpty()) {
            rocks.get(0).hit(10);
        }
    }

    public void hit() {
        List<Monster> neighbors = getNeighbours(1, true, Monster.class);
        for (Monster m : neighbors) {
            m.hit(10);
        }
    }

    @Override
    protected void onDeath() {
        Greenfoot.stop();
    }

    @Override
    protected void addedToWorld(World myWorld) {
        inventory = new InventoryVisualizer(items);
        myWorld.addObject(inventory, 0, myWorld.getHeight() - 1);
    }

    public int getMaxLife()           { return maxLife; }
    public void setMaxLife(int v)     { maxLife = v; }
    public int getMaxItems()          { return maxItems; }
    public int getStartCarrots()      { return startCarrots; }
    public int getWeightStartCarrot() { return weightStartCarrot; }
}
