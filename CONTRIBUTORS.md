# Contributors – Wer hat was gemacht

Analyse des kompletten Git-Verlaufs (alle Branches, Stand `master` @ `566aa12`, 2026-08-14).
Zeitraum: **2026-04-17 bis 2026-08-14** (~4 Monate).

Datengrundlage:

- 157 Commits auf `master` (116 inhaltlich + 41 Merges) + 6 nicht gemergte Feature-Branches
- `git blame` über alle 75 Java-Dateien in `src/` → **4.443 Zeilen** aktiver Code
- `git log --numstat` über Java-Quellen und Assets (PNG/WAV/GIF)

> **Wichtig zum Lesen:** `git blame` misst nur, wessen Zeilen *heute noch* im Code stehen.
> Wer eine Klasse angelegt hat, die später umgeschrieben wurde, taucht dort kaum noch auf.
> Deshalb steht in jedem Abschnitt zusätzlich, **was ursprünglich von wem kam** und
> **was inzwischen überschrieben oder gar nicht erst gemergt wurde**.

---

## 1. Gesamtaufteilung

Gewichtet nach **Aufwand und Schwierigkeit**, nicht nach reiner Zeilenzahl.
Integrations-, Merge-, Nach- und **Projektleitungsarbeit** ist mit eingerechnet
(siehe Abschnitt 1.2 – ein erheblicher Teil davon erzeugt gar keine Codezeilen).

| # | Contributor | Accounts | Anteil (Effort) | Blame-Anteil | Commits |
|---|---|---|---|---|---|
| 1 | **Nikolaj** | `NorkelL`, `Nikolaj Lazic` | **58 %** | 53,1 % | 109 (106 auf `master`: 69 + 37 Merges) |
| 2 | **Tom** | `Vatar007`, `Tom` | **21 %** | 33,2 % | 23 |
| 3 | **Len** | `Len`, `LDK221` | **6 %** | 7,7 %\*\* | 12 |
| 4 | **Claude** (KI) | `Claude` | **4 %** | 0 %\* | 4 (nicht gemergt) + Pair-Arbeit |
| 5 | **Jonas** | `Jonas45677L` | **3,5 %** | 0,8 %\*\* | 11 |
| 6 | **Selma** | `mvogt` | **3 %** | 2,3 % | 7 |
| 7 | **Lennox** | `FlyLennox` | **1,5 %** | 1,3 % | 5 |
| 8 | **Luca** | `rollluca09`, `TuffLuca67` | **1,5 %** | 0,8 % | 3 |
| 9 | **Noah** | `goldfishi08`, `bf5szfzkfg-commits` | **1,5 %** | 0,8 % | 4 |

\* Claudes Code steht unter Nikolajs Commit-Signatur im Repo (Pair-Programming), die eigenen
Claude-Commits liegen auf einem nicht gemergten Branch. Siehe Abschnitt 5.

\*\* Der `SoundManager` wurde von **Len und Jonas gemeinsam** geschrieben, aber nur von Len
commited. Git schreibt die 53 Zeilen deshalb komplett Len zu; im Effort-Anteil sind sie
**je zur Hälfte** angerechnet. Siehe Abschnitt 4.6.

```
Nikolaj  ██████████████████████████████████████████████████████████  58 %
Tom      █████████████████████                                       21 %
Len      ██████                                                       6 %
Claude   ████                                                         4 %
Jonas    ███▌                                                       3,5 %
Selma    ███                                                          3 %
Lennox   █▌                                                         1,5 %
Luca     █▌                                                         1,5 %
Noah     █▌                                                         1,5 %
```

### 1.1 Warum die Effort-Gewichtung vom Blame-Anteil abweicht

| Contributor | Korrektur | Begründung |
|---|---|---|
| Nikolaj | 53,1 % → **58 %** | Der Blame-Wert misst nur Code. Dazu kommen: die komplette Projektleitung und GitHub-Organisation (Abschnitt 1.2), 37 von 41 Merges inkl. Konfliktauflösung, und das Nachziehen fremder Beiträge auf die Projektkonventionen. Ein großer Teil dieser Arbeit erzeugt **null Codezeilen** und ist in `git blame` unsichtbar. |
| Tom | 33,2 % → **21 %** | Drei Gründe: (1) ein großer Teil der Zeilenmasse ist repetitiv – 7 fast identische Rüstungsklassen, 5 Button-Klassen nach demselben Muster; (2) mehrere seiner Beiträge entsprachen nicht den vereinbarten Projektprinzipien und mussten von Nikolaj umgebaut werden (Abschnitt 4.5); (3) sein Inventar-Branch war nicht mergebar und musste von Nikolaj rebased werden. Zeilen ≠ verwertbarer Aufwand. |
| Claude | 0 % → **4 %** | Das komplette A\*-Pathfinding ist algorithmisch das schwierigste Einzelstück im Projekt, steht aber unter Nikolajs Signatur. |
| Jonas | 0,8 % → **3,5 %** | Drei unsichtbare Posten: (1) seine Hälfte am `SoundManager` läuft unter Lens Account; (2) seine Healthbar (6 Commits) wurde nie gemergt; (3) seine Tile-Designs sind Assets und tauchen in der Zeilenzählung nicht auf. Der Aufwand war in allen drei Fällen da. |
| Len | 7,7 % → **6 %** | Die 53 Zeilen `SoundManager` schreibt git komplett ihm zu, obwohl er sie mit Jonas zusammen geschrieben hat – hier zur Hälfte an Jonas abgegeben. |
| Selma | 2,3 % → **3 %** | GameOverScreen und ihre Pause-Buttons wurden von Nikolaj umgeschrieben bzw. gar nicht übernommen – die Vorarbeit ist im Blame unsichtbar. |
| Luca / Lennox | 0,8 / 1,3 % → je **1,5 %** | Die Waffen-Grundklassen kamen von ihnen; die aktuellen Zeilen sind aber größtenteils von Tom bei der Waffenlogik-Überarbeitung ersetzt worden. |

