package main.java.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.java.exceptions.InventoryFullException;
import main.java.models.*;
import main.java.models.Item.Item;
import main.java.models.NPC.Trader;

import java.net.URL;
import java.util.ResourceBundle;

public class TraderEncounterController implements Initializable {
    private Trader trader;
    private Player player;
    private Planet planet;

    @FXML
    private Text playerCreditAmount;

    @FXML
    private Text dialogText;

    @FXML
    private TilePane itemsPane;

    public void updateCredits() {
        Player player = GameController.getGameData().getPlayer();
        playerCreditAmount.setText("Your credits: " + player.getCredits() + " credits");
    }

    public void renderItems() {
        itemsPane.getChildren().clear();
        itemsPane.setPrefRows(3);
        itemsPane.setPrefColumns(2);
        for (Item item : trader.getInventory()) {
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
            Button negotiateButton = new Button("Negotiate");
            negotiateButton.setOnAction(new HandleNegotiateButtonClick(item));
            if (item.hasBeenNegotiated()) {
                negotiateButton.setDisable(true);
            } else {
                negotiateButton.setDisable(false);
            }
            actionsContainer.getChildren().addAll(buyButton, negotiateButton);
            itemContainer.getChildren().addAll(itemImage, itemName, itemPrice,
                    itemQuantity, actionsContainer);
            itemsPane.getChildren().add(itemContainer);
        }
    }

    @FXML
    public void handleIgnore() {
        MusicController.buttonClick();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Thanks for Trading");
        alert.setContentText(trader.getIgnoreText());
        alert.showAndWait();
        player.getShip().travelTo(planet);
    }

    @FXML
    public void handleRob() {
        GameData gameData = GameController.getGameData();
        Player player = gameData.getPlayer();

        MusicController.buttonClick();
        boolean didRob;
        try {
            didRob = trader.rob(player);
        } catch (InventoryFullException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("ROB SUCCESSFUL");
            alert.setContentText("You don't have enough space for these items... looks like your"
                + " new items are lost trader.");
            player.getShip().travelTo(planet);
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (!didRob) {
            alert.setHeaderText("ROB FAILED");
            alert.setContentText(trader.getRobFailedText());
        } else {
            alert.setHeaderText("ROB SUCCESSFUL");
            alert.setContentText(trader.getDeathText());
        }
        updateCredits();
        renderItems();
        alert.showAndWait();
        player.getShip().travelTo(planet);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        trader = new Trader("Andrew");
        player = GameController.getGameData().getPlayer();
        planet  =  GameController.getGameData().getCurrentPlanet();
        dialogText.setText(trader.getWelcomeText());
        trader.getInventory().generateItems(10);
        updateCredits();
        renderItems();
    }

    public class HandleBuyButtonClick implements EventHandler<ActionEvent> {
        private Item item;

        public HandleBuyButtonClick(Item item) {
            this.item = item;
        }

        @Override
        public void handle(ActionEvent mouseEvent) {
            MusicController.buttonClick();
            System.out.println(item.getPrice());
            boolean didBuy = trader.buy(item, 1);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (!didBuy) {
                alert.setHeaderText("INVALID PURCHASE ATTEMPT");
                alert.setContentText("You do not have enough credits or stock to buy item "
                        + item.getName() + "!");
                alert.showAndWait();
                //display ignoreText
            } else {
                alert.setHeaderText("BUY ITEM");
                alert.setContentText(trader.getBuyItemText());
                alert.showAndWait();
            }
            renderItems();
            updateCredits();
        }
    }

    public class HandleNegotiateButtonClick implements EventHandler<ActionEvent> {
        private Item item;

        public HandleNegotiateButtonClick(Item item) {
            this.item = item;
        }

        @Override
        public void handle(ActionEvent mouseEvent) {
            MusicController.buttonClick();

            if (item.hasBeenNegotiated()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("NEGOTIATION FAILED");
                alert.setContentText("You have already negotiated this item once before. No "
                        + "second chances trader.");
                return;
            }

            boolean didNegotiate = trader.negotiate(player, item);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (!didNegotiate) {
                alert.setHeaderText("NEGOTIATION FAILED");
                alert.setContentText(trader.getBadComplyText());
            } else {
                alert.setHeaderText("NEGOTIATION SUCCESSFUL");
                alert.setContentText(trader.getGoodComplyText());
            }
            item.setHasBeenNegotiated(true);
            alert.showAndWait();
            renderItems();
        }

    }
}
