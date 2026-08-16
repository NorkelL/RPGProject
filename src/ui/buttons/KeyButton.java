package ui.buttons;

import greenfoot.*;
import ui.Settings;
import ui.worlds.SettingsWorld;
import util.SoundManager;

public class KeyButton extends StandardButton
{
    private String currentKey;
    private boolean waitingForInput = false;
    private int blinkTimer = 0;
    private String action;
    private GreenfootImage keyImage; // einmal von der platte laden, nicht jeden frame
    private String lastText;



    public KeyButton(String text,String action)
    {
        super(text);
        this.action = action;
        this.currentKey = text;
        this.keyImage = new GreenfootImage("UI/MainMenu/KeyButton.png");
    }

    public void act()
    {
        checkClick();
        checkInput();

        String textToShow;
        if (waitingForInput) { textToShow = (blinkTimer < 15) ? "|" : ""; }
        else                 { textToShow = (currentKey != null) ? currentKey : ""; }

        // nur neu zeichnen wenn sich der text geaendert hat
        if (!textToShow.equals(lastText)) {
            lastText = textToShow;
            updateImage(new GreenfootImage(keyImage));
        }
    }

    private void checkClick()
    {
        if (Greenfoot.mouseClicked(this))
        {
            if (getWorld() instanceof SettingsWorld sw)
            {
                if (sw.isBlinkActivated())
                {
                    return;
                }

                waitingForInput = true;
                blinkTimer = 0;
                sw.setBlinkActivated(true);
            }
        }
    }

    private void checkInput()
    {
        if (!waitingForInput) return;

        blinkTimer++;
        if (blinkTimer >= 60) blinkTimer = 0;

        String key = Greenfoot.getKey();

        if (key != null)
        {
            saveKey(key.toUpperCase());
            return;
        }

        MouseInfo mouse = Greenfoot.getMouseInfo();

        if(mouse != null && Greenfoot.mousePressed(null))
        {
            int button = mouse.getButton();

            if(button == 1) saveKey("MOUSE1");
            else if(button == 2) saveKey("MOUSE2");
            else if(button == 3) saveKey("MOUSE3");
        }
    }

    private void saveKey(String key)
    {
        currentKey = key;

        if(action.equals("up")) Settings.upKey = currentKey;
        if(action.equals("down")) Settings.downKey = currentKey;
        if(action.equals("left")) Settings.leftKey = currentKey;
        if(action.equals("right")) Settings.rightKey = currentKey;
        if(action.equals("putItem")) Settings.putItem = currentKey;
        if(action.equals("takeItem")) Settings.takeItem = currentKey;
        if(action.equals("toggleInventory")) Settings.inventoryToggle = currentKey;
        if(action.equals("useItem")) Settings.useItem = currentKey;
        if(action.equals("attack")) Settings.attack = currentKey;

        // "Sound" ist kein key sondern ein schalter
        if(action.equals("Sound")){
            Settings.soundOn = !Settings.soundOn;
            currentKey = Settings.soundOn ? "on" : "off";
        }

        if(action.equals("Music")){
            SoundManager.toggleMusic();
            currentKey = Settings.musicOn ? "on" : "off";
        }

        waitingForInput = false;

        if(getWorld() instanceof SettingsWorld sw)
        {
            sw.setBlinkActivated(false);
        }
    }


    @Override
    public void updateImage(GreenfootImage image)
    {
        if(!(getWorld() instanceof SettingsWorld)) return;

        image.scale(200, 110);

        int w = image.getWidth();
        int h = image.getHeight();

        image.setFont(new Font("Arial", 40));
        image.setColor(Color.WHITE);

        String textToShow = (lastText != null) ? lastText : "";

        int textWidth = textToShow.length() * 18;

        int x = (w - textWidth) / 2;
        int y = h / 2 + 15;

        image.drawString(textToShow, x, y);

        setImage(image);
    }
}