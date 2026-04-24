# [Projektname – TBD] · Top-Down Dungeon Roguelike

Ein top-down Roguelike-RPG mit prozedural generierten Dungeons, entwickelt mit der
Greenfoot-Bibliothek (Java) als Schulprojekt.

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
2. IntelliJ erkennt kein Build-Tool – das ist gewollt. Als **plain Java project** öffnen.

### 3. Greenfoot-Bibliothek einbinden

Das Projekt braucht `greenfoot.jar` aus der lokalen Greenfoot-Installation als Library.

1. **File → Project Structure → Libraries → + (Add) → Java**
2. Greenfoot-JAR auswählen. Typische Pfade:
   - **Linux:** `/usr/share/greenfoot/lib/greenfoot.jar`
   - **Windows:** `C:\Program Files\Greenfoot\lib\greenfoot.jar`
   - **macOS:** `/Applications/Greenfoot.app/Contents/Resources/Java/greenfoot.jar`
3. Die Library dem Modul `RPGProject` zuweisen → **OK**.

> Alternativ: die `greenfoot.jar` in den `lib/`-Ordner im Projektroot kopieren und
> dort als Library hinzufügen – dann funktioniert es bei allen Teammitgliedern
> ohne Pfad-Anpassung. Absprache im ersten Meeting.

### 4. Source-Root konfigurieren

1. **File → Project Structure → Modules**
2. `src/` als **Sources**-Root markieren (Rechtsklick → Mark as: Sources Root).
3. Für den Level-Editor: `editor/src/` als Sources-Root eines zweiten Moduls hinzufügen
   (Details nach Woche 1, wenn Rolle 2 die Editor-Struktur festgelegt hat).

### 5. Run-Konfiguration starten

Die Run-Konfiguration **"Run Game"** ist bereits im Repository enthalten
(`.idea/runConfigurations/Run_Game.xml`). Sie startet
`greenfoot.export.GreenfootScenarioApplication` mit dem Projektordner als
Working Directory.

1. Rechts oben in IntelliJ **"Run Game"** aus dem Dropdown auswählen.
2. **Run** (▶) drücken.

### Level-Editor starten

---

## Projektstruktur

```
RPGProject/
├── src/                        ← Source-Root (IntelliJ)
│   ├── core/                   ← Architektur & Integration (Rolle 1)
│   ├── entities/               ← Spielobjekte (gemeinsam)
│   ├── world/                  ← Welt, Tiles, Räume (Rolle 2 + 3)
│   ├── combat/                 ← Kampfsystem (Rolle 4)
│   ├── items/                  ← Item-System (Rolle 1)
│   ├── ui/                     ← HUD, Menüs (Rolle 5)
│   └── mapgen/                 ← Map-Generierung (Rolle 2)
├── editor/
│   └── src/                    ← Level-Editor (Rolle 2, eigenes Modul)
├── assets/
│   ├── images/                 ← Sprites, Tilesets
│   └── sounds/                 ← Soundeffekte, Musik
├── lib/                        ← Optional: greenfoot.jar hier ablegen (s. Setup)
├── .idea/
│   └── runConfigurations/
│       └── Run_Game.xml        ← Vorgefertigte Run-Konfiguration (committed)
└── project.greenfoot           ← Greenfoot-Szenario-Datei (committed)
```

---

## Rollen & Zuständigkeiten

---

