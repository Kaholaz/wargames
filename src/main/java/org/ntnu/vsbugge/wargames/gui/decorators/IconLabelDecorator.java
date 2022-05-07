package org.ntnu.vsbugge.wargames.gui.decorators;

import javafx.scene.control.Label;

/**
 * A decorator class used to set the icons of labels. This is done entirely through the use of css classes.
 *
 * @author vsbugge
 */
public class IconLabelDecorator {
    /**
     * Class is static, so no constructor needed.
     */
    private IconLabelDecorator() {
    }

    /**
     * Adds an icon to a label. The label has to be defined through a css class located in the icon-text.css file named
     * %icon%-icon.
     *
     * @param label
     *            The label element to decorate.
     * @param icon
     *            The icon to add to this label.
     */
    public static void setIcon(Label label, String icon) {
        removeIcon(label);

        label.getStyleClass().add("icon-text");
        label.getStyleClass().add(String.format("%s-icon", icon));
    }

    /**
     * Removes all icons from a label with an icon.
     *
     * @param label
     *            The label to remove the icon from.
     */
    public static void removeIcon(Label label) {
        label.getStyleClass().remove("icon-label");
        label.getStyleClass().removeIf(styleClass -> styleClass.endsWith("-icon"));
    }
}
