package org.ntnu.vsbugge.wargames.gui.decorators;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.DoubleClickEventHandler;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.utils.enums.UnitEnum;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.IntSetter;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.StringSetter;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.UnitSetter;

public class EditableDecorator {
    public static void makeEditable(Label label, IntSetter setter) {
        DoubleClickEventHandler onDoubleClick = new DoubleClickEventHandler() {
            @Override
            public void runsIfDoubleClick(MouseEvent event) {
                TextField textField = replaceLabelWithTextField(label);
                textField.requestFocus();

                textField.setOnAction(ignoredEvent -> {
                    try {
                        setter.setInt(Integer.parseInt(textField.getText()));
                    } catch (NumberFormatException e) {
                        Platform.runLater(() -> AlertFactory
                                .createExceptionErrorAlert(new NumberFormatException("Please enter an integer!"))
                                .show());
                    } finally {
                        swapNode(textField, label);
                    }
                });
                textField.focusedProperty().addListener(ignored -> {
                    // Does not swap if the reason for the swap was that it was swapped.
                    if (textField.getParent() != null) {
                        swapNode(textField, label);
                    }
                });
            }
        };
        label.setOnMouseClicked(onDoubleClick);
    }

    public static void makeEditable(Label label, StringSetter setter) {
        DoubleClickEventHandler onDoubleClick = new DoubleClickEventHandler() {
            public void runsIfDoubleClick(MouseEvent event) {
                TextField textField = replaceLabelWithTextField(label);
                textField.requestFocus();

                textField.setOnAction(ignoredEvent -> {
                    setter.setString(textField.getText());
                    swapNode(textField, label);
                });
                textField.focusedProperty().addListener(ignored -> {
                    // Does not swap if the reason for the swap was that it was swapped.
                    if (textField.getParent() != null) {
                        swapNode(textField, label);
                    }
                });
            }
        };
        label.setOnMouseClicked(onDoubleClick);
    }

    public static void makeEditable(Label label, UnitSetter setter) {
        DoubleClickEventHandler onDoubleClick = new DoubleClickEventHandler() {
            @Override
            public void runsIfDoubleClick(MouseEvent event) {
                ComboBox<UnitEnum> comboBox = replaceLabelWithUnitSelector(label);
                comboBox.requestFocus();

                comboBox.setOnAction(ignoredEvent -> {
                    setter.setUnit(comboBox.getSelectionModel().getSelectedItem());
                    swapNode(comboBox, label);
                });
                comboBox.focusedProperty().addListener(ignored -> {
                    // Does not swap if the reason for the swap was that it was swapped.
                    if (comboBox.getParent() != null) {
                        swapNode(comboBox, label);
                    }
                });
            }
        };
        label.setOnMouseClicked(onDoubleClick);
    }

    public static void makeNonEditable(Label label) {
        label.setOnMouseClicked(null);
    }

    private static TextField replaceLabelWithTextField(Label label) {
        TextField textField = new TextField();
        textField.setAlignment(Pos.CENTER);

        setMatchingSize(label, textField);
        swapNode(label, textField);

        return textField;
    }

    private static ComboBox<UnitEnum> replaceLabelWithUnitSelector(Label label) {
        ComboBox<UnitEnum> comboBox = new ComboBox<>();

        comboBox.getItems().addAll(UnitEnum.values());
        comboBox.getSelectionModel().select(UnitEnum.fromProperName(label.getText()));

        setMatchingSize(label, comboBox);
        swapNode(label, comboBox);

        return comboBox;
    }

    private static void setMatchingSize(Label label, Control control) {
        HBox.setHgrow(control, Priority.ALWAYS);

        double computedHeight = label.getHeight();
        double hPadding = 0d;
        double vPadding = 0d;

        // Conditional size to accommodate a difference in size.
        if (control instanceof ComboBox) {
            hPadding = 25d;
            vPadding = 38d;
        } else if (control instanceof TextField field) {
            HBox.setHgrow(control, Priority.SOMETIMES);

            hPadding = 20d;
            double finalPadding = hPadding;
            double textScale = 1.54d;
            field.textProperty().addListener(observable -> {
                Text measuringText = new Text(field.getText());
                field.setPrefWidth(measuringText.getLayoutBounds().getWidth() * textScale + finalPadding * 2);
            });
        }
        control.setMinSize(label.getWidth() + vPadding, computedHeight + hPadding);
        control.setPrefSize(0, 0);
    }

    private static void swapNode(Node node1, Node node2) {
        Pane parent = (Pane) node1.getParent();
        int index = parent.getChildren().indexOf(node1);
        parent.getChildren().remove(index);
        parent.getChildren().add(index, node2);
    }
}
