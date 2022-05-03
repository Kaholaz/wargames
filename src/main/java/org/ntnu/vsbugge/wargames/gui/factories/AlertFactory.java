package org.ntnu.vsbugge.wargames.gui.factories;

import javafx.scene.control.Alert;

/**
 * This class is used to create alerts.
 */
public class AlertFactory {
    private AlertFactory() {
    }

    /**
     * Creates an alert created by the given parameters.
     *
     * @param alertType
     *            The type of the alert.
     * @param alertMessage
     *            The alert message.
     * @param alertTitle
     *            The alert title. This will also be used for the alert header.
     *
     * @return The alert.
     */
    public static Alert createAlert(Alert.AlertType alertType, String alertMessage, String alertTitle) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertTitle);
        alert.setHeaderText(alertTitle);
        alert.setContentText(alertMessage);
        return alert;
    }
}
