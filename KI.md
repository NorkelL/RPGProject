# KI-Einsatz im Projekt

*The Dungeon: Sign of the Moon*

Dieses Dokument beschreibt **wofür** wir KI im Projekt eingesetzt haben, **wofür bewusst nicht**,
und **welche Teile des Codes nicht von uns selbst geschrieben wurden**. Es ergänzt
`Aufteilung.md`, in dem die Aufteilung der Arbeit unter den Teammitgliedern steht.

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

Der mit Abstand größte Nutzen der KI lag **nicht** im Schreiben von Code, sondern im **Erklären**.
Wir haben sie im Alltag da eingesetzt, wo man sonst die Java- oder Greenfoot-Dokumentation
aufgeschlagen hätte.

**Warum wir das so gemacht haben:**

- Die offizielle Greenfoot-API-Doku ist eine reine Referenz: sie sagt, *dass* es
  `getObjectsAt(int x, int y, Class cls)` gibt, aber nicht, *wann* man das braucht und wie es sich
  zu `getOneIntersectingObject()` verhält. Genau diese Einordnung fehlt einem als Anfänger.
- Die Java-Doku (Oracle/JavaDoc) ist für unser Niveau streckenweise zu abstrakt formuliert.
  Themen wie Vererbung, abstrakte Klassen, Interfaces, Enums oder Generics stehen dort korrekt,
  aber nicht so, dass man sie beim ersten Lesen versteht.
- Eine KI kann dieselbe Erklärung auf **unserem Kenntnisstand** liefern, mit einem Beispiel aus
  *unserem* Projekt statt einem abstrakten `Foo`/`Bar`-Beispiel, und man kann so lange nachfragen,
  bis es wirklich sitzt. Das ist der eigentliche Lerneffekt: Rückfragen stellen zu können, ohne
  jemanden aufzuhalten.

**Typische Fragen, die so beantwortet wurden:**

- Wie funktionieren Enums mit eigenen Werten und Methoden (`ItemTyp`, `Rarity`, `SlotType`)?
- Wie skaliert man `GreenfootImage`-Objekte, ohne dass die Pixelgrafik verwaschen wird?
- Was bedeutet diese `NullPointerException` in der Konsole, und wo muss man suchen?

Das Ergebnis dieser Gespräche waren **Erklärungen, keine fertigen Klassen**. Den Code haben wir
danach selbst geschrieben; deshalb steht er auch unter unseren Namen im Git-Verlauf.

---

## 3. Git und GitHub

Für die meisten im Team war Versionsverwaltung komplett neu. Vor diesem Projekt hatte kaum
jemand mit Branches, Pull Requests oder Merge-Konflikten gearbeitet. Hier war die KI der zweite
große Einsatzbereich.

**Womit sie geholfen hat:**

- **Das Grundmodell verstehen:** Was ist ein Commit, was ein Branch, was der Unterschied zwischen
  `fetch`, `pull` und `push`? Warum arbeitet man nicht direkt auf `master`?
- **Konkrete Befehle in der eigenen Situation:** „Ich habe auf dem falschen Branch committet" oder
  „mein Branch ist hinter master" sind Fragen, die man in der Git-Doku nur schwer nachschlägt,
  weil man den Namen der Lösung noch gar nicht kennt.
- **Merge-Konflikte lesen:** Was die `<<<<<<<`/`=======`/`>>>>>>>`-Markierungen bedeuten und wie
  man sie auflöst, ohne die Arbeit der anderen zu zerstören.
- **Der GitHub-Workflow:** Issues anlegen und zuweisen, Branch pro Feature, Pull Request,
  Review, Merge. Und warum diese Reihenfolge sinnvoll ist, wenn acht Leute an einem Repo arbeiten.

Aus diesen Erklärungen ist auch der Git-Leitfaden `GITHUB-HELP.md` entstanden, den Nikolaj für
das Team geschrieben hat: eine auf unser Projekt zugeschnittene Kurzanleitung, damit nicht jede
Person dieselben Fragen einzeln stellen muss.

**Wichtig:** Die KI hat hier **beraten, nicht ausgeführt**. Alle Commits, Branches, Pull Requests
und Merges wurden von uns selbst gemacht.

---

## 4. Von KI geschriebener Code

Es gibt genau **zwei** Stellen im Projekt, an denen KI eigenständig Code produziert hat.
Beide werden hier vollständig offengelegt.


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
**Inhaltlich stammen sie nicht von ihm.** Was er selbst gemacht hat, ist die Integration ins
Spiel und die anschließende Integration.

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

Die Frage, wer wie viel zum Projekt beigetragen hat, ist die einzige im Projekt, bei der niemand
im Team eine neutrale Position hat: jede Selbsteinschätzung ist automatisch parteiisch. Deshalb
haben wir diese Auswertung bewusst von der KI machen lassen.

**Was die KI gemacht hat:** Sie hat den kompletten Git-Verlauf ausgewertet: alle Commits auf
`master` und auf den Feature-Branches, `git blame` über sämtliche Java-Dateien in `src/`,
`git log --numstat` für hinzugefügte und gelöschte Zeilen, dazu die Merge- und Asset-Statistik.
Daraus ist `Aufteilung.md` entstanden: wer welche Klassen angelegt hat, wessen Code heute noch im
Projekt steht, was überschrieben wurde und welche Branches nie gemergt wurden.

**Warum das eine faire Grundlage ist:**

- Die Auswertung kennt niemanden im Team persönlich und hat kein Interesse am Ergebnis.
- **Jede Zahl ist im GitHub-Log nachprüfbar.** Commit-Hashes, Pull-Request-Nummern und
  Branch-Namen stehen im Dokument, sodass sich jede Aussage einzeln kontrollieren lässt.
- Genau das haben wir anschließend gemacht: Die Angaben wurden am tatsächlichen Repository-Verlauf
  gegengeprüft und bestätigt. Wo die Messung falsch lag, steht die Korrektur im Dokument,
  zum Beispiel beim `SoundManager`, den Len und Jonas gemeinsam geschrieben haben, der in git aber
  komplett unter Lens Namen läuft.

**Was wir selbst gemacht haben:** die **Prozentaufteilung**. Reine Zeilenzahlen messen keinen
Aufwand: repetitiver Code erzeugt viele Zeilen, Grafikarbeit und Organisation dagegen keine
einzige, und nie gemergte Arbeit taucht überhaupt nicht auf. Die Messwerte waren deshalb nur der
Ausgangspunkt; die endgültigen Prozentzahlen pro Person haben wir im Team besprochen und
festgelegt. Sie sind eine Teamentscheidung, kein KI-Ergebnis.

---

## 6. Grafiken und Assets

Ein großer Teil der Sprites, Hintergründe und Animationsframes wurde mit generativen
Bild-Werkzeugen erstellt und anschließend im Pixel-Editor **Piskel** nachbearbeitet
(Zuschnitt, Farben, Animationsphasen). Einzelne Item-Icons orientieren sich an
Minecraft-Item-Grafiken und stammen von der Online-Ressource `mc.nerothe.com`.
