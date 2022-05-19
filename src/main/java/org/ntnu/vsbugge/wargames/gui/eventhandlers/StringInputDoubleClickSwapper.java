package org.ntnu.vsbugge.wargames.gui.eventhandlers;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.StringSetter;

public class StringInputDoubleClickSwapper extends AbstractDoubleClickSwapper {
    StringSetter setter;

    public StringInputDoubleClickSwapper(Label label, StringSetter setter) {
        super(label);
        this.setter = setter;
    }

    public void runsIfDoubleClick() {
        TextField textField = AbstractDoubleClickSwapper.replaceLabelWithTextField(label);
        textField.requestFocus();

        textField.setOnAction(ignoredEvent -> {
            setter.setString(textField.getText());
            AbstractDoubleClickSwapper.swapNode(textField, label);
        });

        makeHiddenWhenDeselected(textField);
    }
}
