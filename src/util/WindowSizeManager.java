package util;

import greenfoot.guifx.WorldDisplay;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

public final class WindowSizeManager {

    private static boolean pinned = false;

    private WindowSizeManager() {}

    public static void enforce() {
        Platform.runLater(WindowSizeManager::tryEnforce);
    }

    private static void tryEnforce() {
        Stage stage = findStage();
        if (stage == null || stage.getScene() == null) {
            Platform.runLater(WindowSizeManager::tryEnforce);
            return;
        }
        stage.getScene().setFill(Color.web("#313944"));
        stage.setMaximized(true);
        stage.iconifiedProperty().addListener((obs, wasIconified, isIconified) -> {
            if (isIconified) {
                Platform.runLater(() -> stage.setIconified(false));
            }
        });
        pinWorldDisplaySize(stage.getScene());
    }

    private static void pinWorldDisplaySize(Scene scene) {
        if (pinned) {
            return;
        }

        Parent root = scene.getRoot();
        WorldDisplay worldDisplay = findWorldDisplay(root);
        if (worldDisplay == null) {
            Platform.runLater(() -> pinWorldDisplaySize(scene));
            return;
        }

        Parent wrapper = worldDisplay.getParent();
        if (!(wrapper instanceof Region)) {
            Platform.runLater(() -> pinWorldDisplaySize(scene));
            return;
        }
        Region wrapperRegion = (Region) wrapper;
        wrapperRegion.setStyle("-fx-background-color: #313944;");

        worldDisplay.setMaxWidth(Region.USE_PREF_SIZE);
        worldDisplay.setMaxHeight(Region.USE_PREF_SIZE);
        Node bottom = root instanceof BorderPane ? ((BorderPane) root).getBottom() : null;

        wrapperRegion.prefWidthProperty().bind(scene.widthProperty());
        if (bottom != null) {
            wrapperRegion.prefHeightProperty().bind(Bindings.createDoubleBinding(
                    () -> scene.getHeight() - bottom.getBoundsInParent().getHeight(),
                    scene.heightProperty(), bottom.boundsInParentProperty()));
        } else {
            wrapperRegion.prefHeightProperty().bind(scene.heightProperty());
        }


        pinned = true;
    }

    private static WorldDisplay findWorldDisplay(Node node) {
        if (node instanceof WorldDisplay) {
            return (WorldDisplay) node;
        }
        if (node instanceof Parent) {
            for (Node child : ((Parent) node).getChildrenUnmodifiable()) {
                WorldDisplay found = findWorldDisplay(child);
                if (found != null) {
                    return found;
                }
            }
        }

        return null;
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
