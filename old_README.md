# [Projektname – TBD] · Top-Down Dungeon Roguelike

Ein top-down Roguelike-RPG mit prozedural generierten Dungeons, entwickelt mit der
Greenfoot-Bibliothek (Java) als Schulprojekt.

---
## ❓ Git/GitHub Hilfe
→ [Hier klicken für den KI-Tutor-Prompt](GITHUB-HELP.md)
---

## Voraussetzungen

- [Greenfoot 3.7.1](https://www.greenfoot.org/download) – wird als Bibliothek eingebunden
- Java 17 oder höher (JDK, nicht nur JRE)
- IntelliJ IDEA (Community oder Ultimate)

---

## Setup-Anleitung

### 1. Repository klonen

```bash
git clone <repo-url>
cd RPGProject
```

### 2. Projekt in IntelliJ IDEA öffnen

1. **File → Open** → den Projektordner `RPGProject` auswählen.
2. IntelliJ erkennt kein Build-Tool, das ist so gewollt. Als **plain Java project** öffnen

### 3. Greenfoot Bibliothek einbinden

Das Projekt braucht `greenfoot.jar` aus der lokalen Greenfoot-Installation als Library.

1. **File → Project Structure → Libraries → + (Add) → Java**
2. Greenfoot-JAR auswählen. Typische Pfade:
   - **Linux:** `/usr/share/greenfoot/lib/greenfoot.jar`
   - **Windows:** `C:\Program Files\Greenfoot\lib\greenfoot.jar`
   - **macOS:** `/Applications/Greenfoot.app/Contents/Resources/Java/greenfoot.jar`
3. Die Library dem Modul `RPGProject` zuweisen → **OK**.

> Alternativ: die `greenfoot.jar` in den `lib/`-Ordner im Projektroot kopieren und
> dort als Library hinzufügen – dann funktioniert es bei allen Teammitgliedern
> ohne Pfad-Anpassung.

### 4. Source-Root konfigurieren

1. **File → Project Structure → Modules**
2. `src/` als **Sources**-Root markieren (Rechtsklick → Mark as: Sources Root).

### 5. Run-Konfiguration starten

Die Run-Konfiguration **"Run Game"** ist bereits im Repository enthalten
(`.idea/runConfigurations/Run_Game.xml`). Sie startet
`greenfoot.export.GreenfootScenarioApplication` mit dem Projektordner als
Working Directory.

1. Rechts oben in IntelliJ **"Run Game"** aus dem Dropdown auswählen.
2. **Run** (▶) drücken.

---

## Projektstruktur

```
RPGProject/
├── src/                                  ← Source-Root (IntelliJ)
│   ├── core/
│   │   └── GameStarter.java              ← Einstiegspunkt; verwaltet Seed, Level-History und Weltenwechsel
│   ├── world/
│   │   ├── DungeonLevel.java             ← Greenfoot-World; prozedurale Dungeon-Generierung (Räume, Korridore)
│   │   ├── Entrance.java                 ← Eingang zum vorherigen Level
│   │   └── Exit.java                     ← Ausgang zum nächsten Level
│   ├── entities/
│   │   ├── Player.java                   ← Spielersteuerung, Inventar, Bewegung
│   │   ├── base/
│   │   │   ├── ImprovedActor.java        ← Actor mit verbessertem Image-Handling (HiDPI-fähig)
│   │   │   ├── MovingActor.java          ← Kollisionsgeprüfte Bewegung (canMove / move)
│   │   │   ├── DamageableActor.java      ← HP-System, takeDamage, onDeath
│   │   │   └── BaseMonster.java          ← Agro-/Leash-Radius, A*-Pathfinding-Integration
│   │   ├── enemies/
│   │   │   ├── Gnome.java
│   │   │   └── Orc.java
│   │   └── util/
│   │       ├── ASharpPathfinding.java    ← A*-Interface für Gegner
│   │       ├── Direction.java            ← Enum für die vier Himmelsrichtungen
│   │       └── Hitting.java              ← Trefferlogik (Melee-Angriff)
│   ├── blocks/
│   │   ├── Block.java                    ← Basis-Klasse für alle platzierbaren Blöcke
│   │   ├── Wall.java                     ← Undurchdringbare Wand (Kollision)
│   │   ├── Rock.java                     ← Fels-Variante
│   │   └── Chest.java                    ← Truhe (interagierbar)
│   ├── items/
│   │   ├── Item.java                     ← Basis-Klasse für alle Items
│   │   ├── TestItem.java
│   │   └── util/
│   │       ├── ItemTyp.java              ← Enum der Item-Typen
│   │       ├── Pickable.java             ← Interface: onTake(Player)
│   │       └── Useable.java              ← Interface: onUse(Player)
│   ├── ui/
│   │   ├── UI.java                       ← Basis-Klasse für alle UI-Elemente
│   │   ├── MainMenu.java                 ← Hauptmenü-World (Start, Laden, Einstellungen)
│   │   ├── Clickable.java                ← Interface für klickbare UI-Elemente
│   │   ├── StartButton.java
│   │   ├── LoadGameButton.java
│   │   ├── SettingsButton.java
│   │   ├── Healthbar.java
│   │   ├── XPBar.java
│   │   ├── InventorySlot.java
│   │   └── InventoryVisualizer.java      ← Zeigt das Spieler-Inventar im HUD an
│   └── util/
│       └── ImprovedGreenfootImage.java   ← Erweiterte GreenfootImage-Hilfsmethoden
├── assets/
│   ├── images/                           ← Sprites, Tilesets, Menü-Grafiken
│   └── sounds/                           ← Soundeffekte, Musik
├── editor/                               ← (in Entwicklung) Level-Editor
├── lib/                                  ← Optional: greenfoot.jar hier ablegen (s. Setup)
├── .idea/
│   └── runConfigurations/
│       └── Run_Game.xml                  ← Vorgefertigte Run-Konfiguration (committed)
└── project.greenfoot                     ← Greenfoot-Szenario-Datei (committed)
```

---

## Architektur-Überblick

### Spielablauf

`GameStarter` ist der Einstiegspunkt, eine winzige Greenfoot-World, die man nie zu sehen
bekommt.  Darin liegen der `Random`-Seed, eine Liste der vergangenen Level und das aktuelle Level.

```
GameStarter
  ├── mainMenu()        → wechselt zu MainMenu-World
  ├── start()           → erstellt erstes DungeonLevel
  ├── RenderNextWorld() → speichert aktuelles Level, erstellt nächstes
  └── setSeed(seed, n)  → lädt einen gespeicherten Stand (Seed + Level-Index)
```

### Entity-Hierarchie

```
Actor (Greenfoot)
└── ImprovedActor          ← verbessertes Image-Handling
    └── MovingActor        ← canMove / kollisionsgeprüfte Bewegung
        └── DamageableActor  ← HP, takeDamage, onDeath
            ├── Player       ← WASD-Steuerung, Inventar (T = nehmen, P = ablegen)
            └── BaseMonster  ← Agro/Leash, A*-Pathfinding
                ├── Gnome
                └── Orc

ImprovedActor
└── Block              ← platzierbarer Welt-Block
    ├── Wall
    ├── Rock
    └── Chest
```

### Dungeon-Generierung (DungeonLevel)

Jedes Level wird aus dem Seed heraus prozedural generiert:
1. Eingang (unten) und Ausgang (oben) werden platziert.
2. Ein zentraler Korridor verbindet Eingang und Ausgang.
3. Zufällige Räume (variable Größe) werden auf den freien Platz verteilt.
4. Wände umranden alle freien Flächen.
