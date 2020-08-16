package main.java.controllers;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.util.Duration;
import main.java.SceneNavigator;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller for defining the logic needed in the welcome screen.
 *
 * @author Sam & Isaac & A'maya
 */
public class WelcomeController implements Initializable {
    @FXML
    private Button startButton;

    @FXML
    private BorderPane welcomeContainer;

    @FXML
    private void startGame() {
        MusicController.buttonClick();
        SceneNavigator.getInstance().navigateTo("config");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeContainer.setPrefHeight(Screen.getPrimary().getVisualBounds().getHeight());
        welcomeContainer.setPrefWidth(Screen.getPrimary().getVisualBounds().getWidth());
        //Isaac - Testing animations, will be removed if requested
        //Animation testing begin
        TranslateTransition space = new TranslateTransition();
        space.setDuration(Duration.seconds(3));
        space.setNode(startButton);
        space.setFromX(560);
        space.setToX(560);
        space.setFromY(1500);
        space.setToY(420);
        welcomeContainer.setTop(startButton);
        space.play();
        // Animation testing end

        /** * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
        * Isaac - Testing fonts, will be removed if requested         *
        * If desired, add the following code in the initialize        *
        * method of each controller class to display the font         *
        * specified in the styles.css file on other scenes.           *
        * Be sure to change the welcomeContainer to the correct pane. *
        * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */
        //Font testing begin
        List<String> cssStyles = welcomeContainer.getStylesheets();
        cssStyles.add(this.getClass().getResource("/styles.css").
                toExternalForm());
        //Font testing end
    }
}