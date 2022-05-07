package org.ntnu.vsbugge.wargames.gui.guielements.battlesimulation;

import javafx.scene.Node;
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
    protected Label healthLabel;
    protected Label attackLabel;
    protected Label armorLabel;
    protected Label countLabel;

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
    protected void createBottomRow() {
        // Create labels
        healthLabel = IconLabelFactory.createIconLabel("", "health");
        PaddingDecorator.padLight(healthLabel);

        attackLabel = IconLabelFactory.createIconLabel("", "attack");
        PaddingDecorator.padLight(attackLabel);

        armorLabel = IconLabelFactory.createIconLabel("", "armor");
        PaddingDecorator.padLight(armorLabel);

        countLabel = new Label();
        PaddingDecorator.padLight(countLabel);

        // Add to VBox
        HBox bottom = GUIElementFactory.createHBoxWithCenteredElements(true, healthLabel, attackLabel, armorLabel,
                countLabel);

        this.getChildren().add(bottom);
    }

    /**
     * Sets the health field in the bottom row for the info element.
     *
     * @param health
     *            The new health value.
     */
    public void setHealth(int health) {
        healthLabel.setText(String.format("%d hp", health));
    }

    /**
     * Sets the attack field in the bottom row for the info element.
     *
     * @param attack
     *            The new attack value.
     */
    public void setAttack(int attack) {
        attackLabel.setText(String.format("%d atk", attack));
    }

    /**
     * Sets the armor field in the bottom row for the info element.
     *
     * @param armor
     *            The new armor value.
     */
    public void setArmor(int armor) {
        armorLabel.setText(String.format("%d arm", armor));
    }

    /**
     * Sets the count field in the bottom row for the info element.
     *
     * @param count
     *            The new count value.
     */
    public void setCount(int count) {
        countLabel.setText(String.format("x%d", count));
    }
}
