package ui;
import greenfoot.GreenfootImage;
import util.FontManager;
import java.awt.Color;
public class LevelCounter extends UI{

    //Text mit Textart, Hintergrund und Hintergrundbild größe
    public LevelCounter(int level) {
        GreenfootImage text = FontManager.renderText("Level " + level, FontManager.getMinecraft(14f), new Color(74, 83, 112));
        GreenfootImage background = new GreenfootImage("UI/MainMenu/InfoText.png");
        background.scale(text.getWidth()+20,text.getHeight()+20);
        background.drawImage(text,10,10);
        setImage(background);
    }
}
