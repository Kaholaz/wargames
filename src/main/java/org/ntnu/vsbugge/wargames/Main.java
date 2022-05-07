package org.ntnu.vsbugge.wargames;

import javafx.application.Application;
import org.ntnu.vsbugge.wargames.gui.GUI;

/**
 * Entry point for the application
 *
 * @author vsbugge
 */
public class Main {

    /**
     * Entry point for this java application. Launches the GUI.
     *
     * @param args
     *            This function takes no args.
     */
    public static void main(String[] args) {
        Application.launch(GUI.class, args);
    }
}
