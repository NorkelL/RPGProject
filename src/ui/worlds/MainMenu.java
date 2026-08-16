package ui.worlds;

import core.GameStarter;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.World;
import ui.Settings;
import ui.UI;
import ui.buttons.LoadGameButton;
import ui.buttons.SettingsButton;
import ui.buttons.StartButton;
import util.FontManager;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainMenu extends World {

    private GameStarter gameStarter;
    private StartButton startButton;
    private LoadGameButton loadGameButton;
    private SettingsButton settingsButton;

    private List<File> saves = new ArrayList<>();
    private int saveIndex = 0;
    private LoadFrame loadFrame;
    private boolean loadSelectOpen = false;


    public MainMenu(GameStarter gameStarter) {
        super(16, 9, 60);
        GreenfootImage bg = new GreenfootImage("UI/MainMenu/MainMenu.png");

        bg.scale(960, 540);
        setBackground(bg);
        this.gameStarter = gameStarter;
        int cx = getWidth()/2;
        int cy = getHeight()/2;

        startButton = new StartButton(gameStarter);
        loadGameButton = new LoadGameButton(gameStarter);
        settingsButton = new SettingsButton(gameStarter);

        addObject(startButton, cx, cy-1);
        addObject(loadGameButton, cx, cy+1);
        addObject(settingsButton, cx, cy+3);
    }

    public void showLoadSelect(){
        removeObject(startButton);
        removeObject(loadGameButton);
        removeObject(settingsButton);

        saves = readSaves();
        saveIndex = 0;

        loadFrame = new LoadFrame();
        addObject(loadFrame, getWidth()/2, getHeight()/2);
        loadSelectOpen = true;
        renderLoadFrame();
    }

    private void hideLoadSelect(){
        removeObject(loadFrame);
        loadSelectOpen = false;

        int cx = getWidth()/2;
        int cy = getHeight()/2;
        addObject(startButton, cx, cy-1);
        addObject(loadGameButton, cx, cy+1);
        addObject(settingsButton, cx, cy+3);
    }

    private void cycleSave(int dir){
        if (saves.isEmpty()) return;
        saveIndex = (saveIndex + dir + saves.size()) % saves.size();
        renderLoadFrame();
    }

    private void loadSelected(){
        if (saves.isEmpty()) return;
        try {
            gameStarter.resumeSave(saves.get(saveIndex).toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void renderLoadFrame(){
        GreenfootImage img = new GreenfootImage("UI/MainMenu/Bar_Blank.png");
        img.scale(310, 110);

        String text = saves.isEmpty() ? "keine Speicherstände" : saves.get(saveIndex).getName();
        GreenfootImage name = FontManager.renderText(text, FontManager.getMinecraft(20f), new Color(255, 215, 0));
        img.drawImage(name, (img.getWidth() - name.getWidth()) / 2, (img.getHeight() - name.getHeight()) / 2);

        loadFrame.setImage(img);
    }

    private List<File> readSaves(){
        File[] files = GameStarter.SAVE_DIR.toFile().listFiles();
        if (files == null) return new ArrayList<>();

        return Arrays.stream(files)
                .sorted(Comparator.comparingLong(File::lastModified).reversed())
                .collect(Collectors.toList());
    }

    private class LoadFrame extends UI {
        private boolean upWasDown, downWasDown, enterWasDown, escWasDown;

        @Override
        public void act(){
            boolean up    = Greenfoot.isKeyDown("up");
            boolean down  = Greenfoot.isKeyDown("down");
            boolean enter = Greenfoot.isKeyDown("enter");
            boolean esc   = Greenfoot.isKeyDown(Settings.pauseKey);

            if (up && !upWasDown)       cycleSave(-1);
            if (down && !downWasDown)   cycleSave(1);
            if (enter && !enterWasDown) loadSelected();
            if (esc && !escWasDown)     hideLoadSelect();

            upWasDown = up;
            downWasDown = down;
            enterWasDown = enter;
            escWasDown = esc;
        }
    }
}
