package org.ntnu.vsbugge.wargames.gui.guielements.infoelements;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ntnu.vsbugge.wargames.gui.decorators.PaddingDecorator;
import org.ntnu.vsbugge.wargames.gui.factories.GUIElementFactory;
import org.ntnu.vsbugge.wargames.gui.factories.IconLabelFactory;

/**
 * An abstract base class for a UI with one custom row, and one row with fields for health, attack, armor, and count.
 *
 * @author vsbugge
 */
public abstract class AbstractInfoElement extends VBox {
    /**
     * Creates the top and bottom row by calling the createTopRow and createBottomRow methods and applies the relevant
     * styling.
     */
    public AbstractInfoElement() {
        super();

        // Populate element
        createTopRow();
        createBottomRow();

        // Add style
        this.getStyleClass().add("bordered-box");
        PaddingDecorator.padMedium(this);
    }

    /**
     * Creates the top row of the UI element.
     */
    protected abstract void createTopRow();

    /**
     * Creates the bottom row of the UI element. This row consists of fields for health, attack, armor and count. These
     * fields can be set using the relevant setter methods.
     */
    protected abstract void createBottomRow();


}
