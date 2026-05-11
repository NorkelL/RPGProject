package world;

import core.GameStarter;
import greenfoot.GreenfootImage;
import greenfoot.World;
import ui.InfoPanel;
import ui.LoadGameButton;
import ui.SettingsButton;
import ui.StartButton;

public class MainMenu extends World {

    private GameStarter gameStarter;


    public MainMenu(GameStarter gameStarter) {
        super(16, 9, 60);
        GreenfootImage bg = new GreenfootImage("Map/MainMenu.png");
        bg.scale(960, 540);
        setBackground(bg);
        setPaintOrder(StartButton.class, LoadGameButton.class, SettingsButton.class, InfoPanel.class);
        this.gameStarter = gameStarter;
        int cx = getWidth()/2;
        int cy = getHeight()/2;

        addObject(new StartButton(gameStarter), cx - 3, cy - 1);
        addObject(new LoadGameButton(gameStarter), cx + 2, cy - 1);
        addObject(new SettingsButton(gameStarter), cx, cy + 3);
        addObject(new InfoPanel(
            "Story",
            420,
            130,
            -1,
            "You are the last Warden of Embervault.",
            "A living dungeon below the city has awakened.",
            "Descend, recover relics, and grow strong enough",
            "to seal the Heart beneath the ruins."
        ), cx + 4, 1);
    }
}
