package world;

import greenfoot.Actor;
import greenfoot.World;

/**
 * Eine World, die zwei Koordinaten-Ebenen unterstützt:
 *
 *  - die physische Greenfoot-Zellgröße ({@code unit}) – die feinste Einheit,
 *    in der Objekte tatsächlich platziert und bewegt werden (z.B. 1 = pixelgenau),
 *  - ein logisches Tile-Raster ({@code tileSize}) – die Größe einer Spielkachel
 *    (z.B. 40), in der die Spiellogik (Generierung, Kollision, Pathfinding) denkt.
 *
 * Ein Tile besteht damit aus {@code tileSize / unit} physischen Zellen
 * ({@link #cellsPerTile()}). Texturen behalten ihre Pixelgröße; nur die
 * Platzierungs-Granularität wird feiner.
 *
 * Setzt man {@code unit == tileSize}, ist {@code cellsPerTile() == 1} und die
 * Welt verhält sich exakt wie eine klassische Greenfoot-World – die Umrechnungen
 * sind dann allesamt die Identität. Das macht den Umstieg rückwärtskompatibel.
 */
public class GridWorld extends World {

    private final int tileSize;
    private final int unit;
    private final int cellsPerTile;

    /**
     * @param tilesX   Breite der Welt in Tiles
     * @param tilesY   Höhe der Welt in Tiles
     * @param tileSize Kantenlänge einer Kachel in Pixeln (z.B. 40)
     * @param unit     physische Greenfoot-Zellgröße (z.B. 1 für pixelgenau, 40 = klassisch).
     *                 Sollte {@code tileSize} ganzzahlig teilen.
     */
    public GridWorld(int tilesX, int tilesY, int tileSize, int unit) {
        super(tilesX * (tileSize / unit), tilesY * (tileSize / unit), unit);
        this.tileSize = tileSize;
        this.unit = unit;
        this.cellsPerTile = tileSize / unit;
    }

    /** Physische Greenfoot-Zellgröße in Pixeln. */
    public int getUnit() { return unit; }

    /** Logische Kachelgröße in Pixeln. */
    public int getTileSize() { return tileSize; }

    /** Anzahl physischer Zellen, die ein Tile breit/hoch ist. */
    public int cellsPerTile() { return cellsPerTile; }

    /** Weltbreite in Tiles. */
    public int getTilesX() { return getWidth() / cellsPerTile; }

    /** Welthöhe in Tiles. */
    public int getTilesY() { return getHeight() / cellsPerTile; }

    /** Rechnet eine physische Zellkoordinate in den zugehörigen Tile-Index um. */
    public int cellToTile(int cell) { return Math.floorDiv(cell, cellsPerTile); }

    /** Mitte eines Tiles in physischen Zellkoordinaten. */
    public int tileToCellCenter(int tile) { return tile * cellsPerTile + cellsPerTile / 2; }

    /** Platziert ein Objekt mittig auf dem Tile-Raster (Option 1). */
    public void addTile(Actor actor, int tileX, int tileY) {
        addObject(actor, tileToCellCenter(tileX), tileToCellCenter(tileY));
    }

    /** Platziert ein Objekt pixelgenau (Option 2). */
    public void addPixel(Actor actor, int pixelX, int pixelY) {
        addObject(actor, pixelX / unit, pixelY / unit);
    }

    /** Pixel -> physische Zelle. */
    public int pixelToCell(int pixel) { return pixel / unit; }

    /** Physische Zelle -> Pixel. */
    public int cellToPixel(int cell) { return cell * unit; }
}
