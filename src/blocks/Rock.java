package blocks;

import entities.ImprovedActor;
import greenfoot.GreenfootImage;

public class Rock extends ImprovedActor {
    private int life;

    public Rock() {
        setLife(25);
    }

    public Rock(int life) {
        setLife(life);
    }

    public void hit(int damage) {
        if (life < damage) {
            getWorld().removeObject(this);
        } else {
            setLife(life - damage);
        }
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
        GreenfootImage image = new GreenfootImage("Blocks/Rock.png");
        setImage(image);
        draw(this.life);
    }
}
