package ui;

import greenfoot.Actor;
import greenfoot.GreenfootImage;
import greenfoot.Color;

public class DarkFilter extends Actor {
    public DarkFilter(int width, int height) {
        // Erstelle ein Bild in der Größe der Welt
        GreenfootImage img = new GreenfootImage(width, height);
        // Farbe Schwarz mit Transparenz (z.B. 150 von 255)
        img.setColor(new Color(0, 0, 0, 150));
        img.fill();
        setImage(img);
    }
}