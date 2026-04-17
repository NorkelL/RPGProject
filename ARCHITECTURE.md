# Architektur-Diskussionsdokument

> **Wichtig:** Dies ist kein Pflichtenheft und keine Spezifikation.
> Es ist eine strukturierte Agenda für das erste Team-Meeting in Woche 1.
> Alle hier aufgeführten Fragen sind offen – das Team entscheidet gemeinsam.
> Nach dem Meeting werden die Entscheidungen hier oder in einem separaten
> `DECISIONS.md` festgehalten.

---

## Offene Fragen für das erste Design-Meeting

---

### 1. Greenfoot-Integrationsstrategie: `Actor` direkt erweitern vs. eigenes Entity-System

Die grundlegende Frage: Wie tief integrieren wir uns in Greenfot, und wie stark koppeln
wir das Spiel an die Greenfoot-API?

**Option A – `Actor` direkt erweitern (Greenfoot-nativ)**

Alle Spielobjekte (Spieler, Gegner, Items, …) erben direkt von Greenfots `Actor`.
Greenfoot übernimmt die Render-Schleife, `act()`-Aufrufe, Kollisionserkennung und
das `World`-Modell.

- **Pro:** Greenfoot-nativ, gut dokumentiert, wenig Infrastrukturaufwand in Woche 1–2
- **Pro:** Lernkurve flach – funktioniert mit Greenfoot-Tutorials und Beispielen
- **Pro:** Kollisionserkennung (`getIntersectingObjects`, `isTouching`) ist sofort nutzbar
- **Contra:** Starke Kopplung an Greenfoot – Code außerhalb von Greenfoot testen schwierig
- **Contra:** Greenfots `Actor`-API ist für rasterbasierte Spiele nicht optimal (sie
  arbeitet primär pixelbasiert)
- **Contra:** Kann zu tiefen, schwer wartbaren Vererbungshierarchien verleiten
  (`Player extends Character extends Actor`)

**Option B – Eigenes Entity-System, Greenfoot als reine Render-/Input-Schicht**

Spiellogik läuft in eigenen Klassen ohne Greenfoot-Abhängigkeit. Greenfoot-`Actor`s
sind nur dünne Wrapper, die Position und Sprites weiterreichen.

- **Pro:** Sauberere Architektur, Logik ist ohne Greenfoot testbar
- **Pro:** Tile-Koordinaten können von Anfang an zentral verwaltet werden
- **Pro:** Einfacher portierbar, falls das Projekt später ohne Greenfoot laufen soll
- **Contra:** Mehr Infrastrukturarbeit in Woche 1–2 – jemand muss die Brücke bauen
- **Contra:** Greenfots eingebaute Kollisions- und Render-Helfer können nicht direkt
  genutzt werden; müssen nachgebaut oder umgangen werden
- **Contra:** Für ein 5-Wochen-Schulprojekt möglicherweise over-engineering

**Fragen fürs Meeting:**
- Wie viel Greenfoot-Erfahrung hat das Team insgesamt?
- Soll der Code nach dem Projekt weiterhin portierbar sein, oder ist Greenfoot endgültig?
- Wer (Rolle 3, Rendering) hat bereits eine Präferenz?

---

### 2. Kommunikationsmodell zwischen Modulen

Wie sprechen die Module miteinander? Beispiel: Gegner stirbt → UI soll XP-Anzeige
aktualisieren → Sound soll Effekt abspielen.

**Option A – Direkte Methodenaufrufe**

Jedes Modul kennt die anderen direkt und ruft Methoden auf.
`combatSystem.dealDamage(enemy)` → `ui.updateHealthBar(player.getHealth())`

- **Pro:** Einfach, sofort verständlich, kein Zusatzaufwand, gut debuggbar
- **Pro:** Kein Infrastrukturcode nötig
- **Contra:** Enge Kopplung – ändert sich eine Methode, müssen viele Stellen angepasst werden
- **Contra:** Zirkuläre Abhängigkeiten können entstehen (A kennt B, B kennt A)

**Option B – Observer-/Listener-Pattern**

Module registrieren sich für Ereignisse anderer Module. Wer ein Ereignis auslöst,
kennt die Listener nicht direkt.

