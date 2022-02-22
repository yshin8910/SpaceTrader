package main.java.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.java.SceneNavigator;
import main.java.models.*;
import main.java.models.Item.*;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Navigates to and from planet and market
 *
 * */

public class MarketController implements Initializable {
    private MarketPlace market;
    private int repairPrice = 200;
    private final int refuelPrice = 50;

    @FXML
    private Text playerCreditsLabel;

    @FXML
    private ListView<String> playerInventoryListView;

    @FXML
    private TilePane itemsPane;

    @FXML
    private Button repairButton;

    @FXML
    private Button refuelButton;

    /**
     * Updates the market UI
     */
    private void update() {
        GameData gameData = GameController.getGameData();
        boolean showMarket = gameData.getCurrentPlanet().equals(
                gameData.getCurrentPlanetLocation());
        playerCreditsLabel.setText("Your Credits: "
                + gameData.getPlayer().getCredits());
        if (showMarket) {
            itemsPane.getChildren().clear();
            itemsPane.setPrefRows(3);
            itemsPane.setPrefColumns(2);
            for (Item item : market.getInventory()) {
                VBox itemContainer = new VBox();
                itemContainer.setStyle("-fx-padding: 10;"
                        + "-fx-border-style: solid inside;"
                        + "-fx-border-width: 2;"
                        + "-fx-border-radius: 5;"
                        + "-fx-border-color: #fff;");
                itemContainer.setSpacing(5.0);
                itemContainer.setPrefWidth(300);
                itemContainer.setAlignment(Pos.CENTER);
                ImageView itemImage = new ImageView(item.getImage());
                itemImage.setFitHeight(50);
                itemImage.setFitWidth(50);
                Text itemName = new Text(item.getName());
                itemName.getStyleClass().add("text-with-shadow");
                Text itemPrice = new Text("Price: " + item.getPrice() + " credits");
                itemPrice.getStyleClass().add("text-with-shadow");
                Text itemQuantity = new Text("Quantity: " + item.getQuantity());
                itemQuantity.getStyleClass().add("text-with-shadow");
                HBox actionsContainer = new HBox();
                actionsContainer.setAlignment(Pos.CENTER);
                actionsContainer.setSpacing(10.0);
                Button buyButton = new Button("Buy");
                buyButton.setOnAction(new HandleBuyButtonClick(item));
                Button negotiateButton = new Button("Sell");
                negotiateButton.setOnAction(new HandleSellButtonClick(item));
                actionsContainer.getChildren().addAll(buyButton, negotiateButton);
                itemContainer.getChildren().addAll(itemImage, itemName, itemPrice,
                        itemQuantity, actionsContainer);
                itemsPane.getChildren().add(itemContainer);
            }
            ObservableList<String> g = FXCollections.observableArrayList();
            playerInventoryListView.setItems(g);
            for (Item e : gameData.getPlayer().getShip().getInventory().getItemsAsCollection()) {
                playerInventoryListView.getItems().add(e.toString());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GameData gameData = GameController.getGameData();
        market = gameData.getCurrentPlanet().getMarket();
        market.getInventory().generateItems(10);

        Difficulty currentDifficulty = GameController.getGameData().getPlayer().getDifficulty();
        Player player = GameController.getGameData().getPlayer();
        int engineerPointsDenominator = currentDifficulty == Difficulty.EASY ? 10
                : currentDifficulty == Difficulty.MEDIUM ? 5 : 1;
        if (player.getEngineerPts() != 0) {
            repairPrice = repairPrice / (player.getEngineerPts() / engineerPointsDenominator);
        }
        repairButton.setText("Repair ship for " + repairPrice + " credits");
        refuelButton.setText("Refuel ship for " + refuelPrice + " credits");

        this.update();
    }

    @FXML
    public void handleRefuelClicked() {
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();

        if (player.getCredits() < refuelPrice) {
            Alert notEnoughCredits = new Alert(Alert.AlertType.ERROR);
            notEnoughCredits.setHeaderText("Not Enough Credits");
            notEnoughCredits.setContentText("Sorry, you don't have enough credits to buy this.");
            notEnoughCredits.showAndWait();
            return;
        }

        player.getShip().getFuelTank().refuel();
        player.takeCredits(refuelPrice);
        Alert successfulPurchase = new Alert(Alert.AlertType.INFORMATION);
        successfulPurchase.setHeaderText("Successful Refuel Purchase");
        successfulPurchase.setContentText("Your transaction was processed. Thanks "
                + "for shopping with us.");
        successfulPurchase.showAndWait();
        update();
    }

    @FXML
    public void handleRepairClick() {
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();

        if (player.getCredits() < repairPrice) {
            Alert notEnoughCredits = new Alert(Alert.AlertType.ERROR);
            notEnoughCredits.setHeaderText("Not Enough Credits");
            notEnoughCredits.setContentText("Sorry, you don't have enough credits to buy this.");
            notEnoughCredits.showAndWait();
            return;
        }

        player.getShip().repair(10);
        player.takeCredits(repairPrice);
        Alert successfulPurchase = new Alert(Alert.AlertType.INFORMATION);
        successfulPurchase.setHeaderText("Successful Repair Purchase");
        successfulPurchase.setContentText("Your transaction was processed. Thanks "
                + "for shopping with us.");
        successfulPurchase.showAndWait();
        update();
    }

    /**
     * Navigate to planet view after back button clicked on market
     * view
     */
    @FXML
    public void backButtonClick() {
        MusicController.buttonClick();
        SceneNavigator.getInstance().navigateTo("planet");
    }

    /**
     * Allows player to upgrade Item
     */
    @FXML
    public void upgradeItem() {
        MusicController.buttonClick();
        String selectedItem = playerInventoryListView.getSelectionModel().getSelectedItem();
        Item foundItem = null;
        if (selectedItem != null) {
            // the following code leads us to the player's ship inventory
            GameData gameData = GameController.getGameData();
            Player player = gameData.getPlayer();
            Ship ship = player.getShip();
            Inventory inventory = ship.getInventory();
            Collection<Item> items = inventory.getItemsAsCollection();

            // for loop to find item;
            for (Item item : items) {
                if (item.toString().equals(selectedItem)) {
                    foundItem = item;
                }
            }
            if (foundItem != null && foundItem.getLevel().equals(ItemLevel.GOLD)) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("INVALID UPGRADE ATTEMPT");
                errorAlert.setContentText("You cannot upgrade a gold item!");
                errorAlert.showAndWait();
            } else if (foundItem != null) {
                Popup popUp = new Popup();
                popUp.display(foundItem);
            }
            return;
        }
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("ERROR");
        errorAlert.setContentText("You must select an item before attempting to upgrade!");
        errorAlert.showAndWait();
    }

    public class HandleBuyButtonClick implements EventHandler<ActionEvent> {
        private Item item;

        /**
         * @param item Item to buy
         */
        public HandleBuyButtonClick(Item item) {
            this.item = item;
        }

        @Override
        public void handle(ActionEvent mouseEvent) {
            MusicController.buttonClick();
            boolean didBuy = market.buy(item, 1);
            if (!didBuy) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("INVALID PURCHASE ATTEMPT");
                errorAlert.setContentText("You do not have enough credits or stock to buy item "
                        + item.getName() + "!");
                errorAlert.showAndWait();
                return;
            }
            update();
        }
    }

