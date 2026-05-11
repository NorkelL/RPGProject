package ui;

import core.GameStarter;
import greenfoot.GreenfootImage;

public class SettingsButton extends UI {

    private GameStarter gameStarter;

    public SettingsButton(GameStarter gameStarter){
        super();
        this.gameStarter = gameStarter;
        GreenfootImage SettingsButton = new GreenfootImage("Map/Settings.png");
        SettingsButton.scale(310,110);
        setImage(SettingsButton);

    }
}
