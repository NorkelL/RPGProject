package util;

import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.Window;

public final class WindowSizeManager {

    private static boolean listenerInstalled = false;
    private static double expectedWidth = -1;
    private static double expectedHeight = -1;

    private WindowSizeManager() {
    }

    public static void enforce() {
        Platform.runLater(WindowSizeManager::tryEnforce);
    }

    private static void tryEnforce() {
        Stage stage = findStage();
        if (stage == null) {
            // Fenster existiert beim ersten Weltwechsel evtl. noch nicht, also nochmal probieren
            Platform.runLater(WindowSizeManager::tryEnforce);
            return;
        }

        installSelfHealingListener(stage);
        remaximize(stage);
    }

    // Greenfoot resized das Fenster bei jedem Weltwechsel mit unterschiedlicher Pixelgroesse per
    // sizeToScene() (GreenfootScenarioViewer.setWorldImage). Das setzt Breite/Hoehe direkt, ohne den
    // normalen "restore"-Weg zu gehen - deshalb bleibt maximizedProperty() weiterhin true, obwohl das
    // Fenster jetzt kleiner ist, und der Maximize/Restore-Button in der Titelleiste haengt fest.
    // Deshalb wird hier auf die tatsaechliche Groesse gehoert statt auf maximizedProperty(), und bei
    // jeder Abweichung ein echter Maximize-Zyklus (aus, dann an) erzwungen, damit Windows die
    // Fenstergroesse UND den Titelleisten-Button wieder synchron setzt.
    private static void installSelfHealingListener(Stage stage) {
        if (listenerInstalled) {
            return;
        }
        listenerInstalled = true;

        stage.widthProperty().addListener((obs, oldV, newV) -> {
            if (newV.doubleValue() != expectedWidth) {
                Platform.runLater(() -> remaximize(stage));
            }
        });
        stage.heightProperty().addListener((obs, oldV, newV) -> {
            if (newV.doubleValue() != expectedHeight) {
                Platform.runLater(() -> remaximize(stage));
            }
        });
        stage.iconifiedProperty().addListener((obs, wasIconified, isIconified) -> {
            if (isIconified) {
                Platform.runLater(() -> {
                    stage.setIconified(false);
                    remaximize(stage);
                });
            }
        });
    }

    private static void remaximize(Stage stage) {
        if (stage.isIconified()) {
            stage.setIconified(false);
        }
        stage.setMaximized(false);
        Platform.runLater(() -> {
            stage.setMaximized(true);
            expectedWidth = stage.getWidth();
            expectedHeight = stage.getHeight();
        });
    }

    private static Stage findStage() {
        for (Window window : Window.getWindows()) {
            if (window instanceof Stage) {
                return (Stage) window;
            }
        }
        return null;
    }
}
