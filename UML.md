# UML-Klassendokumentation

Klassendiagramme aller **90 Klassen und Interfaces** des Projekts, direkt aus den Quelltexten in `src/` erzeugt.

Die Diagramme sind in **Mermaid** geschrieben und werden von GitHub direkt gerendert, zum Anschauen wird also kein zusätzliches Programm gebraucht.

Wegen der Größe des Projekts ist die Dokumentation in acht thematische Diagramme aufgeteilt. Erbt eine Klasse von etwas, das in einem anderen Diagramm steht, wird die Oberklasse dort als leerer Kasten mit eingezeichnet.

**So sind die Diagramme zu lesen:**

- `+` public, `-` private, `#` protected, `~` package-private
- `$` statisch, `*` abstrakt beziehungsweise Interface-Methode
- Durchgezogener Pfeil `<|--` bedeutet *erbt von*, gestrichelter Pfeil `<|..` bedeutet
  *implementiert*
- Klassen mit dem Vermerk `<<Greenfoot>>` stammen aus der Greenfoot-Bibliothek und sind
  nur als Aufhängepunkt der Vererbung eingezeichnet
- Bei Methoden stehen die **Parametertypen**, nicht die Parameternamen

## Inhalt

---

## Kern und Spielwelt

*Pakete: `core, world, blocks` · 11 Klassen*

`GameStarter` startet das Spiel und verwaltet Seed, Stockwerke und Spielstände, `DungeonLevel` erzeugt ein Stockwerk und alles, was darin steht. Die Klassen aus `blocks` sind die festen Objekte der Welt.

```mermaid
classDiagram
    direction TB
    class World {
        <<Greenfoot>>
    }
    class Block
    class Chest {
        -boolean isOpen
        -int SIZE$
        +Chest()
        +act() void
        -angeklickt() boolean
        +openChest() void
        +openChestWithoutDrops() void
        -dropRandomItem() void
        +isOpen() boolean
    }
    class DungeonLevel {
        -Random rng
        +int centerExit
        -int centerEntrance
        -int[] centerCorridor
        -List~Room~ placedRooms
        -boolean paused
        -SaveGameButton saveGameButton
        -settingPauseButton settingPauseButton
        -restartButton restartButton
        -PauseScreen pauseScreen
        -GameStarter gameStarter
        +Player player
        +DungeonLevel(long, GameStarter, Player)
        -calcHeight(long) int$
        -calcWidth(long) int$
        -spawnCorridor() void
        -spawnMonsters() void
        -zufaelligesMonster() BaseMonster
        -spawnBloecke() void
        -istFreiFuerMonster(int, int) boolean
        -calcCorridor() int[]
        -spawnRooms() void
        -tryPlaceRoom(Room) boolean
        -savePlaceWall(int, int) void
        -removeWallAt(int, int) void
        -genRandomRoom() Room
        -generateRandomFloor() void
        +act() void
        +togglePause() void
        +showPause() void
        +hidePause() void
        +getOpenedChests() List~int[]~
        +movePlayer(int, int) void
        +getGameStarter() GameStarter
    }
    class Entrance {
        -GameStarter gameStarter
        +Entrance(GameStarter)
    }
    class Exit {
        -GameStarter gameStarter
        +Exit(GameStarter)
        +act() void
    }
    class GameStarter {
        -long seedsseed
        -Random seed
        +List~DungeonLevel~ pastLevels
        +DungeonLevel currentLevel
        +Path SAVE_DIR$
        -Player player
        +GameStarter()
        +mainMenu() void
        +start() void
        +restart() void
        +RenderNextWorld() void
        +resumeSave(Path) void
        +saveGame() void
    }
    class Rock {
        -int life
        +Rock()
        +Rock(int)
        +hit(int) void
        +getLife() int
        +setLife(int) void
    }
    class Room {
        ~int width
        ~int height
        ~int x
        ~int y
        ~Room(int, int, int, int)
    }
    class SaveData {
        ~int playerX
        ~int playerY
        ~int health
        ~int currentLevel
        ~long seed
        ~int level
        ~int xp
        ~int maxLife
        ~int bonusDamage
        ~List~List_ItemData~ inventorys
        ~List~List_int[]~ pastLevelLootedChests
        ~List~int[]~ currentLevelLootedChests
    }
    class UpgradeTable {
        -int SIZE$
        -boolean rWasDown
        +UpgradeTable()
        +act() void
    }
    class Wall {
        +Wall()
    }
    ImprovedActor <|-- Block
    Block <|-- Chest
    World <|-- DungeonLevel
    Block <|-- Entrance
    Block <|-- Exit
    World <|-- GameStarter
    ImprovedActor <|-- Rock
    Block <|-- UpgradeTable
    ImprovedActor <|-- Wall
```

