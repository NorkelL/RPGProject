package ui;

import entities.Player;
import greenfoot.Color;
import greenfoot.GreenfootImage;

public class XPBar extends UI {
    private final Player player;
    private final int depth;

    public XPBar(Player player, int depth) {
        this.player = player;
        this.depth = depth;
        updateImage();
    }

    @Override
    public void act() {
        updateImage();
    }

    private void updateImage() {
        GreenfootImage image = createPanelImage(240, 68, "Hunter Lv." + player.getPlayerLevel());
        int maxWidth = 180;
        int fillWidth = Math.max(0, (int) Math.round(maxWidth * (player.getXp() / (double) player.getXpForNextLevel())));
        image.setColor(new Color(72, 112, 116));
        image.fillRect(24, 24, maxWidth, 10);
        image.setColor(new Color(224, 189, 96));
        image.fillRect(24, 24, fillWidth, 10);
        image.setColor(new Color(241, 234, 220));
        image.drawString("Depth " + depth, 24, 52);
        image.drawString(player.getXp() + "/" + player.getXpForNextLevel() + " XP", 110, 52);
        setImage(image);
    }
}
