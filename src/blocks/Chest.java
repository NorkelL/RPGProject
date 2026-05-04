package blocks;

import entities.Player;
import greenfoot.Greenfoot;
import greenfoot.World;
import items.Item;

import java.util.List;

public class Chest extends Block {
    private boolean isOpen;

    public Chest(){
        isOpen = false;
    }

    public void act(){
        if (isTouching(Player.class) && !isOpen){
            openChest();
        }
    }

    public void openChest(){
        isOpen = true;
        dropRandomItem();
    }

    private void dropRandomItem(){
        World world = getWorld();
        List<Item> allItem = (List<Item>) world.getObjects(Item.class);

        int randomIndex = Greenfoot.getRandomNumber(allItem.size());
        Item chosenItem = allItem.get(randomIndex);

        chosenItem.setLocation(getX() + 40, getY());
    }

    public boolean isOpen(){
        return isOpen;
    }
}
