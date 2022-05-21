package org.ntnu.vsbugge.wargames.gui.eventhandlers;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import org.ntnu.vsbugge.wargames.utils.enums.UnitEnum;

/**
 * The class with all methods and helpers necessary for swapping a label and a control on a double press.
 *
 * @author vsbugge
 */
public abstract class AbstractDoubleClickSwapper extends AbstractDoubleClickEventHandler {
    protected Label label;

    /**
     * The constructor of the abstract method.
     *
     * @param label
     *            The label to swap on the double click.
     */
    public AbstractDoubleClickSwapper(Label label) {
        this.label = label;
    }

    /**
     * Replaces a label with a TextField.
     *
     * @param label
     *            The Label.
     *
     * @return The TextField.
     */
    protected static TextField replaceLabelWithTextField(Label label) {
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);

        setMatchingSize(label, textField);
        swapNode(label, textField);

        return textField;
    }

    /**
     * Replaces a label with a ComboBox.
     *
     * @param label
     *            The Label.
     *
     * @return The ComboBox.
     */
    protected static ComboBox<UnitEnum> replaceLabelWithUnitSelector(Label label) {
        ComboBox<UnitEnum> comboBox = new ComboBox<>();

        comboBox.getItems().addAll(UnitEnum.values());
        comboBox.getSelectionModel().select(UnitEnum.fromProperName(label.getText()));

        setMatchingSize(label, comboBox);
        swapNode(label, comboBox);

        return comboBox;
    }

    /**
     * Sets the size of the control to match the label.
     *
     * @param label
     *            The Label.
     * @param control
     *            The Control.
     */
    protected static void setMatchingSize(Label label, Control control) {
        HBox.setHgrow(control, Priority.ALWAYS);

        label.applyCss();
        double computedHeight = label.getHeight();
        double vPadding = 0d;
        double hPadding = 0d;

        control.setPrefSize(0, 0);
        // Conditional size to accommodate a difference in size.
        if (control instanceof ComboBox) {
            vPadding = 5d;
            hPadding = 38d;
        } else if (control instanceof TextField field) {
            HBox.setHgrow(control, Priority.NEVER);

            hPadding = 30d;
            vPadding = 15d;
            double textScale = 1.54d;

            // Compute hPadding to get the desired vertical height.
            Text measuringText = new Text(label.getText());
            vPadding = measuringText.getLayoutBounds().getHeight() * textScale + vPadding - computedHeight;

            // Make size update when the user types in the text-field
            double finalHPadding = hPadding;
            field.textProperty().addListener(observable -> {
                Text _measuringText = new Text(field.getText());
                field.setPrefWidth(_measuringText.getLayoutBounds().getWidth() * textScale + finalHPadding);
            });

        }
        control.setMinSize(label.getWidth() + hPadding, computedHeight + vPadding);
    }

    /**
     * Swaps two nodes in the UI by taking one of the nodes and inserting it into the place of the other.
     *
     * @param node1
     *            The node whose place to insert into.
     * @param node2
     *            The node to insert.
     */
    protected static void swapNode(Node node1, Node node2) {
        Pane parent = (Pane) node1.getParent();
        int index = parent.getChildren().indexOf(node1);
        parent.getChildren().remove(index);
        parent.getChildren().add(index, node2);
    }

    /**
     * Adds a listener for when the control is deselected and triggers the onAction event.
     *
     * @param control
     *            The Control.
     */
    protected void triggerTheOnActionWhenDeselected(Control control) {
        control.focusedProperty().addListener(observable -> {
            // Does not swap if the node is already hidden.
            if (control.getParent() != null) {
                // Save contents
                if (control instanceof TextField field) {
                    field.getOnAction().handle(new ActionEvent());
                } else if (control instanceof ComboBox<?> comboBox) {
                    comboBox.getOnAction().handle(new ActionEvent());
                } else {
                    swapNode(control, label);
                }
            }
        });
    }
}
