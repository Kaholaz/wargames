package org.ntnu.vsbugge.wargames.gui.decorators;

import javafx.scene.Node;

/**
 * A decorator class used to add padding to UI elements.
 */
public class PaddingDecorator {
    /**
     * The class is static so the constructor is made private
     */
    private PaddingDecorator() {
    }

    /**
     * Removes all padding css classes form this UI element.
     *
     * @param node
     *            The UI element to decorate.
     */
    public static void clearPadding(Node node) {
        node.getStyleClass().remove("padded-light");
        node.getStyleClass().remove("padded-medium");
    }

    /**
     * Removes all padding css classes and adds the 'padded-light' css class.
     *
     * @param node
     *            The UI element to decorate.
     */
    public static void padLight(Node node) {
        clearPadding(node);
        node.getStyleClass().add("padded-light");
    }

    /**
     * Removes all padding css classes and adds the 'padded-medium' css class.
     *
     * @param node
     *            The UI element to decorate.
     */
    public static void padMedium(Node node) {
        clearPadding(node);
        node.getStyleClass().add("padded-medium");
    }
}