- **Pro:** Mittlere Entkopplung, in Java gut bekannt (`java.util.EventListener`-Muster)
- **Pro:** Mehrere Module können auf dasselbe Ereignis reagieren, ohne dass der Auslöser
  davon weiß
- **Contra:** Etwas mehr Boilerplate; Event-Typen müssen früh definiert werden
- **Contra:** Debugging kann aufwändiger sein ("Wer hat diesen Listener registriert?")

**Option C – Zentraler Event-Bus**

Ein globaler Bus (`EventBus.post(new EnemyDiedEvent(enemy))`). Alle Module abonnieren
Events, die sie interessieren.

- **Pro:** Maximale Entkopplung – Module kennen sich gegenseitig gar nicht mehr
- **Pro:** Leicht erweiterbar; neues Modul einfach abonnieren
- **Contra:** Globaler Zustand, Event-Flows schwer nachzuverfolgen
- **Contra:** Für diesen Projektumfang wahrscheinlich over-engineering
- **Contra:** Erfordert entweder eine Bibliothek (Guava EventBus) oder eigene Implementierung

**Fragen fürs Meeting:**
- Wie viele Module müssen auf dasselbe Ereignis reagieren? (1–2 → Option A reicht,
  3+ → Option B lohnt sich)
- Dürfen externe Bibliotheken verwendet werden?

---

### 3. Map-Dateiformat: JSON vs. eigenes Textformat

Der Level-Editor (Rolle 2) und das Hauptspiel müssen dasselbe Format lesen/schreiben.

**Option A – JSON**

```json
{
  "width": 20, "height": 15,
  "tiles": [[1,1,0,0],[1,0,0,1]],
  "spawnPoints": [{"x": 2, "y": 3, "type": "player"}]
}
```

- **Pro:** Weitverbreitet, gut lesbar, einfach um neue Felder erweiterbar
- **Pro:** Viele Parser-Bibliotheken verfügbar (z. B. Gson, Jackson, `org.json`)
- **Pro:** Editor und Spiel teilen sich exakt dasselbe Format ohne Konversion
- **Contra:** Externe Abhängigkeit nötig (oder eigener, fehleranfälliger Parser)
- **Empfehlung:** Wenn externe Bibliotheken erlaubt sind, ist JSON die pragmatischste Wahl

**Option B – Eigenes Textformat (z. B. CSV-ähnlich)**

```
20 15
1 1 0 0 1 0 ...
SPAWN 2 3 player
```

- **Pro:** Keine externe Abhängigkeit, selbst implementierbar in wenigen Stunden
- **Pro:** Für einfache Karten sehr kompakt
- **Contra:** Schwer erweiterbar – jede neue Information (Metadaten, Spawn-Typen,
  Verbindungen zwischen Räumen) macht das Format komplizierter
- **Contra:** Editor und Spiel müssen denselben Parser parallel pflegen

**Fragen fürs Meeting:**
- Dürfen externe Bibliotheken eingebunden werden? (Rückfrage an Lehrkraft sinnvoll)
- Wie komplex sollen Karten-Metadaten werden? (Nur Tiles, oder auch Events, Türen,
  Loot-Tabellen, …?)
- Wer (Rolle 2) implementiert den Parser – Editor oder Spiel zuerst?

---

### 4. Koordinatensystem: Pixelbasiert vs. Tile-basiert mit Render-Übersetzung

**Option A – Pixelbasiert (Greenfots Standard)**

Alle Positionen in Pixeln. `actor.setLocation(64, 96)` bedeutet Pixel 64/96.

- **Pro:** Direkt mit Greenfots `setLocation`/`getX`/`getY` kompatibel, kein Wrapper nötig
- **Pro:** Smooth-Bewegung (nicht nur sprungweise von Tile zu Tile) einfacher zu realisieren
- **Contra:** Tile-Raster-Logik (Kollision, Pathfinding, Map-Generierung, Raumstruktur)
  muss ständig zwischen Pixel- und Tile-Koordinaten umrechnen (`tileX = pixelX / TILE_SIZE`)
- **Contra:** Rounding-Fehler und Alignment-Bugs können auftreten

**Option B – Tile-basiert, Render-Schicht übersetzt**

Spiellogik arbeitet in ganzzahligen Tile-Koordinaten. Nur die Render-Schicht (Rolle 3)
übersetzt Tile → Pixel unter Berücksichtigung des Kamera-Offsets.

