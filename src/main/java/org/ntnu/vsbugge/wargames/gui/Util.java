package org.ntnu.vsbugge.wargames.gui;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.ntnu.vsbugge.wargames.army.Army;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.utils.files.ArmyFileUtil;

import java.io.File;
import java.io.IOException;

public class Util {
    static private final FileChooser.ExtensionFilter EXTENSIONS = new FileChooser.ExtensionFilter("Army files", "army");
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

    public static File pickArmyFileToOpen(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick an army");

        ArmyFileUtil files = new ArmyFileUtil();
        fileChooser.setInitialDirectory(files.getDefaultPath());
        fileChooser.setSelectedExtensionFilter(EXTENSIONS);
        File armyFile = fileChooser.showOpenDialog(window);

        if (armyFile == null) {
            return null;
        }

        if (parseArmyFileAndAlertUserIfUnsuccessful(armyFile) == null) {
            return null;
        }

        return armyFile;
    }

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

    public static File pickSaveAsArmyFile(Window window) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as...");

        ArmyFileUtil files = new ArmyFileUtil();
        fileChooser.setSelectedExtensionFilter(EXTENSIONS);
        fileChooser.setInitialDirectory(files.getDefaultPath());

        return fileChooser.showSaveDialog(window);
    }
}