    public class HandleSellButtonClick implements EventHandler<ActionEvent> {
        private Item item;

        /**
         * @param item Item to buy
         */
        public HandleSellButtonClick(Item item) {
            this.item = item;
        }

        @Override
        public void handle(ActionEvent mouseEvent) {
            MusicController.buttonClick();
            boolean didSell = market.sell(item);
            if (!didSell) {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setHeaderText("INVALID SELL ATTEMPT");
                errorAlert.setContentText("You do not have enough of "
                        + item.getName() + " to sell it!");
                errorAlert.showAndWait();
                return;
            }
            update();
        }
    }

    /**
     * This is a Popup Window Class for Upgrading Item
     * @author A'maya Solomon
     */
    private class Popup { //
        /**
         * This method will display the upgrade pop up
         * @param item that the player wants to upgrade
         */
        public void display(Item item) {
            int upgradeCost;
            String newLevel;
            if (item.getLevel().equals(ItemLevel.BRONZE)) {
                upgradeCost = 15 * item.getQuantity();
                newLevel = "SILVER";
            } else {
                upgradeCost = 100 * item.getQuantity();
                newLevel = "GOLD";
            }
            Stage popUp = new Stage();
            popUp.initModality(Modality.APPLICATION_MODAL);
            popUp.setTitle("WARNING");

            // Warning
            Label welcome = new Label("Do you wish to upgrade this item to "
                    + newLevel + "?");
            welcome.setTextFill(Color.MEDIUMPURPLE);
            welcome.setFont(new Font("Verdana", 20));

            // Price
            Label label = new Label("Price:" + upgradeCost);
            label.setTextFill(Color.web("#0076a3"));
            label.setFont(new Font("Verdana", 12));

            // Cancel Button
            Button cancel = new Button("CANCEL");
            cancel.setOnAction(e -> {
                popUp.close();
                MusicController.buttonClick();
            });

            // Upgrade Button
            Button upgrade = new Button("UPGRADE");
            upgrade.setOnAction(e -> {
                MusicController.buttonClick();
                IUpgradable upgradableItem = (IUpgradable) item;
                boolean result = upgradableItem.upgradeItem();
                if (!result) {
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("ERROR");
                    errorAlert.setContentText("You do not have enough credits to"
                            + " upgrade the item!.");
                    errorAlert.showAndWait();
                } else {
                    update();
                }
                popUp.close();
            });
            // VBox
            VBox layout = new VBox(10);
            layout.setStyle("-fx-background-color: #D3D3D3");
            layout.getChildren().addAll(welcome, label);
            layout.setAlignment(Pos.CENTER);
            layout.setSpacing(20);

            // HBox
            HBox buttons = new HBox(5);
            layout.getChildren().addAll(upgrade, cancel);

            // BorderPane
            BorderPane bp = new BorderPane();
            bp.setCenter(layout);
            bp.setBottom(buttons);

            Scene scene1 = new Scene(bp, 550, 250, Color.PURPLE);
            popUp.setScene(scene1);
            popUp.showAndWait();
        }
    }
}
