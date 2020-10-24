package screens;

import config.AppState;
import config.ConfigParser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import uimanager.UIManager;

import java.util.ArrayList;

/** Responsible for SettingsScreen logic */
public class SettingsScreenController {
    @FXML
    public Button ReturnBtn;
    @FXML
    public ComboBox<String> gameModes;      //TO DO change black text to white

    public void initialize() {
        ConfigParser cp = ConfigParser.getInstance();
        ArrayList<String> modes = cp.getAvailableGameModeNames();
        ObservableList<String> elements = FXCollections.observableArrayList();
        elements.addAll(modes);
        gameModes.setItems(elements);
        gameModes.setStyle("-fx-text-fill: white");
        gameModes.getSelectionModel().selectFirst();
        gameModes.onActionProperty();
    }

    public void ReturnBtnOnClick() {
        UIManager.getInstance().setMainScreen();
    }

    public void comboAction(ActionEvent actionEvent) {
        AppState.getInstance().setGameMode(ConfigParser.getInstance().getGameMode(gameModes.getValue()));
    }
}