- **Pro:** Saubere Trennung von Logik und Darstellung
- **Pro:** A\*-Pathfinding, Kollision und Map-Generierung arbeiten auf einfachen Integers
- **Pro:** Kamera-Scrolling ist klar lokalisiert (nur in der Render-Schicht)
- **Contra:** Render-Schicht ist etwas komplexer; Tile→Pixel-Mapping muss konsistent sein
- **Contra:** Smooth-Bewegung (animiertes Laufen zwischen Tiles) erfordert einen
  Interpolationsmechanismus in der Render-Schicht

**Fragen fürs Meeting:**
- Soll sich der Spieler pixelgenau oder tileweise bewegen?
- Scrollt die Kamera, oder ist die gesamte Welt immer sichtbar?
- Welche Tile-Größe? 16×16, 32×32, oder 64×64 Pixel? (Hat Einfluss auf Sprite-Größen
  und wie viele Tiles auf den Bildschirm passen)
- Wer (Rolle 3, Rendering) hat eine Präferenz?

---

### 5. Code-Sharing zwischen Level-Editor und Hauptspiel

Der Level-Editor (Rolle 2) ist ein eigenständiges Java-Tool. Das Hauptspiel läuft in
Greenfoot. Beide brauchen wahrscheinlich gemeinsamen Code: Tile-Definitionen,
das Karten-Datenmodell, den Dateiformat-Parser.

Das Projekt verwendet kein Build-Tool – alles läuft als plain Java-Projekt in IntelliJ.
Gemeinsamer Code zwischen Editor und Spiel wird deshalb über IntelliJ-Module geteilt,
nicht über eine externe Dependency.

**Option A – Gemeinsames IntelliJ-Modul (`shared/`)**

Gemeinsamer Code zieht in ein drittes Modul `shared/src/`, das in IntelliJ als
Modul-Abhängigkeit in Spiel und Editor eingetragen wird.

- **Pro:** Eine Wahrheit – Änderung an einer Stelle wirkt überall
- **Pro:** In IntelliJ ohne Build-Tool umsetzbar (File → Project Structure → Modules)
- **Contra:** Jedes Teammitglied muss das `shared`-Modul in IntelliJ korrekt einrichten
- **Contra:** Mehr initialer Aufwand in Woche 1

**Option B – Klassen kopieren**

Gemeinsame Klassen werden in beide Module kopiert und parallel gepflegt.

- **Pro:** Sofort machbar, kein Extra-Modul nötig
- **Contra:** Jede Änderung muss in beiden Modulen gemacht werden – Fehlerquelle,
  besonders unter Zeitdruck

**Option C – Editor ohne Shared Code (nur Format-Kompatibilität)**

Editor und Spiel kennen keine gemeinsamen Klassen. Der Editor schreibt nur Dateien
im vereinbarten Format; das Spiel liest sie. Jeder parst das Format für sich selbst.

- **Pro:** Maximale Trennung, keine Modul-Abhängigkeit
- **Contra:** Tile-Definitionen und Format-Konstanten müssen synchron gehalten
  werden – nur ohne gemeinsamen Code

**Fragen fürs Meeting:**
- Wie viel gemeinsamer Code ist realistisch? (Nur Map-Format, oder auch Tile-Klassen?)
- Kann Rolle 2 (Editor) und Rolle 1 (Architektur) sich auf eine Lösung einigen,
  die beide Workflows nicht blockiert?
- Wann wird der Editor gebraucht, und wer richtet das Modul-Setup ein?

---

## Nächste Schritte nach dem Meeting

Nach dem ersten Meeting sollten diese Entscheidungen dokumentiert und in den Code
übersetzt sein:

- [ ] Greenfoot-Integrationsstrategie festgelegt
- [ ] Kommunikationsmodell festgelegt
- [ ] Map-Dateiformat und Parser-Verantwortung festgelegt
- [ ] Koordinatensystem + Tile-Größe festgelegt
- [ ] Code-Sharing-Strategie Editor ↔ Spiel festgelegt
- [ ] Interfaces / Klassen-Skelette auf die 5 Rollen aufgeteilt
- [ ] Dieses Dokument um einen "Entscheidungen"-Abschnitt ergänzen
  (oder neues `DECISIONS.md` anlegen)
