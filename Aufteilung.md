# Contributors: Wer hat was gemacht

Analyse des kompletten Git-Verlaufs (alle Branches, Stand `master` @ `415769a`, 2026-08-16).
Zeitraum: **2026-04-17 bis 2026-08-16** (~4 Monate).

Datengrundlage:

- 175 Commits auf `master` (128 inhaltlich + 47 Merges) + 6 nicht gemergte Feature-Branches
- `git blame` über alle 87 Java-Dateien in `src/` → **5.353 Zeilen** aktiver Code
- `git log --numstat` über Java-Quellen und Assets (PNG/WAV/GIF)

> **Wichtig zum Lesen:** `git blame` misst nur, wessen Zeilen *heute noch* im Code stehen.
> Wer eine Klasse angelegt hat, die später umgeschrieben wurde, taucht dort kaum noch auf.
> Deshalb steht in jedem Abschnitt zusätzlich, **was ursprünglich von wem kam** und
> **was inzwischen überschrieben oder gar nicht erst gemergt wurde**.

> **Hinweis zu KI-Code:** Zwei Stellen im Projekt stammen nicht von einem Teammitglied:
> `AStarPathfinding.java` (111 Zeilen) wurde vollständig von KI geschrieben, beim
> `WindowSizeManager.java` (104 Zeilen) kam der Swing/AWT-Teil von KI. Beides steht in `git blame`
> unter Nikolajs Namen, weil die Commits über seinen Account liefen; in den Tabellen unten ist
> das an den betroffenen Stellen vermerkt. Der vollständige Umgang des Teams mit KI-Werkzeugen
> steht in **`KI.md`**.

---

## 1. Gesamtaufteilung

Gewichtet nach **Aufwand und Schwierigkeit**, nicht nach reiner Zeilenzahl.
Integrations-, Merge-, Nach- und **Projektleitungsarbeit** ist mit eingerechnet
(siehe Abschnitt 1.2; ein erheblicher Teil davon erzeugt gar keine Codezeilen).


| # | **Anteil (Effort)** | Contributor | Git-Accounts                        | Commits                               | Blame-Anteil |
| - | ------------------- | ----------- | ----------------------------------- | ------------------------------------- | ------------ |
| 1 | **49,5 %**          | **Nikolaj** | `NorkelL`, `Nikolaj Lazic`          | 122 (119 auf`master`: 76 + 43 Merges) | 50,1 %       |
| 2 | **21 %**            | **Tom**     | `Vatar007`, `Tom`                   | 24 (21 auf`master`)                   | 31,4 %       |
| 3 | **7 %**             | **Len**     | `Len`, `LDK221`                     | 13                                    | 8,5 %\*      |
| 4 | **5 %**             | **Jonas**   | `Jonas45677L`                       | 12 (6 auf`master`)                    | 0,7 %\*      |
| 5 | **5,5 %**           | **Selma**   | `mvogt`                             | 10 (4 auf`master`)                    | 7,8 %        |
| 6 | **4 %**             | **Lennox**  | `FlyLennox`                         | 5                                     | 0,6 %        |
| 7 | **4 %**             | **Luca**    | `rollluca09`, `TuffLuca67`          | 3                                     | 0,4 %        |
| 8 | **4 %**             | **Noah**    | `goldfishi08`, `bf5szfzkfg-commits` | 4                                     | 0,5 %        |

Wer zwei Accounts hat, hat unter beiden committet; die Zahlen sind hier jeweils zusammengezogen.

\* Der `SoundManager` wurde von **Len und Jonas gemeinsam** geschrieben, aber nur von Len
commited. Git schreibt die 53 Zeilen deshalb komplett Len zu; im Effort-Anteil sind sie
**je zur Hälfte** angerechnet. Siehe Abschnitt 4.6.

```
Nikolaj  █████████████████████████████████████████████████▌  49,5 %
Tom      █████████████████████                                 21 %
Len      ███████                                                7 %
Selma    █████▌                                               5,5 %
Jonas    █████                                                  5 %
Lennox   ████                                                   4 %
Noah     ████                                                   4 %
Luca     ████                                                   4 %
```

**Die Prozentspalte ist mit dem Team abgestimmt und bleibt so stehen**, auch wo die neuen
Rohdaten inzwischen in eine andere Richtung zeigen (siehe 1.1, insbesondere Selma).

### 1.1 Warum die Effort-Gewichtung vom Blame-Anteil abweicht


| Contributor | Korrektur         | Begründung                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| ----------- | ----------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| Nikolaj     | 50,1 % →**49,5 %** | Sein Blame-Anteil ist seit der ersten Fassung von 53,1 % auf 50,1 % gefallen, weil Selmas Kampfsystem und Toms Upgrade-Mechanik viel neue Zeilenmasse gebracht haben. Die Effort-Zahl liegt trotzdem in derselben Größenordnung, weil dieselben zwei Effekte weiter gelten; 0,5 % hat er zusätzlich freiwillig an Luca abgegeben.**Dafür:** Projektleitung und GitHub-Organisation (Abschnitt 1.2), 43 von 47 Merges inkl. Konfliktauflösung, Lauffähigmachen fremder Branches, also Arbeit, die **null Codezeilen** erzeugt und in `git blame` unsichtbar ist; rein auf inhaltliche Zeilen gerechnet läge er bei 53,7 % (siehe 4.7). **Dagegen:** ein Teil seiner Zeilen entstand durch das Umschreiben fremder Klassen (zählt bei ihm, nicht beim Ersteller). |
| Tom         | 31,4 % →**21 %** | Drei Gründe: (1) ein großer Teil der Zeilenmasse ist repetitiv: 7 fast identische Rüstungsklassen, 5 Button-Klassen nach demselben Muster, zuletzt`Gold`/`Iron` als items.material-Klassen nach gleichem Schema; (2) mehrere seiner Beiträge entsprachen nicht den vereinbarten Projektprinzipien und mussten von Nikolaj umgebaut werden (Abschnitt 4.5); (3) sein Inventar-Branch war nicht mergebar und musste von Nikolaj rebased werden. Zeilen ≠ verwertbarer Aufwand.                                                                                                                                                                                                              |
| Len         | 8,5 % →**7 %**   | Die 53 Zeilen`SoundManager` schreibt git komplett ihm zu, obwohl er sie mit Jonas zusammen geschrieben hat; hier zur Hälfte an Jonas abgegeben.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| Selma       | 7,8 % →**5,5 %** | **Hier zeigen die Rohdaten inzwischen nach oben, nicht nach unten.** Ihr Kampfsystem (`DamageNumber`, `DamageableActor`, `Waffen`-Umbau, Monster-Angriffe) hat ihren Blame-Anteil von 2,3 % auf 7,8 % gehoben; gemessen liegt sie damit über den vereinbarten 5,5 %. Dagegen steht, dass der Branch nicht lauffähig gemergt werden konnte: Nikolaj musste Spawning, `BaseMonster` und Pathfinding nachziehen (`d6f0cae`, 140 geänderte Zeilen), bevor PR #76 gemergt werden konnte. Die 5,5 % sind der **mit dem Team abgesprochene** Wert und bleiben unverändert; die gemessene Zahl steht hier zur Transparenz daneben.                                                           |
| Jonas       | 0,7 % →**5 %**   | Drei unsichtbare Posten: (1) seine Hälfte am`SoundManager` läuft unter Lens Account; (2) seine Healthbar (6 Commits) wurde nie gemergt; (3) seine Tile- und Treppen-Designs sind Assets und tauchen in der Zeilenzählung nicht auf. Der Aufwand war in allen drei Fällen da.                                                                                                                                                                                                                                                                                                                                                                                                           |
| Lennox      | 0,6 % →**4 %**   | Die Waffen-Klassen kamen von ihm; die aktuellen Zeilen sind aber größtenteils erst von Tom (Waffenlogik) und dann von Selma (Kampfsystem) ersetzt worden (4.3).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| Noah        | 0,5 % →**4 %**   | `BaseMonster` ist die Basisklasse **aller vier Monster** im Spiel und strukturell weit wichtiger, als die 17 verbliebenen Zeilen vermuten lassen. Dazu Gnome, Orc, Zombie und ein eigeninitiativer Refactor.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| Luca        | 0,4 % →**4 %**   | Legte das Waffen-Grundgerüst und die erste`Bow` an. Beides wurde später von Tom, Lennox und Selma überschrieben; von seinen 7 verbliebenen `Bow`-Zeilen ist keine einzige inhaltlich (4.7). Die Waffen sind trotzdem eine tragende Säule des Spiels, deshalb hat Nikolaj 0,5 % von seinem Anteil an Luca abgegeben (3,5 % → 4 %).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |

