package items.armor;

import greenfoot.GreenfootImage;
import items.Item;
import items.util.OnHover;
import items.util.Rarity;

// Wichtig: Armor erbt von Item, damit es ins Inventar/Backpack passt!
public class Armor extends Item {
    @OnHover.ShowOnHover
    private final String slotType;// "head" oder "chest"
    @OnHover.ShowOnHover
    private final String material; // "leather", "iron", etc.

    public Armor(String slotType, String material) {
        this.slotType = slotType;
        this.material = material;

        // Setzt das Bild für das Item auf dem Boden oder im Slot
        // z.B. "items/leather_chest.png"
        GreenfootImage armor = new GreenfootImage("items/" + material + "_" + slotType + ".png");
        armor.scale(60,60);
        setImage(armor);
    }

    public String getSlotType() {
        return slotType;
    }

    public String getMaterial() {
        return material;
    }

    @Override
    public void act(){
        super.act();
        checkHover();
    }
}
