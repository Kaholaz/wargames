package org.ntnu.vsbugge.wargames.gui.decorators;

import javafx.scene.control.Label;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.IntInputDoubleClickSwapper;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.StringInputDoubleClickSwapper;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.UnitSelectorDoubleClickSwapper;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.IntSetter;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.StringSetter;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.UnitSetter;

public class EditableDecorator {
    public static void makeEditable(Label label, IntSetter setter) {
        label.setOnMouseClicked(new IntInputDoubleClickSwapper(label, setter));
    }

    public static void makeEditable(Label label, StringSetter setter) {
        label.setOnMouseClicked(new StringInputDoubleClickSwapper(label, setter));
    }

    public static void makeEditable(Label label, UnitSetter setter) {
        label.setOnMouseClicked(new UnitSelectorDoubleClickSwapper(label, setter));
    }

    public static void makeNonEditable(Label label) {
        label.setOnMouseClicked(null);
    }
}
