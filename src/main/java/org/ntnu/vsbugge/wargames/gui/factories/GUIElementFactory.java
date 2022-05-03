package org.ntnu.vsbugge.wargames.gui.factories;

import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

/**
 * A class used to create various UI elements.
 */
public class GUIElementFactory {
    /**
     * The class is static so constructor is made private.
     */
    private GUIElementFactory() {
    }

    /**
     * Creates a pane with a set prefSize of 0 that will always try to hgrow and vgro. This pane can be used as a
     * divider pane to create dynamically scaling UI elements.
     *
     * @return A pane that can be used to space other elements.
     */
    public static Pane createDividerPane() {
        Pane pane = new Pane();
        pane.setPrefSize(0, 0);
        HBox.setHgrow(pane, Priority.ALWAYS);
        VBox.setVgrow(pane, Priority.ALWAYS);

        return pane;
    }

    /**
     * This metod takes an array of UI elements and spaces them evenly through an HBox.
     *
     * @param padSides
     *            If this is set to true, a devider pane will be added before and after the first and last ui elements.
     * @param nodes
     *            The ui elements to populate the vbox with.
     *
     * @return An HBox that contains evenly spaced UI elements.
     */
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
