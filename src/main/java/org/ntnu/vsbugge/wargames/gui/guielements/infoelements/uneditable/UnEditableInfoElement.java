package org.ntnu.vsbugge.wargames.gui.guielements.infoelements.uneditable;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.ntnu.vsbugge.wargames.gui.decorators.PaddingDecorator;
import org.ntnu.vsbugge.wargames.gui.factories.GUIElementFactory;
import org.ntnu.vsbugge.wargames.gui.factories.IconLabelFactory;
import org.ntnu.vsbugge.wargames.gui.guielements.infoelements.AbstractInfoElement;

public abstract class UnEditableInfoElement extends AbstractInfoElement {
    protected Label healthLabel;
    protected Label attackLabel;
    protected Label armorLabel;
    protected Label countLabel;

    public UnEditableInfoElement() {
        super();
    }

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
