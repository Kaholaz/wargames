package org.ntnu.vsbugge.wargames.gui;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.models.army.Army;
import org.ntnu.vsbugge.wargames.utils.files.ArmyFileUtil;

import java.io.File;
import java.io.IOException;

/**
 * A utility class for various methods related to picking, opening, and saving Army files.
 *
 * @author vsbugge
 */
public class ArmyFilePickerUtil {
    static private final FileChooser.ExtensionFilter EXTENSIONS = new FileChooser.ExtensionFilter("Army files",
            "*.army", "*.csv");

    /**
     * Opens a dialog for picking an army file, and shows any file parsing errors to the user.
     *
     * @param window
     *            The base window of the javaFX application.
     *
     * @return Returns the picked army if it was successfully parsed. If the file-picker was closed, or the file could
     *         not be parsed, {@code null} is returned.
     */
    public static Army pickArmy(Window window) {
        File armyFile = pickArmyFileToOpen(window);
        return parseArmyFileAndAlertUserIfUnsuccessful(armyFile);
    }

    /**
     * Opens a file picker window and lets the user pick a file to open.
     *
     * @param window
     *            The active window of the JavaFX application.
     *
     * @return The file chosen by the user. If the file could not be parsed as an army file, an error message is
     *         displayed and null is returned.
     */
    public static File pickArmyFileToOpen(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick an army");

        ArmyFileUtil files = new ArmyFileUtil();
        fileChooser.setInitialDirectory(files.getDefaultPath());
        fileChooser.getExtensionFilters().add(EXTENSIONS);
        File armyFile = fileChooser.showOpenDialog(window);

        if (armyFile == null) {
            return null;
        }

        if (parseArmyFileAndAlertUserIfUnsuccessful(armyFile) == null) {
            return null;
        }

        return armyFile;
    }

    /**
     * Parses the file and alerts the user if the parsing was unsuccessful.
     *
     * @param file
     *            The file to parse.
     *
     * @return The parsed Army. If the parsing was unsuccessful, null is returned instead.
     */
    public static Army parseArmyFileAndAlertUserIfUnsuccessful(File file) {
        if (file == null) {
            return null;
        }

        ArmyFileUtil fileUtil = new ArmyFileUtil();
        try {
            return fileUtil.loadFromPath(file);
        } catch (IOException e) {
            AlertFactory.createExceptionErrorAlert(e).show();
            return null;
        }
    }

    /**
     * Picks an existing or new file with a file explorer.
     *
     * @param window
     *            The active window of the JavaFx application.
     *
     * @return The file chosen by the user.
     */
    public static File pickSaveAsArmyFile(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as...");

        ArmyFileUtil files = new ArmyFileUtil();
        fileChooser.getExtensionFilters().add(EXTENSIONS);
        fileChooser.setInitialDirectory(files.getDefaultPath());

        return fileChooser.showSaveDialog(window);
    }
}