---

## Spieler und Gegner

*Pakete: `entities` · 12 Klassen*

Die Vererbungskette vom Greenfoot-`Actor` bis zu Spieler und Monstern. `MovingActor` bringt die kollisionsgeprüfte Bewegung mit, `DamageableActor` Leben und Schaden, `BaseMonster` das Verfolgen des Spielers per A\*-Pathfinding.

```mermaid
classDiagram
    direction TB
    class Actor {
        <<Greenfoot>>
    }
    class ASharpPathfinding {
        <<interface>>
        ~getX() int*
        ~getY() int*
        ~getWorld() World*
        ~setRotation(int) void*
        ~move(int) void*
        ~aSharpPathfindTakeStep(int, int) boolean*
        ~pickRandomTarget() int[]*
        ~isBlocked(int, int) boolean*
    }
    class BaseMonster {
        <<abstract>>
        -int agroRadius
        -int leashRadius
        -boolean isFollowingPlayer
        -int moveCooldown
        -int moveDelay
        -int zielX
        -int zielY
        -long ANGRIFF_ANIMATION_MS$
        -long animationBis
        -int attackDamage
        -int xpDrop
        -long ANGRIFF_PAUSE_MS$
        -long naechsterAngriff
        +BaseMonster(int, int, int)
        +BaseMonster(int, int, int, int)
        +act() void
        +moveRandom() void
        #move() void
        -laufeZufaellig() void
        -neuesZiel() void
        -checkAgro() boolean
        -checkFollowRadius() boolean
        -spielerImUmkreis(int) boolean
        #moveToPlayer() void
        -greifeSpielerAn() boolean
        -findeSpielerInReichweite() Player
        -richtungZu(Player) Direction
        +getAttackDamage() int
        #setAttackDamage(int) void
        +getXpDrop() int
        #setXpDrop(int) void
        #onDeath() void
        +hit() void
        #onDamageSound() void
    }
    class DamageableActor {
        <<abstract>>
        -int life
        +getLife() int
        +setLife(int) void
        +takeDamage(int) void
        +takeDamage(int, boolean) void
        #zeigeSchadenszahl(int, boolean) void
        #onDamageSound() void
        +hit(int) void
        #onDeath() void*
    }
    class Direction {
        <<enumeration>>
        +EAST$
        +SOUTH$
        +WEST$
        +NORTH$
        -int value
        -Direction(int)
        +getValue() int
        +getRotation() int
        +getDirectionByRotation(int) Direction$
        +byValue(int) Direction$
    }
    class Gnome {
        +Gnome(int)
        #onDeath() void
    }
    class Hitting {
        <<interface>>
    }
    class ImprovedActor {
        -GreenfootImage currentImage
        +setImage(GreenfootImage) void
        +draw(String) void
        +draw(int) void
    }
    class MovingActor {
        -GreenfootImage[][] movingActorImages
        -int animationStep
        -boolean invisible
        -int sayTimer
        -int sayX
        -int sayY
        +MovingActor()
        +canMove() boolean
        +canMove(int) boolean
        +getNextX(int) int
        +getNextY(int) int
        +getNextX() int
        +getNextY() int
        +setRotation(int) void
        +turnLeft() void
        +turnRight() void
        +turn(Direction) void
        +turn(int) void
        +setImageRotation(Direction) void
        +act() void
        +say(String) void
        +say(boolean) void
        +say(int) void
        +say(double) void
        +move(int) void
        +loadImages(String, String) void
        +setInvisible(boolean) void
        -updateCurrentImage() void
    }
    class Orc {
        +Orc(int)
        #onDeath() void
    }
    class Player {
        -Item[] items
        -Item[] backpack
        -int maxItems
        -int maxBackpack
        -boolean eWasDown
        -boolean invisible
        -int invisibleTimer
        -double DamageMultiplier
        -int multiplierTimer
        -int maxLife
        -int moveCounter
        -InventoryVisualizer inventory
        -int activeSlot
        -int currentXP
        -int currentLevel
        -int xpToNextLevel
        -int bonusDamage
        -Item headArmor
        -Item chestArmor
        -BowSprite activeBowSprite
        -long ANGRIFF_PAUSE_MS$
        -long naechsterAngriff
        -List~String~ itemPackages$
        -int stepSoundCooldown
        +Player()
        +Player(int, int, int, int)
        +act() void
        +move() void
        +takeItem() void
        +putItem() void
        +gibStartwaffe() void
        +useItem() void
        -toggleInventory() void
        +openInventoryFromTable(World) void
        +angreifen() void
        #onDeath() void
        #onDamageSound() void
        #addedToWorld(World) void
        +updateAppearance() void
        +removeItem(Item) void
        +setInvisible(boolean) void
        +getInventorys() List~List_ItemData~
        +setInventorys(List~List_ItemData~) void
        -createItem(ItemData) Item
        -getItemPackages() List~String~$
        -collectSubPackages(File, String, List~String~) void$
        -updateActiveWeapon() void
        +gainXP(int) void
        -zeigeLevelUp(int, int) void
        +ladeFortschritt(int, int, int, int) void
        +getCurrentXP() int
        +getCurrentLevel() int
        +getXpToNextLevel() int
        +getBonusDamage() int
        +getMaxLife() int
        +getMaxItems() int
        +getItems() Item[]
        -setActiveSlot(int) void
        +getActiveSlot() int
        +getHeadArmor() Item
        +getChestArmor() Item
        +hasChestArmor() boolean
        +hasHeadArmor() boolean
        +setHeadArmor(Item) void
        +setChestArmor(Item) void
        +setInvisibleTimer(int) void
        +isInvisible() boolean
    }
    class Skeleton {
        +Skeleton(int)
        +act() void
        #onDeath() void
    }
    class Zombie {
        +Zombie(int)
        +act() void
        #onDeath() void
    }
    DamageableActor <|-- BaseMonster
    ASharpPathfinding <|.. BaseMonster
    MovingActor <|-- DamageableActor
    BaseMonster <|-- Gnome
    Hitting <|.. Gnome
    Actor <|-- ImprovedActor
    ImprovedActor <|-- MovingActor
    BaseMonster <|-- Orc
    Hitting <|.. Orc
    DamageableActor <|-- Player
    BaseMonster <|-- Skeleton
    Hitting <|.. Skeleton
    BaseMonster <|-- Zombie
    Hitting <|.. Zombie
```

