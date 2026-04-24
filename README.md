# [Projektname – TBD] · Top-Down Dungeon Roguelike

> Schulprojekt · Fach Informatik · 5 Wochen

---

## Teammitglieder

| # | Name | Rolle |
|---|------|-------|
| 1 | [Name 1 TBD] | Architektur & Integration · Item-System |
| 2 | [Name 2 TBD] | Map-Generierung · Level-Editor (Swing/JavaFX) |
| 3 | [Name 3 TBD] | Rendering · Kamera · Animationen |
| 4 | [Name 4 TBD] | Gegner-KI (A\*) · Kampfsystem |
| 5 | [Name 5 TBD] | UI · Menüs · Speichern/Laden · Sound |

---

## Projektbeschreibung

[Kurze Beschreibung hier einfügen – 3–5 Sätze nach dem ersten Meeting.]

Ein top-down Roguelike-RPG mit prozedural generierten Dungeons, entwickelt mit der
Greenfoot-Bibliothek (Java) als Schulprojekt.

---

## Voraussetzungen

- [Greenfoot 3.x](https://www.greenfoot.org/download) – wird als Bibliothek eingebunden
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

> **Erste Anpassung nötig:** Sobald die World-Klasse des Spiels erstellt ist, muss
> der Klassenname ggf. als Argument der Run-Konfiguration angegeben werden.
> Wer das zuerst einrichtet, aktualisiert die `Run_Game.xml` und committed sie.

### Level-Editor starten

[Nach Woche 1 ausfüllen – Rolle 2 legt die Struktur fest.]

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

| Rolle | Zuständigkeit | Verantwortliche/r |
|-------|--------------|-------------------|
| Architektur & Integration · Items | Paketstruktur, Schnittstellen, Item-Logik | [Name 1] |
| Map-Generierung · Level-Editor | Prozeduraler Dungeon-Generator, Editor-Tool | [Name 2] |
| Rendering · Kamera · Animationen | Greenfoot-Render-Schicht, Sprite-Management | [Name 3] |
| Gegner-KI · Kampfsystem | A\*-Pathfinding, Kampf-Mechaniken | [Name 4] |
| UI · Menüs · Speichern/Laden · Sound | HUD, Hauptmenü, Serialisierung, Audio | [Name 5] |

---

