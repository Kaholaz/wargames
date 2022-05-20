package org.ntnu.vsbugge.wargames.gui.decorators;

import javafx.scene.control.Label;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.IntInputDoubleClickSwapper;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.StringInputDoubleClickSwapper;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.UnitSelectorDoubleClickSwapper;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.IntSetter;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.StringSetter;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.UnitSetter;

/**
 * A decorator used to make labels editable.
 *
 * @author vsbugge
 */
public class EditableDecorator {
    private static final String[] EDITABLE_STYLE_CLASSES = { "editable-label" };

    /**
     * Makes a label editable.
     *
     * @param label
     *            The label to make editable.
     * @param setter
     *            The function to call when the input is supplied.
     */
    public static void makeEditable(Label label, IntSetter setter) {
        label.setOnMouseClicked(new IntInputDoubleClickSwapper(label, setter));
        addStyleClass(label);
    }

    /**
     * Makes a label editable.
     *
     * @param label
     *            The label to make editable.
     * @param setter
     *            The function to call when the input is supplied.
     */
    public static void makeEditable(Label label, StringSetter setter) {
        label.setOnMouseClicked(new StringInputDoubleClickSwapper(label, setter));
        addStyleClass(label);
    }

    /**
     * Makes a label editable.
     *
     * @param label
     *            The label to make editable.
     * @param setter
     *            The function to call when the input is supplied.
     */
    public static void makeEditable(Label label, UnitSetter setter) {
        label.setOnMouseClicked(new UnitSelectorDoubleClickSwapper(label, setter));
        addStyleClass(label);
    }

    /**
     * Removes any ond all alterations done when making a label editable.
     *
     * @param label
     *            The label to make non-editable.
     */
    public static void makeNonEditable(Label label) {
        label.setOnMouseClicked(null);
        removeStyleClass(label);
    }

    /**
     * Removes all style classes associated with editable labels.
     *
     * @param label
     *            The label to remove the style classes from.
     */
    private static void removeStyleClass(Label label) {
        label.getStyleClass().removeAll(EDITABLE_STYLE_CLASSES);

        if (label.getStyleClass().contains("editable-icon-text")) {
            label.getStyleClass().remove("editable-icon-text");
            label.getStyleClass().add("icon-text");
        }
    }

    /**
     * Adds the style classes associated with editable labels.
     *
     * @param label
     *            The label to add the style classes to.
     */
    private static void addStyleClass(Label label) {
        removeStyleClass(label);
        label.getStyleClass().addAll(EDITABLE_STYLE_CLASSES);

        if (label.getStyleClass().contains("icon-text")) {
            label.getStyleClass().remove("icon-text");
            label.getStyleClass().add("editable-icon-text");
        }
    }
}