---

## Items und Waffen

*Pakete: `items, items.waffen, items.util` · 17 Klassen*

`Item` ist die Basis für alles Aufsammelbare, `Waffen` erweitert sie um Schaden, Krit-Chance und Reichweite. Die Interfaces `Pickable` und `Useable` legen fest, was aufgehoben und was benutzt werden kann.

```mermaid
classDiagram
    direction TB
    class Actor {
        <<Greenfoot>>
    }
    class Arrow {
        -int speed
        -int damage
        -int moveDelay
        -int delayCounter
        -boolean isFlying
        +Arrow(int, int)
        +Arrow()
        +act() void
    }
    class Bow {
        -GreenfootImage normalImage
        -GreenfootImage loadedImage
        +Bow(int, int)
        +Bow()
        +hasArrows(Player) boolean
        -consumeArrow(Player) boolean
        +shoot(Player, int) void
        +hit(MovingActor) boolean
    }
    class BowSprite {
        -Bow bow
        -GreenfootImage normalImage
        -GreenfootImage loadedImage
        -int range
        -boolean isCharging
        +BowSprite(Bow)
        +update(Player) void
        -getClosestMonster(Player) BaseMonster
    }
    class Item {
        <<abstract>>
        +Rarity rarity
        #ItemText currentHoverer
        #Item()
        #Item(Rarity)
        +onTake(Actor) Item
        +onPut(int, int) void
        +use(Player) void
        +checkHover() void
    }
    class ItemData {
        +int slot
        +String classname
        +String rarity
    }
    class ItemTyp {
        <<enumeration>>
        +LeatherArmor$
        +SWORD$
        +MESSER$
        +STOCK$
        +BOW$
        +ARROW$
        +HEALTH_POTION$
        +int gewicht
        -ItemTyp(int)
        +erstelleItem() Item
        +zufällig() ItemTyp$
    }
    class Material {
        -String name
        +Material(String)
        +getName() String
    }
    class Messer {
        -int SIZE$
        +Messer(int, int)
        +Messer()
    }
    class OnHover {
        <<interface>>
        +hovering() GreenfootImage*
        +getOnHoverFields() Field[]*
    }
    class Pickable {
        <<interface>>
    }
    class Rarity {
        <<enumeration>>
        +COMMON$
        +UNCOMMON$
        +RARE$
        +EPIC$
        +LEGENDARY$
        +double Chance
        +double Multiplier
        +Color Color
        -Random rng$
        -Rarity(double, double, Color)
        +setRarity() Rarity$
        +makeRare(int, Rarity) int$
    }
    class SlotType {
        <<enumeration>>
        +GENERIC$
        +HELMET$
        +CHESTPLATE$
        +MATERIAL$
        -String imagePath
        -SlotType(String)
        +getImagePath() String
    }
    class Stock {
        -int SIZE$
        +Stock(int, int)
        +Stock()
    }
    class Sword {
        -int SIZE$
        +Sword(int, int)
        +Sword()
    }
    class TestItem {
        +Rarity rarity
        +TestItem()
        +act() void
        +showMultiplier() void
    }
    class Useable {
        <<interface>>
        ~use(Player) void*
    }
    class Waffen {
        <<abstract>>
        -int damage
        -int kritChance
        -int maxDistance
        -int KRIT_FAKTOR$
        -int STANDARD_KRIT_CHANCE$
        +Waffen(int, int)
        +Waffen(int, int, int)
        +hit(MovingActor) boolean
        +getNextX(int) int
        +getNextY(int) int
        +getDamage() int
        +getKritChance() int
        +getMaxDistance() int
        +setDamage(int) void
        +setDistance(int) void
    }
    Item <|-- Arrow
    Waffen <|-- Bow
    Useable <|.. Bow
    Actor <|-- BowSprite
    ImprovedActor <|-- Item
    Useable <|.. Item
    OnHover <|.. Item
    Item <|-- Material
    Waffen <|-- Messer
    Waffen <|-- Stock
    Waffen <|-- Sword
    Item <|-- TestItem
    Pickable <|.. TestItem
    Item <|-- Waffen
```

