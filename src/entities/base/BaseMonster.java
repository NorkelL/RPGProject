package entities.base;

import entities.Player;
import entities.util.ASharpPathfinding;
import greenfoot.Greenfoot;

import java.util.List;

public abstract class BaseMonster extends DamageableActor implements ASharpPathfinding {
    private int life;
    private int agroRadius;
    private int leashRadius;
    private boolean isFollowingPlayer = false;

    public BaseMonster(int life, int agroRadius, int leashRadius) {
        this.life = life;
        this.agroRadius = agroRadius;
        this.leashRadius = leashRadius;
    }

    @Override
    public void act(){
        move();
        checkDeath();
    }

    // ersetzt durch a*
    public void moveRandom() {
        int rotation = Greenfoot.getRandomNumber(4) * 90;
        setRotation(rotation);
        if(canMove(tileStepCells())){
            move(tileStepCells());
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



    /** Findet den Spieler, falls er innerhalb von {@code radius} Tiles liegt. */
    private boolean playerWithinTiles(int radius){
        List<Player> players = getWorld().getObjects(Player.class);
        if (players.isEmpty()) return false;
        Player p = players.get(0);
        int dx = Math.abs(toTile(p.getX()) - toTile(getX()));
        int dy = Math.abs(toTile(p.getY()) - toTile(getY()));
        return dx <= radius && dy <= radius;
    }

    private boolean checkAgro(){
        return playerWithinTiles(agroRadius);
    }

    private boolean checkFollowRadius(){
        return playerWithinTiles(leashRadius);
    }

    protected void moveToPlayer(){
        List<Player> players = getWorld().getObjects(Player.class);
        if (players.isEmpty()) return;
        Player p = players.get(0);
        aSharpPathfindTakeStep(toTile(p.getX()), toTile(p.getY()));
    }

    private void checkDeath(){
        if(life <= 0){
            onDeath();
        }
    }

    @Override
    protected void onDeath() {
        getWorld().removeObject(this);
    }
}
