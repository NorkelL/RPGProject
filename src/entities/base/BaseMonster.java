package entities.base;

import entities.Player;
import entities.util.ASharpPathfinding;
import entities.util.Hitting;
import entities.util.Direction;
import greenfoot.Greenfoot;
import util.SoundManager;

import java.util.List;

public abstract class BaseMonster extends DamageableActor implements ASharpPathfinding {
    private int agroRadius;
    private int leashRadius;
    private boolean isFollowingPlayer = false;
    private int moveCooldown = 0;
    private int moveDelay = 360; // Anzahl der act()-Aufrufe zwischen Bewegungen
    private static final long ANGRIFF_ANIMATION_MS = 300;
    private long animationBis = 0;

    private int attackDamage;

    private static final long ANGRIFF_PAUSE_MS = 1000;
    private long naechsterAngriff = 0;

    public BaseMonster(int life, int agroRadius, int leashRadius ) {
        this(life, agroRadius, leashRadius, 10);
    }

    public BaseMonster(int life, int agroRadius, int leashRadius, int attackDamage) {
        this.agroRadius = agroRadius;
        this.leashRadius = leashRadius;
        this.attackDamage = attackDamage;
        setLife(life);
    }

    @Override
    public void act(){
        super.act();

        if(animationBis > 0 && System.currentTimeMillis() >= animationBis){
            animationBis = 0;
            loadImages(this.getClass().getSimpleName(), "Walking");
        }

        if (this instanceof Hitting && greifeSpielerAn()) {
            return;   // in diesem Takt wird geschlagen, nicht gelaufen
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

    private boolean greifeSpielerAn(){
        if (System.currentTimeMillis() < naechsterAngriff) return false;

        Player ziel = findeSpielerInReichweite();
        if (ziel == null) return false;

        naechsterAngriff = System.currentTimeMillis() + ANGRIFF_PAUSE_MS;

        turn(richtungZu(ziel));   // erst zum Spieler drehen, dann die Animation laden
        hit();                    // Sound + Angriffsbild
        ziel.takeDamage(attackDamage);
        return true;
    }

    /** Sucht einen sichtbaren Spieler auf dem eigenen oder einem Nachbarfeld. */
    private Player findeSpielerInReichweite(){
        for (Player player : getWorld().getObjects(Player.class)) {
            if (player.isInvisible()) continue;

            // Manhattan-Distanz <= 1: eigenes Feld oder direkt daneben.
            // Bewusst ueber getX/getY statt getIntersectingObjects - die Bilder
            // sind groesser als eine Zelle und ueberlappen sonst zu frueh.
            int abstand = Math.abs(player.getX() - getX()) + Math.abs(player.getY() - getY());
            if (abstand <= 1) return player;
        }
        return null;
    }

    /**In welche der vier Richtungen das Monster schauen muss, um das Ziel anzusehen. */
    private Direction richtungZu(Player ziel){
        int dx = ziel.getX() - getX();
        int dy = ziel.getY() - getY();

        if (Math.abs(dx) >= Math.abs(dy)) {
            return dx >= 0 ? Direction.EAST : Direction.WEST;
        }
        return dy >= 0 ? Direction.SOUTH : Direction.NORTH;
    }

    public int getAttackDamage() { return attackDamage; }

    protected void setAttackDamage(int attackDamage) { this.attackDamage = attackDamage; }

    @Override
    protected void onDeath() {
        SoundManager.play("death_monster.wav");
        getWorld().removeObject(this);
    }

    public void hit (){
        SoundManager.play("attack.mp3");
        loadImages(this.getClass().getSimpleName(), "Attacking");
        animationBis = System.currentTimeMillis() + ANGRIFF_ANIMATION_MS;
    }

    @Override
    protected void onDamageSound() {
        // Monster haben keinen eigenen Schaden-Sound
    }


}
