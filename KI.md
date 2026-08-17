# KI-Einsatz im Projekt

*The Dungeon: Sign of the Moon*

Hier steht, wofür wir im Projekt KI benutzt haben und wofür nicht, und welche Teile des Codes
nicht von uns selbst kommen. Wie die Arbeit im Team verteilt war, steht in `Aufteilung.md`.

---

## 1. Kurzfassung


| Einsatzbereich                                                                             | Umfang                                                                    |
| ------------------------------------------------------------------------------------------ | ------------------------------------------------------------------------- |
| **Erklären von Java- und Greenfoot-Konzepten** (Ersatz für die offizielle Dokumentation) | Hauptnutzung, über die gesamte Projektlaufzeit                           |
| **Git und GitHub verstehen und richtig benutzen**                                          | zweiter großer Bereich, vor allem in den ersten Wochen                   |
| **Von KI eigenständig geschriebener Code**                                                | 2 Stellen:`ASharpPathfinding` (komplett), `WindowSizeManager` (teilweise) |
| **Debugging-Hilfe und Fehlersuche**                                                        | punktuell, Lösung immer selbst eingebaut                                 |
| **Auswertung der Beitragsverteilung (`Aufteilung.md`)**                                    | Analyse des Git-Verlaufs als unparteiische Grundlage                      |
| **Grafiken und Sprite-Animationen**                                                        | siehe Abschnitt 6                                                         |

---

## 2. Hauptnutzung: KI als Ersatz für die Dokumentation

Am meisten gebracht hat uns die KI beim **Erklären**, nicht beim Schreiben von Code. Wir haben
sie überall da benutzt, wo man sonst die Java- oder Greenfoot-Dokumentation aufgeschlagen hätte.

**Warum wir das so gemacht haben:**

- Die offizielle Greenfoot-API-Doku ist eine reine Referenz. Da steht, *dass* es
  `getObjectsAt(int x, int y, Class cls)` gibt, aber nicht, *wann* man das braucht und was der
  Unterschied zu `getOneIntersectingObject()` ist. Als Anfänger fehlt einem genau das.
- Die Java-Doku (Oracle/JavaDoc) ist für unser Niveau an vielen Stellen zu abstrakt geschrieben.
  Vererbung, abstrakte Klassen, Interfaces, Enums oder Generics stehen dort zwar korrekt drin,
  aber nicht so, dass man sie beim ersten Lesen versteht.
- Eine KI kann dieselbe Erklärung auf **unserem Kenntnisstand** liefern, mit einem Beispiel aus
  *unserem* Projekt statt einem abstrakten `Foo`/`Bar`-Beispiel. Und man kann so lange nachfragen,
  bis es sitzt, ohne dass man dabei jemanden aufhält.

**Typische Fragen, die so beantwortet wurden:**

- Wie funktionieren Enums mit eigenen Werten und Methoden (`ItemTyp`, `Rarity`, `SlotType`)?
- Wie skaliert man `GreenfootImage`-Objekte, ohne dass die Pixelgrafik verwaschen wird?
- Was bedeutet diese `NullPointerException` in der Konsole, und wo muss man suchen?

Rausgekommen sind bei diesen Gesprächen immer nur Erklärungen und keine fertigen Klassen. Den
Code haben wir danach selbst geschrieben, deshalb steht er auch unter unseren Namen im Git-Verlauf.

---

## 3. Git und GitHub

Für die meisten im Team war Versionsverwaltung komplett neu, vor diesem Projekt hatte kaum
jemand mit Branches, Pull Requests oder Merge-Konflikten gearbeitet. Das war der zweite große
Einsatzbereich für die KI.

**Womit sie geholfen hat:**

- **Das Grundmodell verstehen:** Was ist ein Commit, was ein Branch, was der Unterschied zwischen
  `fetch`, `pull` und `push`? Warum arbeitet man nicht direkt auf `master`?
- **Konkrete Befehle in der eigenen Situation:** „Ich habe auf dem falschen Branch committet" oder
  „mein Branch ist hinter master" sind Fragen, die man in der Git-Doku nur schwer nachschlägt,
  weil man den Namen der Lösung noch gar nicht kennt.
- **Merge-Konflikte lesen:** Was die `<<<<<<<`/`=======`/`>>>>>>>`-Markierungen bedeuten und wie
  man sie auflöst, ohne die Arbeit der anderen zu zerstören.
- **Der Github-Workflow:** Issues anlegen und zuweisen, Branch pro Feature, Pull Request,
  Review, Merge. Und warum diese Reihenfolge sinnvoll ist, wenn acht Leute an einem Repo arbeiten.

Aus diesen Erklärungen ist auch der Git-Leitfaden `GITHUB-HELP.md` entstanden, den Nikolaj für
das Team geschrieben hat: eine auf unser Projekt zugeschnittene Kurzanleitung, damit nicht jede
Person dieselben Fragen einzeln stellen muss.

**Wichtig:** Die KI hat hier nur beraten. Alle Commits, Branches, Pull Requests und Merges
haben wir selbst gemacht.

---

## 4. Von KI geschriebener Code

Es gibt genau **zwei** Stellen im Projekt, an denen KI selbst Code produziert hat. Beide legen
wir hier offen.


