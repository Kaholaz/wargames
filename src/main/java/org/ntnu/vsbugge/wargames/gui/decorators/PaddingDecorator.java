package org.ntnu.vsbugge.wargames.gui.decorators;

import javafx.scene.Node;

public class PaddingDecorator {
    /**
     * The class is static so the constructor is made private
     */
    private PaddingDecorator() {
    }

    public static void clearPadding(Node node) {
        node.getStyleClass().remove("padded-light");
        node.getStyleClass().remove("padded-medium");
    }

    public static void padLight(Node node) {
        clearPadding(node);
        node.getStyleClass().add("padded-light");
    }

    public static void padMedium(Node node) {
        clearPadding(node);
        node.getStyleClass().add("padded-medium");
    }
}
