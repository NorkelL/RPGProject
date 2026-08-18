package ui;

import items.material.Gold;
import items.material.Iron;
import items.Item;
import items.armor.*;
import greenfoot.*;

import java.util.List;

public class UpgradeButton extends Actor {
    private UpgradeSlot armorSlot;
    private UpgradeSlot materialSlot;
    private UpgradeSlot outputSlot;
    private  GreenfootImage UpgradeButton = new GreenfootImage("UI/Inventory/UpgradeButton.png");
    private GreenfootImage UpgradeButtonGlowing = new GreenfootImage("UI/Inventory/UpgradeButtonGlowing.png");
    private boolean isScaled = false;
    private int textTimer = 0;

    // Der Konstruktor bekommt die 3 Slots aus der Welt übergeben
    public UpgradeButton(UpgradeSlot armor, UpgradeSlot material, UpgradeSlot output) {
        this.armorSlot = armor;
        this.materialSlot = material;
        this.outputSlot = output;
        GreenfootImage UpgradeButton = new GreenfootImage("UI/Inventory/UpgradeButton.png");
        UpgradeButton.scale(250,190);
        setImage(UpgradeButton);

    }

    public void act() {
        if (textTimer > 0){
            textTimer--;
            if(textTimer==0){getWorld().showText(null, getX(), getY() - 2);}
        }

        mouseHover();
        // Prüfen, ob der Spieler genau auf diesen Button klickt
        if (Greenfoot.mouseClicked(this)) {
            versucheUpgrade();
        }
    }

    //hier stehen alle rezepte, neue kombination = neuer else-if zweig
    private void versucheUpgrade() {
        Item armor = armorSlot.getItem();
        Item material = materialSlot.getItem();

        // Wenn einer der beiden vorderen Slots leer ist, passiert nichts
        if (armor == null || material == null) {
            return;
        }

        if (outputSlot.getItem() != null) {
            meldung("Erst das Ergebnis rausnehmen");
            return;
        }

        // --- REZEPTE PRÜFEN ---

        // Brustpanzer
        if (armor instanceof LeatherArmor && material instanceof Iron) {
            erfolgreichesUpgrade(new IronArmor("chest"));
        }
        else if (armor instanceof IronArmor && material instanceof Gold) {
            erfolgreichesUpgrade(new GoldArmor("chest"));
        }

        // Helme
        else if (armor instanceof LeatherHelmet && material instanceof Iron) {
            erfolgreichesUpgrade(new IronHelmet("head"));
        }
        else if (armor instanceof IronHelmet && material instanceof Gold) {
            erfolgreichesUpgrade(new GoldHelmet("head"));
        }
        else {
            // Wenn etwas Falsches drin liegt (z.B. Holz mit Gold)
            meldung("Das kann nicht kombiniert werden");
        }
    }


    private void erfolgreichesUpgrade(Item neuesItem) {
        // 1. Das neue Item in den Output-Slot legen
        outputSlot.setItem(neuesItem);

        // 2. Die alten Items löschen.
        // Da  InventorySlot setItem(null) unterstützt und das Bild updatet, klappt das
        armorSlot.setItem(null);
        materialSlot.setItem(null);

        meldung("Upgrade erfolgreich");
    }

    private void meldung(String text){
        getWorld().showText(text, getX(), getY() - 2);
        textTimer = 90;
    }
    private void mouseHover(){
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (!isScaled) {
            UpgradeButton.scale(250,190);
            UpgradeButtonGlowing.scale(250,190);
            isScaled = true;
        }


        if (mouse != null) {
            setImage(UpgradeButton);
            List objects = getWorld().getObjectsAt(mouse.getX(), mouse.getY(), UpgradeButton.class);
            for (Object object : objects)
            {
                if (object == this)
                {
                    setImage(UpgradeButtonGlowing);
                }
            }
        }
    }
}