| Datei                                      | Zeilen | Rolle der KI                                                          |
| ------------------------------------------ | ------ | --------------------------------------------------------------------- |
| `src/entities/util/ASharpPathfinding.java` | 111    | **vollständig von KI geschrieben**                                   |
| `src/util/WindowSizeManager.java`          | 104    | **teilweise**: Grundgerüst des Swing/AWT-Teils von KI, Rest selbst |

### 4.1 A\*-Pathfinding (`ASharpPathfinding.java`)

Die Klasse sorgt dafür, dass Monster den Spieler durch die prozedural erzeugten Räume und
Korridore verfolgen, statt stumpf in Wände zu laufen. Sie setzt den A\*-Algorithmus um:
Suche über das Dungeon-Grid mit Heuristik, Open- und Closed-Set und Rekonstruktion des Pfads.

Das ist die algorithmisch anspruchsvollste Einzelkomponente im Projekt und lag zum Zeitpunkt
der Implementierung (Mai 2026) deutlich über dem, was wir selbst hätten schreiben können.
Wir haben uns bewusst dafür entschieden, sie von der KI erzeugen zu lassen, statt das Feature
zu streichen, und den Einsatz direkt in der Commit-Message vermerkt:

```
4f09be9   2026-05-14   added monster pathfinding logic (A* mit hilfe von claude) + static map größe
```

Weil dieser Commit über Nikolajs Account lief, schreibt `git blame` alle 111 Zeilen ihm zu.
**Inhaltlich stammen sie nicht von ihm.** Von ihm sind die Einbindung ins Spiel und die
spätere Überarbeitung im August.

### 4.2 Fenster- und Fullscreen-Handling (`WindowSizeManager.java`)

Die Klasse regelt Fenstergröße, Maximieren und Vollbild. Greenfoot bietet dafür praktisch keine
Unterstützung, man muss unter die Oberfläche greifen und direkt mit Swing/AWT arbeiten. Das ist ein
Bereich, der mit dem Rest des Projekts nichts zu tun hat und den wir nie behandelt hatten.

Hier hat die KI beim Swing/AWT-Teil geholfen (Zugriff auf das umgebende Fenster, Umschalten
in den Vollbildmodus, Reaktion auf Größenänderungen). Der Rest (Einbindung ins Spiel,
Hintergrundfarbe, Zusammenspiel mit den Worlds) ist selbst geschrieben.

### 4.3 Verworfener KI-Code

Der Vollständigkeit halber: Es gab einen weiteren KI-Versuch, der **nicht** ins Spiel gekommen ist.
Auf dem Branch `claude/gridworld-dual-cellsize` liegen vier Commits vom `2026-06-02`, die eine
`GridWorld` mit doppeltem Raster (Tile- und Pixel-Ebene) für flüssigere Spielerbewegung einführen
sollten. Der Ansatz hat das Projekt zu stark umgebaut und wurde verworfen; `src/world/GridWorld.java`
existiert in `master` nicht.

---

## 5. Auswertung der Beitragsverteilung (`Aufteilung.md`)

Bei der Frage, wer wie viel zum Projekt beigetragen hat, ist niemand im Team neutral, jede
Selbsteinschätzung ist automatisch parteiisch. Deshalb haben wir diese Auswertung von der KI
machen lassen.

**Was die KI gemacht hat:** Sie hat den kompletten Git-Verlauf ausgewertet, also alle Commits auf
`master` und auf den Feature-Branches, `git blame` über alle Java-Dateien in `src/`,
`git log --numstat` für hinzugefügte und gelöschte Zeilen, dazu die Merge- und Asset-Statistik.
Daraus ist `Aufteilung.md` entstanden: wer welche Klassen angelegt hat, wessen Code heute noch im
Projekt steht, was überschrieben wurde und welche Branches nie gemergt wurden.

**Warum das eine faire Grundlage ist:**

- Die Auswertung kennt niemanden von uns und hat nichts davon, wie das Ergebnis aussieht
- **Jede Zahl ist im GitHub-Log nachprüfbar.** Commit-Hashes, Pull-Request-Nummern und
  Branch-Namen stehen im Dokument, man kann also jede Aussage einzeln kontrollieren.
- Genau das haben wir danach auch gemacht und die Angaben am echten Repository-Verlauf
  gegengeprüft. Wo die Messung falsch lag, steht die Korrektur im Dokument, zum Beispiel beim
  `SoundManager`, den Len und Jonas zusammen geschrieben haben, der in git aber komplett unter
  Lens Namen läuft.

**Was wir selbst gemacht haben:** die **Prozentaufteilung**. Reine Zeilenzahlen sagen nichts über
den Aufwand aus. Repetitiver Code erzeugt viele Zeilen, Grafikarbeit und Organisation dagegen
keine einzige, und nie gemergte Arbeit taucht gar nicht erst auf. Die Messwerte waren deshalb nur
der Ausgangspunkt, die endgültigen Prozentzahlen pro Person haben wir im Team besprochen und
festgelegt.

---

## 6. Grafiken und Assets

Einen großen Teil der Sprites, Hintergründe und Animationsframes haben wir mit generativen
Bild-Werkzeugen erzeugt und danach im Pixel-Editor **Piskel** nachbearbeitet (Zuschnitt, Farben,
Animationsphasen).  Ein paar Item-Icons sind an die Minecraft-Item-Grafiken angelehnt und
kommen von `mc.nerothe.com`.
