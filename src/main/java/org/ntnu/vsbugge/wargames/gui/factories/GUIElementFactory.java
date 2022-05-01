package org.ntnu.vsbugge.wargames.gui.factories;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class GUIElementFactory {
    /**
     * The class is static so constructor is made private.
     */
    private GUIElementFactory() {
    }

    public static Pane createDividerPane() {
        Pane pane = new Pane();
        pane.setPrefSize(0, 0);
        HBox.setHgrow(pane, Priority.ALWAYS);
        VBox.setVgrow(pane, Priority.ALWAYS);

        return pane;
    }

    public static HBox createHBoxWithCenteredElements(boolean padSides, Node... nodes) {
        HBox hBox = new HBox();
        if (nodes.length == 0) {
            return hBox;
        }

        for (Node node : nodes) {
            // Add element
            hBox.setPrefSize(0, 0);
            HBox.setHgrow(node, Priority.ALWAYS);
            hBox.getChildren().add(node);

            // Add divider pane
            Pane pane = createDividerPane();
            hBox.getChildren().add(pane);
        }

        if (padSides) {
            // Add a divider to the start
            Pane pane = createDividerPane();
            hBox.getChildren().add(0, pane);
        } else {
            // Remove last divider
            hBox.getChildren().remove(hBox.getChildren().size() - 1);
        }

        return hBox;
    }

}
