package org.ntnu.vsbugge.wargames.gui;

import javafx.stage.FileChooser;
import javafx.stage.Window;
import org.ntnu.vsbugge.wargames.army.Army;
import org.ntnu.vsbugge.wargames.gui.factories.AlertFactory;
import org.ntnu.vsbugge.wargames.utils.files.ArmyFileUtil;

import java.io.File;
import java.io.IOException;

public class Util {
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pick an army");

        try {
            ArmyFileUtil files = new ArmyFileUtil();
            fileChooser.setInitialDirectory(files.getDefaultPath());
            File armyFile = fileChooser.showOpenDialog(window);
            return files.loadFromPath(armyFile);
        } catch (IOException e) {
            AlertFactory.createExceptionErrorAlert(e).show();
            return null;
        } catch (NullPointerException ignore) {
            return null;
        }
    }
}
