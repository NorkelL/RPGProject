package entities.base;

import entities.Player;
import entities.util.ASharpPathfinding;
import greenfoot.Greenfoot;

import java.util.List;

public abstract class BaseMonster extends DamageableActor implements ASharpPathfinding {
    private int agroRadius;
    private int leashRadius;
    private boolean isFollowingPlayer = false;
    private int moveCooldown = 0;
    private int moveDelay = 20; // Anzahl der act()-Aufrufe zwischen Bewegungen
    private int attackCooldown = 0; // laeuft nach hit() runter, statt delay()

    public BaseMonster(int life, int agroRadius, int leashRadius ) {
        this.agroRadius = agroRadius;
        this.leashRadius = leashRadius;
        setLife(life);
    }

    @Override
    public void act(){
        super.act();

        if(attackCooldown > 0){
            attackCooldown--;
            if(attackCooldown == 0){
                loadImages(this.getClass().getSimpleName(), "Walking");
            }
        }

        if(moveCooldown > 0){
            moveCooldown--;
            return;
        }

        move();

        moveCooldown = moveDelay;
    }

    // ersetzt durch a*
    public void moveRandom() {
        int rotation = Greenfoot.getRandomNumber(4) * 90;
        setRotation(rotation);
        if(canMove()){
            move(1);
        }

    }

    protected void move(){
        if(isFollowingPlayer && checkFollowRadius()){
            moveToPlayer();
        } else if (checkAgro()) {
            isFollowingPlayer = true;
            moveToPlayer();
        } else {
            aSharpRandomStep();
        }
    }



    private boolean checkAgro(){
        int x = getX();
        int y = getY();
        for (int i = x-agroRadius; i < x+agroRadius+1; i++) {
            for (int j = y-agroRadius; j < y+agroRadius+1; j++) {
                List<Player> players = getWorld().getObjectsAt(i, j, Player.class);
                for (Player player : players) {
                    if (!player.isInvisible()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkFollowRadius(){
        int x = getX();
        int y = getY();
        for (int i = x-leashRadius; i < x+leashRadius+1; i++) {
            for (int j = y-leashRadius; j < y+leashRadius+1; j++) {
                List<Player> players = getWorld().getObjectsAt(i, j, Player.class);

                for (Player player : players) {
                    if (!player.isInvisible()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // spieler einmal suchen, dann ein a*-aufruf
    protected void moveToPlayer(){
        for (Player player : getWorld().getObjects(Player.class)) {
            if (!player.isInvisible()) {
                aSharpPathfindTakeStep(player.getX(), player.getY());
                return;
            }
        }
    }

    @Override
    protected void onDeath() {
        getWorld().removeObject(this);
    }

    public void hit (){
        loadImages(this.getClass().getSimpleName(), "Attacking");
        attackCooldown = 15;
    }
}
