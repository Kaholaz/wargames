package org.ntnu.vsbugge.wargames.gui.guielements;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.ntnu.vsbugge.wargames.gui.decorators.PaddingDecorator;
import org.ntnu.vsbugge.wargames.gui.factories.GUIElementFactory;
import org.ntnu.vsbugge.wargames.gui.factories.IconLabelFactory;

public abstract class AbstractInfoElement extends VBox {
    protected Label healthLabel;
    protected Label attackLabel;
    protected Label armorLabel;
    protected Label countLabel;

    public AbstractInfoElement() {
        super();

        // Populate element
        createTopRow();
        createBottomRow();

        // Add style
        this.getStyleClass().add("bordered-box");
        PaddingDecorator.padMedium(this);
    }

    protected abstract void createTopRow();

    private void createTopRow(Node... nodes) {
        // Add padding
        for (Node node : nodes) {
            PaddingDecorator.padLight(node);
        }

        // Add to VBox
        HBox top = GUIElementFactory.createHBoxWithCenteredElements(true, nodes);
        this.getChildren().add(0, top);
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

    public void setHealth(int health) {
        healthLabel.setText(String.format("%d hp", health));
    }

    public void setAttack(int attack) {
        attackLabel.setText(String.format("%d atk", attack));
    }

    public void setArmor(int armor) {
        armorLabel.setText(String.format("%d arm", armor));
    }

    public void setCount(int count) {
        countLabel.setText(String.format("x%d", count));
    }
}