---

## Rüstung, Verbrauchsgegenstände und Materialien

*Pakete: `items.armor, items.misc, Material` · 14 Klassen*

Die sieben Rüstungsklassen, die essbaren beziehungsweise trinkbaren Gegenstände und die Materialien zum Aufwerten an der Werkbank.

```mermaid
classDiagram
    direction TB
    class Apple {
        -int healAmount
        -int SIZE$
        +Apple()
        +Apple(Rarity)
        +use(Player) void
        +act() void
    }
    class Armor {
        -String slotType
        -String material
        +Armor(String, String)
        +getSlotType() String
        +getMaterial() String
        +act() void
    }
    class Cookie {
        -int healAmount
        -int SIZE$
        +Cookie()
        +Cookie(Rarity)
        +use(Player) void
        +act() void
    }
    class Gold {
        -int SIZE$
        +Gold()
    }
    class GoldArmor {
        +GoldArmor(String)
        +GoldArmor()
    }
    class GoldHelmet {
        +GoldHelmet(String)
        +GoldHelmet()
    }
    class HealthPotion {
        -int HEAL_AMOUNT
        -int SIZE$
        +HealthPotion()
        +HealthPotion(Rarity)
        +use(Player) void
        +act() void
    }
    class HoneyBottle {
        -int invisibleDuration
        -int SIZE$
        +HoneyBottle()
        +HoneyBottle(Rarity)
        +use(Player) void
        +act() void
    }
    class Iron {
        -int SIZE$
        +Iron()
    }
    class IronArmor {
        +IronArmor(String)
        +IronArmor()
    }
    class IronHelmet {
        +IronHelmet(String)
        +IronHelmet()
    }
    class LeatherArmor {
        +LeatherArmor(String)
        +LeatherArmor()
    }
    class LeatherHelmet {
        +LeatherHelmet(String)
        +LeatherHelmet()
    }
    class UnstablePotion {
        -int SIZE$
        +UnstablePotion()
        +UnstablePotion(Rarity)
        +use(Player) void
        +act() void
    }
    Item <|-- Apple
    Item <|-- Armor
    Item <|-- Cookie
    Material <|-- Gold
    Armor <|-- GoldArmor
    Armor <|-- GoldHelmet
    Item <|-- HealthPotion
    Item <|-- HoneyBottle
    Material <|-- Iron
    Armor <|-- IronArmor
    Armor <|-- IronHelmet
    Armor <|-- LeatherArmor
    Armor <|-- LeatherHelmet
    Item <|-- UnstablePotion
```

