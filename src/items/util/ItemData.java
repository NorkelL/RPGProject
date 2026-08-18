package items.util;

//ein item im save, gespeichert wird nur klassenname und rarity
// das objekt selbst baut Player.createItem() daraus wieder neu
public class ItemData{
    public int slot;
    public String classname;
    public String rarity;
}
