package items;

import items.util.OnHover;
import items.util.Pickable;
import items.util.Rarity;

//nur zum ausprobieren, kommt im fertigen spiel nicht vor
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
