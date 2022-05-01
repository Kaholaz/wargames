package org.ntnu.vsbugge.wargames.gui.factories;

import javafx.scene.control.Label;
import org.ntnu.vsbugge.wargames.gui.decorators.IconLabelDecorator;

public class IconLabelFactory {
    public static Label createIconLabel(String labelText, String icon) {
        Label label = new Label(labelText);
        IconLabelDecorator.setIcon(label, icon);
        return label;
    }
}