---

## Inventar und Upgrade-UI

*Pakete: `ui (Inventar)` · 6 Klassen*

Slots, Rucksack-Darstellung, Drag & Drop sowie die Upgrade-Oberfläche der Werkbank.

```mermaid
classDiagram
    direction TB
    class Actor {
        <<Greenfoot>>
    }
    class GhostItem {
        +GhostItem(GreenfootImage)
        +act() void
    }
    class InventoryOverlay {
        +InventoryOverlay()
    }
    class InventorySlot {
        -SlotType slotType
        -Item item
        #GreenfootImage baseImage
        #GreenfootImage glowingImage
        -boolean isSelected
        -int slotPixelWidth
        -int slotPixelHeight
        -InventorySlot draggedSlot$
        -ui.GhostItem ghost$
        -boolean isItemHidden
        -boolean locked
        -ItemText currentHoverer
        +InventorySlot()
        +InventorySlot(int, int)
        +InventorySlot(int, int, SlotType)
        +InventorySlot(Item)
        +setItem(Item) void
        +getItem() Item
        +setSelected(boolean) void
        +isSelected() boolean
        #updateImage() void
        -drawLockIcon(GreenfootImage) void
        +setLocked(boolean) void
        +isLocked() boolean
        -handleDragAndDrop() void
        -passtInSlot(ui.worlds.Backpack, InventorySlot, Item) boolean
        -swapItems(InventorySlot, InventorySlot) void
        +canAcceptItem(Item) boolean
        +act() void
    }
    class InventoryVisualizer {
        -InventorySlot[] slots
        -Item[] inventory
        -int slotPixelWidth
        -int slotPixelHeight
        +InventoryVisualizer(Item[], int, int)
        +act() void
        #addedToWorld(World) void
        -update() void
        +forceSyncArray() void
        +containsSlot(InventorySlot) boolean
        +removeSelf() void
        -checkSlot() void
    }
    class UpgradeButton {
        -UpgradeSlot armorSlot
        -UpgradeSlot materialSlot
        -UpgradeSlot outputSlot
        -GreenfootImage UpgradeButton
        -GreenfootImage UpgradeButtonGlowing
        -boolean isScaled
        +UpgradeButton(UpgradeSlot, UpgradeSlot, UpgradeSlot)
        +act() void
        -versucheUpgrade() void
        -erfolgreichesUpgrade(Item) void
        -mouseHover() void
    }
    class UpgradeSlot {
        -String slotType
        +UpgradeSlot(String)
        -loadImage() void
        +getSlotType() String
    }
    Item <|-- GhostItem
    UI <|-- InventoryOverlay
    Actor <|-- InventorySlot
    Actor <|-- InventoryVisualizer
    Actor <|-- UpgradeButton
    InventorySlot <|-- UpgradeSlot
```

---

## HUD und Anzeigen

*Pakete: `ui (HUD)` · 11 Klassen*

Alles, was während des Spiels eingeblendet wird: Lebens- und XP-Leiste, Schadenszahlen, Level-Up-Meldung, Tooltips und der Pause-Screen.

