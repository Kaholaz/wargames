package org.ntnu.vsbugge.wargames.gui.decorators;

import javafx.scene.Node;

/**
 * A class used to change the state of gui elements.
 */
public class StatusDecorator {
    /**
     * Constructor is made private since the class is static.
     */
    private StatusDecorator() {

    }

    /**
     * Sets the state of an element to 'disabled'.
     *
     * @param node
     *            The button to change.
     */
    public static void makeDisabled(Node node) {
        node.setDisable(true);
    }

    /**
     * Sets the state of an element to 'enabled'.
     *
     * @param node
     *            The button to change.
     */
    public static void makeEnabled(Node node) {
        node.setDisable(false);
    }
}
