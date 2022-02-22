package main.java;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import main.java.controllers.GameController;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Utility class for navigating between scenes.
 *
 * To access the instance use the getInstance method.
 * When adding scenes use the addScene method in Main.java with a / in front of the file name
 * When switching between scenes use the navigateTo method
 *
 */
public class SceneNavigator {
    private static SceneNavigator instance = new SceneNavigator();
    private static final String ROOT = "/views/root.fxml";
    private static Map<String, String> scenes = new HashMap<>();
    private static GameController rootController;

    /**
     * Gets the current instance of the scene navigator
     *
     * @return instance of current scene navigator
     */
    public static SceneNavigator getInstance() {
        return instance;
    }

    /**
     * Creates the root application scene.
     * @return Created root scene
     */
    public static Scene createRootScene() {
        return new Scene(getRootPane());
    }

    /**
     * Adds a scene to the hash map
     * @param sceneName scene identifier
     * @param fileName file containing fxml for scene
     */
    public static void addScene(String sceneName, String fileName) {
        scenes.put(sceneName, fileName);
    }

    /**
     * Gets the root fxml layout.
     *
     * @return the root pane.
     */
    private static Pane getRootPane() {
        Pane mainPane = null;
        try {
            FXMLLoader loader =
                    new FXMLLoader(SceneNavigator.class.getResource(ROOT));
            mainPane = loader.load();
            rootController = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mainPane;
    }

    /**
     * Loads the scene specified by the fxml file into the
     * sceneHolder pane of the root entrance into the application.
     *
     * @param sceneName Scene name to be loaded corresponding to the
     *                  sceneName provided in Main.java.
     */
    public static void navigateTo(String sceneName) {
        try {
            URL url =
                    SceneNavigator.class.getResource(getScene(sceneName));
            FXMLLoader loader = new FXMLLoader(url);
            Node scene = loader.load();
            rootController.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a scene fileName from the hash map.
     * @param sceneName Scene identifier
     * @return fileName of scene
     */
    private static String getScene(String sceneName) {
        String scene = scenes.get(sceneName);
        if (scene == null) {
            throw new NoSuchElementException("Scene " + sceneName + " not found");
        } else {
            return scene;
        }
    }
}
