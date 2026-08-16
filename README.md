# The Dungeon: Sign of the Moon

Ein Top-Down Action-RPG / Dungeon-Crawler, entwickelt mit **Greenfoot** (Java) als Schulprojekt.
Acht Personen, Projektlaufzeit **2026-04-17 bis 2026-08-16**, aktueller Stand: 87 Java-Klassen
mit rund 5.350 Zeilen Code.

---

## Inhalt

- [Grundidee](#grundidee)
- [Story](#story)
- [Funktionen des Spiels](#funktionen-des-spiels)
- [Steuerung](#steuerung)
- [Installation und Start](#installation-und-start)
- [Projektstruktur](#projektstruktur)
- [UML-Klassendokumentation](#uml-klassendokumentation)
- [Aufteilung der Arbeit](#aufteilung-der-arbeit)
- [KI-Einsatz](#ki-einsatz)

---

## Grundidee

*The Dungeon: Sign of the Moon* ist ein **Top-Down Action-RPG** im Stil eines Dungeon-Crawlers.
Man bewegt sich durch **prozedural generierte** Räume und Korridore, bekämpft Monster im Nah-
und Fernkampf, sammelt Loot aus Truhen, verwaltet sein Inventar und steigt über Treppen immer
tiefer in den Dungeon hinab.

Jedes Stockwerk wird aus einem Seed neu erzeugt, es gibt also kein festes Level-Design und
keine feste Länge: Ziel ist es, **so tief wie möglich zu kommen**, bevor man stirbt.
Mit jedem besiegten Monster gibt es Erfahrung, mit jedem Level-Up mehr Leben und Schaden.

---

## Story

> Unter dem blassen Schein des „Mondes der Zeichen" (*Sign of the Moon*) haben sich die
> verfallenen Verliese des alten Königreichs erneut geöffnet. Uralte Kreaturen, Zombies,
> Gnome, Orks und Skelette, sind aus der Tiefe erwacht und bedrohen das Umland.
>
> Als mutiger Abenteurer steigst du in die Tiefen des Dungeons hinab. Dein Ziel ist es,
> die Horden der Finsternis zu besiegen, mächtige Rüstungen und Waffen zu bergen und so
> tief zu kommen wie möglich.

---

## Funktionen des Spiels

**Welt und Gegner**

- Prozedurale Dungeon-Generierung aus einem Seed: Räume, Korridore, Wände, Eingangs- und
  Ausgangstreppe pro Stockwerk
- Vier Monsterarten: **Gnome, Ork, Zombie, Skelett**, mit eigenen Sprites und Lauf-/Angriffs-Animationen
- Gegner verfolgen den Spieler per **A\*-Pathfinding** durch Räume und Korridore, inklusive
  Agro- und Leash-Radius

**Kampf**

- Nahkampf (Schwert, Messer, Stock) und Fernkampf (Bogen + Pfeile mit Flug- und Explosionsanimation)
- Schadenswerte, **Krit-Chance** und Reichweite pro Waffe, aufsteigende Schadenszahlen über dem Ziel
- Lebensanzeige, Todes-Sounds pro Monsterart, Game-Over-Screen mit „Try Again"

**Items und Ausrüstung**

- Truhen mit zufälligem Loot, gewichtete Drop-Wahrscheinlichkeiten pro Item-Typ
- Waffen, 7 Rüstungsklassen (Leder / Eisen / Gold, jeweils Helm und Brust) sowie Verbrauchsgegenstände
  (Heiltrank, Apfel, Keks, Honigflasche, instabiler Trank)
- Angelegte Rüstung verändert das **Aussehen des Spielers**
- **Seltenheitsstufen** (Rarity) mit Farbcodierung und Tooltips beim Drüberfahren
- Inventar mit Drag & Drop, Hotbar-Slots und separatem Rucksack

**Fortschritt**

- Erfahrungspunkte und Level-Ups mit Bonus auf Leben und Schaden, dazu eine Level-Up-Einblendung
- **Werkbank (`UpgradeTable`)**: Ausrüstung lässt sich mit Materialien (Eisen, Gold) aufwerten,
  das Upgraden funktioniert ausschließlich an der Werkbank
- **Speichern und Laden**: Spielstände liegen als JSON in `saves/` und enthalten Seed,
  Stockwerk, Inventar und Spielerwerte

**Drumherum**

- Hauptmenü, Einstellungsmenü, Pause-Screen (ESC), Game-Over-Screen
- Sound-Effekte und Hintergrundmusik, im Menü einzeln abschaltbar
- Fenster-, Maximieren- und Vollbild-Handling über einen eigenen `WindowSizeManager`

---

## Steuerung


| Taste / Aktion          | Funktion                                              |
| ----------------------- | ----------------------------------------------------- |
| **W / A / S / D**       | Bewegung (oben, links, unten, rechts)                 |
| **Maus 1 (Linksklick)** | Angriff / Waffe einsetzen                             |
| **Leertaste**           | Bogen spannen, beim Loslassen wird geschossen         |
| **1 bis 8**             | Aktiven Inventar-Slot wählen                         |
| **E**                   | Inventar öffnen / schließen                         |
| **T**                   | Gegenstand aufnehmen                                  |
| **P**                   | Gegenstand ablegen                                    |
| **F**                   | Gegenstand benutzen (z. B. Tränke, Essen)            |
| **R**                   | Truhe öffnen bzw. Werkbank benutzen (beim Berühren) |
| **ESC**                 | Pause-Menü öffnen / zurück                         |

### Einstellungen

Die Steuerung ist **nicht fest verdrahtet**: Im **Einstellungsmenü** (Hauptmenü → Settings oder
über das Pause-Menü) lässt sich jede der folgenden Belegungen frei ändern. Dazu klickt man den
Button neben der Aktion an und drückt anschließend die gewünschte Taste.

Belegbar sind:

- **Bewegung**: forward, backward, left, right (Standard W / S / A / D)
- **Attack** (Standard linke Maustaste, Maustasten sind ebenfalls zulässig)
- **Take Item** (T), **Put Item** (P), **Use Item** (F), **Inventory** (E)

Ebenfalls im Einstellungsmenü:

- **Sound** an / aus
- **Musik** an / aus

Nicht belegbar und fest im Spiel verankert sind **ESC** (Pause / zurück), **R** (Truhe und Werkbank),
die **Zahlentasten 1 bis 8** (Slot-Auswahl) und die **Leertaste** (Bogen).

> Hinweis: Die Einstellungen gelten für die laufende Sitzung, sie werden aktuell **nicht**
> im Spielstand mitgespeichert.

---

## Installation und Start

### Voraussetzungen

- [Greenfoot 3.7.1](https://www.greenfoot.org/download), wird als Bibliothek eingebunden
  (inklusive der mitgelieferten JavaFX-Dateien)
- JDK 17 oder höher
- IntelliJ IDEA

### Schritte

1. **Repository klonen**

   ```bash
   git clone <repo-url>
   cd RPGProject
   ```
2. **In IntelliJ öffnen** über *File → Open* und den Ordner `RPGProject` auswählen.
   Es gibt bewusst kein Build-Tool, das Projekt läuft als reines Java-Projekt.
3. **Bibliotheken prüfen** unter *File → Project Structure → Libraries*:

   - `GreenfootLibs` zeigt auf die lokale Greenfoot-Installation
     (Standard unter Windows: `C:\Program Files\Greenfoot\lib` und `...\lib\javafx\lib`).
     Wer Greenfoot woanders installiert hat, muss diese Pfade einmalig anpassen.
   - `google.code.gson` liegt bereits im Repository unter `lib/` und wird für das
     Speichern und Laden gebraucht.
4. **`src/` als Sources-Root** markieren, falls IntelliJ das nicht automatisch erkennt.
5. **Starten** über die mitgelieferte Run-Konfiguration:

   - **Windows:** `Run Scenario`
   - **Linux:** `Linux_Scenario` (erwartet Greenfoot unter `/opt/greenfoot` und JavaFX unter `/opt/javafx-11`)

   Beide starten `greenfoot.export.GreenfootScenarioApplication` mit den nötigen
   JavaFX-Modulparametern.

---

## Projektstruktur

```
RPGProject/
├── src/
│   ├── core/          GameStarter: Einstiegspunkt, Seed, Level-Wechsel, Speichern/Laden
│   ├── world/         DungeonLevel: prozedurale Generierung, Spawning, Kampf-Hooks
│   ├── entities/      Player
│   │   ├── base/      ImprovedActor, MovingActor, DamageableActor, BaseMonster
│   │   ├── enemies/   Gnome, Orc, Zombie, Skeleton
│   │   └── util/      ASharpPathfinding, Direction, Hitting
│   ├── blocks/        Wall, Rock, Chest, UpgradeTable, Entrance, Exit
│   ├── items/         Item, Waffen, Material
│   │   ├── waffen/    Sword, Messer, Stock, Bow, Arrow, BowSprite
│   │   ├── armor/     Armor + 6 Rüstungsteile
│   │   ├── misc/      HealthPotion, Apple, Cookie, HoneyBottle, UnstablePotion
│   │   └── util/      ItemTyp, Rarity, SlotType, OnHover, ItemData, Pickable, Useable
│   ├── Material/      Gold, Iron
│   ├── ui/            Inventar, Healthbar, XPBar, DamageNumber, LevelUpMessage, Tooltips
│   │   ├── buttons/   Menü-Buttons
│   │   ├── Buttons/   Pause- und Save-Buttons
│   │   └── worlds/    MainMenu, SettingsWorld, Backpack, GameOverScreen
│   └── util/          SoundManager, WindowSizeManager, FontManager, ImprovedGreenfootImage
├── images/            Sprites, Tiles, UI-Grafiken
├── sounds/            Effekte und Hintergrundmusik
├── saves/             Spielstände (JSON)
├── lib/               gson (im Repository enthalten)
└── .idea/runConfigurations/   Run Scenario (Windows), Linux_Scenario
```

---

## UML-Klassendokumentation

Klassendiagramme aller 90 Klassen und Interfaces, jeweils mit **Feldern, Methoden,
Sichtbarkeiten und Vererbung**. Weil ein einzelnes Diagramm über das ganze Projekt unlesbar
wäre, ist die Dokumentation in acht thematische Diagramme aufgeteilt: Kern und Spielwelt,
Spieler und Gegner, Items und Waffen, Rüstung und Materialien, Inventar, HUD, Buttons sowie
Worlds und Hilfsklassen.

Die Diagramme sind in **Mermaid** geschrieben und werden von GitHub direkt im Browser
gerendert, zum Anschauen wird also kein zusätzliches Programm gebraucht.

Die Diagramme sind **nicht von Hand geschrieben**, sondern von einem Skript erzeugt, das die
Java-Quellen in `src/` einliest und daraus Klassen, Vererbung, Felder und Methodensignaturen
ausliest. Das hat zwei Gründe: bei 90 Klassen wäre Handarbeit weder in vertretbarer Zeit zu
schaffen noch fehlerfrei, und so lässt sich die Dokumentation nach Codeänderungen jederzeit
neu erzeugen, statt zu veralten.

→ [**UML.md**](UML.md)

---

## Aufteilung der Arbeit

Wer im Team welchen Teil des Spiels gebaut hat, ist vollständig aus dem Git-Verlauf ausgewertet:
Aufteilung nach Feature-Bereichen, die Beiträge jeder Person im Detail, überschriebene und nie
gemergte Arbeit sowie die Rohdaten (Blame, geschriebene Zeilen, Merges, Assets). Die prozentuale
Aufteilung am Ende ist im Team abgesprochen.

→ [**Aufteilung.md**](Aufteilung.md)

---

## KI-Einsatz

Womit wir KI im Projekt gearbeitet haben und womit nicht: hauptsächlich als Ersatz für die
Java- und Greenfoot-Dokumentation und als Hilfe beim Einstieg in Git und GitHub. Offengelegt
sind dort auch die zwei Stellen, an denen KI eigenständig Code geschrieben hat, sowie der
Umgang mit Grafiken und Assets.

→ [**KI.md**](KI.md)
