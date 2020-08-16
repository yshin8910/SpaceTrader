package main.java;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.java.controllers.MusicController;

public class Main extends Application {
    private SceneNavigator sceneNavigator = SceneNavigator.getInstance();

    @Override
    public void start(Stage primaryStage) {

        MusicController.multipleThreads();

        sceneNavigator.addScene("welcome", "/views/welcome.fxml");
        sceneNavigator.addScene("config", "/views/config.fxml");
        sceneNavigator.addScene("characterSheet", "/views/character-sheet.fxml");
        sceneNavigator.addScene("universe", "/views/universe.fxml");
        sceneNavigator.addScene("planet", "/views/planet.fxml");
        sceneNavigator.addScene("market", "/views/market.fxml");
        sceneNavigator.addScene("banditEncounter", "/views/bandit-encounter.fxml");
        sceneNavigator.addScene("traderEncounter", "/views/trader-encounter.fxml");
        sceneNavigator.addScene("policeEncounter", "/views/police-encounter.fxml");
        sceneNavigator.addScene("gameOver", "/views/game-over.fxml");
        sceneNavigator.addScene("gameWin", "/views/game-win.fxml");
        sceneNavigator.addScene("gameCredits", "/views/game-credits.fxml");

        primaryStage.setTitle("Space Trader");
        primaryStage.setMinHeight(720); // added by A'maya
        primaryStage.setMinWidth(1280); // added by A'maya
        Scene rootScene = sceneNavigator.createRootScene();
        rootScene.getStylesheets().add(getClass().getResource("/styles.css").toString());
        primaryStage.setScene(rootScene);
        sceneNavigator.navigateTo("welcome");
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}