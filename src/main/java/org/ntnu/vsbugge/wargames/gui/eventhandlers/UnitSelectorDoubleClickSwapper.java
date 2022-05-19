package org.ntnu.vsbugge.wargames.gui.eventhandlers;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.ntnu.vsbugge.wargames.utils.enums.UnitEnum;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.UnitSetter;

public class UnitSelectorDoubleClickSwapper extends AbstractDoubleClickSwapper {
    private final UnitSetter setter;

    public UnitSelectorDoubleClickSwapper(Label label, UnitSetter setter) {
        super(label);
        this.setter = setter;
    }
    @Override
    public void runsIfDoubleClick() {
        ComboBox<UnitEnum> comboBox = AbstractDoubleClickSwapper.replaceLabelWithUnitSelector(label);
        comboBox.requestFocus();

        comboBox.setOnAction(ignoredEvent -> {
            setter.setUnit(comboBox.getSelectionModel().getSelectedItem());
            AbstractDoubleClickSwapper.swapNode(comboBox, label);
        });

        makeHiddenWhenDeselected(comboBox);
    }
}
