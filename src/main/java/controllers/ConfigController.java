package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import main.java.SceneNavigator;
import main.java.models.Difficulty;
import main.java.models.GameData;
import main.java.models.Player;
import main.java.models.Skill;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for defining the logic needed in the configuration screen.
 *
 * @author Sam and A'maya
 */
public class ConfigController implements Initializable {
    private int engineerPoints;
    private int pilotPoints;
    private int fighterPoints;
    private int merchantPoints;
    private int remainingPoints;
    private Difficulty difficulty = Difficulty.EASY;
    private int incrementAmount = determineIncrementAmount();

    @FXML
    private TextField characterName;

    @FXML
    private RadioButton easy;

    @FXML
    private RadioButton medium;

    @FXML
    private RadioButton hard;

    @FXML
    private Text remainingSkillPoints;

    @FXML
    private Text pilotSkillPoints;

    @FXML
    private Text engineerSkillPoints;

    @FXML
    private Text fighterSkillPoints;

    @FXML
    private Text merchantSkillPoints;

    @FXML
    private void handleSubmit() {
        MusicController.buttonClick();
        if (!characterName.getText().isEmpty()) {
            Player player = new Player(characterName.getText(), difficulty);
            player.allocatePoints(engineerPoints, pilotPoints, fighterPoints, merchantPoints);
            GameData currentGameData = GameController.getGameData();
            currentGameData.setPlayer(player);
            GameController.setGameData(currentGameData);
            SceneNavigator.getInstance().navigateTo("characterSheet");
        }
    }

    @FXML
    private void handleGoBack() {
        MusicController.buttonClick();
        SceneNavigator.getInstance().navigateTo("welcome");
    }

    @FXML
    private void decreasePilotSkillPoints() {
        decreaseSkillPoints(incrementAmount, Skill.PILOT);
        MusicController.buttonClick();
    }

    @FXML
    private void increasePilotSkillPoints() {
        increaseSkillPoints(incrementAmount, Skill.PILOT);
        MusicController.buttonClick();
    }

    @FXML
    private void decreaseEngineerSkillPoints() {
        decreaseSkillPoints(incrementAmount, Skill.ENGINEER);
        MusicController.buttonClick();
    }

    @FXML
    private void increaseEngineerSkillPoints() {
        increaseSkillPoints(incrementAmount, Skill.ENGINEER);
        MusicController.buttonClick();
    }

    @FXML
    private void decreaseFighterSkillPoints() {
        decreaseSkillPoints(incrementAmount, Skill.FIGHTER);
        MusicController.buttonClick();
    }

    @FXML
    private void increaseFighterSkillPoints() {
        increaseSkillPoints(incrementAmount, Skill.FIGHTER);
        MusicController.buttonClick();
    }

    @FXML
    private void decreaseMerchantSkillPoints() {
        decreaseSkillPoints(incrementAmount, Skill.MERCHANT);
        MusicController.buttonClick();
    }

    @FXML
    private void increaseMerchantSkillPoints() {
        increaseSkillPoints(incrementAmount, Skill.MERCHANT);
        MusicController.buttonClick();
    }

    /**
     * Sets the engineer skill points and updates the GUI text
     * @param engineerPoints Engineer skill points
     */
    private void setEngineerSkillPoints(int engineerPoints) {
        this.engineerPoints = engineerPoints;
        engineerSkillPoints.setText(Integer.toString(engineerPoints));
    }

    /**
     * Sets the pilot skill points and updates the GUI text
     * @param pilotPoints Pilot skill points
     */
    private void setPilotSkillPoints(int pilotPoints) {
        this.pilotPoints = pilotPoints;
        pilotSkillPoints.setText(Integer.toString(pilotPoints));
    }

    /**
     * Sets the fighter skill points and updates the GUI text
     * @param fighterPoints Fighter skill points
     */
    private void setFighterSkillPoints(int fighterPoints) {
        this.fighterPoints = fighterPoints;
        fighterSkillPoints.setText(Integer.toString(fighterPoints));
    }

    /**
     * Sets the merchant skill points and updates the GUI text
     * @param merchantPoints Merchant skill points
     */
    private void setMerchantSkillPoints(int merchantPoints) {
        this.merchantPoints = merchantPoints;
        merchantSkillPoints.setText(Integer.toString(merchantPoints));
    }

    /**
     * Sets the remaining skill points and updates the GUI text
     * @param remainingPoints Remaining skill points
     */
    private void setRemainingSkillPoints(int remainingPoints) {
        this.remainingPoints = remainingPoints;
        remainingSkillPoints.setText(Integer.toString(remainingPoints));
    }

