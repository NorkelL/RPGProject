package ui;

import greenfoot.Greenfoot;
import greenfoot.MouseInfo;

public class Settings  {

    public static String upKey = "w";
    public static String downKey = "s";
    public static String leftKey = "a";
    public static String rightKey = "d";
    public static String takeItem = "t";
    public static String putItem ="p";
    public static String inventoryToggle ="e";
    public static String useItem ="f";
    public static String attack ="MOUSE1";


    public static boolean soundOn = true;
    public static int volume = 50;


    public static boolean isPressed(String key)
    {
        MouseInfo mouse = Greenfoot.getMouseInfo();

        if(key.equals("MOUSE1"))
        {
            return mouse != null
                    && Greenfoot.mousePressed(null)
                    && mouse.getButton() == 1;
        }

        if(key.equals("MOUSE2"))
        {
            return mouse != null
                    && Greenfoot.mousePressed(null)
                    && mouse.getButton() == 2;
        }

        if(key.equals("MOUSE3"))
        {
            return mouse != null
                    && Greenfoot.mousePressed(null)
                    && mouse.getButton() == 3;
        }

        return Greenfoot.isKeyDown(key);
    }

}
