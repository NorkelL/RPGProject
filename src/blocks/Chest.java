package blocks;

import entities.Player;
import greenfoot.Color;
import greenfoot.GreenfootImage;
import items.Item;
import items.ItemTyp;

public class Chest extends Block {
    private boolean isOpen;

    public Chest() {
        isOpen = false;
        setImage(zeichneChest());
    }

    private GreenfootImage zeichneChest() {
        GreenfootImage img = new GreenfootImage(40, 32);

        // Deckel
        img.setColor(new Color(120, 70, 20));
        img.fillRect(1, 1, 38, 13);

        // Körper
        img.setColor(new Color(160, 100, 40));
        img.fillRect(1, 14, 38, 17);

        // Holzmaserung
        img.setColor(new Color(140, 85, 30));
        img.drawLine(8, 15, 8, 30);
        img.drawLine(20, 15, 20, 30);
        img.drawLine(32, 15, 32, 30);

        // Goldband Mitte
        img.setColor(new Color(180, 150, 60));
        img.fillRect(0, 12, 40, 3);

        // Goldband unten
        img.fillRect(0, 25, 40, 2);

        // Äußerer Rahmen
        img.setColor(new Color(60, 30, 5));
        img.drawRect(0, 0, 39, 31);

        // Schloss
        img.setColor(new Color(220, 180, 50));
        img.fillRect(16, 9, 8, 8);
        img.setColor(new Color(80, 50, 10));
        img.fillOval(18, 13, 4, 4);
        img.fillRect(19, 16, 2, 3);

        // Nieten an den Ecken
        img.setColor(new Color(200, 170, 60));
        img.fillOval(2, 2, 5, 5);
        img.fillOval(33, 2, 5, 5);
        img.fillOval(2, 25, 5, 5);
        img.fillOval(33, 25, 5, 5);

        return img;
    }

    public void act() {
        if (isTouching(Player.class) && !isOpen) {
            openChest();
        }
    }

    public void openChest() {
        isOpen = true;
        setImage(zeichneOffeneChest());
        dropRandomItem();
    }

    private GreenfootImage zeichneOffeneChest() {
        GreenfootImage img = new GreenfootImage(40, 40);

        // Deckel (nach hinten offen, schmal oben)
        img.setColor(new Color(120, 70, 20));
        img.fillRect(1, 1, 38, 7);

        // Goldband am Deckel
        img.setColor(new Color(180, 150, 60));
        img.fillRect(0, 7, 40, 2);

        // Körper
        img.setColor(new Color(160, 100, 40));
        img.fillRect(1, 9, 38, 30);

        // Innenraum dunkel
        img.setColor(new Color(40, 20, 5));
        img.fillRect(3, 11, 34, 16);

        // Goldglanz innen
        img.setColor(new Color(255, 220, 80));
        img.fillRect(8, 14, 24, 8);
        img.setColor(new Color(255, 240, 150));
        img.fillRect(13, 16, 14, 4);

        // Holzmaserung unten
        img.setColor(new Color(140, 85, 30));
        img.drawLine(8, 27, 8, 38);
        img.drawLine(20, 27, 20, 38);
        img.drawLine(32, 27, 32, 38);

        // Goldbänder
        img.setColor(new Color(180, 150, 60));
        img.fillRect(0, 27, 40, 2);
        img.fillRect(0, 35, 40, 2);

        // Äußerer Rahmen
        img.setColor(new Color(60, 30, 5));
        img.drawRect(0, 0, 39, 39);
        img.drawLine(0, 8, 39, 8);

        // Nieten
        img.setColor(new Color(200, 170, 60));
        img.fillOval(2, 2, 5, 5);
        img.fillOval(33, 2, 5, 5);
        img.fillOval(2, 31, 5, 5);
        img.fillOval(33, 31, 5, 5);

        return img;
    }

    private void dropRandomItem() {
        if (ItemTyp.values().length == 0) return;
        getWorld().addObject(ItemTyp.zufällig().erstelleItem(), getX(), getY());
    }

    public boolean isOpen() {
        return isOpen;
    }
}