    /**
     * Decreases the specified skill points by an amount
     * @param amount Amount to decrease by
     * @param skill Skill to apply the decrease to
     */
    private void decreaseSkillPoints(int amount, Skill skill) {
        boolean executed = false;
        switch (skill) {
        case ENGINEER:
            if (engineerPoints > 0) {
                executed = true;
            }
            setEngineerSkillPoints(Math.max(0, engineerPoints - amount));
            break;
        case PILOT:
            if (pilotPoints > 0) {
                executed = true;
            }
            setPilotSkillPoints(Math.max(0, pilotPoints - amount));
            break;
        case FIGHTER:
            if (fighterPoints > 0) {
                executed = true;
            }
            setFighterSkillPoints(Math.max(0, fighterPoints - amount));
            break;
        case MERCHANT:
            if (merchantPoints > 0) {
                executed = true;
            }
            setMerchantSkillPoints(Math.max(0, merchantPoints - amount));
            break;
        default:
            break;
        }

        // only add to the remaining points if the decrement occurred
        if (executed) {
            setRemainingSkillPoints(remainingPoints + amount);
        }
    }

    /**
     * Increases the specified skill points by an amount
     * @param amount Amount to increase by
     * @param skill Skill to apply the increase to
     */
    private void increaseSkillPoints(int amount, Skill skill) {
        if (remainingPoints >= amount) {
            switch (skill) {
            case ENGINEER:
                setEngineerSkillPoints(engineerPoints + amount);
                break;
            case PILOT:
                setPilotSkillPoints(pilotPoints + amount);
                break;
            case FIGHTER:
                setFighterSkillPoints(fighterPoints + amount);
                break;
            case MERCHANT:
                setMerchantSkillPoints(merchantPoints + amount);
                break;
            default:
                break;
            }
            setRemainingSkillPoints(Math.max(0, remainingPoints - amount));
        }
    }

    /**
     * Determines the increment amount based on the selected difficulty
     *
     * Current config:
     * Easy - increment by 10
     * Medium - increment by 1
     * Hard - increment by 1
     * @return Increment amount
     */
    private int determineIncrementAmount() {
        if (difficulty == Difficulty.EASY) {
            return 10;
        } else {
            return 1;
        }
    }

    /**
     * Sets all of the skill points to 0
     */
    private void setInitialPoints() {
        setEngineerSkillPoints(0);
        setPilotSkillPoints(0);
        setMerchantSkillPoints(0);
        setFighterSkillPoints(0);
        setRemainingSkillPoints(difficulty.getSkillPoints());
    }

    public void update() {
        // ToggleGroup allows for only one of the inputs to be selected at any one point
        ToggleGroup toggleGroup = new ToggleGroup();
        easy.setToggleGroup(toggleGroup);
        easy.setSelected(true);
        medium.setToggleGroup(toggleGroup);
        hard.setToggleGroup(toggleGroup);
        easy.setOnAction(new DifficultyChangeHandler());
        medium.setOnAction(new DifficultyChangeHandler());
        hard.setOnAction(new DifficultyChangeHandler());

        // load the players previous config if it exists
        GameData gameData = GameController.getGameData();
        if (gameData.getPlayer() != null) {
            Player player = gameData.getPlayer();
            difficulty = player.getDifficulty();
            switch (difficulty) {
            case EASY:
                easy.setSelected(true);
                break;
            case MEDIUM:
                medium.setSelected(true);
                break;
            case HARD:
                hard.setSelected(true);
                break;
            default:
                break;
            }
            int remainingSkillPoints = player.getDifficulty().getSkillPoints()
                    - (player.getEngineerPts() + player.getPilotPts()
                    + player.getFighterPts() + player.getMerchantPts());
            characterName.setText(player.getName());
            incrementAmount = determineIncrementAmount();
            setRemainingSkillPoints(remainingSkillPoints);
            setEngineerSkillPoints(player.getEngineerPts());
            setPilotSkillPoints(player.getPilotPts());
            setFighterSkillPoints(player.getFighterPts());
            setMerchantSkillPoints(player.getMerchantPts());
        } else {
            setInitialPoints();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }

    class DifficultyChangeHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            // In the future, find a better way other than text to
            // determine which difficulty was selected
            String id = ((RadioButton) (event.getSource())).getId();
            switch (id) {
            case "easy":
                MusicController.buttonClick();
                difficulty = Difficulty.EASY;
                setInitialPoints();
                incrementAmount = determineIncrementAmount();
                update();
                break;
            case "medium":
                MusicController.buttonClick();
                difficulty = Difficulty.MEDIUM;
                setInitialPoints();
                incrementAmount = determineIncrementAmount();
                update();
                break;
            case "hard":
                MusicController.buttonClick();
                difficulty = Difficulty.HARD;
                setInitialPoints();
                incrementAmount = determineIncrementAmount();
                update();
                break;
            default:
                MusicController.buttonClick();
                break;
            }
        }
    }
}
