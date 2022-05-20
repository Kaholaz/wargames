package org.ntnu.vsbugge.wargames.gui.eventhandlers;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.IntSetter;

/**
 * The DoubleClickSwapper for integer TextFields.
 *
 * @author vsbugge
 */
public class IntInputDoubleClickSwapper extends AbstractDoubleClickSwapper {
    private IntSetter setter;

    /**
     * The constructor of the DoubleClickSwapper.
     *
     * @param label
     *            The label to swap.
     * @param setter
     *            The setter to call when the Input from the TextField is read.
     */
    public IntInputDoubleClickSwapper(Label label, IntSetter setter) {
        super(label);
        this.setter = setter;
    }

    /**
     * The function that is called when the label is double-clicked.
     */
    @Override
    public void runsIfDoubleClick() {
        TextField textField = AbstractDoubleClickSwapper.replaceLabelWithTextField(label);
        textField.requestFocus();

        textField.setOnAction(ignoredEvent -> {
            try {
                setter.setInt(Integer.parseInt(textField.getText()));
            } catch (NumberFormatException e) {
                Platform.runLater(() -> AlertFactory
                        .createExceptionErrorAlert(new NumberFormatException("Please enter an integer!")).show());
            } finally {
                AbstractDoubleClickSwapper.swapNode(textField, label);
            }
        });

        triggerTheOnActionWhenDeselected(textField);
    }
}
