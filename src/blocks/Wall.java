package blocks;

import entities.base.ImprovedActor;
import greenfoot.GreenfootImage;

//das bild ist 40x80 statt 40x40, die wand ragt also in die zelle darueber
// deswegen wird woanders immer ueber getX/getY geprueft und nicht ueber getObjectsAt
public class Wall extends ImprovedActor {

    public Wall() {
        GreenfootImage wall = new GreenfootImage("Blocks/WallTile.png");
        wall.scale(40, 60);

        GreenfootImage image = new GreenfootImage(40, 80);
        image.drawImage(wall, 0, 0);
        setImage(image);
    }
}
