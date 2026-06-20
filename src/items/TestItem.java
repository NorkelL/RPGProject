package items;

import greenfoot.Greenfoot;
import greenfoot.MouseInfo;
import items.util.OnHover;
import items.util.Pickable;
import items.util.Rarity;
import ui.Buttons.LoadGameButton;
import ui.ItemText;

import java.util.List;

public class TestItem extends Item implements Pickable {

    @OnHover.ShowOnHover
    public Rarity rarity;

    public TestItem() {
        setImage("Blocks/WallTile.png");
        rarity = Rarity.setRarity();
    }

    @Override
    public void act(){
        checkHover();
    }
    public void showMultiplier(){
        System.out.println(rarity.Multiplier);
    }
}
