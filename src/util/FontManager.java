package util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import greenfoot.GreenfootImage;

public class FontManager {

    private static final String FONT_DIR = "fonts/";
    private static final Map<String, Font> baseCache = new HashMap<>();

    public static Font get(String filename, float size) {
        return loadBase(filename).deriveFont(size);
    }

    public static Font getMinecraft(float size) {
        return get("Minecraft.otf", size);
    }

    public static Font getMinecraftBold(float size) {
        return get("MinecraftBold.otf", size);
    }

    private static Font loadBase(String filename) {
        return baseCache.computeIfAbsent(filename, k -> {
            try {
                return Font.createFont(Font.TRUETYPE_FONT, new File(FONT_DIR + k));
            } catch (FontFormatException | IOException e) {
                System.err.println("FontManager: could not load " + k + ", falling back to Arial");
                return new Font("Arial", Font.PLAIN, 12);
            }
        });
    }

    public static GreenfootImage renderText(String text, Font font, java.awt.Color color) {
        BufferedImage tmp = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = tmp.createGraphics();
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        g2.dispose();

        GreenfootImage result = new GreenfootImage(width, height);
        Graphics2D g = (Graphics2D) result.getAwtImage().getGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, 0, fm.getAscent());
        g.dispose();
        return result;
    }
}
