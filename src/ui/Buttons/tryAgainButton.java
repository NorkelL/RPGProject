package ui.buttons;

import core.GameStarter;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import ui.UI;
import util.SoundManager;

import java.util.List;

public class tryAgainButton extends UI {

    private GameStarter gameStarter;
    private GreenfootImage normal;
    private GreenfootImage leuchtend;

    public tryAgainButton(GameStarter gameStarter){
        this.gameStarter = gameStarter;

        normal = schneidePlakette();
        leuchtend = schneidePlakette();
        leuchtend.setColor(new Color(255, 255, 255, 28));
        leuchtend.fillRect(23, 22, 354, 72);   // nur die innenflaeche, nicht den rahmen

        setImage(normal);
    }

    public void act(){
        if (Greenfoot.mouseClicked(this)){
            SoundManager.play("button_click.mp3");
            gameStarter.restart();
            return;
        }

        mouseHover();
    }

    // die plakette ist im game-over-bild schon aufgemalt - von dort ausgeschnitten
    // passt sie farblich automatisch zum hintergrund
    private GreenfootImage schneidePlakette(){
        GreenfootImage voll = new GreenfootImage("UI/GameOverScreen.png");
        voll.scale(1067, 1092);

        GreenfootImage plakette = new GreenfootImage(400, 116);
        plakette.drawImage(voll, -107, -949);   // alles ausserhalb wird abgeschnitten
        return plakette;
    }

    private void mouseHover(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse == null) {
            return;
        }

        setImage(normal);
        List objects = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), tryAgainButton.class);
        for (Object object : objects)
        {
            if (object == this)
            {
                setImage(leuchtend);
            }
        }
    }
}
