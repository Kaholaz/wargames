package org.ntnu.vsbugge.wargames.gui.factories;

import javafx.scene.control.Label;
import org.ntnu.vsbugge.wargames.gui.decorators.IconLabelDecorator;

/**
 * A class used to generate labels with icons.
 *
 * @author vsbugge
 */
public class IconLabelFactory {
    /**
     * This is a static class, so the constructor is private.
     */
    private IconLabelFactory() {
    }

    /**
     * Creates a label with the given icon.
     *
     * @param labelText
     *            The text of the label.
     * @param icon
     *            The icon to add to the label.
     *
     * @return The label with the applied icon.
     */
    public static Label createIconLabel(String labelText, String icon) {
        Label label = new Label(labelText);
        IconLabelDecorator.setIcon(label, icon);
        return label;
    }
}
