package ui.Buttons;

import greenfoot.Color;
import greenfoot.Font;
import greenfoot.GreenfootImage;
import ui.UI;

public class StandardButton extends UI {

    private final GreenfootImage base = new GreenfootImage("UI/MainMenu/StandardButton.png");


    public String text;

    public StandardButton(String text)
    {
        this.text = text;
        updateImage(base);
    }

    public void updateImage(GreenfootImage image)
    {
        image.scale(310, 110);

        int w = image.getWidth();
        int h = image.getHeight();

        image.setFont(new Font("Arial",40));
        image.setColor(Color.WHITE);

        int textWidth = text.length() * 18;
        int x = (w - textWidth) / 2;
        int y = h / 2 + 15;

        image.drawString(text, x-15, y);

        setImage(image);
    }
}
