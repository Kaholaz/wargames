package org.ntnu.vsbugge.wargames.gui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * This is the main gui class of the application. The class has also many helper methods that can be used in Controller
 * classes.
 *
 * @author vsbugge
 */
public class GUI extends Application {

    private static final int STAGE_MIN_HEIGHT = 700;
    private static final int STAGE_MIN_WIDTH = 1000;
    private static final String VIEWS_ROOT_DIRECTORY = "/gui/views/";

    /** {@inheritDoc} */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Wargames");
        setInitialSceneOfStage(primaryStage, "launchPage.fxml", true);
    }

    /**
     * Returns the url to a file inside the views root directory specified by the VIEWS_ROOT_DIRECTORY constant.
     *
     * @param resourcePath
     *            A name of a resource (FXML document) in the directory specified by the VIEWS_ROOT_DIRECTORY constant.
     *
     * @return The url to a file inside the views root directory specified by the VIEWS_ROOT_DIRECTORY constant.
     */
    private static URL getViewResource(String resourcePath) {
        return GUI.class.getResource(VIEWS_ROOT_DIRECTORY + resourcePath);
    }

    /**
     * Sets a scene by getting the current stage from a given nod and replacing the scene. The path should be expressed
     * relative to the 'views' directory.
     *
     * @param node
     *            A node that can extract the current stage.
     * @param fxmlDocumentName
     *            The path for the FXML sheet for the next scene relative to the directory specified by the
     *            VIEWS_ROOT_DIRECTORY constant.
     */
    public static void setSceneFromNode(Node node, String fxmlDocumentName) {
        // Prepares the new scene.
        Parent root = GUI.checkedFXMLLoader(getViewResource(fxmlDocumentName));
        Scene prev = node.getScene();
        Stage stage = (Stage) node.getScene().getWindow();

        // Sets the new scene.
        Scene scene = new Scene(root, prev.getWidth(), prev.getHeight());
        stage.setScene(scene);
    }

    /**
     * Set the scene of the stage by extracting the stage from an event. This might be a better solution if there is no
     * node to extract the stage from. The path should be expressed relative to the 'views' directory.
     *
     * @param actionEvent
     *            A javaFX event to extract the stage from.
     * @param fxmlDocumentName
     *            The path for the FXML sheet for the next scene relative to the directory specified by the
     *            VIEWS_ROOT_DIRECTORY constant.
     */
    public static void setSceneFromActionEvent(ActionEvent actionEvent, String fxmlDocumentName) {
        setSceneFromNode((Node) actionEvent.getSource(), fxmlDocumentName);
    }

    /**
     * Change the scene of a given stage. The path should be expressed relative to the 'views' directory.
     *
     * @param stage
     *            The stage to swap the scene of.
     * @param fxmlDocumentName
     *            The path for the FXML sheet for the next scene relative to the directory specified by the
     *            VIEWS_ROOT_DIRECTORY constant.
     * @param startMaximized
     *            If this is set to true, the stage will start maximized, if false it will not.
     */
    public static void setInitialSceneOfStage(Stage stage, String fxmlDocumentName, boolean startMaximized) {
        // Prepares the new scene
        Parent root = checkedFXMLLoader(getViewResource(fxmlDocumentName));

        // Sets scene
        Scene scene = new Scene(root);
        stage.setScene(scene);

        // Sets stage parameters
        stage.setMinHeight(STAGE_MIN_HEIGHT);
        stage.setMinWidth(STAGE_MIN_WIDTH);
        stage.setMaximized(startMaximized);

        // Pulls the window to the front
        stage.show();
    }

    /**
     * This is a helper method that checks the loader for exceptions and returns the Parent if successful. This makes it
     * easier to troubleshoot errors. This method should not produce errors unless something is worng with the FXMl page
     * or the connected controller.
     *
     * @param url
     *            The url of an FXML page.
     *
     * @return The loaded Parent.
     *
     * @throws java.lang.IllegalArgumentException
     *             Throws an exception if something went wrong during the loading of the FXML page.
     */
    private static Parent checkedFXMLLoader(URL url) throws IllegalArgumentException {
        Parent root = null;

        try {
            root = FXMLLoader.load(url);
        } catch (java.io.IOException e) {
            System.out.println("Could not load XML file... Check the controller class for " + e.getMessage());
            System.out.println("Stack Trace:");
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.out.println("Most likely, you mistyped the fxml resource path that you tried to load.");
            System.out.println(
                    "Remember to add / in the beginning of the path and give the path relative to the resources folder.");
            System.out.println("\n" + e.getClass() + ": " + e.getMessage());
            System.out.println("Stack Trace:");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("A different, unexpected exception was thrown while loading the FXML file...\n\n"
                    + e.getClass() + ": " + e.getMessage());
            System.out.println("Stack Trace:");
            e.printStackTrace();
        }

        // We don't want a NullPointerException down the line.
        if (root == null) {
            throw new IllegalArgumentException("Something went wrong during the loading of the FXML page");
        }

        return root;
    }
}
