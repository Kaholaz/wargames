package org.ntnu.vsbugge.wargames.gui.factories;

import javafx.scene.control.Alert;

public class AlertFactory {
    private AlertFactory() {
    }

    public static Alert createError(String errorMsg, String errorTitle) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(errorTitle);
        alert.setHeaderText(errorTitle);
        alert.setContentText(errorMsg);
        return alert;
    }

    public static Alert createWarning(String errorMsg, String errorTitle) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(errorTitle);
        alert.setHeaderText(errorTitle);
        alert.setContentText(errorMsg);
        return alert;
    }

    public static Alert createInfo(String errorMsg, String errorTitle) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(errorTitle);
        alert.setHeaderText(errorTitle);
        alert.setContentText(errorMsg);
        return alert;
    }

}
