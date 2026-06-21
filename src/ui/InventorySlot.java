package ui;

import greenfoot.Actor;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;
import greenfoot.MouseInfo;
import items.Item;
import items.util.OnHover;
import ui.worlds.Backpack;

public class InventorySlot extends Actor {
    private Item item;
    private final GreenfootImage baseImage;
    private final GreenfootImage glowingImage;
    private boolean isSelected = false;
    private final int slotPixelWidth;
    private final int slotPixelHeight;
    private static InventorySlot draggedSlot= null;
    private static ui.GhostItem ghost = null;
    private boolean isItemHidden = false;

    private ItemText currentHoverer;


    public InventorySlot() {
        this(60, 60);
    }


    public InventorySlot(int pixelWidth, int pixelHeight) {
        this.slotPixelWidth = pixelWidth;
        this.slotPixelHeight = pixelHeight;
        this.baseImage = new GreenfootImage("UI/Inventory/InventorySlot.png");
        this.baseImage.scale(slotPixelWidth, slotPixelHeight);

        // Leuchtendes Hintergrundbild laden und scalen
        this.glowingImage = new GreenfootImage("UI/Inventory/InventorySlotGlowing.png");
        this.glowingImage.scale(slotPixelWidth, slotPixelHeight);

        updateImage();
    }

    public InventorySlot(Item item) {
        this();
        setItem(item);
    }

    public void setItem(Item item) {
        this.item = item;
        updateImage(); // Bild neu zeichnen, wenn sich das Item ändert
    }

    public Item getItem() {
        return item;
    }

    public void setSelected(boolean selected) {

        if (draggedSlot == this && !selected) {
            return;
        }

        if (this.isSelected != selected) {
            this.isSelected = selected;
            updateImage();
        }
    }

    public boolean isSelected() {
        return isSelected;
    }


    private void updateImage() {

        GreenfootImage currentBackground = isSelected ? new GreenfootImage(glowingImage) : new GreenfootImage(baseImage);

        //  Nur zeichnen, wenn das Item nicht gerade als Ghost unterwegs ist!
        if (item != null && !isItemHidden) {
            GreenfootImage itemImage = item.getImage();
            if (itemImage != null) {
                if (itemImage.getWidth() > slotPixelWidth || itemImage.getHeight() > slotPixelHeight) {
                    itemImage = new GreenfootImage(itemImage);
                    itemImage.scale(slotPixelWidth - 10, slotPixelHeight - 10);
                }

                currentBackground.drawImage(itemImage,
                        (currentBackground.getWidth() - itemImage.getWidth()) / 2,
                        (currentBackground.getHeight() - itemImage.getHeight()) / 2);
            }
        }


        setImage(currentBackground);
    }

    private void handleDragAndDrop() {
        // 1. MAUS GEDRÜCKT: Drag startet
        if (Greenfoot.mousePressed(this)) {
            if (this.item != null && draggedSlot == null) {
                draggedSlot = this;

                for (InventorySlot slot : getWorld().getObjects(InventorySlot.class)) {
                    slot.setSelected(false);
                }
                setSelected(true);

                // --- GEIST ERSTELLEN ---
                if (this.item.getImage() != null) {
                    ghost = new ui.GhostItem(this.item.getImage());
                    // Geist genau auf die Maus-Position setzen und zur Welt hinzufügen
                    greenfoot.MouseInfo mouse = Greenfoot.getMouseInfo();
                    int startX = mouse != null ? mouse.getX() : getX();
                    int startY = mouse != null ? mouse.getY() : getY();
                    getWorld().addObject(ghost, startX, startY);

                    // Den aktuellen Slot unsichtbar machen, während man zieht
                    this.isItemHidden = true;
                    updateImage(); // Zeichnet den Slot ohne das Item-Bild neu
                }
            }
        }

        // 2. MAUS LOSGELASSEN: Drag endet
        if (Greenfoot.mouseDragEnded(null)) {
            if (draggedSlot == this) {

                // --- GEIST LÖSCHEN ---
                if (ghost != null && ghost.getWorld() != null) {
                    getWorld().removeObject(ghost);
                    ghost = null;
                }

                greenfoot.MouseInfo mouse = Greenfoot.getMouseInfo();
                if (mouse != null) {
                    int mouseX = mouse.getX();
                    int mouseY = mouse.getY();

                    java.util.List<InventorySlot> targets = getWorld().getObjectsAt(mouseX, mouseY, InventorySlot.class);
                    if (!targets.isEmpty()) {
                        InventorySlot targetSlot = targets.get(0);
                        if (targetSlot != this) {
                            swapItems(this, targetSlot);

                        }
                    }
                }

                // Den Slot wieder ganz normal zeichnen
                this.isItemHidden = false;
                draggedSlot = null;
                this.setSelected(false);

                //  alle Slots updaten damit dass alle Bilder stimmen
                for (InventorySlot slot : getWorld().getObjects(InventorySlot.class)) {
                    slot.isItemHidden = false;
                    slot.updateImage();
                }
            }
        }
    }


    private void swapItems(InventorySlot slotA, InventorySlot slotB) {
        if (slotA == slotB) return;

        // Items tauschen
        Item tempItem = slotA.getItem();
        slotA.setItem(slotB.getItem());
        slotB.setItem(tempItem);

        if (getWorld() != null) {

            // Normale Inventare synchronisieren
            java.util.List<InventoryVisualizer> visualizers =
                    getWorld().getObjects(InventoryVisualizer.class);

            for (InventoryVisualizer visualizer : visualizers) {
                visualizer.forceSyncArray();
            }

            // Rüstungsslots mit dem Player synchronisieren
            if (getWorld() instanceof ui.worlds.Backpack backpack) {

                backpack.getPlayer().setHeadArmor(
                        (items.Item) backpack.getHeadSlot().getItem()
                );

                backpack.getPlayer().setChestArmor(
                        (items.Item) backpack.getChestSlot().getItem()
                );
            }

            if (getWorld() instanceof ui.worlds.Backpack) {
                ((ui.worlds.Backpack) getWorld()).act();
            }
        }
    }




    @Override
    public void act() {
        handleDragAndDrop();
        super.act();
        MouseInfo mouse = Greenfoot.getMouseInfo();
        if (mouse != null && item != null && getWorld() instanceof Backpack) {
            OnHover oh = item;
            boolean isHovering = getWorld()
                    .getObjectsAt(mouse.getX(), mouse.getY(), InventorySlot.class)
                    .contains(this);

            if (isHovering && currentHoverer == null) {
                currentHoverer = new ItemText(oh.hovering(),this);
                getWorld().addObject(currentHoverer, getX(), getY() - 1);
            } else if (!isHovering && currentHoverer != null) {
                getWorld().removeObject(currentHoverer);
                currentHoverer = null;
            }
        }
    }
}
