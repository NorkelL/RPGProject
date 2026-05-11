package entities;

import greenfoot.Color;
import greenfoot.GreenfootImage;
import world.DungeonLevel;

public abstract class BaseMonster extends DamageableActor {
    private final String displayName;
    private final int contactDamage;
    private final int levelReward;
    private int moveCooldown;
    private int attackCooldown;

    protected BaseMonster(String displayName, int maxLife, int contactDamage, int levelReward) {
        this.displayName = displayName;
        this.contactDamage = contactDamage;
        this.levelReward = levelReward;
        setImage(createDefaultImage());
        setLife(maxLife);
    }

    @Override
    public void act() {
        if (!(getWorld() instanceof DungeonLevel)) {
            return;
        }
        if (((DungeonLevel) getWorld()).isGameplayLocked()) {
            return;
        }

        if (moveCooldown > 0) {
            moveCooldown--;
        }
        if (attackCooldown > 0) {
            attackCooldown--;
        }

        DungeonLevel level = (DungeonLevel) getWorld();
        Player player = level.getPlayer();
        if (player == null) {
            return;
        }

        if (isAdjacentTo(player)) {
            attack(player);
            return;
        }

        if (moveCooldown == 0) {
            takeTurnTowards(player);
            moveCooldown = getMoveDelay();
        }
    }

    protected void takeTurnTowards(Player player) {
        int dx = player.getTileX() - getTileX();
        int dy = player.getTileY() - getTileY();

        if (Math.abs(dx) > Math.abs(dy)) {
            turn(dx > 0 ? Direction.EAST : Direction.WEST);
            if (!tryStep()) {
                turn(dy > 0 ? Direction.SOUTH : Direction.NORTH);
                tryStep();
            }
        } else {
            turn(dy > 0 ? Direction.SOUTH : Direction.NORTH);
            if (!tryStep()) {
                turn(dx > 0 ? Direction.EAST : Direction.WEST);
                tryStep();
            }
        }
    }

    protected boolean tryStep() {
        if (canMove()) {
            move(1);
            return true;
        }
        return false;
    }

    private boolean isAdjacentTo(Player player) {
        int dx = Math.abs(player.getTileX() - getTileX());
        int dy = Math.abs(player.getTileY() - getTileY());
        return dx + dy == 1;
    }

    private void attack(Player player) {
        if (attackCooldown > 0) {
            return;
        }
        player.takeDamage(contactDamage);
        attackCooldown = getAttackDelay();
    }

    @Override
    protected void onDeath() {
        if (getWorld() instanceof DungeonLevel) {
            ((DungeonLevel) getWorld()).onMonsterDefeated(this);
        }
        getWorld().removeObject(this);
    }

    protected GreenfootImage createDefaultImage() {
        GreenfootImage image = new GreenfootImage(52, 52);
        image.setColor(getPrimaryColor());
        image.fillOval(8, 8, 34, 34);
        image.setColor(getAccentColor());
        image.fillOval(16, 14, 7, 7);
        image.fillOval(27, 14, 7, 7);
        image.drawLine(18, 31, 32, 31);
        return image;
    }

    protected abstract int getMoveDelay();
    protected abstract int getAttackDelay();
    protected abstract Color getPrimaryColor();
    protected abstract Color getAccentColor();

    public int getLevelReward() {
        return levelReward;
    }

    public String getDisplayName() {
        return displayName;
    }
}
