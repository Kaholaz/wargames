package org.ntnu.vsbugge.wargames.gui.eventhandlers;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.StringSetter;

/**
 * The DoubleClickSwapper for string TextFields.
 *
 * @author vsbugge
 */
public class StringInputDoubleClickSwapper extends AbstractDoubleClickSwapper {
    private final StringSetter setter;

    /**
     * The constructor of the DoubleClickSwapper.
     *
     * @param label
     *            The label to swap.
     * @param setter
     *            The setter to call when the Input from the TextField is read.
     */
    public StringInputDoubleClickSwapper(Label label, StringSetter setter) {
        super(label);
        this.setter = setter;
    }

    /**
     * {@inheritDoc}
     */
    public void runsIfDoubleClick() {
        TextField textField = AbstractDoubleClickSwapper.replaceLabelWithTextField(label);
        textField.requestFocus();

        textField.setOnAction(ignoredEvent -> {
            try {
                if (textField.getText().contains(",")) {
                    // Hack to remove the unit if it is brand new:
                    if (label.getText().equals("New unit...")) {
                        try {
                            setter.setString("");
                        } catch (IllegalArgumentException ignore) {}
                    }
                    throw new IllegalArgumentException("Commas in text fields are unsupported!");
                }
                setter.setString(textField.getText());
            } catch (RuntimeException e) {
                AlertFactory.createExceptionErrorAlert(e).show();
            }

            AbstractDoubleClickSwapper.swapNode(textField, label);
        });

        triggerTheOnActionWhenDeselected(textField);
    }
}
