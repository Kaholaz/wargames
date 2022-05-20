package org.ntnu.vsbugge.wargames.gui.decorators;

import javafx.scene.control.Label;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.IntInputDoubleClickSwapper;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.StringInputDoubleClickSwapper;
import org.ntnu.vsbugge.wargames.gui.eventhandlers.UnitSelectorDoubleClickSwapper;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.IntSetter;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.StringSetter;
import org.ntnu.vsbugge.wargames.utils.funcinterfaces.UnitSetter;

public class EditableDecorator {
    private static final String[] EDITABLE_STYLE_CLASSES = {"editable-label", "padded-light"};

    public static void makeEditable(Label label, IntSetter setter) {
        label.setOnMouseClicked(new IntInputDoubleClickSwapper(label, setter));
        addStyleClass(label);
    }

    public static void makeEditable(Label label, StringSetter setter) {
        label.setOnMouseClicked(new StringInputDoubleClickSwapper(label, setter));
        addStyleClass(label);
    }

    public static void makeEditable(Label label, UnitSetter setter) {
        label.setOnMouseClicked(new UnitSelectorDoubleClickSwapper(label, setter));
        addStyleClass(label);
    }

    public static void makeNonEditable(Label label) {
        label.setOnMouseClicked(null);
        removeStyleClass(label);
    }

    private static void removeStyleClass(Label label) {
        label.getStyleClass().removeAll(EDITABLE_STYLE_CLASSES);
    }

    private static void addStyleClass(Label label) {
        removeStyleClass(label);
        label.getStyleClass().addAll(EDITABLE_STYLE_CLASSES);
    }
}
