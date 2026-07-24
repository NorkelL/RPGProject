package ui.buttons;

import greenfoot.*;
import ui.Settings;
import ui.worlds.SettingsWorld;

public class KeyButton extends StandardButton
{
    private String currentKey;
    private boolean waitingForInput = false;
    private int blinkTimer = 0;
    private String action;



    public KeyButton(String text,String action)
    {
        super(text);
        this.action = action;
        this.currentKey = text;
    }

    public void act()
    {
        checkClick();
        checkInput();
        updateImage(new GreenfootImage("UI/MainMenu/KeyButton.png"));
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

        waitingForInput = false;

        if(getWorld() instanceof SettingsWorld sw)
        {
            sw.setBlinkActivated(false);
        }
    }


    @Override
    public void updateImage(GreenfootImage image)
    {
        image.scale(200, 110);

        int w = image.getWidth();
        int h = image.getHeight();

        image.setFont(new Font("Arial", 40));
        image.setColor(Color.WHITE);

        String textToShow;

        if(getWorld() instanceof SettingsWorld){
            Boolean activateBlink = ((SettingsWorld) getWorld()).isBlinkActivated();
            if (waitingForInput)
            {
                textToShow = (blinkTimer < 15) ? "|" : "";
            }
            else
            {
                textToShow = (currentKey != null) ? currentKey : "";
            }

            int textWidth = textToShow.length() * 18;

            int x = (w - textWidth) / 2;
            int y = h / 2 + 15;

            image.drawString(textToShow, x, y);

            setImage(image);
        }


    }
}