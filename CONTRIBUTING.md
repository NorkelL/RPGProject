# Mitwirken am Projekt

## Git-Workflow

### Grundregeln

- `main` ist der stabile Branch – **nie direkt in `main` pushen**.
- Jedes Feature, jeder Bugfix, jede größere Änderung bekommt einen eigenen Branch.
- Änderungen kommen nur über Pull Requests (PR) in `main`.
- Mindestens **eine andere Person** reviewed jeden PR, bevor er gemergt wird.

### Branch erstellen

```bash
git checkout main
git pull                          # immer von aktuellem Stand ausgehen
git checkout -b feature/map-generation
```

Namensschema: `feature/`, `fix/`, `docs/` als Präfix, dann ein kurzer beschreibender Name
(Kleinbuchstaben, Bindestriche statt Leerzeichen):

```
feature/procedural-dungeon
feature/item-pickup
fix/player-collision-off-screen
docs/setup-anleitung
```

### Änderungen pushen und PR öffnen

```bash
git push -u origin feature/mein-feature
```

Dann auf GitHub/GitLab einen Pull Request öffnen:
- Titel: kurz und beschreibend
- Beschreibung: Was wurde geändert? Warum? Gibt es etwas, worauf der Reviewer besonders
  achten soll?
- Bei Unsicherheit: als Draft-PR öffnen und um Feedback bitten

### Merge-Konflikte lösen

Vor dem Öffnen eines PRs den eigenen Branch auf `main` aktualisieren:

```bash
git fetch origin
git merge origin/main     # oder: git rebase origin/main
```

Bei Konflikten: lokal lösen, **nicht** einfach Änderungen anderer überschreiben.
Im Zweifel kurz im Team fragen.

---

## Commit-Nachrichten

**Format:** Klares Imperativ, eine Zeile, max. 72 Zeichen, kein Punkt am Ende.

```
Add tile-based collision detection
Fix player movement off-screen bug
Refactor map loader to use JSON parser
Update README with level editor instructions
Remove unused debug output in Enemy class
```

**Regeln:**
- Beschreibt **was** geändert wurde und **warum** (wenn nicht offensichtlich)
- Kein: `fix`, `changes`, `stuff`, `update`, `wip` als alleiniger Commit-Text
- Keine auskommentierten Code-Blöcke commiten – lieber löschen oder Branch behalten
- Sprache: Englisch oder Deutsch – im ersten Meeting einigen und dann konsequent durchhalten

**Optionaler erweiterte Beschreibung** (für komplexe Änderungen):

```
Add A* pathfinding for enemy movement

Enemies now navigate around walls instead of walking straight toward
the player. Uses grid coordinates, not pixel positions.
Pathfinding recalculates every 10 ticks to avoid performance issues.
```

---

## Code-Stil

- Standard Java-Konventionen ([Oracle Java Style Guide](https://www.oracle.com/java/technologies/javase/codeconventions-introduction.html))
- `camelCase` für Variablen und Methoden
- `PascalCase` für Klassen und Interfaces
- `UPPER_SNAKE_CASE` für Konstanten
- Keine „Magic Numbers" – Konstanten mit sprechenden Namen (`TILE_SIZE = 32`, nicht `32`)
- **Formatter im ersten Meeting gemeinsam festlegen** und in IntelliJ/BlueJ konfigurieren,
  damit kein automatisches Reformatting fremden Code verändert

---

## Kommunikation

- Blocker und Probleme sofort ansprechen – nicht bis zum nächsten Meeting warten
- Schnittstellen-Änderungen **immer** erst mit den betroffenen Teammitgliedern abstimmen,
  bevor der Code geändert wird – andere bauen darauf auf
