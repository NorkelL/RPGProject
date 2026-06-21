package items.util;

import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import items.TestItem;
import ui.ItemText;
import util.FontManager;

import java.awt.Color;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public interface OnHover {

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ShowOnHover {}
    

    
    

    
    public default GreenfootImage hovering() {
        Field[] onHoverFields = getOnHoverFields();
        int padding = 10;

        List<GreenfootImage> lines = new ArrayList<>();
        for (Field field : onHoverFields) {
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                if (value instanceof Rarity r) {
                    lines.add(FontManager.renderText("Rarity: " + r.name(), FontManager.getMinecraft(12f), r.Color));
                    lines.add(FontManager.renderText("Multiplier: x" + r.Multiplier, FontManager.getMinecraft(12f), Color.DARK_GRAY));
                } else {
                    lines.add(FontManager.renderText(field.getName() + ": " + value, FontManager.getMinecraft(12f), Color.DARK_GRAY));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        int maxWidth = 0;
        int totalHeight = 0;
        for (GreenfootImage line : lines) {
            if (line.getWidth() > maxWidth) maxWidth = line.getWidth();
            totalHeight += line.getHeight() + 4;
        }

        GreenfootImage bg = new GreenfootImage("UI/MainMenu/InfoText.png");
        bg.scale(maxWidth + padding * 2, totalHeight + padding * 2);

        int y = padding;
        for (GreenfootImage line : lines) {
            bg.drawImage(line, padding, y);
            y = y + line.getHeight() + 4;
        }
        return bg;
    }

    public default Field[] getOnHoverFields() {
        List<Field> foundFields = new ArrayList<>();
        Class<?> clazz = this.getClass();
        while (clazz != null && clazz != Object.class) {
            for (Field f : clazz.getDeclaredFields()) {
                if (f.isAnnotationPresent(ShowOnHover.class)) {
                    foundFields.add(f);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return foundFields.toArray(new Field[0]);
    }
}