```mermaid
classDiagram
    direction TB
    class Actor {
        <<Greenfoot>>
    }
    class Clickable {
        <<interface>>
        +onClick() UI*
        ~playClickSound() void*
    }
    class DamageNumber {
        -long LEBENSDAUER_MS$
        -double AUSBLENDEN_AB$
        -Color FARBE_NORMAL$
        -Color FARBE_KRITISCH$
        -float GROESSE_NORMAL$
        -float GROESSE_KRITISCH$
        -long startZeit
        -long endeZeit
        +DamageNumber(int, boolean)
        +act() void
    }
    class DarkFilter {
        +DarkFilter(int, int)
    }
    class Explosion {
        -int moveDelay
        -int delayCounter
        +Explosion(int, int)
        +act() void
    }
    class Healthbar {
        -Player player
        -int WIDTH$
        -int HEIGHT$
        -GreenfootImage barImage
        -int lastStep
        -int lastLife
        +Healthbar(Player)
        +act() void
        -update() void
    }
    class ItemText {
        -Actor parent
        +ItemText(GreenfootImage, Actor)
        +act() void
    }
    class LevelUpMessage {
        -long LEBENSDAUER_MS$
        -double AUSBLENDEN_AB$
        -Color GOLD$
        -float GROESSE_TITEL$
        -float GROESSE_BONUS$
        -long startZeit
        -long endeZeit
        +LevelUpMessage(int, int)
        +act() void
    }
    class PauseScreen {
        +PauseScreen(int, int)
    }
    class Settings {
        +String upKey$
        +String downKey$
        +String leftKey$
        +String rightKey$
        +String takeItem$
        +String putItem$
        +String inventoryToggle$
        +String useItem$
        +String pauseKey$
        +String attack$
        +boolean soundOn$
        +boolean musicOn$
        +int volume$
        +isPressed(String) boolean$
    }
    class UI
    class XPBar {
        -Player player
        -int WIDTH$
        -int HEIGHT$
        -int lastStep
        -int lastLevel
        +XPBar(Player)
        +act() void
        -update() void
    }
    Actor <|-- DamageNumber
    Actor <|-- DarkFilter
    Actor <|-- Explosion
    UI <|-- Healthbar
    Actor <|-- ItemText
    Actor <|-- LevelUpMessage
    DarkFilter <|-- PauseScreen
    Actor <|-- UI
    UI <|-- XPBar
```

---

## Buttons

*Pakete: `ui.buttons, ui.Buttons` · 10 Klassen*

Alle klickbaren Schaltflächen aus Hauptmenü, Einstellungen, Pause-Menü und Game-Over-Screen.

```mermaid
classDiagram
    direction TB
    class KeyButton {
        -String currentKey
        -boolean waitingForInput
        -int blinkTimer
        -String action
        -GreenfootImage keyImage
        -String lastText
        +KeyButton(String, String)
        +act() void
        -checkClick() void
        -checkInput() void
        -saveKey(String) void
        +updateImage(GreenfootImage) void
    }
    class LoadGameButton {
        -GameStarter gameStarter
        -GreenfootImage loadGameButton
        -GreenfootImage loadGameButtonGlowing
        -boolean isScaled
        +LoadGameButton(GameStarter)
        +act() void
        -mouseHover() void
    }
    class PauseButtons {
        <<abstract>>
        +PauseButtons(String)
        +act() void
        #onPauseClick() void
    }
    class SaveGameButton {
        -GameStarter gameStarter
        -GreenfootImage saveGameButton
        -GreenfootImage saveGameButtonGlowing
        -boolean isScaled
        -int textTimer
        +SaveGameButton(GameStarter)
        +act() void
        -save() void
        -mouseHover() void
    }
    class SettingsButton {
        -GameStarter gameStarter
        -GreenfootImage settingsButton
        -GreenfootImage settingsButtonGlowing
        -boolean isScaled
        +SettingsButton(GameStarter)
        +act() void
        -mouseHover() void
    }
    class StandardButton {
        -GreenfootImage base
        +String text
        +StandardButton(String)
        +updateImage(GreenfootImage) void
    }
    class StartButton {
        -GameStarter gameStarter
        -GreenfootImage StartButton
        -GreenfootImage StartButtonGlowing
        -boolean isScaled
        +StartButton(GameStarter)
        +act() void
        +onClick() UI
        -mouseHover() void
    }
    class restartButton {
        -GameStarter gameStarter
        -GreenfootImage restartGameButton
        -GreenfootImage restartGameButtonGlowing
        -boolean isScaled
        +restartButton(GameStarter)
        +act() void
        -mouseHover() void
    }
    class settingPauseButton {
        -GreenfootImage settingButton
        -GreenfootImage settingButtonGlowing
        -boolean isScaled
        +settingPauseButton()
        +act() void
        -mouseHover() void
    }
    class tryAgainButton {
        -GameStarter gameStarter
        -GreenfootImage normal
        -GreenfootImage leuchtend
        +tryAgainButton(GameStarter)
        +act() void
        -schneidePlakette() GreenfootImage
        -mouseHover() void
    }
    StandardButton <|-- KeyButton
    UI <|-- LoadGameButton
    UI <|-- PauseButtons
    PauseButtons <|-- SaveGameButton
    UI <|-- SettingsButton
    UI <|-- StandardButton
    UI <|-- StartButton
    Clickable <|.. StartButton
    PauseButtons <|-- restartButton
    PauseButtons <|-- settingPauseButton
    UI <|-- tryAgainButton
```

