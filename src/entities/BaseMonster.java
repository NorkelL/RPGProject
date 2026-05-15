package entities;

import greenfoot.Greenfoot;

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
                if(!getWorld().getObjectsAt(i,y, Player.class).isEmpty())
                    return true;
            }
        }
        return false;
    }

    private boolean checkFollowRadius(){
        int x = getX();
        int y = getY();
        for (int i = x-leashRadius; i < x+leashRadius+1; i++) {
            for (int j = y-leashRadius; j < y+leashRadius+1; j++) {
                if(!getWorld().getObjectsAt(i,y, Player.class).isEmpty())
                    return true;
            }
        }
        return false;
    }

    protected void moveToPlayer(){
        int x = getX();
        int y = getY();
        for (int i = x-leashRadius; i < x+leashRadius+1; i++) {
            for (int j = y-leashRadius; j < y+leashRadius+1; j++) {
                if (!getWorld().getObjectsAt(i, y, Player.class).isEmpty()){
                    aSharpPathfindTakeStep(i,j);
                }
            }
        }
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
