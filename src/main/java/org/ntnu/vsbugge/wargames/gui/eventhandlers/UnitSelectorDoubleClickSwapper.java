package org.ntnu.vsbugge.wargames.gui.eventhandlers;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.ntnu.vsbugge.wargames.utils.enums.UnitEnum;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.UnitSetter;

/**
 * The DoubleClickSwapper for unit selector ComboBoxes.
 *
 * @author vsbugge
 */
public class UnitSelectorDoubleClickSwapper extends AbstractDoubleClickSwapper {
    private final UnitSetter setter;

    /**
     * The constructor of the DoubleClickSwapper.
     *
     * @param label
     *            The label to swap.
     * @param setter
     *            The setter to call when the Input from the TextField is read.
     */
    public UnitSelectorDoubleClickSwapper(Label label, UnitSetter setter) {
        super(label);
        this.setter = setter;
    }

    /** {@inheritDoc} */
    @Override
    public void runsIfDoubleClick() {
        ComboBox<UnitEnum> comboBox = AbstractDoubleClickSwapper.replaceLabelWithUnitSelector(label);
        comboBox.requestFocus();

        comboBox.setOnAction(ignoredEvent -> {
            setter.setUnit(comboBox.getSelectionModel().getSelectedItem());
            AbstractDoubleClickSwapper.swapNode(comboBox, label);
        });

        triggerTheOnActionWhenDeselected(comboBox);
    }
}
