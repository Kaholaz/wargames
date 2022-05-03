package org.ntnu.vsbugge.wargames.gui.decorators;

import javafx.scene.control.Button;

/**
 * A class used to change the state of buttons.
 */
public class ButtonDecorator {
    /**
     * Constructor is made private since the class is static.
     */
    private ButtonDecorator() {

    }

    /**
     * Sets the state of a button to 'disabled'.
     *
     * @param button
     *            The button to change.
     */
    public static void makeDisabled(Button button) {
        button.setDisable(true);
    }

    /**
     * Sets the state of a button to 'enabled'.
     *
     * @param button
     *            The button to change.
     */
    public static void makeEnabled(Button button) {
        button.setDisable(false);
    }
}