---

## Worlds und Hilfsklassen

*Pakete: `ui.worlds, util` · 9 Klassen*

Die Greenfoot-Worlds neben dem Dungeon (Hauptmenü, Einstellungen, Rucksack, Game-Over) und die projektweiten Hilfsklassen für Sound, Fenster, Schrift und Bilder.

```mermaid
classDiagram
    direction TB
    class World {
        <<Greenfoot>>
    }
    class GreenfootImage {
        <<Greenfoot>>
    }
    class Backpack {
        -Item[] backpackItems
        -InventorySlot[] slots
        -World previousWorld
        -boolean eWasDown
        -boolean escWasDown
        -boolean rWasDown
        -InventorySlot headSlot
        -InventorySlot chestSlot
        -UpgradeSlot upgradeSlot1
        -UpgradeSlot upgradeSlot2
        -UpgradeSlot upgradeSlot3
        -Player player
        -boolean tableMode
        +Backpack(Player, Item[], Item[], World, boolean)
        +act() void
        -updateBackpackSlots() void
        -raeumeUpgradeSlotsAus() void
        -gibZurueck(UpgradeSlot) void
        -updateArmorSlots() void
        +isTableMode() boolean
        +getPlayer() Player
        +getChestSlot() InventorySlot
        +getHeadSlot() InventorySlot
        +getUpgradeSlot1() InventorySlot
        +getUpgradeSlot2() InventorySlot
        +getUpgradeSlot3() InventorySlot
    }
    class FontManager {
        -String FONT_DIR$
        +get(String, float) Font$
        +getMinecraft(float) Font$
        +getMinecraftBold(float) Font$
        -loadBase(String) Font$
        +renderText(String, Font, java.awt.Color) GreenfootImage$
    }
    class GameOverScreen {
        +GameOverScreen(GameStarter)
        -baueHintergrund() GreenfootImage
    }
    class ImprovedGreenfootImage {
        -int rotation
        +ImprovedGreenfootImage(String)
        +ImprovedGreenfootImage(int, int)
        +ImprovedGreenfootImage(GreenfootImage)
        +ImprovedGreenfootImage(String, int, Color, Color)
        +ImprovedGreenfootImage(String, int, Color, Color, Color)
        +rotate(int) void
        +drawString(String, int, int) void
        +drawImage(GreenfootImage, int, int) void
        +drawLine(int, int, int, int) void
        +drawOval(int, int, int, int) void
        +drawPolygon(int[], int[], int) void
        +drawRect(int, int, int, int) void
    }
    class LoadFrame {
        -boolean upWasDown
        -boolean downWasDown
        -boolean enterWasDown
        -boolean escWasDown
        +act() void
    }
    class MainMenu {
        -GameStarter gameStarter
        -StartButton startButton
        -LoadGameButton loadGameButton
        -SettingsButton settingsButton
        -List~File~ saves
        -int saveIndex
        -LoadFrame loadFrame
        -boolean loadSelectOpen
        +MainMenu(GameStarter)
        +showLoadSelect() void
        -hideLoadSelect() void
        -cycleSave(int) void
        -loadSelected() void
        -renderLoadFrame() void
        -readSaves() List~File~
    }
    class SettingsWorld {
        +boolean waitingForKey$
        +String switchKey$
        +boolean blinkActivated
        -World lastWorld
        +isBlinkActivated() boolean
        +setBlinkActivated(boolean) void
        +SettingsWorld()
        +SettingsWorld(World)
        +act() void
    }
    class SoundManager {
        -GreenfootSound bgMusic$
        +play(String) void$
        +play(String, int) void$
        +startMusic() void$
        +stopMusic() void$
        +toggleMusic() void$
    }
    class WindowSizeManager {
        -boolean pinned$
        -WindowSizeManager()
        +enforce() void$
        -tryEnforce() void$
        -pinWorldDisplaySize(Scene) void$
        -findWorldDisplay(Node) WorldDisplay$
        -findStage() Stage$
    }
    World <|-- Backpack
    World <|-- GameOverScreen
    GreenfootImage <|-- ImprovedGreenfootImage
    UI <|-- LoadFrame
    World <|-- MainMenu
    World <|-- SettingsWorld
```

---