### 1.2 Projektleitung und Organisation (größtenteils Nikolaj)

Diese Arbeit taucht in **keiner** Code-Statistik auf, war aber die Voraussetzung dafür,
dass acht Leute überhaupt parallel an einem Repository arbeiten konnten:


| Aufgabe                                             | Belege im Repo                                                                                                                                                           |
| --------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **GitHub-Repository komplett aufgesetzt**           | `Initial project scaffolding` (`2026-04-17`), `.gitignore`, Branch-Struktur, Merge-Workflow                                                                              |
| **Alle Teammitglieder in Git/GitHub eingearbeitet** | `GITHUB-HELP.md`: ein eigens für das Team geschriebener Git/GitHub-Leitfaden („Create Git & GitHub Help Guide for Student Team", `0222b6b`), verlinkt aus der README |
| **Projektplan, Issues und Aufgabenverteilung**      | 76 durchnummerierte Issues/PRs im Repo; Branch-Namen wie`48-refactor-split-entities-into-base-monsters-player-subpackages` zeigen die issue-getriebene Arbeitsweise      |
| **Repository-Governance**                           | `CODEOWNERS` (`45228f8`), Issue-Templates (`.github/ISSUE_TEMPLATE/`), `SECURITY.md`, `CONTRIBUTING.md`, MIT-`LICENSE`, **alle vier von Nikolaj**                      |
| **Grundprojekt lauffähig vorbereitet**             | Greenfoot-Libs + Module eingebunden, IntelliJ-Run-Config`Run_Game.xml`, Windows-SDK-Fix (PR #30), README mit kompletter Setup-Anleitung für das Team                    |
| **Alle Merges reviewed, Konflikte gefixt**          | 43 von 47 Merge-Commits (91 %), davon 27 PR-Merges; dazu die`Merge branch 'master' into <feature>`-Commits, die reine Konfliktauflösung waren                           |
| **Fremde Branches lauffähig gemacht**              | siehe Abschnitte 4.1, 4.5 und 4.8                                                                                                                                        |

Die Merge-Verteilung macht das am deutlichsten: **91 % aller Integrationen liefen über Nikolaj.**
Jeder dieser Merges bedeutete Review, Konfliktauflösung und oft Nacharbeit am fremden Code.

---

## 2. Aufteilung nach Feature-Bereichen


| Bereich                                                                                       | Hauptverantwortlich       | Beteiligt                                                 | Anmerkung                                                |
| --------------------------------------------------------------------------------------------- | ------------------------- | --------------------------------------------------------- | -------------------------------------------------------- |
| Projekt-Setup, IntelliJ/Greenfoot-Integration, README, Lizenz, CI-Templates                   | Nikolaj                   | -                                                        | komplett allein                                          |
| Map-/Dungeon-Generierung (`DungeonLevel`, Räume, Korridore, Seed)                            | Nikolaj                   | Selma (Pause-Hook, Kampf-Hooks), Jonas (Tiles), Tom       | Kern von Nikolaj                                         |
| A\*-Pathfinding (`ASharpPathfinding`)                                                         | **KI** (s. `KI.md`)       | Nikolaj                                                  | Code von KI; Integration, Rock→Wall, Refactor, Umbau 08/2026 von Nikolaj |
| Player-Bewegung & Kollision (`Player`, `MovingActor`, `Direction`)                            | Nikolaj                   | Tom, Len, Selma                                           | mehrfach überarbeitet                                   |
| Monster-Basis (`BaseMonster`, Gnome, Orc, Zombie)                                             | Noah                      | Nikolaj, Selma (Angriffslogik), Tom, Len                  | Grundgerüst von Noah                                    |
| Neue Monster + Animationen (Skeleton, Zombie-Sprites, Walking/Attacking-Ordner)               | Tom                       | Len                                                       |                                                          |
| Monster-Spawning                                                                              | Nikolaj                   | Selma                                                     | `d6f0cae`, `19c0a5f`                                     |
| **Kampfsystem** (Schaden, Krit-Chance, Reichweite, `DamageableActor`, `DamageNumber`)         | **Selma**                 | Nikolaj (Integration + Fixes), Lennox/Luca (Waffen-Basis) | neu seit`2026-08-15`, PR #76                             |
| Hauptmenü, Buttons, Settings-World                                                           | Tom                       | Nikolaj                                                   |                                                          |
| Inventar (Slots, Drag & Drop, Backpack, Visualizer)                                           | Tom                       | Nikolaj (Rebase + Logik-Finish), Len                      | größte gemeinsame Baustelle                            |
| **Upgrade-Mechanik** (`UpgradeSlot`, `UpgradeButton`, `Material`, `Gold`, `Iron`, `SlotType`) | **Tom**                   | Nikolaj                                                   | neu, PR #73                                              |
| **Werkbank** (`UpgradeTable`)                                                                 | **Len**                   | Nikolaj (Upgrade nur noch an der Werkbank möglich)       | neu, PR #75                                              |
| Item-System (`Item`, `Pickable`, `Useable`, `ItemTyp`)                                        | Nikolaj + Len             | Tom, Selma (Waffen-Einträge)                             |                                                          |
| Rarity-System, Tooltips (`Rarity`, `OnHover`, `ItemText`, `FontManager`)                      | Nikolaj                   | -                                                        | komplett allein                                          |
| Rüstungen (7 Klassen +`Armor`)                                                               | Tom                       | Nikolaj                                                   |                                                          |
| Waffen (`Waffen`, Messer, Stock, Sword)                                                       | Luca + Lennox (Basis)     | Selma (Kampfwerte), Tom, Nikolaj                          | heutiger Code v. a. Selma, siehe 4.3                   |
| Bogen & Pfeil-Animation (`Bow`, `Arrow`, `BowSprite`, `Explosion`)                            | Tom                       | Luca (erste`Bow`), Selma                                  |                                                          |
| Chest + Loot-Drop                                                                             | Len                       | Nikolaj                                                   |                                                          |
| Tränke & Essen (HealthPotion, Apple, Cookie, HoneyBottle, UnstablePotion)                    | Len (Design + Logik)      | Nikolaj (Refactor,`UnstablePotion`)                       |                                                          |
| Healthbar                                                                                     | Nikolaj                   | Tom (Gerüst)                                             | **Jonas' Version wurde nie gemergt**                     |
| XP-Bar & Level-System                                                                         | Len                       | Nikolaj (Fixes + Finish)                                  |                                                          |
| **Level-Up-System + `LevelUpMessage`**                                                        | **Nikolaj**               | Len (XP-Basis)                                            | überarbeitet`415769a`                                   |
| Pause-Screen                                                                                  | Selma                     | Nikolaj (Buttons neu geschrieben)                         |                                                          |
| **GameOver-Screen**                                                                           | Selma (Konzept + Grafik)  | **Nikolaj** (Neuimplementierung, `tryAgainButton`)        | Grafik 1:1 von Selmas Branch, siehe 4.2                |
| Save-/Load-Game (`ItemData`, SaveGameButton, LoadGameButton)                                  | Nikolaj                   | Tom (LoadGameButton-Basis)                                |                                                          |
| Sound & Hintergrundmusik (`SoundManager`)                                                     | **Len + Jonas (je 50 %)** | -                                                        | gemeinsam geschrieben, nur Len hat commited, siehe 4.6 |
| Fenster-/Fullscreen-Handling (`WindowSizeManager`)                                            | Nikolaj                   | **KI** (Swing/AWT-Teil, s. `KI.md`)                      | Rest von Nikolaj                                         |
| Floor-/Wall-Tiles-Design                                                                      | Jonas                     | Nikolaj (Kollision/Paint-Order)                           |                                                          |
| **Treppen-Grafiken (`Entrance`, `Exit`)**                                                     | **Jonas**                 | Nikolaj                                                   | neu, PR #74                                              |
| `ImprovedGreenfootImage`, Bild-Loader                                                         | Nikolaj                   | Len (Verschieben nach`util`), Tom (Loader-Überarbeitung) |                                                          |
| Merges, Konfliktauflösung, Branch-Verwaltung                                                 | Nikolaj                   | Tom (3), Len (1)                                          | 43 von 47 Merges                                         |

---

## 3. Contributors im Detail

### 3.1 Nikolaj (49,5 %) · *Projektleitung*

**Git-Accounts:** 2 (hier zusammengefasst)
**Commits:** 119 auf `master` (76 inhaltliche + 43 Merge-Commits), 122 über alle Branches
**Blame:** 2.684 von 5.353 Zeilen (50,1 %)

**Allein gebaut:**


| Datei                            | Zeilen      | Inhalt                                                          |
| -------------------------------- | ----------- | --------------------------------------------------------------- |
| `core/GameStarter.java`          | 141 von 145 | Einstiegspunkt des Spiels, World-Wechsel, Game-Loop-Anbindung\* |
| `util/WindowSizeManager.java`    | 104         | Fenster-/Fullscreen-Handling (Swing/AWT-Teil von KI, s. `KI.md`) |
| `items/util/OnHover.java`        | 82          | Tooltip-System                                                  |
| `ui/Buttons/tryAgainButton.java` | 67          | GameOver-Button (neu,`e6f2ebc`)                                 |
| `ui/LevelUpMessage.java`         | 65          | Level-Up-Einblendung (neu,`415769a`)                            |
| `util/FontManager.java`          | 58          | zentrales Font-Loading                                          |
| `items/util/Rarity.java`         | 51          | Seltenheitsstufen + Farbcodierung                               |
| `ui/worlds/GameOverScreen.java`  | 25          | GameOver-World (neu,`e6f2ebc`)                                  |
| `ui/ItemText.java`               | 21 von 22   | Item-Textrendering (1 Leerzeile von Selma)                      |
| `items/util/ItemData.java`       | 7           | Save-Datenmodell                                                |
| `items/util/Pickable.java`       | 4           | Interface                                                       |

\* `GameStarter.java` ist inhaltlich zu 100 % von Nikolaj. `git blame` weist Tom 4 Zeilen zu,
die aber alle aus dem Main-Menu-Commit stammen und an der Funktionsweise nichts ändern. Ein gutes
Beispiel dafür, warum `git blame` allein kein Maß für Beitrag ist. Das Phänomen betrifft das
ganze Projekt und **jeden im Team**, siehe **Abschnitt 4.7**.

**Rolle:** Projektleitung. Neben dem Code lagen Organisation, Planung, Einarbeitung des Teams
und die gesamte Integration bei ihm, ausführlich in **Abschnitt 1.2**.

**Hauptbeiträge:**

- **Projekt-Fundament und Team-Setup** (`2026-04-17` bis `2026-04-27`): Repository aufgesetzt,
  Initial Scaffolding, kombinierte Quellen, Greenfoot-Libs + Module, IntelliJ-Run-Configs,
  Windows-SDK-Fix, `.gitignore`, MIT-Lizenz, CODEOWNERS, Issue-Templates, README mit
  Setup-Anleitung und `GITHUB-HELP.md` als Git-Leitfaden fürs Team. Damit war das Projekt
  für alle acht Mitglieder überhaupt erst benutzbar.
- **Planung und Aufgabenverteilung**: Projektplan, Anlegen und Zuweisen der Issues
  (76 Issues/PRs über die Projektlaufzeit), Definition der Branch- und Merge-Konventionen.
- **Map-Generierung** (`feature/map-gen`, 8 Commits): Räume, Korridore, Seed-System, Entrance/Exit,
  Grundstruktur von `DungeonLevel.java` (heute 276 von 392 Zeilen = 70 %).
- **Refactoring der Paketstruktur** (PR #55): Aufteilung von `entities` in `base` / `enemies` /
  `util`, dabei u. a. `ASharpPathfinding` aus `src/entities/` nach `src/entities/util/` verschoben.
- **Item-System-Ausbau**: Rarity, Tooltips, `OnHover`, `ItemText`, `FontManager` (PR #59, `2026-06-20`).
- **Save-/Resume-Logik** (`feature/save-game`, 3 Commits, Juli): kompletter Speicher-/Ladezyklus
  inkl. Inventar-Serialisierung; `SaveGameButton` (65 von 72 Zeilen).
- **Health- + XP-Bar-Finalisierung** (`2026-08-11`): `Healthbar.java` 52 von 56 Zeilen,
  `XPBar.java` 25 von 51 Zeilen.
- **Fullscreen-Fix + Load-Game-Implementierung** (PR #72, `2026-08-14`).
- **Kampfsystem lauffähig gemacht** (`d6f0cae`, PR #76, `2026-08-16`): Selmas Branch spawnte keine
  Monster; Nikolaj hat `BaseMonster` (140 Zeilen), `ASharpPathfinding` (47 Zeilen) und die
  Spawn-Logik in `DungeonLevel` umgebaut, siehe 4.8.
- **Upgrade nur noch an der Werkbank** (`59fedbd`, PR #75): Lens `UpgradeTable` mit Toms
  Upgrade-Slots zusammengeführt, Upgraden außerhalb der Werkbank deaktiviert.
- **Inventar-Fix** (`a2925f1`): doppelten Tausch in `swapItems` behoben, Rüstung aktualisiert
  jetzt das Aussehen des Spielers.
- **Finales Spawning** (`19c0a5f`) und **GameOver-Screen** (`e6f2ebc`, aus Selmas `KockBack`-Branch
  übernommen und neu geschrieben, siehe 4.2).
- **Überarbeitetes Level-Up-System** (`415769a`, letzter Commit): XP-Kurve `100 * level^1.5`,
  Bonus-Leben/-Schaden pro Level, neue `LevelUpMessage`-Einblendung.
- **Integrationsarbeit / Review**: 43 von 47 Merge-Commits auf `master` (91 %), davon 27 PR-Merges.
  Jeder davon bedeutete Review des fremden Codes und Konfliktauflösung; inkl. der aufwendigen Rebases
  (`feature/Inventory-rebase(master)`, PR #57) und mehrerer `Merge branch 'master' into <feature>`
  Konfliktauflösungen.

**Nacharbeit an fremdem Code (wichtig für die Bewertung):**

- **Selmas Pause-Buttons komplett neu geschrieben**, Details in Abschnitt 4.1.
- **Selmas Kampfsystem-Branch lauffähig gemacht**, Details in Abschnitt 4.8.
- **Toms Inventar-Branch gerettet**: `feature/Inventory` wurde von Nikolaj auf `master` rebased
  (`ee71c3a` „working rebased branch", `f3456c0` „refactoring + finsished inventory logic"),
  danach `InventorySlot.java` Größenfix. Ohne den Rebase wäre der Branch nicht mergebar gewesen.
- **`selma hat vergessen die bilder hohzuladen`** (`6f919c0`): fehlende Assets für den PauseScreen
  nachgeliefert.
- **`working master with fixed misc items`** (`dbe9d9d`): Toms Item-Effekte-Commit (`18fe2b0`)
  hinterließ einen nicht lauffähigen Stand, den Nikolaj repariert hat; dabei entstand
  `UnstablePotion.java` neu.
- **`fixed windows version`** (`d7db0e5`): plattformspezifische Fixes nach dem großen Juli-Merge.
- Pathfinding-Anpassung `Rock` → `Wall` + Paint-Order-Fix für `room.Walls` nach Jonas' Tile-Umbau.

---

### 3.2 Tom (21 %)

**Git-Accounts:** 2 (dieselbe Person, zwei Anzeigenamen)
**Commits:** 21 auf `master` (inkl. 3 Merges), 24 über alle Branches
**Blame:** 1.680 Zeilen (31,4 %) · **256 Asset-Dateien** angefasst (mit Abstand am meisten)

**Größte eigene Blöcke:**


| Datei                          | Toms Zeilen / gesamt | Anteil |
| ------------------------------ | -------------------- | ------ |
| `ui/InventorySlot.java`        | 187 / 313            | 60 %   |
| `ui/buttons/KeyButton.java`    | 110 / 146            | 75 %   |
| `ui/UpgradeButton.java`        | 104 / 104            | 100 %  |
| `ui/worlds/Backpack.java`      | 93 / 168             | 55 %   |
| `ui/InventoryVisualizer.java`  | 90 / 124             | 73 %   |
| `items/waffen/BowSprite.java`  | 76 / 76              | 100 %  |
| `items/waffen/Arrow.java`      | 70 / 70              | 100 %  |
| `ui/worlds/SettingsWorld.java` | 67 / 84              | 80 %   |
| `items/waffen/Bow.java`        | 53 / 72              | 74 %   |
| `ui/Settings.java`             | 50 / 53              | 94 %   |
| `ui/UpgradeSlot.java`          | 44 / 44              | 100 %  |
| `ui/Explosion.java`            | 28 / 28              | 100 %  |
| `ui/GhostItem.java`            | 21 / 21              | 100 %  |

**Hauptbeiträge:**

- **Main-Menu-Grundlage** (`2026-04-28` bis `2026-05-19`, 4 Commits): `MainMenu`-Buttons,
  `StartButton`, `SettingsButton`, `LoadGameButton`, `StandardButton`, `Clickable`, `DarkFilter`, `UI`.
- **Projekt-Struktur-Commit** (`Structure`, PR #34): legte `Hitting`, `Pickable`, `Healthbar`
  und `XPBar` als Gerüst an; die beiden Bars wurden später von anderen ausgefüllt.
- **Inventar-System** (`feature/inventoryslots`, Mai/Juni): Backpack-Inventar, auswählbare Slots,
  `getActiveSlot()`, `useItem()`, Drag & Drop (`GhostItem`), `InventoryOverlay`.
- **Settings** (`2026-06-12`): komplettes Settings-Menü inkl. `KeyButton` für Tastenbelegung.
- **Rüstungssystem** (`2026-06-15` / `2026-06-29`): `Armor` + 6 konkrete Teile (Leather/Iron/Gold,
  jeweils Helm + Body).
- **Monster-Erweiterung** (`2026-07-07`): `Skeleton`, `Zombie`-Überarbeitung, neuer `LoadImage`-Ansatz
  mit getrennten `Walking`/`Attacking`-Ordnern für Animationen.
- **Item-Effekte + neuer Image-Loader** (`2026-07-12`).
- **Bogen & Pfeil-Animation** (`2026-08-07`, PR #68): `Arrow`, `BowSprite`, `Explosion`; dabei wurde Lucas
  ursprüngliche `Bow`-Klasse fast vollständig überschrieben (siehe 4.3).
- **Upgrade-Mechanik** (`f46865e`, `2026-08-14`, gemergt als PR #73 am `2026-08-16`):
  `UpgradeSlot`, `UpgradeButton` (mit Glow-Zustand), `Material` + `Gold`/`Iron`, `SlotType`-Enum,
  eigene Slot-Grafiken (Helmet-, Chest-, items.material-Slot). Nikolaj hat das Upgraden danach auf die
  Werkbank beschränkt und den Slot-Tausch gefixt.

**Anmerkung zur Gewichtung:** Toms Zeilenanteil (31,4 %) liegt deutlich über seinem
Effort-Anteil (21 %). Gründe:

1. **Repetitiver Code.** Ein erheblicher Teil der Zeilenmasse ist Copy-Paste-nah: 7 Rüstungsklassen
   mit je 6 bis 7 Zeilen identischer Struktur, 5 Button-Klassen nach demselben Muster, zuletzt
   `Gold.java` / `Iron.java` als praktisch identische items.material-Klassen.
   Das erzeugt viele Zeilen bei geringer Schwierigkeit.
2. **Abweichung von den Projektprinzipien.** Mehrere seiner Beiträge entsprachen nicht den
   vereinbarten Konventionen (Paketstruktur, Vererbungshierarchie, Bild-Loading) und mussten von
   Nikolaj nachträglich umgebaut werden, im Detail in **Abschnitt 4.5**.
3. **Nicht mergebarer Branch.** `feature/Inventory` musste von Nikolaj auf `master` rebased werden,
   bevor er überhaupt integrierbar war (PR #57).

Das schmälert nicht den Umfang seiner Arbeit: Tom ist unbestritten der zweitgrößte Beitragende
und hat mit 256 berührten Asset-Dateien den größten Anteil an der Grafikeinbindung. Der
Effort-Anteil bildet aber ab, wie viel davon **ohne Nacharbeit verwertbar** war.

---

### 3.3 Len (7 %)

**Git-Accounts:** 2 (dieselbe Person, zwei Anzeigenamen)
**Commits:** 13 (inkl. 1 Merge) · **Blame:** 454 Zeilen (8,5 %) · 16 Asset-Dateien

**Gemeinsam mit Jonas:** `util/SoundManager.java` (53 Zeilen), zu **je 50 %** von beiden,
auch wenn git die Datei zu 100 % Len zuschreibt (siehe 4.6).

**Hauptbeiträge:**

- **Chest-System** (`2026-05-04` bis `2026-05-10`, 4 Commits): Chest-Design, Platzierung in der Welt,
  Random-Item-Drop, `ItemTyp`-Enum, `TestItem`; Öffnen erst per E-Taste, dann auf Mausklick umgestellt.
  `blocks/Chest.java` heute 49 von 59 Zeilen = 83 % Len.
- **Item-Designs** (`2026-05-13`): HealthPotion, Apple, Cookie, HoneyBottle, also Sprites *und*
  erste Klassen. Nikolaj hat die Klassen später refactored, Lens Design-Assets sind geblieben.
- **`ImprovedGreenfootImage` nach `util` verschoben** (PR #53): ein kleiner, aber sauberer Refactor.
- **HealthPotion-Logik fertiggestellt** (PR #61, `2026-07-07`): benutzbar, spawnt aus Kiste,
  wird nach Nutzung aus dem Inventar entfernt.
- **XP-Bar & Level-up-System** (PR #67, `2026-07-28`): `XPBar.java` 22 von 51 Zeilen;
  Nikolaj hat am `2026-08-11` Bugfixes und die finale Einbindung ergänzt, am `2026-08-16`
  das Level-System überarbeitet.
- **Sounds & Hintergrundmusik** (PR #71, `2026-08-11`): `SoundManager`, **gemeinsam mit Jonas
  entwickelt, Anteil je 50 %**. Len hat die Commits gemacht, deshalb steht die Datei in git
  vollständig unter seinem Namen (siehe 4.6).
- **Werkbank / `UpgradeTable`** (`e335c1c`, PR #75, `2026-08-16`): neuer Block `UpgradeTable.java`
  (40 Zeilen, 100 % Len) inkl. Grafik, Öffnen des Upgrade-Inventars per **R** beim Berühren,
  dazu `openInventoryFromTable()` im Player und Anpassungen an `InventorySlot` (50 Zeilen)
  und `Backpack` (34 Zeilen). Nikolaj hat direkt danach das Upgraden auf die Werkbank beschränkt.

---

### 3.4 Selma (5,5 %)

**Git-Accounts:** 1 · **Commits:** 10 über alle Branches (4 auf `master`) ·
**Blame:** 415 Zeilen (7,8 %)

Selma ist der Contributor mit der größten Veränderung in den letzten zwei Tagen: durch das
Kampfsystem ist ihr Blame-Anteil von 2,3 % auf 7,8 % gestiegen.

**Hauptbeiträge:**

- **Kampfsystem** (`a0e658f` / `dba0c74`, `2026-08-15`, gemergt als PR #76), ihr mit Abstand
  größter Beitrag:
  - `ui/DamageNumber.java` (56 Zeilen, **100 % Selma**): aufsteigende Schadenszahlen
  - `entities/base/DamageableActor.java`: `takeDamage()`, Krit-Darstellung, Todes-Hook (27 von 62 Z.)
  - `items/Waffen.java`: Schaden, Krit-Chance (`KRIT_FAKTOR`, `STANDARD_KRIT_CHANCE`),
    Reichweite, `hit()`-Trefferabfrage in Blickrichtung: **70 von 108 Zeilen (65 %)**
  - Angriffslogik in `BaseMonster.java` (68 von 205 Z.), Kampf-Hooks in `DungeonLevel.java` (76 Z.),
    Kampfwerte in `Sword`, `Messer`, `Stock`, `Bow` und `ItemTyp`
- **PauseScreen** (`2026-07-14` bis `2026-07-17`, PR #70): `ui/PauseScreen.java` gehört ihr zu
  **100 %** (22 von 22 Zeilen). Dazu die Integration in `DungeonLevel.java`.
- **Pause-Button-Klassen angelegt**: `PauseButtons`, `SaveGameButton`, `restartButton`,
  `settingPauseButton`, als Stubs. Diese wurden anschließend von Nikolaj
  ausimplementiert (siehe 4.1).
- **GameOverScreen** (`2026-05-29` / `2026-06-09`, Branch `KockBack`): der Branch wurde **nie
  gemergt**, aber am `2026-08-16` hat Nikolaj die Grafik **unverändert** übernommen und die
  Klasse neu geschrieben. Ihre Vorarbeit ist damit im Spiel, im Blame aber unsichtbar (4.2).
- **`hit`-Methode im Player** (`8d8af3d`, `2026-05-04`, Branch `feature/Hitting`): **nicht
  gemergt**; die frühe Kampf-Grundlage kam erst über das Kampfsystem im August in `master`.

---

### 3.5 Jonas (5 %)

**Git-Accounts:** 1 · **Commits:** 12 über alle Branches
(6 auf `master`) · **Blame:** nur 39 Zeilen (0,7 %) · 10 Asset-Dateien

Jonas ist der klarste Fall, bei dem der Blame-Wert den tatsächlichen Aufwand **stark unterschätzt**, gleich dreifach: Grafik-Assets zählen nicht als Zeilen, sein Healthbar-Branch wurde nie gemergt,
und seine Hälfte am `SoundManager` läuft unter Lens Account:

- **Floor- und Wall-Tiles** (`2026-05-05` bis `2026-05-14`, 5 Commits): Tile-Designs, schwarzer Boden
  außenrum, undurchdringbare Wände. Das ist überwiegend **Grafik-Arbeit** und taucht in der
  Zeilenzählung praktisch nicht auf. Im Code geblieben: `blocks/Wall.java` (14 von 16 Zeilen = 88 %)
  und 17 Zeilen in `DungeonLevel.java`.
  Dabei war ein eigener Revert nötig (`37f23de`), weil die erste Version die Map unspielbar machte;
  Nikolaj musste danach die Kollisionsabfrage (`getObjectsAt` in `canMove`/`isBlocked`) und die
  Paint-Order nachziehen.
- **Healthbar** (`2026-06-08` bis `2026-06-15`, 6 Commits auf Branch `Healthbar`):
  **komplett verworfen**: der Branch wurde nie gemergt (siehe 4.2). Die Healthbar in `master`
  stammt von Nikolaj.
- **Sounds & Hintergrundmusik** (`2026-08-11`): `util/SoundManager.java` **gemeinsam mit Len
  entwickelt, Anteil je 50 %**. Die Commits liefen über Lens Account, weshalb git die 53 Zeilen
  vollständig Len zuschreibt; Jonas' Hälfte ist in **keiner** Statistik dieses Dokuments
  sichtbar (siehe 4.6).
- **Treppen-Grafiken** (`4b7a940`, PR #74, `2026-08-15`): `StairsEntrance.png` und `StairsExit.png`
  plus die Einbindung in `Entrance.java` und `Exit.java`; wieder Grafik-Arbeit, im Code nur
  3 geänderte Zeilen.

---

### 3.6 Lennox (4 %)

**Git-Accounts:** 1 · **Commits:** 5 · **Blame:** 33 Zeilen (0,6 %)

- **Waffen-Klassen** (alle am `2026-05-11`, vier davon mit der Commit-Message „yooo"):
  `Sword.java` neu angelegt, Ausbau von `Waffen.java` (heute noch 21 von 108 Zeilen; bis August
  waren es 40, Selmas Kampfsystem hat den Rest ersetzt), Beiträge in `Messer.java` und `Stock.java`.
- Kurzer, aber kompakter Einsatz an genau einem Tag; die Waffen-Basisklasse ist bis heute im Einsatz,
  ihr Inhalt ist inzwischen aber überwiegend von anderen (siehe 4.3).

---

### 3.7 Noah (4 %)

**Git-Accounts:** 2 (Apple-Private-Relay hat zwei leicht unterschiedliche Adressen erzeugt,
git zählt das als zwei Identitäten) · **Commits:** 4 · **Blame:** 29 Zeilen (0,5 %)

- **`BaseMonster.java` angelegt** (PR #35, `2026-04-28`) inklusive verbesserter Death-Methode im Player.
  Das ist die Basisklasse, von der **alle vier Monster** im Spiel erben und damit strukturell wichtiger,
  als die Zeilenzahl vermuten lässt.
- **Gnome, Orc, Zombie** angelegt (`2026-05-04`, PR #39/#40) mit der `receiveHit`-Methode.
- **`receiveHit` von Gnome nach BaseMonster hochgezogen**: sauberer Refactor auf eigene Initiative.
- Heute stehen von `BaseMonster.java` noch 17 von 205 Zeilen (8 %) von Noah; der Rest kam durch
  Nikolajs Pathfinding-Anbindung, Toms Animations-Umbau und Selmas Angriffslogik dazu.

---

### 3.8 Luca (4 %)

**Git-Accounts:** 2 (die Mailadresse ist einmal groß und einmal klein geschrieben, git zählt
das als zwei Identitäten) · **Commits:** 3 · **Blame:** 19 Zeilen (0,4 %)

- **Waffen-Grundgerüst** (`2026-05-04`): legte `Waffen.java`, `Messer.java` und `Stock.java` an: die Basis, auf der Lennox, Tom und Selma später aufgebaut haben.
- **New weapons** (`2026-06-01` / `2026-06-02`): erste `Bow.java`.
  Von dieser Datei sind heute noch 7 von 72 Zeilen (10 %) von Luca; Tom hat den Bogen bei der
  Pfeil-Animation im August weitgehend neu geschrieben (siehe 4.3).

---

## 4. Überschriebene und nicht gemergte Arbeit

Dieser Abschnitt hält fest, wo Arbeit geleistet wurde, die im aktuellen `master` **nicht mehr
oder nur noch teilweise sichtbar** ist.

### 4.1 Selmas Pause-Buttons → von Nikolaj neu geschrieben

Selma legte am `2026-07-17` vier Button-Klassen als funktionsfähige Stubs an. Am `2026-08-11`
implementierte Nikolaj sie im Commit `95486cc` („all buttons implenemted (except load game)")
vollständig aus:


| Datei                     | Selma (heute) | Nikolaj (heute) | Len | gesamt |
| ------------------------- | ------------- | --------------- | --- | ------ |
| `SaveGameButton.java`     | 7             | **65**          | -  | 72     |
| `restartButton.java`      | 4             | **48**          | -  | 52     |
| `settingPauseButton.java` | 5             | **46**          | -  | 51     |
| `PauseButtons.java`       | **12**        | 1               | 13  | 26     |

Selmas Struktur und Namensgebung sind geblieben, die eigentliche Logik ist neu.
`ui/PauseScreen.java` selbst ist dagegen unverändert zu 100 % ihre Datei.

### 4.2 Nie gemergte Branches


| Branch                   | Autor     | offene Commits | Inhalt                                                                  | Status                                                                                                                                                                                   |
| ------------------------ | --------- | -------------- | ----------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| `origin/Healthbar`       | **Jonas** | 5              | komplette eigene Healthbar-Implementierung + PNG                        | **verworfen**: die Healthbar in `master` stammt von Nikolaj (52 Z.) und Tom (4 Z. Gerüst)                                                                                             |
| `origin/KockBack`        | **Selma** | 3              | GameOverScreen + Knockback                                              | Branch verworfen,**Inhalt teilweise übernommen**: Nikolaj hat am `2026-08-16` die Grafik `GameOverScreen.png` **byte-identisch** übernommen und die Klasse neu geschrieben (`e6f2ebc`) |
| `origin/PauseScreen`     | Selma     | 4              | frühe PauseScreen-Version                                              | ersetzt durch`pauseScreenFinal` (PR #70), der gemergt wurde                                                                                                                              |
| `origin/kampfsystem`     | Selma     | 1              | erste Kampfsystem-Version                                               | ersetzt durch`kampfsystem-aktuell` (PR #76), der gemergt wurde                                                                                                                           |
| `origin/feature/Hitting` | **Selma** | 2              | `hit`-Methode im Player (`8d8af3d`), früher GameOverScreen (`8ae0b59`) | **nicht gemergt**: das `Hitting.java`-Interface in `master` stammt aus Toms `Structure`-Commit, nicht von diesem Branch                                                                |
| `origin/enemy/ghost`     | Nikolaj   | 1              | `Ghost`-Monster (`d677f36`)                                             | **nicht in `master`**: die Klasse existiert dort nicht                                                                                                                                 |

Die übrigen Remote-Branches des Teams (`Stairs`, `Tom_Work`, `feature/upgrade-table`, `kampfsystem-aktuell`,
`WeaponLogic`, `ArmorUpgrade`, `Item_Design_Logic`, `monster_logic_Design`, `feature/sounds`,
`feature/xp-bar`, `feature/save-game`, `feature/inventoryslots`, `pauseScreenFinal`,
`NorkelL/fullseceen-fix`, `enemy-attak-methode`, `Weapons`) sind vollständig in `master` gemergt.

### 4.3 Waffen: Luca/Lennox → Tom → Selma

Die Waffen sind der Bereich mit dem stärksten Autorenwechsel, inzwischen in **drei** Stufen:

- `Waffen.java`: von **Luca** angelegt (`2026-05-04`), von **Lennox** ausgebaut (`2026-05-11`),
  im August von **Selma** für das Kampfsystem neu geschrieben →
  heute 70 Z. Selma, 21 Z. Lennox, 7 Z. Luca, 7 Z. Nikolaj, 3 Z. Tom (108 gesamt)
- `Sword.java`: von **Lennox** angelegt → heute 8 Z. Selma, 6 Z. Lennox, 6 Z. Nikolaj; von Luca ist in dieser
  Datei nichts mehr übrig
- `Messer.java` / `Stock.java`: von **Luca** angelegt → heute überwiegend Selma (8 bzw. 10 Z.)
- `Bow.java`: von **Luca** angelegt (`2026-06-02`) → heute **53 von 72 Zeilen von Tom** (74 %),
  6 Z. Selma, Luca nur noch 7 Zeilen. Tom hat den Bogen bei der Arrow-Animation (`2026-08-07`)
  neu aufgebaut. **Inhaltlich war die Übernahme sogar vollständig:** Lucas verbliebene 7 Zeilen sind
  5 Leerzeilen und 2 Klammern, also keine einzige inhaltliche Zeile (siehe 4.7).

### 4.4 Weitere Umschreibungen

- **`Healthbar.java` und `XPBar.java`** wurden von **Tom** im `Structure`-Commit (`2026-04-28`)
  als leere Gerüste angelegt. Gefüllt wurden sie erst Monate später von Len (XP-Bar) und
  Nikolaj (beide Bars). Von Toms Gerüst sind noch 4 Zeilen pro Datei übrig.
- **`ASharpPathfinding.java`** existierte kurzzeitig doppelt: erst unter `src/entities/`,
  dann beim Refactoring nach `src/entities/util/` verschoben und die alte Datei gelöscht
  (`15b3f3a` „Delete src/entities/ASharpPathfinding.java"). Am `2026-08-16` hat Nikolaj sie
  im Zuge des Spawn-Fixes noch einmal überarbeitet (47 geänderte Zeilen).
- **Toms Item-Effekte-Commit** (`18fe2b0`, `2026-07-12`) wurde direkt danach von Nikolaj
  in `dbe9d9d` („working master with fixed misc items") repariert, weil der Stand nicht lief.
- **`DungeonLevel.java`** ist die am stärksten geteilte Datei im Projekt:
  Nikolaj 224 Z. · Selma 76 Z. · Nikolaj (Zweitaccount) 52 Z. · Tom 22 Z. · Jonas 17 Z. · Len 1 Z.
- **`Player.java`** ist mit 519 Zeilen die größte Datei geworden:
  Nikolaj 281 Z. · Tom 159 Z. · Len 57 Z. · Selma 22 Z.

### 4.5 SoundManager: Jonas' Anteil ist in git unsichtbar

`src/util/SoundManager.java` (53 Zeilen) wurde von **Len und Jonas gemeinsam** geschrieben.
Weil die Commits über Lens Account liefen, weist git die Datei zu **100 % Len** zu:

```
2026-08-11  ac3db27  Len  Sounds und Hintergrundmusik implementiert
2026-08-11  1d84a89  Len  Sounds und Hintergrundmusik implementiert
            PR #71   feature/sounds → master
```

`git blame src/util/SoundManager.java` → 53 von 53 Zeilen unter Lens Account,
0 Zeilen Jonas. Es gibt im gesamten Repository **keinen technischen Hinweis** auf seine
Beteiligung: kein `Co-authored-by`-Trailer, kein eigener Commit, kein Branch.

**Korrektur in diesem Dokument:** Der Sound-Bereich wird **je zur Hälfte** Len und Jonas
zugerechnet. Alle Blame- und numstat-Tabellen in Abschnitt 5 zeigen weiterhin die
unkorrigierten Rohwerte; die Korrektur steckt ausschließlich in den Effort-Prozenten
in Abschnitt 1.

Das ist der einzige bekannte Fall dieser Art. Sollte es weitere Pair-Programming-Sessions
gegeben haben, die nur ein Beteiligter commited hat, sind sie hier nicht erfasst, weil sie
sich aus dem Git-Verlauf nicht rekonstruieren lassen.

### 4.6 Blame-Rauschen: triviale Zeilen ohne inhaltlichen Beitrag

Eine Auswertung aller 87 Dateien zeigt: **47,2 % aller 5.353 Zeilen sind Leerzeilen, Imports,
`package`-Zeilen, einzelne Klammern oder Kommentare**, also Zeilen, die `git blame` jemandem
zurechnet, ohne dass dahinter inhaltliche Arbeit steht.

Das trifft **jeden** im Team, deshalb hier vollständig:

#### Was passiert, wenn man nur inhaltliche Zeilen zählt

Rechnet man Leerzeilen, Imports, Klammern und Kommentare komplett heraus, bleiben
**2.828 inhaltliche Zeilen**. Die Verteilung verschiebt sich so:


| Person  | alle Zeilen | nur inhaltliche | Verschiebung | Trivial-Anteil |
| ------- | ----------- | --------------- | ------------ | -------------- |
| Nikolaj | 50,1 %      | **53,7 %**      | +3,6         | 43 %           |
| Tom     | 31,4 %      | **28,7 %**      | −2,7        | 52 %           |
| Len     | 8,5 %       | **8,8 %**       | +0,3         | 45 %           |
| Selma   | 7,8 %       | **7,4 %**       | −0,4        | 50 %           |
| Jonas   | 0,7 %       | **0,8 %**       | +0,1         | 44 %           |
| Lennox  | 0,6 %       | **0,3 %**       | −0,3        | 76 %           |
| Noah    | 0,5 %       | **0,3 %**       | −0,2        | 69 %           |
| Luca    | 0,4 %       | **0,1 %**       | −0,3        | 89 %           |

**Einordnung:** Die reine Zeilenmessung *ohne jede inhaltliche Bewertung* liefert für Nikolaj
**53,7 %**. Die Effort-Aufteilung in Abschnitt 1 setzt ihn knapp darunter an (**49,5 %**),
weil Zeilen allein seinen Anteil überzeichnen: ein Teil davon entstand durch das Umschreiben
fremder Klassen.

**Einschränkung zu dieser Auswertung:** Der Trivial-Anteil hängt stark an der Dateigröße: kleine Klassen bestehen fast nur aus Leerzeilen, Imports und Klammern, große Klassen deutlich
weniger. Wer viele große, dichte Klassen schreibt, schneidet hier automatisch besser ab als
jemand, der viele kleine Boilerplate-Klassen anlegt. Der Vorsprung misst also eher „hat die
großen Logik-Brocken geschrieben" als „hat sauberer programmiert", was in diesem Projekt
zusammenfällt, aber nicht dasselbe ist.

### 4.7 Nacharbeit an Selmas Kampfsystem

Selmas Branch `kampfsystem` (`0922be9`) war der größte Einzelbeitrag außerhalb von Nikolaj und Tom,
konnte aber nicht direkt gemergt werden: mit dem neuen `DamageableActor`-Verhalten spawnten keine
Monster mehr. Nikolaj hat den Branch als `kampfsystem-aktuell` weitergeführt und in `d6f0cae`
(„spawming mobs fix") repariert:


| Datei                                  | geänderte Zeilen | was                                                  |
| -------------------------------------- | ----------------- | ---------------------------------------------------- |
| `entities/base/BaseMonster.java`       | 64                | Spawn- und Lebenslogik an`DamageableActor` angepasst |
| `world/DungeonLevel.java`              | 70                | Monster-Spawning neu aufgebaut, 45 Zeilen entfernt   |
| `entities/util/ASharpPathfinding.java` | 47                | Pathfinding auf die neue Monster-Basis umgestellt    |
| `items/util/ItemTyp.java`              | 17                | Waffen-Einträge bereinigt                           |
| Gnome / Orc / Skeleton / Zombie        | je 1 bis 8           | Konstruktoren nachgezogen                            |

Erst danach ging PR #76 nach `master`. Zwei weitere Commits (`19c0a5f` „final spawning",
`415769a`) haben das Spawning und die Kampf-Rückmeldung endgültig fertiggestellt.
Das Kampfsystem selbst (Schadensberechnung, Krit, Trefferabfrage, Schadenszahlen) ist
inhaltlich Selmas Arbeit und steht heute mit 415 Blame-Zeilen im Code.

---

## 5. Rohdaten

### Blame über alle 87 Java-Dateien in `src/` (5.353 Zeilen)


| Contributor              | Zeilen | Anteil |
| ------------------------ | ------ | ------ |
| Nikolaj (beide Accounts) | 2.684  | 50,1 % |
| Tom                      | 1.680  | 31,4 % |
| Len                      | 454    | 8,5 %  |
| Selma                    | 415    | 7,8 %  |
| Jonas                    | 39     | 0,7 %  |
| Lennox                   | 33     | 0,6 %  |
| Noah                     | 29     | 0,5 %  |
| Luca                     | 19     | 0,4 %  |

Aufteilung Nikolaj: Hauptaccount 2.095 Zeilen, Zweitaccount 589 Zeilen.

**Unkorrigierte Rohwerte.** Ein bekannter Zuordnungsfehler ist hier bewusst *nicht* bereinigt:
Jonas' Hälfte der 53 `SoundManager`-Zeilen steht bei Len (4.6). Korrigiert ist das nur in den
Effort-Prozenten in Abschnitt 1.

### Geschriebene Zeilen über die Projektlaufzeit (`git log --numstat`, nur `src/*.java`, ohne Merges)


| Contributor               | hinzugefügt | gelöscht | netto  |
| ------------------------- | ------------ | --------- | ------ |
| Nikolaj (Hauptaccount)    | 2.867        | 1.134     | +1.733 |
| Tom                       | 2.386        | 288       | +2.098 |
| Nikolaj (Zweitaccount)    | 1.327        | 540       | +787   |
| Len                       | 641          | 208       | +433   |
| Selma                     | 514          | 72        | +442   |
| Jonas                     | 233          | 162       | +71    |
| Lennox                    | 132          | 49        | +83    |
| Noah                      | 90           | 10        | +80    |
| Luca                      | 66           | 21        | +45    |

Die hohe Löschzahl bei Nikolaj (1.674 Zeilen über beide Accounts) spiegelt genau die
Refactoring- und Integrationsarbeit aus Abschnitt 4 wider.

### Assets (PNG / GIF / WAV / MP3), Anzahl berührter Dateien


| Contributor               | Dateien |
| ------------------------- | ------- |
| Tom                       | 256     |
| Nikolaj (Hauptaccount)    | 71      |
| Nikolaj (Zweitaccount)    | 66      |
| Len                       | 16      |
| Jonas                     | 10      |
| Luca                      | 6       |

### Commits auf `master`


| Contributor | inhaltlich | Merges | gesamt  |
| ----------- | ---------- | ------ | ------- |
| Nikolaj     | 76         | 43     | 119     |
| Tom         | 18         | 3      | 21      |
| Len         | 12         | 1      | 13      |
| Jonas       | 6          | -     | 6       |
| Lennox      | 5          | -     | 5       |
| Noah        | 4          | -     | 4       |
| Selma       | 4          | -     | 4       |
| Luca        | 3          | -     | 3       |
| **gesamt**  | **128**    | **47** | **175** |

**Nikolajs Merge-Anteil: 91 %** (43 von 47).

### Die 12 größten Dateien


| Datei                                  | Zeilen | Verteilung                                             |
| -------------------------------------- | ------ | ------------------------------------------------------ |
| `entities/Player.java`                 | 519    | Nikolaj 281 · Tom 159 · Len 57 · Selma 22           |
| `world/DungeonLevel.java`              | 392    | Nikolaj 276 · Selma 76 · Tom 22 · Jonas 17 · Len 1 |
| `ui/InventorySlot.java`                | 313    | Tom 187 · Nikolaj 76 · Len 50                        |
| `entities/base/BaseMonster.java`       | 205    | Nikolaj 99 · Selma 68 · Noah 17 · Tom 13 · Len 8   |
| `entities/base/MovingActor.java`       | 183    | Nikolaj 111 · Tom 66 · Jonas 5 · Len 1              |
| `ui/worlds/Backpack.java`              | 168    | Tom 93 · Nikolaj 41 · Len 34                         |
| `ui/buttons/KeyButton.java`            | 146    | Tom 110 · Nikolaj 30 · Len 6                         |
| `core/GameStarter.java`                | 145    | Nikolaj 141 · Tom 4                                   |
| `ui/worlds/MainMenu.java`              | 136    | Nikolaj 120 · Tom 16                                  |
| `ui/InventoryVisualizer.java`          | 124    | Tom 90 · Nikolaj 34                                   |
| `entities/util/ASharpPathfinding.java` | 111    | Nikolaj 111 (Code von KI, s. `KI.md`)                  |
| `items/Waffen.java`                    | 108    | Selma 70 · Lennox 21 · Luca 7 · Nikolaj 7 · Tom 3  |

---
