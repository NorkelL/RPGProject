package ui;

import entities.Player;
import greenfoot.Color;
import greenfoot.GreenfootImage;

public class Healthbar extends UI{
    private final Player player;

    public Healthbar(Player player) {
        this.player = player;
        updateImage();
    }

    @Override
    public void act() {
        updateImage();
    }

    private void updateImage() {
        GreenfootImage image = createPanelImage(240, 56, "Health");
        int maxWidth = 180;
        int fillWidth = Math.max(0, (int) Math.round(maxWidth * (player.getLife() / (double) player.getMaxLife())));
        image.setColor(new Color(73, 20, 31));
        image.fillRect(24, 24, maxWidth, 10);
        image.setColor(new Color(193, 73, 60));
        image.fillRect(24, 24, fillWidth, 10);
        image.setColor(new Color(241, 234, 220));
        image.drawString(player.getLife() + " / " + player.getMaxLife(), 78, 46);
        setImage(image);
    }
}
