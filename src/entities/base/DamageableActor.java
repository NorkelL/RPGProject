package entities.base;

import util.SoundManager;

public abstract class DamageableActor extends MovingActor {
    private int life;

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = Math.max(0, life);
    }

    public void takeDamage(int damage) {
        if (damage <= 0) return;
        setLife(life - damage);
        if (life < 1) {
            onDeath();
        } else {
            onDamageSound();
        }
    }

    protected void onDamageSound() {
        SoundManager.play("damage.mp3");
    }

    public void hit(int damage) {
        takeDamage(damage);
    }

    protected abstract void onDeath();
}
