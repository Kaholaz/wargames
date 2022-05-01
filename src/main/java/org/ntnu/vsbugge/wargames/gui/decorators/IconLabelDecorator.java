package org.ntnu.vsbugge.wargames.gui.decorators;

import javafx.scene.control.Label;

public class IconLabelDecorator {
    /**
     * Class is static, so no constructor needed.
     */
    private IconLabelDecorator() {
    }

    public static void setIcon(Label label, String icon) {
        label.getStyleClass().add("icon-text");
        label.getStyleClass().add(String.format("%s-icon", icon));
    }

    public static void removeIcon(Label label) {
        label.getStyleClass().remove("icon-label");
        label.getStyleClass().removeIf(styleClass -> styleClass.endsWith("-icon"));
    }
}
