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

public abstract class AbstractDoubleClickSwapper extends AbstractDoubleClickEventHandler {
    protected Label label;

    public AbstractDoubleClickSwapper(Label label) {
        this.label = label;
    }

    protected static TextField replaceLabelWithTextField(Label label) {
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);

        setMatchingSize(label, textField);
        swapNode(label, textField);

        return textField;
    }

    protected static ComboBox<UnitEnum> replaceLabelWithUnitSelector(Label label) {
        ComboBox<UnitEnum> comboBox = new ComboBox<>();

        comboBox.getItems().addAll(UnitEnum.values());
        comboBox.getSelectionModel().select(UnitEnum.fromProperName(label.getText()));

        setMatchingSize(label, comboBox);
        swapNode(label, comboBox);

        return comboBox;
    }

    protected static void setMatchingSize(Label label, Control control) {
        HBox.setHgrow(control, Priority.ALWAYS);

        label.applyCss();
        double computedHeight = label.getHeight();
        double hPadding = 0d;
        double vPadding = 0d;

        control.setPrefSize(0, 0);
        // Conditional size to accommodate a difference in size.
        if (control instanceof ComboBox) {
            hPadding = 25d;
            vPadding = 38d;
        } else if (control instanceof TextField field) {
            HBox.setHgrow(control, Priority.SOMETIMES);

            vPadding = 40d;
            hPadding = 20d;
            double textScale = 1.54d;

            // Compute hPadding to get the desired vertical height.
            Text measuringText = new Text(field.getText());
            hPadding = measuringText.getLayoutBounds().getHeight() * textScale + hPadding - computedHeight;

            // Make size update when the user types in the text-field
            double finalVPadding = vPadding;
            field.textProperty().addListener(observable -> {
                Text _measuringText = new Text(field.getText());
                field.setPrefWidth(_measuringText.getLayoutBounds().getWidth() * textScale + finalVPadding);
            });

        }
        control.setMinSize(label.getWidth() + vPadding, computedHeight + hPadding);
    }

    protected static void swapNode(Node node1, Node node2) {
        Pane parent = (Pane) node1.getParent();
        int index = parent.getChildren().indexOf(node1);
        parent.getChildren().remove(index);
        parent.getChildren().add(index, node2);
    }

    protected void makeHiddenWhenDeselected(Control control) {
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