### 1.2 Projektleitung und Organisation (größtenteils Nikolaj)

Diese Arbeit taucht in **keiner** Code-Statistik auf, war aber die Voraussetzung dafür,
dass acht Leute überhaupt parallel an einem Repository arbeiten konnten:

| Aufgabe | Belege im Repo |
|---|---|
| **GitHub-Repository komplett aufgesetzt** | `Initial project scaffolding` (`2026-04-17`), `.gitignore`, Branch-Struktur, Merge-Workflow |
| **Alle Teammitglieder in Git/GitHub eingearbeitet** | `GITHUB-HELP.md` – ein eigens für das Team geschriebener Git/GitHub-Leitfaden („Create Git & GitHub Help Guide for Student Team", `0222b6b`), verlinkt aus der README |
| **Projektplan, Issues und Aufgabenverteilung** | 72 durchnummerierte Issues/PRs im Repo; Branch-Namen wie `48-refactor-split-entities-into-base-monsters-player-subpackages` zeigen die issue-getriebene Arbeitsweise |
| **Repository-Governance** | `CODEOWNERS` (`45228f8`), Issue-Templates (`.github/ISSUE_TEMPLATE/`), `SECURITY.md`, `CONTRIBUTING.md`, MIT-`LICENSE` – **alle vier von Nikolaj** |
| **Grundprojekt lauffähig vorbereitet** | Greenfoot-Libs + Module eingebunden, IntelliJ-Run-Config `Run_Game.xml`, Windows-SDK-Fix (PR #30), README mit kompletter Setup-Anleitung für das Team |
| **Alle Merges reviewed, Konflikte gefixt** | 37 von 41 Merge-Commits (90 %), davon 23 PR-Merges; dazu 12 `Merge branch 'master' into <feature>`-Commits, die reine Konfliktauflösung waren |
| **Fremde Branches lauffähig gemacht** | siehe Abschnitte 4.1 und 4.5 |

Die Merge-Verteilung macht das am deutlichsten: **90 % aller Integrationen liefen über Nikolaj.**
Jeder dieser Merges bedeutete Review, Konfliktauflösung und oft Nacharbeit am fremden Code.

---

## 2. Aufteilung nach Feature-Bereichen

| Bereich | Hauptverantwortlich | Beteiligt | Anmerkung |
|---|---|---|---|
| Projekt-Setup, IntelliJ/Greenfoot-Integration, README, Lizenz, CI-Templates | Nikolaj | – | komplett allein |
| Map-/Dungeon-Generierung (`DungeonLevel`, Räume, Korridore, Seed) | Nikolaj | Selma (Pause-Hook), Jonas (Tiles), Tom | Kern von Nikolaj |
| A\*-Pathfinding (`ASharpPathfinding`) | **Claude** | Nikolaj (Integration, Rock→Wall, Refactor) | siehe Abschnitt 5 |
| Player-Bewegung & Kollision (`Player`, `MovingActor`, `Direction`) | Nikolaj | Tom, Len, Selma (`hit`-Methode) | mehrfach überarbeitet |
| Monster-Basis (`BaseMonster`, Gnome, Orc, Zombie) | Noah | Nikolaj, Tom, Len | Grundgerüst von Noah |
| Neue Monster + Animationen (Skeleton, Zombie-Sprites, Walking/Attacking-Ordner) | Tom | Len | |
| Hauptmenü, Buttons, Settings-World | Tom | Nikolaj | |
| Inventar (Slots, Drag & Drop, Backpack, Visualizer) | Tom | Nikolaj (Rebase + Logik-Finish) | größte gemeinsame Baustelle |
| Item-System (`Item`, `Pickable`, `Useable`, `ItemTyp`) | Nikolaj + Len | Tom | |
| Rarity-System, Tooltips (`Rarity`, `OnHover`, `ItemText`, `FontManager`) | Nikolaj | – | komplett allein |
| Rüstungen (7 Klassen + `Armor`) | Tom | Nikolaj | |
| Waffen (`Waffen`, Messer, Stock, Sword) | Luca + Lennox | Tom, Nikolaj | Grundlage Luca/Lennox, heutiger Code v. a. Tom |
| Bogen & Pfeil-Animation (`Bow`, `Arrow`, `BowSprite`, `Explosion`) | Tom | Luca (erste `Bow`) | |
| Chest + Loot-Drop | Len | Nikolaj | |
| Tränke & Essen (HealthPotion, Apple, Cookie, HoneyBottle, UnstablePotion) | Len (Design + Logik) | Nikolaj (Refactor, `UnstablePotion`) | |
| Healthbar | Nikolaj | Len, Tom | **Jonas' Version wurde nie gemergt** |
| XP-Bar & Level-System | Len | Nikolaj (Fixes + Finish) | |
| Pause-Screen | Selma | Nikolaj (Buttons neu geschrieben) | |
| Save-/Load-Game (`ItemData`, SaveGameButton, LoadGameButton) | Nikolaj | Tom (LoadGameButton-Basis) | |
| Sound & Hintergrundmusik (`SoundManager`) | **Len + Jonas (je 50 %)** | – | gemeinsam geschrieben, nur Len hat commited – siehe 4.6 |
| Fenster-/Fullscreen-Handling (`WindowSizeManager`) | Nikolaj | **Claude** (Hilfe) | siehe Abschnitt 5 |
| Floor-/Wall-Tiles-Design | Jonas | Nikolaj (Kollision/Paint-Order) | |
| `ImprovedGreenfootImage`, Bild-Loader | Nikolaj | Len (Verschieben nach `util`), Tom (Loader-Überarbeitung) | |
| Merges, Konfliktauflösung, Branch-Verwaltung | Nikolaj | Tom (2), Len (1) | 35 von 41 Merges |

---

## 3. Contributors im Detail

### 3.1 Nikolaj — 58 % · *Projektleitung*

**Accounts:** `NorkelL <n.l.lazic@outlook.com>` und `Nikolaj Lazic <lazicnikolaj@gmail.com>` (hier zusammengefasst)
**Commits:** 106 auf `master` (69 inhaltliche + 37 Merge-Commits), 109 über alle Branches
**Blame:** 2.360 von 4.443 Zeilen (53,1 %)

**Allein gebaut:**

| Datei | Zeilen | Inhalt |
|---|---|---|
| `core/GameStarter.java` | 134 | Einstiegspunkt des Spiels, World-Wechsel, Game-Loop-Anbindung \* |
| `util/WindowSizeManager.java` | 104 | Fenster-/Fullscreen-Handling (mit Claude-Hilfe, s. 5.2) |
| `items/util/OnHover.java` | 82 | Tooltip-System |
| `util/FontManager.java` | 58 | zentrales Font-Loading |
| `items/util/Rarity.java` | 51 | Seltenheitsstufen + Farbcodierung |
| `ui/ItemText.java` | 21 | Item-Textrendering |
| `items/util/ItemData.java` | 7 | Save-Datenmodell |
| `items/util/Pickable.java` | 4 | Interface |

\* `GameStarter.java` ist inhaltlich zu 100 % von Nikolaj. `git blame` weist Tom 6 Zeilen zu,
davon sind aber **3 reine Leerzeilen**, eine ein `import`-Statement und zwei Einzelzeilen
innerhalb von Methoden, die Nikolaj geschrieben hat (`Greenfoot.setWorld(new MainMenu(this))`
und eine Methodensignatur). An der Funktionsweise der Klasse ändert das nichts – ein gutes
Beispiel dafür, warum `git blame` allein kein Maß für Beitrag ist.
Alle übrigen Dateien in dieser Tabelle enthalten **keine einzige fremde Zeile**.

**Rolle:** Projektleitung. Neben dem Code lagen Organisation, Planung, Einarbeitung des Teams
und die gesamte Integration bei ihm – ausführlich in **Abschnitt 1.2**.

**Hauptbeiträge:**

- **Projekt-Fundament und Team-Setup** (`2026-04-17` – `2026-04-27`): Repository aufgesetzt,
  Initial Scaffolding, kombinierte Quellen, Greenfoot-Libs + Module, IntelliJ-Run-Configs,
  Windows-SDK-Fix, `.gitignore`, MIT-Lizenz, CODEOWNERS, Issue-Templates, README mit
  Setup-Anleitung und `GITHUB-HELP.md` als Git-Leitfaden fürs Team. Damit war das Projekt
  für alle acht Mitglieder überhaupt erst benutzbar.
- **Planung und Aufgabenverteilung**: Projektplan, Anlegen und Zuweisen der Issues
  (72 Issues/PRs über die Projektlaufzeit), Definition der Branch- und Merge-Konventionen.
- **Map-Generierung** (`feature/map-gen`, 8 Commits): Räume, Korridore, Seed-System, Entrance/Exit,
  Grundstruktur von `DungeonLevel.java` (heute 177 von 314 Zeilen = 56 %).
- **Refactoring der Paketstruktur** (PR #55): Aufteilung von `entities` in `base` / `enemies` /
  `util`, dabei u. a. `ASharpPathfinding` aus `src/entities/` nach `src/entities/util/` verschoben.
- **Item-System-Ausbau**: Rarity, Tooltips, `OnHover`, `ItemText`, `FontManager` (PR #59, `2026-06-20`).
- **Save-/Resume-Logik** (`feature/save-game`, 3 Commits, Juli): kompletter Speicher-/Ladezyklus
  inkl. Inventar-Serialisierung; `SaveGameButton` (62 von 72 Zeilen).
- **Health- + XP-Bar-Finalisierung** (`2026-08-11`): `Healthbar.java` zu 40 von 56 Zeilen,
  `XPBar.java` zu 25 von 51 Zeilen.
- **Fullscreen-Fix + Load-Game-Implementierung** (PR #72, `2026-08-14`): letzte zwei Commits im Repo.
- **Integrationsarbeit / Review**: 37 von 41 Merge-Commits auf `master` (90 %), davon 23 PR-Merges.
  Jeder davon bedeutete Review des fremden Codes und Konfliktauflösung; inkl. der aufwendigen Rebases
  (`feature/Inventory-rebase(master)`, PR #57) und mehrerer `Merge branch 'master' into <feature>`
  Konfliktauflösungen.

**Nacharbeit an fremdem Code (wichtig für die Bewertung):**

- **Selmas Pause-Buttons komplett neu geschrieben** – Details in Abschnitt 4.1.
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

### 3.2 Tom — 21 %

**Accounts:** `Vatar007 <tom.lenny.ruppert@gmail.com>`, `Tom <tom.lenny.ruppert@gmail.com>`
**Commits:** 23 (inkl. 3 Merges)
**Blame:** 1.476 Zeilen (33,2 %) · **254 Asset-Dateien** angefasst (mit Abstand am meisten)

**Größte eigene Blöcke:**

| Datei | Toms Zeilen / gesamt | Anteil |
|---|---|---|
| `ui/InventorySlot.java` | 164 / 238 | 69 % |
| `ui/buttons/KeyButton.java` | 115 / 146 | 79 % |
| `ui/worlds/Backpack.java` | 111 / 132 | 84 % |
| `ui/InventoryVisualizer.java` | 85 / 123 | 69 % |
| `items/waffen/BowSprite.java` | 75 / 76 | 99 % |
| `items/waffen/Arrow.java` | 69 / 70 | 99 % |
| `ui/worlds/SettingsWorld.java` | 67 / 84 | 80 % |
| `ui/Settings.java` | 50 / 53 | 94 % |
| `ui/Explosion.java` | 28 / 28 | 100 % |

**Hauptbeiträge:**

- **Main-Menu-Grundlage** (`2026-04-28` – `2026-05-19`, 4 Commits): `MainMenu`-Buttons,
  `StartButton`, `SettingsButton`, `LoadGameButton`, `StandardButton`, `Clickable`, `DarkFilter`, `UI`.
- **Projekt-Struktur-Commit** (`Structure`, PR #34): legte `Hitting`, `Pickable`, `Healthbar`
  und `XPBar` als Gerüst an – die beiden Bars wurden später von anderen ausgefüllt.
- **Inventar-System** (`feature/inventoryslots`, Mai–Juni): Backpack-Inventar, auswählbare Slots,
  `getActiveSlot()`, `useItem()`, Drag & Drop (`GhostItem`), `InventoryOverlay`.
- **Settings** (`2026-06-12`): komplettes Settings-Menü inkl. `KeyButton` für Tastenbelegung.
- **Rüstungssystem** (`2026-06-15` / `2026-06-29`): `Armor` + 6 konkrete Teile (Leather/Iron/Gold,
  jeweils Helm + Body).
- **Monster-Erweiterung** (`2026-07-07`): `Skeleton`, `Zombie`-Überarbeitung, neuer `LoadImage`-Ansatz
  mit getrennten `Walking`/`Attacking`-Ordnern für Animationen.
- **Item-Effekte + neuer Image-Loader** (`2026-07-12`).
- **Bogen & Pfeil-Animation** (`2026-08-07`, PR #68): `Arrow`, `BowSprite`, `Explosion` –
  überschrieb dabei Lucas ursprüngliche `Bow`-Klasse fast vollständig (siehe 4.3).

**Anmerkung zur Gewichtung:** Toms Zeilenanteil (33,2 %) liegt deutlich über seinem
Effort-Anteil (21 %). Gründe:

1. **Repetitiver Code.** Ein erheblicher Teil der Zeilenmasse ist Copy-Paste-nah – 7 Rüstungsklassen
   mit je 6–7 Zeilen identischer Struktur, 5 Button-Klassen nach demselben Muster.
   Das erzeugt viele Zeilen bei geringer Schwierigkeit.
2. **Abweichung von den Projektprinzipien.** Mehrere seiner Beiträge entsprachen nicht den
   vereinbarten Konventionen (Paketstruktur, Vererbungshierarchie, Bild-Loading) und mussten von
   Nikolaj nachträglich umgebaut werden – im Detail in **Abschnitt 4.5**.
3. **Nicht mergebarer Branch.** `feature/Inventory` musste von Nikolaj auf `master` rebased werden,
   bevor er überhaupt integrierbar war (PR #57).

Das schmälert nicht den Umfang seiner Arbeit – Tom ist unbestritten der zweitgrößte Beitragende
und hat mit 254 berührten Asset-Dateien den größten Anteil an der Grafikeinbindung. Der
Effort-Anteil bildet aber ab, wie viel davon **ohne Nacharbeit verwertbar** war.

---

### 3.3 Len — 6 %

**Accounts:** `Len <lenkoehler22@gmail.com>`, `LDK221 <lenkoehler22@gmail.com>`
**Commits:** 12 (inkl. 1 Merge) · **Blame:** 343 Zeilen (7,7 %) · 15 Asset-Dateien

**Gemeinsam mit Jonas:** `util/SoundManager.java` (53 Zeilen) – zu **je 50 %** von beiden,
auch wenn git die Datei zu 100 % Len zuschreibt (siehe 4.6).

**Hauptbeiträge:**

- **Chest-System** (`2026-05-04` – `2026-05-10`, 4 Commits): Chest-Design, Platzierung in der Welt,
  Random-Item-Drop, `ItemTyp`-Enum, `TestItem`; Öffnen erst per E-Taste, dann auf Mausklick umgestellt.
  `blocks/Chest.java` heute 52 von 59 Zeilen = 88 % Len.
- **Item-Designs** (`2026-05-13`): HealthPotion, Apple, Cookie, HoneyBottle – Sprites *und*
  erste Klassen. Nikolaj hat die Klassen später refactored, Lens Design-Assets sind geblieben.
- **`ImprovedGreenfootImage` nach `util` verschoben** (PR #53) – kleiner, aber sauberer Refactor.
- **HealthPotion-Logik fertiggestellt** (PR #61, `2026-07-07`): benutzbar, spawnt aus Kiste,
  wird nach Nutzung aus dem Inventar entfernt.
- **XP-Bar & Level-up-System** (PR #67, `2026-07-28`): `XPBar.java` 22 von 51 Zeilen;
  Nikolaj hat am `2026-08-11` Bugfixes und die finale Einbindung ergänzt.
- **Sounds & Hintergrundmusik** (PR #71, `2026-08-11`): `SoundManager` – **gemeinsam mit Jonas
  entwickelt, Anteil je 50 %**. Len hat die Commits gemacht, deshalb steht die Datei in git
  vollständig unter seinem Namen (siehe 4.6). Eines der letzten Features vor Projektabschluss.

---

### 3.4 Claude (KI) — 4 %

Siehe Abschnitt 5 für die vollständige Darstellung.

Kurz: **A\*-Pathfinding komplett allein gebaut**, **Hilfestellung beim `WindowSizeManager`**,
plus vier eigene Commits auf `claude/gridworld-dual-cellsize`, die **nie gemergt wurden**.

---

### 3.5 Jonas — 3,5 %

**Account:** `Jonas45677L <Jonaslehndorff@gmail.com>` · **Commits:** 11 (inkl. 1 Merge) ·
**Blame:** nur 34 Zeilen (0,8 %) · 8 Asset-Dateien

Jonas ist der klarste Fall, bei dem der Blame-Wert den tatsächlichen Aufwand **stark unterschätzt** –
gleich dreifach: Grafik-Assets zählen nicht als Zeilen, sein Healthbar-Branch wurde nie gemergt,
und seine Hälfte am `SoundManager` läuft unter Lens Account:

- **Floor- und Wall-Tiles** (`2026-05-05` – `2026-05-14`, 5 Commits): Tile-Designs, schwarzer Boden
  außenrum, undurchdringbare Wände. Das ist überwiegend **Grafik-Arbeit** und taucht in der
  Zeilenzählung praktisch nicht auf. Im Code geblieben: `blocks/Wall.java` (14 von 16 Zeilen = 88 %)
  und 16 Zeilen in `DungeonLevel.java`.
  Dabei war ein eigener Revert nötig (`37f23de`), weil die erste Version die Map unspielbar machte;
  Nikolaj musste danach die Kollisionsabfrage (`getObjectsAt` in `canMove`/`isBlocked`) und die
  Paint-Order nachziehen.
- **Healthbar** (`2026-06-08` – `2026-06-15`, 6 Commits auf Branch `Healthbar`):
  **komplett verworfen** – der Branch wurde nie gemergt (siehe 4.2). Die Healthbar in `master`
  stammt von Nikolaj und Len.
- **Sounds & Hintergrundmusik** (`2026-08-11`): `util/SoundManager.java` **gemeinsam mit Len
  entwickelt, Anteil je 50 %**. Die Commits liefen über Lens Account, weshalb git die 53 Zeilen
  vollständig Len zuschreibt – Jonas' Hälfte ist in **keiner** Statistik dieses Dokuments
  sichtbar (siehe 4.6). Das ist sein größter erhaltener Code-Beitrag im Projekt.

---

### 3.6 Selma — 3 %

**Account:** `mvogt <selma.v@gmx.de>` · **Commits:** 7 (2 davon auf `master`) · **Blame:** 102 Zeilen (2,3 %)

**Hauptbeiträge:**

- **`hit`-Methode im Player** (`2026-05-04`) – frühe Kampf-Grundlage.
- **GameOverScreen** (`2026-05-29` / `2026-06-09`, Branch `KockBack`) – **nicht gemergt**,
  existiert heute nicht in `master` (siehe 4.2).
- **PauseScreen** (`2026-07-14` – `2026-07-17`, PR #70): `ui/PauseScreen.java` gehört ihr zu
  **100 %** (22 von 22 Zeilen) – eine der wenigen Dateien, die unverändert von ihr stammt.
  Dazu 58 Zeilen Integration in `DungeonLevel.java` (heute noch 48 davon = 15 % der Datei).
- **Pause-Button-Klassen angelegt**: `PauseButtons`, `SaveGameButton`, `restartButton`,
  `settingPauseButton` – als Stubs mit 8–14 Zeilen. Diese wurden anschließend von Nikolaj
  ausimplementiert (siehe 4.1).

---

### 3.7 Lennox — 1,5 %

**Account:** `FlyLennox <menlennox@gmail.com>` · **Commits:** 5 · **Blame:** 58 Zeilen (1,3 %)

- **Waffen-Klassen** (alle am `2026-05-11`, vier davon mit der Commit-Message „yooo"):
  `Sword.java` neu angelegt, Ausbau von `Waffen.java` (heute 40 von 60 Zeilen = 67 % Lennox – sein
  größter erhaltener Block), Beiträge in `Messer.java` und `Stock.java`.
- Kurzer, aber kompakter Einsatz an genau einem Tag; die Waffen-Basisklasse ist bis heute im Einsatz.

---

### 3.8 Luca — 1,5 %

**Accounts:** `TuffLuca67 <luca@roll-consult.de>`, `rollluca09 <luca@…>` / `<Luca@…>` (Groß-/Kleinschreibung
der Mail variiert, git zählt das als zwei Identitäten) · **Commits:** 3 · **Blame:** 35 Zeilen (0,8 %)

- **Waffen-Grundgerüst** (`2026-05-04`): legte `Waffen.java`, `Messer.java` und `Stock.java` an –
  die Basis, auf der Lennox und Tom später aufgebaut haben.
- **New weapons** (`2026-06-01` / `2026-06-02`): erste `Bow.java`.
  Von dieser Datei sind heute noch 7 von 66 Zeilen (11 %) von Luca – Tom hat den Bogen bei der
  Pfeil-Animation im August weitgehend neu geschrieben (siehe 4.3).

---

### 3.9 Noah — 1,5 %

**Accounts:** `goldfishi08 <bf5szfzkfg@privaterelay.appleid.com>`,
`bf5szfzkfg-commits <bf5szfzkfg@privatere-lay.appleid.com>` (Apple-Private-Relay, deshalb zwei
leicht unterschiedliche Adressen) · **Commits:** 4 · **Blame:** 35 Zeilen (0,8 %)

- **`BaseMonster.java` angelegt** (PR #35, `2026-04-28`) inklusive verbesserter Death-Methode im Player.
  Das ist die Basisklasse, von der **alle vier Monster** im Spiel erben – strukturell wichtiger,
  als die Zeilenzahl vermuten lässt.
- **Gnome, Orc, Zombie** angelegt (`2026-05-04`, PR #39/#40) mit der `receiveHit`-Methode.
- **`receiveHit` von Gnome nach BaseMonster hochgezogen** – sauberer Refactor auf eigene Initiative.
- Heute stehen von `BaseMonster.java` noch 20 von 127 Zeilen (16 %) von Noah; der Rest kam durch
  Nikolajs Pathfinding-Anbindung und Toms Animations-Umbau dazu.

---

## 4. Überschriebene und nicht gemergte Arbeit

Dieser Abschnitt hält fest, wo Arbeit geleistet wurde, die im aktuellen `master` **nicht mehr
oder nur noch teilweise sichtbar** ist.

### 4.1 Selmas Pause-Buttons → von Nikolaj neu geschrieben

Selma legte am `2026-07-17` vier Button-Klassen als funktionsfähige Stubs an. Am `2026-08-11`
implementierte Nikolaj sie im Commit `95486cc` („all buttons implenemted (except load game)")
vollständig aus:

| Datei | Selma (heute) | Nikolaj (heute) | Tom |
|---|---|---|---|
| `SaveGameButton.java` | 7 | **62** | 2 |
| `restartButton.java` | 5 | **44** | 2 |
| `settingPauseButton.java` | 6 | **43** | 2 |
| `PauseButtons.java` | **12** | 1 | – (13 Len) |

Selmas Struktur und Namensgebung sind geblieben, die eigentliche Logik ist neu.
`ui/PauseScreen.java` selbst ist dagegen unverändert zu 100 % ihre Datei.

### 4.2 Nie gemergte Branches

| Branch | Autor | Commits | Inhalt | Status |
|---|---|---|---|---|
| `origin/Healthbar` | **Jonas** | 6 | komplette eigene Healthbar-Implementierung + PNG | **verworfen** – die Healthbar in `master` stammt von Nikolaj (40 Z.), Len (12 Z.) und Tom (4 Z.) |
| `origin/KockBack` | **Selma** | 4 | GameOverScreen + Knockback | **verworfen** – `GameOverScreen` existiert in `master` nicht |
| `origin/claude/gridworld-dual-cellsize` | **Claude** | 5 | `GridWorld` mit Dual-Grid, pixelweise Player-Bewegung | **verworfen** – `src/world/GridWorld.java` existiert in `master` nicht |
| `origin/PauseScreen` | Selma | 5 | frühe PauseScreen-Version | ersetzt durch `pauseScreenFinal` (PR #70), der gemergt wurde |
| `origin/feature/Hitting` | Tom / Nikolaj | 2 | Hitting-Interface-Experiment | teilweise übernommen, `Hitting.java` wurde erst gelöscht (`52bb88e`), dann wieder eingefügt (`fd03069`, „readded Hitting (is needed for monsters)") |
| `origin/enemy/ghost` | Nikolaj | 1 | `Ghost`-Monster (`d677f36`) | **nicht in `master`** – die Klasse existiert dort nicht |

### 4.3 Waffen: Luca/Lennox → Tom

Die Waffen sind der Bereich mit dem stärksten Autorenwechsel:

- `Waffen.java`: von **Luca** angelegt (`2026-05-04`) → heute 40 Z. **Lennox**, 5 Z. Tom, 9 Z. Luca, 6 Z. Nikolaj
- `Sword.java`: von **Lennox** angelegt → heute 7 Z. Lennox, 5 Z. Luca, 5 Z. Nikolaj
- `Messer.java` / `Stock.java`: von **Luca** angelegt → heute gemischt Luca/Lennox/Nikolaj
- `Bow.java`: von **Luca** angelegt (`2026-06-02`) → heute **54 von 66 Zeilen von Tom** (82 %),
  Luca nur noch 7 Zeilen. Tom hat den Bogen bei der Arrow-Animation (`2026-08-07`) neu aufgebaut.

### 4.4 Weitere Umschreibungen

- **`Healthbar.java` und `XPBar.java`** wurden von **Tom** im `Structure`-Commit (`2026-04-28`)
  als leere Gerüste angelegt. Gefüllt wurden sie erst Monate später von Len (XP-Bar) und
  Nikolaj (beide Bars). Von Toms Gerüst sind noch 4 Zeilen pro Datei übrig.
- **`ASharpPathfinding.java`** existierte kurzzeitig doppelt: erst unter `src/entities/`,
  dann beim Refactoring nach `src/entities/util/` verschoben und die alte Datei gelöscht
  (`15b3f3a` „Delete src/entities/ASharpPathfinding.java").
- **Toms Item-Effekte-Commit** (`18fe2b0`, `2026-07-12`) wurde direkt danach von Nikolaj
  in `dbe9d9d` („working master with fixed misc items") repariert, weil der Stand nicht lief.
- **`DungeonLevel.java`** ist die am stärksten geteilte Datei im Projekt:
  Nikolaj 177 Z. · Nikolaj (Zweitaccount) 55 Z. · Selma 48 Z. · Tom 17 Z. · Jonas 16 Z. · Len 1 Z.

### 4.5 Nacharbeit an Toms Beiträgen

Tom hat den zweitgrößten Anteil am Projekt, aber ein wiederkehrendes Muster ist, dass seine
Beiträge nicht den vereinbarten Projektprinzipien folgten und von Nikolaj nachgezogen werden
mussten. In Zahlen: **von den 28 Dateien, die Tom angelegt hat (1.139 Zeilen), stammen heute
21 % der Zeilen von Nikolaj** – reine Nacharbeit an fremdem Code.

| Fall | Was passierte | Belege |
|---|---|---|
| **Flache Paketstruktur** | Toms `Structure`-Commit (PR #34) legte Klassen ohne die vereinbarte Paket-Trennung ab. Nikolaj musste `entities` nachträglich in `base` / `enemies` / `util` aufteilen. | Issue #48 → PR #55 `48-refactor-split-entities-into-base-monsters-player-subpackages` |
| **Nicht mergebarer Inventar-Branch** | `feature/Inventory` war gegen `master` divergiert und ließ sich nicht mergen. Nikolaj hat ihn manuell rebased und die Inventar-Logik fertiggestellt. | `ee71c3a` „working rebased branch", `f3456c0` „refactoring + finsished inventory logic", PR #57; danach `92f74f0` Größenfix an `InventorySlot.java` |
| **Nicht lauffähiger Stand** | Toms Item-Effekte-Commit hinterließ ein kaputtes `master`. Nikolaj hat es direkt danach repariert und dabei `UnstablePotion.java` neu geschrieben. | `18fe2b0` → `dbe9d9d` „working master with fixed misc items" |
| **Paralleler Image-Loader** | Tom führte einen eigenen `LoadImage`-Ansatz in den Item-Klassen ein, obwohl mit `util/ImprovedGreenfootImage` bereits eine zentrale Lösung existierte. | `bd8f304`, `18fe2b0` vs. `util/ImprovedGreenfootImage.java` (Nikolaj, 78 von 79 Z.) |
| **Plattform-Fixes** | Nach dem großen Juli-Merge lief die Windows-Version nicht mehr; Nikolaj hat nachgebessert. | `d7db0e5` „fixed windows version" |
| **`Hitting`-Interface** | Von Tom im `Structure`-Commit angelegt, von Nikolaj erst gelöscht, dann wieder eingefügt, weil die Monsterlogik es doch brauchte. | `52bb88e` → `fd03069` „readded Hitting (is needed for monsters)" |
| **Leere Gerüste** | `Healthbar.java` und `XPBar.java` wurden von Tom nur als Stubs angelegt und lagen ~3,5 Monate ungenutzt, bis Len und Nikolaj sie ausfüllten. | siehe 4.4 |

**Einordnung:** Das ist keine Abwertung seiner Arbeit – Main-Menu, Inventar, Settings, Rüstungen
und die Pfeil-Animation sind substanzielle Features, und mit 254 berührten Asset-Dateien trägt er
den größten Teil der Grafikeinbindung. Es erklärt aber, warum sein Effort-Anteil (21 %) unter
seinem Zeilenanteil (33,2 %) liegt: ein messbarer Teil seines Outputs wurde erst durch fremde
Nacharbeit nutzbar, und dieser Aufwand gehört zu Nikolaj.

### 4.6 SoundManager: Jonas' Anteil ist in git unsichtbar

`src/util/SoundManager.java` (53 Zeilen) wurde von **Len und Jonas gemeinsam** geschrieben.
Weil die Commits über Lens Account liefen, weist git die Datei zu **100 % Len** zu:

```
2026-08-11  ac3db27  Len  Sounds und Hintergrundmusik implementiert
2026-08-11  1d84a89  Len  Sounds und Hintergrundmusik implementiert
            PR #71   feature/sounds → master
```

`git blame src/util/SoundManager.java` → 53 von 53 Zeilen `lenkoehler22@gmail.com`,
0 Zeilen Jonas. Es gibt im gesamten Repository **keinen technischen Hinweis** auf seine
Beteiligung – kein `Co-authored-by`-Trailer, kein eigener Commit, kein Branch.

**Korrektur in diesem Dokument:** Der Sound-Bereich wird **je zur Hälfte** Len und Jonas
zugerechnet. Alle Blame- und numstat-Tabellen in Abschnitt 6 zeigen weiterhin die
unkorrigierten Rohwerte – die Korrektur steckt ausschließlich in den Effort-Prozenten
in Abschnitt 1.

Das ist der einzige bekannte Fall dieser Art. Sollte es weitere Pair-Programming-Sessions
gegeben haben, die nur ein Beteiligter commited hat, sind sie hier nicht erfasst, weil sie
sich aus dem Git-Verlauf nicht rekonstruieren lassen.

---

## 5. Claude (KI) im Detail

### 5.1 A\*-Pathfinding — allein von Claude

`src/entities/util/ASharpPathfinding.java` (114 Zeilen) wurde **vollständig von Claude gebaut**.
Es ist die algorithmisch anspruchsvollste Einzelkomponente im Projekt: A\*-Suche über das
Dungeon-Grid mit Heuristik, Open/Closed-Set und Pfadrekonstruktion, damit die Monster den Spieler
durch die prozedural erzeugten Räume und Korridore verfolgen können.

Der Commit trägt Nikolajs Signatur und benennt es explizit:

```
4f09be9  2026-05-14  added monster pathfinding logic (A* mit hilfe von claude) + static map größe
```

Weil der Commit unter Nikolajs Account läuft, weist `git blame` alle 114 Zeilen ihm zu –
inhaltlich stammen sie von Claude. Nikolaj hat danach die Integration übernommen
(`Rock` → `Wall`-Umstellung, Paket-Refactoring, Anpassungen im August).

### 5.2 WindowSizeManager — Nikolaj mit Claude-Hilfe

`src/util/WindowSizeManager.java` (104 Zeilen) hat **Nikolaj geschrieben und dabei Claude zu Hilfe
geholt**. Die Klasse regelt Fenstergröße, Maximieren und Fullscreen-Verhalten – ein Bereich, in dem
Greenfoot wenig Unterstützung bietet und einiges an Swing-/AWT-Handling nötig ist.

Entstanden in drei Commits (`2026-08-11` bis `2026-08-14`), zuletzt im Rahmen von PR #72
(`NorkelL/fullseceen-fix`).

### 5.3 Nicht gemergte Claude-Commits

Vier Commits unter der Signatur `Claude <noreply@anthropic.com>` liegen auf
`claude/gridworld-dual-cellsize` und wurden **nie nach `master` gemergt**:

| Commit | Datum | Inhalt |
|---|---|---|
| `e61fcd7` | 2026-06-02 | `GridWorld` mit Dual-Grid (Tile- + Pixel-Raster), 9 Dateien, +285/−92 |
| `2307cbc` | 2026-06-02 | smoothe pixelweise Player-Bewegung auf feinem Raster |
| `cbc24c6` | 2026-06-02 | diagonale WASD-Bewegung ohne Priorisierung, ~30 % langsamer |
| `8128b1c` | 2026-06-02 | Tuning: `SPEED_DIVISOR` 28 → 40 |

`src/world/GridWorld.java` existiert in `master` nicht. Die smoothe Spielerbewegung wurde
stattdessen von Nikolaj direkt in `master` gelöst
(`80f48af`, „fixed mainmenu clicking bugs + smoother player movement + fixed room-gen not enterable bug").

Diese vier Commits sind **nicht** in Claudes 4 % eingerechnet – der Anteil ergibt sich aus
A\*-Pathfinding (allein) und der WindowSizeManager-Hilfe.

---

## 6. Rohdaten

### Blame über alle 75 Java-Dateien in `src/` (4.443 Zeilen)

| Contributor | Zeilen | Anteil |
|---|---|---|
| Nikolaj | 2.360 | 53,1 % |
| Tom | 1.476 | 33,2 % |
| Len | 343 | 7,7 % |
| Selma | 102 | 2,3 % |
| Lennox | 58 | 1,3 % |
| Noah | 35 | 0,8 % |
| Luca | 35 | 0,8 % |
| Jonas | 34 | 0,8 % |
| Claude | 0\* | 0 %\* |

\* Claudes Code läuft unter Nikolajs Commit-Signatur, siehe 5.1.

**Unkorrigierte Rohwerte.** Zwei bekannte Zuordnungsfehler sind hier bewusst *nicht* bereinigt:
Claudes 114 A\*-Zeilen stehen bei Nikolaj (5.1), und Jonas' Hälfte der 53 `SoundManager`-Zeilen
steht bei Len (4.6). Beides ist nur in den Effort-Prozenten in Abschnitt 1 korrigiert.

### Geschriebene Zeilen über die Projektlaufzeit (`git log --numstat`, nur `src/*.java`, ohne Merges)

| Contributor | hinzugefügt | gelöscht | netto |
|---|---|---|---|
| Nikolaj (`NorkelL`) | 2.513 | 976 | +1.537 |
| Tom | 2.110 | 274 | +1.836 |
| Nikolaj (`Nikolaj Lazic`) | 1.335 | 546 | +789 |
| Len | 491 | 139 | +352 |
| Jonas | 230 | 160 | +70 |
| Selma | 136 | 17 | +119 |
| Lennox | 132 | 49 | +83 |
| Noah | 90 | 10 | +80 |
| Luca | 66 | 21 | +45 |

Die hohe Löschzahl bei Nikolaj (1.522 Zeilen über beide Accounts) spiegelt genau die
Refactoring- und Integrationsarbeit aus Abschnitt 4 wider.

### Assets (PNG / GIF / WAV / MP3), Anzahl berührter Dateien

| Contributor | Dateien |
|---|---|
| Tom | 254 |
| Nikolaj (`NorkelL`) | 70 |
| Nikolaj (`Nikolaj Lazic`) | 66 |
| Len | 15 |
| Jonas | 8 |
| Luca | 6 |

### Merge-Commits auf `master`

| Contributor | Merges |
|---|---|
| Nikolaj | 37 |
| Tom | 3 |
| Len | 1 |
| **Nikolaj Anteil** | **90 %** |

---

